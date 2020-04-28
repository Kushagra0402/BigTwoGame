/**
 * The Straight class is a sub-class of Hand class.It checks whether the hand forms a Straight  or not and 
 * returns the type of hand.
 * @author kushkan
 */
public class Straight extends Hand  {
	/**
  * Straight Constructor
  * builds a hand with specified player and list of cards.
  * @param player A parameter
  * the current active player who plays a hand.
  * @param cards A parameter
  * the list of cards played by the current player.
  */
 Straight(CardGamePlayer player, CardList cards)
	{
		super(player,cards);
	}
	/**
  * Method getType
  *  Returns the type of Hand.(Here, Straight)
  * @return The return value
  *  Returns the type of Hand.(Here, Straight)
  */
 public String getType()
	{
		return "Straight";
	}
 /**
  * Method xyz
  * This method basically manages the cases where cards with rank 2 or A are present and assigns them higher value.
  * @param m A parameter
  * used for index of a card in  the cardlist.
  * @return The return value
  * returns the value assigned by the function to cards to maintain the order of priority.
  */
 public int xyz(int m)
	{
			    if(this.getCard(m).getRank()==0)
			     return 13;
			    else if(this.getCard(m).getRank()==1)
			     return 14;
			     else
			     return this.getCard(m).getRank();
			 }
	/**
  * Method isValid
  * Checks whether this hand is Straight or not.
  * @return The return value
  * Returns true if the current Hand is Straight otherwise returns false.
  */
 public boolean isValid()
	{
		if(this.size()==5)
		{
			this.sort();
			for(int i=0;i<4;i++)
			{
				if(Math.abs(xyz(i)-xyz(i+1))!=1)
				{
					return false;
				}
			}
			return true;
		}
		else
			return false;
	}
}
