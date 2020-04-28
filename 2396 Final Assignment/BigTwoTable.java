import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * The BigTwoTable class implements the CardGameTable interface. It is used to build a GUI for the Big Two
 * card game and handle all user actions.
 *
 * @author kushkan
 */
public class BigTwoTable implements CardGameTable {
    private CardGame game;
    private boolean[] selected;
    private int activePlayer;
    private JFrame frame;
    private JPanel bigTwoPanel;
    private JPanel panel2;
    private JButton playButton;
    private JButton passButton;
    private JTextArea msgArea;
    private JTextArea msgArea2;
    private Image[][] cardImages = new Image[4][13];
    private Image cardBackImage;
    private Image[] avatars = new Image[4];
    private JTextField enter;
    /**
     * An array for checking if the face of the card is being displayed or not.
     */
    boolean frontshow[] = new boolean[4];
    /**
     * implementing the "connect" button in the menu.
     */
    JMenuItem connect;
    /**
     * BigTwoTable Constructor
     * A constructor for creating a BigTwoTable.
     *
     * @param game A parameter
     *             It is a reference to a card game which associates with this table.
     */
    public BigTwoTable(CardGame game) {

        this.game = game;
        selected = new boolean[13];
        resetSelected();
        frame = new JFrame();
        bigTwoPanel = new BigTwoPanel();
        panel2 = new JPanel();
        msgArea = new JTextArea();
        msgArea2 = new JTextArea();
        JScrollPane scrollableText = new JScrollPane(msgArea);
        scrollableText.setPreferredSize(new Dimension(500, 400));
        JScrollPane scrollableText2 = new JScrollPane(msgArea2);
        playButton = new JButton("Play");
        passButton = new JButton("Pass");
        enter = new JTextField();
        JLabel label = new JLabel("Message:");
        frame.setLayout(new BorderLayout());
        bigTwoPanel.setPreferredSize(new Dimension(500, 900));
        //panel2.setPreferredSize(new Dimension(500,1000));
        //msgArea.setPreferredSize(new Dimension(500,400));
        //msgArea2.setPreferredSize(new Dimension(500,400));
        enter.setPreferredSize(new Dimension(300, 50));
        bigTwoPanel.addMouseListener(new BigTwoPanel());
        playButton.addActionListener(new PlayButtonListener());
        passButton.addActionListener(new PassButtonListener());
        enter.addActionListener(new EnterButtonListener());
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        // JMenuItem restartitem=new JMenuItem("Restart");
        JMenuItem quititem = new JMenuItem("Quit");
         connect = new JMenuItem("Connect");
        //restartitem.addActionListener(new RestartMenuItemListener());
        quititem.addActionListener(new QuitMenuItemListener());
        connect.addActionListener(new ConnectListener());
        // menu.add(restartitem);
        menu.add(quititem);
        menu.add(connect);
        menuBar.add(menu);
        bigTwoPanel.add(playButton);
        bigTwoPanel.add(passButton);
        panel2.setLayout(new BorderLayout());
        panel2.add(scrollableText, BorderLayout.NORTH);
        panel2.add(scrollableText2, BorderLayout.CENTER);
        JPanel dummyPanel = new JPanel();
        dummyPanel.add(label);
        dummyPanel.add(enter);

        //panel2.add(label,BorderLayout.SOUTH);
        panel2.add(dummyPanel, BorderLayout.SOUTH);
        bigTwoPanel.setBackground(Color.GREEN);
        msgArea.setBackground(Color.WHITE);
        msgArea2.setBackground(Color.WHITE);
        frame.add(bigTwoPanel, BorderLayout.WEST);
        frame.add(panel2, BorderLayout.CENTER);
        frame.add(menuBar, BorderLayout.NORTH);
        frame.setSize(1000, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        String str = "", str3 = "";
        avatars[0] = new ImageIcon("png/64/batman_64.png").getImage();
        avatars[1] = new ImageIcon("png/64/flash_64.png").getImage();
        avatars[2] = new ImageIcon("png/64/superman_64.png").getImage();
        avatars[3] = new ImageIcon("png/64/green_lantern_64.png").getImage();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                if (i == 0)
                    str = "d";
                if (i == 1)
                    str = "c";
                if (i == 2)
                    str = "h";
                if (i == 3)
                    str = "s";
                str3 = ("cards/" + Integer.toString(j) + str + ".gif");
                cardImages[i][j] = new ImageIcon(str3).getImage();

            }
        }
        cardBackImage = new ImageIcon("cards/b.gif").getImage();
    }


    /**
     * @inheritDoc
     */
    public void setActivePlayer(int activePlayer) {
        this.activePlayer = activePlayer;
    }

    /**
     * @inheritDoc
     */
    public int[] getSelected() {
        int[] cardIdx = {20};
        int count = 0;
        for (int j = 0; j < selected.length; j++) {
            if (selected[j]) {
                count++;
            }
        }

        if (count != 0) {
            cardIdx = new int[count];
            count = 0;
            for (int j = 0; j < selected.length; j++) {
                if (selected[j]) {
                    cardIdx[count] = j;
                    count++;
                }
            }
        }
        return cardIdx;
    }


    /**
     * @inheritDoc
     */
    public void resetSelected() {
        for (int i = 0; i < selected.length; i++) {
            selected[i] = false;
        }
    }

    /**
     * @inheritDoc
     */
    public void repaint() {
        bigTwoPanel.revalidate();
        bigTwoPanel.repaint();
        frame.setVisible(true);
    }


    /**
     * @inheritDoc
     */
    public void printMsg(String msg) {
        msgArea.append(msg + "\n");
        msgArea.setCaretPosition(msgArea.getText().length());
    }

    /**
     * prints the chats in the box
     *
     * @param msg The message printed in the chat-box.
     */
    public void printmsg2(String msg) {
        msgArea2.append(msg + "\n");
        msgArea2.setCaretPosition(msgArea2.getText().length());
    }

    /**
     * Method clearMsgArea
     * Clears the message area.
     */
    public void clearMsgArea() {
        msgArea.setText(null);
    }

    /**
     * @inheritDoc
     */
    public void reset() {
        resetSelected();
        clearMsgArea();
        enable();
    }


    /**
     * @inheritDoc
     */
    public void enable() {
        playButton.setEnabled(true);
        passButton.setEnabled(true);
        bigTwoPanel.setEnabled(true);
    }


    /**
     * @inheritDoc
     */
    public void disable() {
        playButton.setEnabled(false);
        passButton.setEnabled(false);
        bigTwoPanel.setEnabled(false);
    }

    /**
     * An inner class that extends the JPanel class and implements the
     * MouseListener interface. Overrides the paintComponent() method inherited from the
     * JPanel class to draw the card game table. Implements the mouseClicked() method from
     * the MouseListener interface to handle mouse click events.
     */
    class BigTwoPanel extends JPanel implements MouseListener {

        /**
         * Method paintComponent
         * Overrides the paintComponent() method inherited from the
         * JPanel class to draw the card game table.
         *
         * @param g A parameter
         *          A Graphics object used for implementing the method.
         */
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(avatars[0], 3, 50, this);
            // g.drawLine(0,150,800,150);
            g.drawImage(avatars[1], 3, 200, this);
            // g.drawLine(0,350,800,350);
            g.drawImage(avatars[2], 3, 350, this);
            // g.drawLine(0,550,800,550);
            g.drawImage(avatars[3], 3, 500, this);
            // g.drawLine(0,720,800,720);
            int x = 80;
            int y = 50;

            for (int i = 0; i < 4; i++) {
                g.drawString(game.getPlayerList().get(i).getName(), 3, y - 15);
                if (i == ((BigTwoClient) game).getPlayerID()) {
                    frontshow[i] = true;
                    for (int j = 0; j < game.getPlayerList().get(i).getCardsInHand().size(); j++) {
                        x = x + 20;
                        if (selected[j] == true)
                            g.drawImage(cardImages[game.getPlayerList().get(i).getCardsInHand().getCard(j).getSuit()][game.getPlayerList().get(i).getCardsInHand().getCard(j).getRank()], x, y - 20, this);
                        else
                            g.drawImage(cardImages[game.getPlayerList().get(i).getCardsInHand().getCard(j).getSuit()][game.getPlayerList().get(i).getCardsInHand().getCard(j).getRank()], x, y, this);
                    }
                } else if (activePlayer == -1) {

                    for (int j = 0; j < game.getPlayerList().get(i).getCardsInHand().size(); j++) {
                        x = x + 20;
                        g.drawImage(cardImages[game.getPlayerList().get(i).getCardsInHand().getCard(j).getSuit()][game.getPlayerList().get(i).getCardsInHand().getCard(j).getRank()], x, y, this);
                    }
                } else {
                    frontshow[i] = false;
                    for (int j = 0; j < game.getPlayerList().get(i).getCardsInHand().size(); j++) {
                        x = x + 20;
                        g.drawImage(cardBackImage, x, y, this);
                    }
                }
                x = 80;
                y = y + 150;
                g.drawLine(0, y - 40, 10000, y - 40);
            }
            x = 40;
            Hand lastHandOnTable = (game.getHandsOnTable().isEmpty()) ? null : game.getHandsOnTable().get(game.getHandsOnTable().size() - 1);
            if (lastHandOnTable != null) {
                g.drawString("Last Hand played by " + lastHandOnTable.getPlayer().getName(), 3, y - 15);
                for (int i = 0; i < lastHandOnTable.size(); i++) {
                    x = x + 20;
                    g.drawImage(cardImages[lastHandOnTable.getCard(i).getSuit()][lastHandOnTable.getCard(i).getRank()], x, y, this);
                }
            }
        }

        /**
         * Method mouseClicked
         * Method used for handling mouse click events.
         *
         * @param event A parameter
         *              A mouse-click event.
         */
        public void mouseClicked(MouseEvent event) {
            int x = event.getX();
            int tempvar = 0;
            int tempvar1 = 0, tempvar2 = 0, tempvar3 = 0;
            int y = event.getY();
            //System.out.println(x+" "+y);
            if (activePlayer == 0 && x >= 100 && x <= (100 + ((game.getPlayerList().get(0).getCardsInHand().size() - 1) * 20) + 73) && y >= 50 && y <= 147 && frontshow[activePlayer] == true) {
                tempvar = 1;
                if (x >= (100 + ((game.getPlayerList().get(0).getCardsInHand().size() - 1) * 20))) {
                    if (selected[game.getPlayerList().get(0).getCardsInHand().size() - 1] == true) {
                        selected[game.getPlayerList().get(0).getCardsInHand().size() - 1] = false;
                    } else {
                        selected[game.getPlayerList().get(0).getCardsInHand().size() - 1] = true;
                    }
                } else {
                    if (selected[((x - 100) / 20)] == true)
                        selected[((x - 100) / 20)] = false;
                    else
                        selected[((x - 100) / 20)] = true;
                }
                BigTwoTable.this.repaint();
            }
            if (activePlayer == 0 && x >= 100 && x <= (100 + ((game.getPlayerList().get(0).getCardsInHand().size() - 1) * 20) + 73) && y >= 30 && y <= 127 && tempvar == 0 && frontshow[activePlayer] == true) {
                if (selected[game.getPlayerList().get(0).getCardsInHand().size() - 1] == true)
                    selected[game.getPlayerList().get(0).getCardsInHand().size() - 1] = false;
                else if (selected[((x - 100) / 20)] == true)
                    selected[((x - 100) / 20)] = false;
                BigTwoTable.this.repaint();
            }
            if (activePlayer == 1 && x >= 100 && x <= (100 + ((game.getPlayerList().get(1).getCardsInHand().size() - 1) * 20) + 73) && y >= 200 && y <= 297 && frontshow[activePlayer] == true) {
                tempvar1 = 1;
                if (x >= (100 + ((game.getPlayerList().get(1).getCardsInHand().size() - 1) * 20))) {
                    if (selected[game.getPlayerList().get(1).getCardsInHand().size() - 1] == true) {
                        selected[game.getPlayerList().get(1).getCardsInHand().size() - 1] = false;
                    } else {
                        selected[game.getPlayerList().get(1).getCardsInHand().size() - 1] = true;
                    }

                } else {
                    if (selected[((x - 100) / 20)] == true)
                        selected[((x - 100) / 20)] = false;
                    else
                        selected[((x - 100) / 20)] = true;
                }
                BigTwoTable.this.repaint();
            }
            if (activePlayer == 1 && x >= 100 && x <= (100 + ((game.getPlayerList().get(0).getCardsInHand().size() - 1) * 20) + 73) && y >= 180 && y <= 277 && tempvar1 == 0 && frontshow[activePlayer] == true) {
                if (selected[game.getPlayerList().get(1).getCardsInHand().size() - 1] == true)
                    selected[game.getPlayerList().get(1).getCardsInHand().size() - 1] = false;
                else if (selected[((x - 100) / 20)] == true)
                    selected[((x - 100) / 20)] = false;
                BigTwoTable.this.repaint();
            }
            if (activePlayer == 2 && x >= 100 && x <= (100 + ((game.getPlayerList().get(2).getCardsInHand().size() - 1) * 20) + 73) && y >= 350 && y <= 447 && frontshow[activePlayer] == true) {
                tempvar2 = 1;
                if (x >= (100 + ((game.getPlayerList().get(2).getCardsInHand().size() - 1) * 20))) {
                    if (selected[game.getPlayerList().get(2).getCardsInHand().size() - 1] == true) {
                        selected[game.getPlayerList().get(2).getCardsInHand().size() - 1] = false;
                    } else {
                        selected[game.getPlayerList().get(2).getCardsInHand().size() - 1] = true;
                    }
                } else {
                    if (selected[((x - 100) / 20)] == true)
                        selected[((x - 100) / 20)] = false;
                    else
                        selected[((x - 100) / 20)] = true;
                }
                BigTwoTable.this.repaint();
            }
            if (activePlayer == 2 && x >= 100 && x <= (100 + ((game.getPlayerList().get(2).getCardsInHand().size() - 1) * 20) + 73) && y >= 330 && y <= 427 && tempvar2 == 0 && frontshow[activePlayer] == true) {
                if (selected[game.getPlayerList().get(2).getCardsInHand().size() - 1] == true)
                    selected[game.getPlayerList().get(2).getCardsInHand().size() - 1] = false;
                else if (selected[((x - 100) / 20)] == true)
                    selected[((x - 100) / 20)] = false;
                BigTwoTable.this.repaint();
            }
            if (activePlayer == 3 && x >= 100 && x <= (100 + ((game.getPlayerList().get(3).getCardsInHand().size() - 1) * 20) + 73) && y >= 500 && y <= 597 && frontshow[activePlayer] == true) {
                tempvar3 = 1;
                if (x >= (100 + ((game.getPlayerList().get(3).getCardsInHand().size() - 1) * 20))) {
                    if (selected[game.getPlayerList().get(3).getCardsInHand().size() - 1] == true) {
                        selected[game.getPlayerList().get(3).getCardsInHand().size() - 1] = false;
                    } else {
                        selected[game.getPlayerList().get(3).getCardsInHand().size() - 1] = true;
                    }
                } else {
                    if (selected[((x - 100) / 20)] == true)
                        selected[((x - 100) / 20)] = false;
                    else
                        selected[((x - 100) / 20)] = true;
                }
                BigTwoTable.this.repaint();
            }
            if (activePlayer == 3 && x >= 100 && x <= (100 + ((game.getPlayerList().get(2).getCardsInHand().size() - 1) * 20) + 73) && y >= 480 && y <= 577 && tempvar3 == 0 && frontshow[activePlayer] == true) {
                if (selected[game.getPlayerList().get(3).getCardsInHand().size() - 1] == true)
                    selected[game.getPlayerList().get(3).getCardsInHand().size() - 1] = false;
                else if (selected[((x - 100) / 20)] == true)
                    selected[((x - 100) / 20)] = false;
                BigTwoTable.this.repaint();
            }


        }

        /**
         * Method mousePressed
         * Not used.
         *
         * @param event A parameter
         *              Not used.
         */
        public void mousePressed(MouseEvent event) {
        }

        /**
         * Method mouseReleased
         * Not used.
         *
         * @param event A parameter
         *              Not used.
         */
        public void mouseReleased(MouseEvent event) {
        }

        /**
         * Method mouseEntered
         * Not used.
         *
         * @param event A parameter
         *              Not used.
         */
        public void mouseEntered(MouseEvent event) {
        }

        /**
         * Method mouseExited
         * Not used.
         *
         * @param event A parameter
         *              Not used.
         */
        public void mouseExited(MouseEvent event) {
        }
    }

    /**
     * an inner class that implements the ActionListener interface. Implements the actionPerformed() method
     * from the ActionListener interface to handle button-click events for the “Play” button.
     */
    class PlayButtonListener implements ActionListener {
        /**
         * Method actionPerformed
         * Method for handling Play-button click events.
         *
         * @param event A parameter
         *              A Play button-click event.
         */
        public void actionPerformed(ActionEvent event) {
            if (((BigTwoClient) game).getPlayerID() == activePlayer)
                game.makeMove(activePlayer, BigTwoTable.this.getSelected());
        }
    }

    /**
     * an inner class that implements the ActionListener interface. Implements the actionPerformed() method
     * from the ActionListener interface to handle button-click events for the “Pass” button.
     */
    class PassButtonListener implements ActionListener {
        /**
         * Method actionPerformed
         * Method for handling Pass-button click events.
         *
         * @param event A parameter
         *              A Pass-button click event.
         */
        public void actionPerformed(ActionEvent event) {
            if (((BigTwoClient) game).getPlayerID() == activePlayer)
                game.makeMove(activePlayer, null);
        }
    }
    /**
     * an inner class that implements the ActionListener interface. Implements the actionPerformed() method 
     * from the ActionListener interface to handle button-click events for the “Restart” button in the menu. 
     *
     */
   /* class RestartMenuItemListener implements ActionListener
    {
        /**
         * Method actionPerformed
         * A method for handling Restart button click events.
         * @param event A parameter
         * a Restart button click event.
         */
        /*public void actionPerformed(ActionEvent event)
        {
            //game=new BigTwoT();
            BigTwoTable.this.reset();
            Deck de=new BigTwoDeck();
             de.shuffle();
           BigTwoTable.this.game.start(de);
        }
    }*/

    /**
     * an inner class that implements the ActionListener interface. Implements the actionPerformed() method
     * from the ActionListener interface to handle button-click events for the “Quit” button in the menu.
     */
    class QuitMenuItemListener implements ActionListener {
        /**
         * A method for handling Quit button click events.
         *
         * @param event A Quit button click event.
         */
        public void actionPerformed(ActionEvent event) {
            System.exit(0);
        }
    }

    /**
     * This inner class sends the messages to the server which are typed in the text field by the client and displays it to all the clients after
     * implementing the ActionListener interface.
     */
    class EnterButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            ((BigTwoClient) game).sendMessage(new CardGameMessage(CardGameMessage.MSG, -1, enter.getText()));
            enter.setText("");
        }
    }

    /**
     * If the client has not yet established a connection to the server, and the user selects “Connect” from the menu,the client makes a connection to
     * the server using this class which implements ActionListener Interface .
     */
    class ConnectListener implements ActionListener {
        /**
         * A method for handling Connect button click events.
         *
         * @param event A Connect button click event.
         */
        public void actionPerformed(ActionEvent event) {

           // if (((BigTwoClient) game).checkconnected() == false) {
                ((BigTwoClient) game).makeConnection();
            //}

        }
    }
}
    
            