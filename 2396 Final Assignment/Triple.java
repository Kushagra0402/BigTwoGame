/**
 * The Triple class is a sub-class of Hand class.It checks whether the hand is Triple or not and 
 * returns the type of hand.
 * @author kushkan
 */
public class Triple extends Hand
 {
	/**
  * Triple Constructor
  * builds a hand with specified player and list of cards.
  * @param player A parameter
  * the current active player who plays a hand.
  * @param cards A parameter
  * the list of cards played by the current player.
  */
 Triple(CardGamePlayer player, CardList cards)
	{
		super(player,cards);
	}
	/**
  * Method getType
  * Returns the type of Hand.(Here, Triple)
  * @return The return value
  * returns the type of Hand.(Here, Triple)
  */
 public String getType()
	{
		return "Triple";
	}
	
	/**
  * Method isValid
  * Checks whether this hand is Triple or not.
  * @return The return value
  * Returns true if the current Hand is Triple otherwise returns false.
  */
 public boolean isValid()
	{
		if(this.size()==3 && this.getCard(0).getRank()==this.getCard(1).getRank() && this.getCard(2).getRank()==this.getCard(1).getRank())
			return true;
		else
			return false;
	}
}