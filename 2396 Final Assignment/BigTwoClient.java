import javax.swing.*;
import java.net.Socket;
import java.util.ArrayList;
import java.io.*;
/**
 *The BigTwoClient class is used to model a Big Two card game. It has private instance variables for storing a deck of cards,a list of players, a list of hands played on the table, an index of the current player, and a
 *console for providing the user interface.
 *
 *@author kushkan
 */

public class BigTwoClient implements CardGame, NetworkGame {
    private Deck deck;
    private ArrayList<CardGamePlayer> playerList;
    private ArrayList<Hand> handsOnTable;
    private int currentIdx;
    private BigTwoTable table;
    private int playerID;
    private String playerName;
    private String serverIP = "127.0.0.1";
    private int serverPort = 2396;
    private Socket sock;
    private ObjectOutputStream oos;
    private boolean isconnected = false;

    /**
     * A variable for checking whether the game has restarted or not.
     */
    int flag;
    /**
     * A variable that makes sure that player select equal number of cards as the cards played in the last
     * hand on table.
     */
    int copylength;

    /**
     * BigTwoClient Constructor- constructor for creating a Big Two card game. 4 players are created and added to the playerlist. A â€˜consoleâ€™ (i.e., a BigTwoConsole object) also has been created to provide the user interface.
     */
    public BigTwoClient() {
        String tempstr=JOptionPane.showInputDialog("Enter your name:");
        while(tempstr==null) {
          tempstr = JOptionPane.showInputDialog("Enter your name:");
        }
        playerName=tempstr;
        playerList = new ArrayList<CardGamePlayer>();
        handsOnTable = new ArrayList<Hand>();
        CardGamePlayer player1 = new CardGamePlayer();
        CardGamePlayer player2 = new CardGamePlayer();
        CardGamePlayer player3 = new CardGamePlayer();
        CardGamePlayer player4 = new CardGamePlayer();
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player4);
        table = new BigTwoTable(this);
        makeConnection();
        flag = 0;
        copylength = 0;
    }
   /* public boolean checkconnected()
    {
        return isconnected;
    }*/

    public int getPlayerID() {
        return playerID;
    }

    /**
     * @inheritDoc
     */
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    /**
     * @inheritDoc
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * @inheritDoc
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * @inheritDoc
     */
    public String getServerIP() {
        return serverIP;
    }

    /**
     * @inheritDoc
     */
    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    /**
     * @inheritDoc
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * @inheritDoc
     */
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * @inheritDoc
     */
    public void makeConnection() {
        // sets up the network connection
        try {
                sock = new Socket(serverIP, serverPort);
                    oos = new ObjectOutputStream(sock.getOutputStream());
                    Thread readerThread = new Thread(new ServerHandler());
                    readerThread.start();
                    CardGameMessage sender1 = new CardGameMessage(CardGameMessage.JOIN, -1, playerName);
                    sendMessage(sender1);
                    CardGameMessage sender2 = new CardGameMessage(CardGameMessage.READY, -1, null);
                    sendMessage(sender2);

            }
        catch (Exception ex) {
            ex.printStackTrace();
        }



    }

    /**
     * @inheritDoc
     */
    //    int c = 0;

    public void parseMessage(GameMessage message) {
        if (message.getType() == 0) {
            setPlayerID(message.getPlayerID());
            String[] str = (String[]) message.getData();
            for (int i = 0; i < str.length; i++) {
                if (str[i] != null) {
                    playerList.get(i).setName(str[i]);
                }
            }
        } else if (message.getType() == CardGameMessage.JOIN) {
            playerList.get(message.getPlayerID()).setName((String) (message.getData()));

        } else if (message.getType() == 2) {
            table.printMsg("Server full: cannot add more player");
        } else if (message.getType() == CardGameMessage.QUIT) {
           String str=playerList.get(message.getPlayerID()).getName();
            playerList.get(message.getPlayerID()).setName("");

            if (!endOfGame()) {
                table.disable();
                sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
            }
            table.printMsg((String) message.getData() +" " +str+ " left the game.");
        } else if (message.getType() == 4) {
            table.printMsg(playerList.get(message.getPlayerID()).getName() + " is ready");
            table.repaint();
          table.connect.setEnabled(false);

        } else if (message.getType() == 5) {
            start((Deck) (message.getData()));
        } else if (message.getType() == 6) {
            checkMove(message.getPlayerID(), (int[]) message.getData());
        } else if (message.getType() == 7) {
            table.printmsg2((String) (message.getData()));
        }
    }

    /**
     * @inheritDoc
     */
    public void sendMessage(GameMessage message) {
        try {
            oos.writeObject(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * an inner class that implements the Runnable interface.
     */
    class ServerHandler implements Runnable {
        /**
         * the run() method from the Runnable interface  creates a thread
         * with an instance of the inner class as its job in the makeConnection() method from the
         * NetworkGame interface for receiving messages from the game server
         */
        public void run() {
            CardGameMessage incoming;
            try {
                ObjectInputStream incomingstream = new ObjectInputStream((sock.getInputStream()));
                incoming = (CardGameMessage) incomingstream.readObject();
                while (incoming != null) {
                    parseMessage(incoming);
                    incoming = (CardGameMessage) incomingstream.readObject();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * @inheritDoc
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * @inheritDoc
     */
    public ArrayList<CardGamePlayer> getPlayerList() {
        return playerList;
    }

    /**
     * @inheritDoc
     */
    public ArrayList<Hand> getHandsOnTable() {
        return handsOnTable;
    }

    /**
     * @inheritDoc
     */
    public int getCurrentIdx() {
        return currentIdx;
    }

    /**
     * @inheritDoc
     */
    public void start(Deck deck) {
        flag = 0;
        table.enable();
        this.deck = deck;
        handsOnTable = new ArrayList<Hand>();
        int counter = 0;
        CardGamePlayer cd = new CardGamePlayer();
        //int copylength=0;
        for (int i = 0; i < 4; i++) // Assigns 13 cards to all 4 players each
        {
            playerList.get(i).removeAllCards();
            for (int j = 0; j < 13; j++) {
                playerList.get(i).addCard(deck.getCard(counter));
                counter++;
            }
            playerList.get(i).sortCardsInHand();// Sorts the cards
        }
        for (int i = 0; i < 4; i++) // Sets the player with 3 of diamonds as current player
        {
            for (int j = 0; j < 13; j++) {
                if ((playerList.get(i).getCardsInHand().getCard(j).getRank() == 2) && (playerList.get(i).getCardsInHand().getCard(j).getSuit() == 0)) {
                    //playerID=i;
                    table.setActivePlayer(i);
                    table.printMsg(playerList.get(i).getName() + "'s turn:");
                    cd = playerList.get(i);
                    break;

                }
            }
        }

        table.repaint();
    }

    /**
     * @inheritDoc
     */
    public void checkMove(int playerID, int cardsIdx[]) {
        CardList tempcard = new CardList();
        if (flag == 0) {
            if (cardsIdx == null)//A player cannot pass in his first chance.
            {
                flag = 0;
                table.printMsg("Pass<==Not a legal move!!!");
                if (!endOfGame())
                    table.printMsg(playerList.get(playerID).getName() + "'s turn:");

            } else if (cardsIdx[0] == 20) // A player cannot press play without selecting cards.
            {

            } else if (cardsIdx != null && cardsIdx[0] != 20) {
                for (int i = 0; i < cardsIdx.length; i++)//Cards are stored in the temporary cardlist
                {
                    tempcard.addCard(playerList.get(playerID).getCardsInHand().getCard(cardsIdx[i]));
                }
                BigTwoCard cardone = new BigTwoCard(0, 2);
                int test = 0;
                if (composeHand(playerList.get(playerID), tempcard) == null || tempcard.contains(cardone) == false)// Checking the first move
                {
                    flag = 0;
                    table.printMsg("Not a legal move!!! " + tempcard.toString());
                    if (!endOfGame())
                        table.printMsg(playerList.get(playerID).getName() + "'s turn:");
                    table.resetSelected();
                    table.repaint();
                    test = 1;
                }
                if (composeHand(playerList.get(playerID), tempcard) != null && test == 0)// Valid Hand
                {
                    flag = 1;
                    copylength = cardsIdx.length;
                    playerList.get(playerID).removeCards(tempcard);
                    table.printMsg("{" + BigTwoClient.composeHand(playerList.get(playerID), tempcard).getType() + "}" + " " + BigTwoClient.composeHand(playerList.get(playerID), tempcard).toString());
                    handsOnTable.add(composeHand(playerList.get(playerID), tempcard));// Adding this legal Hand on the table

                    playerID++;// Setting next player as active player
                    if (playerID > 3) {
                        playerID = 0;
                    }

                    table.setActivePlayer(playerID);
                    table.printMsg(playerList.get(playerID).getName() + "'s turn:");
                    table.resetSelected();
                    table.repaint();
                }
            }
        } else if (flag == 1) {
            if (!endOfGame())// After the first turn till the end of the game.
            {
                if (cardsIdx == null) {
                    if (handsOnTable.get(handsOnTable.size() - 1).getPlayer() != playerList.get(playerID))// Condition of pass
                    {

                        table.printMsg("{Pass}");
                        playerID++;
                        if (playerID > 3) {
                            playerID = 0;
                        }
                        table.setActivePlayer(playerID);
                        if (!endOfGame())
                            table.printMsg(playerList.get(playerID).getName() + "'s turn:");
                        table.resetSelected();
                        table.repaint();
                    } else if (handsOnTable.get(handsOnTable.size() - 1).getPlayer() == playerList.get(playerID))// A player cannot pass after 3 passes.
                    {
                        table.printMsg("Pass<==Not a legal move!!!");
                        table.printMsg(playerList.get(playerID).getName() + "'s turn:");


                    }
                } else if (cardsIdx[0] == 20) {

                }
                if (cardsIdx != null && cardsIdx[0] != 20) {
                    for (int i = 0; i < cardsIdx.length; i++) {
                        tempcard.addCard(playerList.get(playerID).getCardsInHand().getCard(cardsIdx[i]));
                    }

                    if (composeHand(playerList.get(playerID), tempcard) == null)// Invalid Hand
                    {
                        table.printMsg("Not a legal move!!! " + tempcard.toString());
                        table.resetSelected();
                        table.repaint();
                    }
                    if (composeHand(playerList.get(playerID), tempcard) != null)//Valid hand
                    {
                        if ((handsOnTable.get(handsOnTable.size() - 1).getPlayer() == playerList.get(playerID) || composeHand(playerList.get(playerID), tempcard).beats(handsOnTable.get(handsOnTable.size() - 1)))) {
                            if (handsOnTable.get(handsOnTable.size() - 1).getPlayer() == playerList.get(playerID))//Only if 3 passes are there, the player can change the number of cards in  a hand
                                copylength = tempcard.size();// Stores the valid number of cards in each hand
                            if (tempcard.size() == copylength) {
                                playerList.get(playerID).removeCards(tempcard);
                                table.printMsg("{" + BigTwoClient.composeHand(playerList.get(playerID), tempcard).getType() + "}" + " " + BigTwoClient.composeHand(playerList.get(playerID), tempcard).toString());
                                //  table.printMsg(BigTwoClient.composeHand(playerList.get(playerID), tempcard).toString());
                                handsOnTable.add(composeHand(playerList.get(playerID), tempcard));
                                playerID++;
                                if (playerID > 3)
                                    playerID = 0;
                            } else {
                                table.printMsg("Not a legal move!!! <== {" + BigTwoClient.composeHand(playerList.get(playerID), tempcard).getType() + "} " + BigTwoClient.composeHand(playerList.get(playerID), tempcard).toString());

                            }
                        } else if (handsOnTable.get(handsOnTable.size() - 1).getPlayer() != playerList.get(playerID) && composeHand(playerList.get(playerID), tempcard).beats(handsOnTable.get(handsOnTable.size() - 1)) == false) {
                            table.printMsg("Not a legal move!!! <== {" + BigTwoClient.composeHand(playerList.get(playerID), tempcard).getType() + "} " + BigTwoClient.composeHand(playerList.get(playerID), tempcard).toString());
                        }
                    }


                    table.setActivePlayer(playerID);
                    if (!endOfGame())
                        table.printMsg(playerList.get(playerID).getName() + "'s turn:");
                    table.resetSelected();
                    table.repaint();
                }
            }

            tempcard.removeAllCards();

            if (endOfGame())//End of Game
            {
                table.setActivePlayer(-1);
                //table.printMsg("Game ends");
                String message_dialogue = "";
                for (int i = 0; i < playerList.size(); i++) {
                    if (playerList.get(i).getCardsInHand().isEmpty()) {
                        message_dialogue = message_dialogue + playerList.get(i).getName() + " wins the game.\n";
                    } else {
                        message_dialogue = message_dialogue + playerList.get(i).getName() + " has " + playerList.get(i).getCardsInHand().size() + " cards in hand.\n";
                    }
                }
                table.disable();//disabling all the buttons on the table.

                int click = JOptionPane.showOptionDialog(null, message_dialogue, "Game ends:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if (click == JOptionPane.OK_OPTION) {
                    sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
                } else {
                    System.exit(0);
                }
            }
        }
    }

    /**
     * @inheritDoc
     */
    public void makeMove(int playerID, int cardsIdx[]) {
        CardGameMessage ob = new CardGameMessage(CardGameMessage.MOVE, playerID, cardsIdx);
        sendMessage(ob);
        //checkMove(playerID,cardsIdx);
    }

    /**
     * Method main
     * This method starts the BigTwoClient game.It creates a game, creates and shuffles a deck of cards and starts
     * the game.
     *
     * @param args A parameter
     *             Not used.
     */
    public static void main(String[] args) {
        BigTwoClient game = new BigTwoClient();
        /*Deck de=new BigTwoDeck();
        de.shuffle();
        game.start(de);*/
    }


    /**
     * Method composeHand
     * It checks whether a hand played by a player is valid and returns the valid hand.
     * It returns null for an invalid hand
     *
     * @param player A parameter
     *               The current player whose hand is to be checked.
     * @param cards  A parameter
     *               The cards contained in the hand played by the player.
     * @return The return value
     * Returns the hand if it is valid and null if it is invalid.
     */
    public static Hand composeHand(CardGamePlayer player, CardList cards) {
        Hand[] hand = new Hand[8];
        hand[0] = new Single(player, cards);
        hand[1] = new Pair(player, cards);
        hand[2] = new Triple(player, cards);
        hand[3] = new Straight(player, cards);
        hand[4] = new Flush(player, cards);
        hand[5] = new FullHouse(player, cards);
        hand[6] = new Quad(player, cards);
        hand[7] = new StraightFlush(player, cards);
        for (int i = 7; i >= 0; i--) {
            if (hand[i].isValid() == true) {
                return hand[i];
            }
        }
        return null;

    }

    /**
     * @inheritDoc
     */
    public int getNumOfPlayers() {
        return 4;
    }

    /**
     * @inheritDoc
     */
    public boolean endOfGame() {
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).getCardsInHand().size() == 0) {
                return true;
            }

        }
        return false;
    }
}



