/**
 * The  Flush class is a sub-class of Hand class.It checks whether the hand forms a Flush or not and 
 * returns the type of hand.
 * @author kushkan
 */
public class Flush extends Hand {
    /**
     * Flush Constructor
     * builds a hand with specified player and list of cards.
     * @param player A parameter
     * the current active player who plays a hand.
     * @param cards A parameter
     * the list of cards played by the current player.
     */
    Flush(CardGamePlayer player, CardList cards)
    {
        super(player,cards);
    }
    /**
     * Method getType
     * Returns the type of Hand.(Here, Flush)
     * @return The return value
     * Returns the type of Hand.(Here, Flush)
     */
    public String getType()
    {
        return "Flush";
    }
    /**
     * Method isValid
     * Checks whether this hand forms a Flush or not.
     * @return The return value
     * Returns true if the current Hand forms a Flush otherwise returns false.
     */
    public boolean isValid()
    {
        if(this.size()==5 && this.getCard(0).getSuit()==this.getCard(1).getSuit() && this.getCard(1).getSuit()==this.getCard(2).getSuit()&& this.getCard(2).getSuit()==this.getCard(3).getSuit()&& this.getCard(3).getSuit()==this.getCard(4).getSuit())
            return true;
        else
            return false;
    }
}
