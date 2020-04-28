/**
 * The Quad class is a sub-class of Hand class.It checks whether the hand forms a Quad or not and 
 * returns the type of hand.
 * @author kushkan
 */
public class Quad extends Hand 
{
	 /**
     * Quad Constructor
     * builds a hand with specified player and list of cards.
     * @param player A parameter
     *  the current active player who plays a hand.
     * @param cards A parameter
     *  the list of cards played by the current player.
     */
    Quad(CardGamePlayer player, CardList cards)
	{
		super(player,cards);
	}
	/**
  * Method getType
  * Returns the type of Hand.(Here, Quad)
  * @return The return value
  * Returns the type of Hand.(Here, Quad)
  */
 public String getType()
	{
		return "Quad";
	}
	/**
  * Method isValid
  * Checks whether this hand forms a Quad or not.
  * @return The return value
  * Checks whether this hand forms a Quad or not.
  */
 public boolean isValid()
	{
		if(this.size()==5)
		{
			this.sort();
			if(this.getCard(0).getRank()==this.getCard(1).getRank() && this.getCard(1).getRank()==this.getCard(2).getRank()&& this.getCard(2).getRank()==this.getCard(3).getRank())
				return true;
			else if(this.getCard(4).getRank()==this.getCard(1).getRank() && this.getCard(1).getRank()==this.getCard(2).getRank()&& this.getCard(2).getRank()==this.getCard(3).getRank())
				return true;
			else
				return false;
		}
		else
			return false;
	}
}
