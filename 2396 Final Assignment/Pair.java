/**
 * The Pair class is a sub-class of Hand class.It checks whether the hand is Pair or not and 
 * returns the type of hand.
 * @author kushkan
 */
public class Pair extends Hand {
	
	/**
  * Pair Constructor
  * builds a hand with specified player and list of cards.
  * @param player A parameter
  * the current active player who plays a hand.
  * @param cards A parameter
  * the list of cards played by the current player.
  */
 Pair(CardGamePlayer player, CardList cards)
	{
		super(player,cards);
	}
	/**
  * Method getType
  * Returns the type of Hand.(Here, Pair)
  * @return The return value
  * returns the type of Hand.(Here, Pair)
  */
 public String getType()
	{
		return "Pair";
	}
	/**
  * Method isValid
  * Checks whether this hand is Pair or not.
  *@return The return value
  * Returns true if the current Hand is Pair otherwise returns false.
  */
 public boolean isValid()
	{
		if(this.size()==2 && this.getCard(0).getRank()==this.getCard(1).getRank())
			return true;
		else
			return false;
	}
}