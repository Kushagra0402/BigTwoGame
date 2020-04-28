/**
 * The Single class is a sub-class of Hand class.It checks whether the hand is Single or not and 
 * returns the type of hand.
 * @author kushkan
 */
public class Single extends Hand {
	/**
  * Single Constructor
  * builds a hand with specified player and list of cards.
  * @param player A parameter
  * the current active player who plays a hand.
  * @param cards A parameter
  * the list of cards played by the current player.
  */
 Single(CardGamePlayer player, CardList cards)
	{
		super(player,cards);
	}
	/**
  * Method getType
  * Returns the type of Hand.(Here, Single)
  *@return The return value
  *returns the type of Hand.(Here, Single)
  */
 public String getType()
	{
		return "Single";
	}
	/**
  * Method isValid
  * Checks whether this hand is Single or not.
  * @return The return value
  * Returns true if the current Hand is Single otherwise returns false.
  */
 public boolean isValid()
	{
		if(this.size()==1)
			return true;
		else
			return false;
	}
}
