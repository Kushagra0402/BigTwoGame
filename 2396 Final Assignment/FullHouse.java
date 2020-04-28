/**
 * The FullHouse class is a sub-class of Hand class.It checks whether the hand forms a FullHouse or not and 
 * returns the type of hand.
 * @author kushkan
 */
public class FullHouse extends Hand {
	/**
  * FullHouse Constructor
  * builds a hand with specified player and list of cards.
  * @param player A parameter
  * the current active player who plays a hand.
  * @param cards A parameter
  * the list of cards played by the current player.
  */
 FullHouse(CardGamePlayer player, CardList cards)
	{
		super(player,cards);
	}
	/**
  * Method getType
  *  Returns the type of Hand.(Here, FullHouse)
  * @return The return value
  * Returns the type of Hand.(Here, FullHouse)
  */  
 public String getType()
	{
		return "FullHouse";
	}
 /**
     * Method isValid
     * Checks whether this hand is FullHouse or not.
     * @return The return value
     * Returns true if the current Hand is FullHouse otherwise returns false.
     */
    public boolean isValid()
	{
		if(this.size()==5)
		{
			this.sort();
			if(this.getCard(0).getRank()==this.getCard(1).getRank()&&this.getCard(3).getRank()==this.getCard(4).getRank())
			{
				if(this.getCard(1).getRank()==this.getCard(2).getRank()||this.getCard(2).getRank()==this.getCard(3).getRank())
					return true;
				else
					return false;
			}
			else
				return false;
		}
		else
			return false;
	}
}
