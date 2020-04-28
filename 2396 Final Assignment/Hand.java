/**
 * The Hand class is a sub-class of Cardlist class which performs operations on a sepecified hand of a player like
 * returning the top card of the Hand and checking whether the hand beats a given hand or not.
 * @author kushkan
 */
public abstract class Hand extends CardList {
	CardGamePlayer player;
	/**
  * Hand Constructor
  * builds a hand with specified player and list of cards.
  * @param player A parameter
  * the current active player who plays a hand.
  * @param cards A parameter
  * the list of cards played by the current player
  */
 public Hand(CardGamePlayer player, CardList cards)
	{
		this.player=player;
		for(int m=0;m<cards.size();m++)
		{
			addCard(cards.getCard(m));
		}
	}
	
	/**
  * Method getPlayer
  * returns the current active player who plays the hand.
  * @return The return value
  * reuturns the player who plays a hand.
  */
 public CardGamePlayer getPlayer()
	{
		return this.player;
	}
	/**
  * Method getTopCard
  * This method retrieves the top card of a hand.
  * @return The return value
  * Returns the top card of the hand played.
  */
 public Card getTopCard()
	{
		if(getType()!="FullHouse"&&getType()!="Quad")
		{
			this.sort();
			return this.getCard(this.size()-1);
		}
		else if(getType()=="FullHouse")
		{
			this.sort();
			Card cr=this.getCard(2);
			
			for(int i=0;i<this.size();i++)
			{
				if(this.getCard(i).getRank()==this.getCard(2).getRank())
				{
					if(this.getCard(i).getSuit()>cr.getSuit())
					{
						cr=this.getCard(i);
					}
				}
			}
			return cr;
		}
		else
		{
			this.sort();
			Card cr=this.getCard(1);
			for(int i=0;i<this.size();i++)
			{
				if(this.getCard(i).getRank()==this.getCard(1).getRank()&&this.getCard(i).getSuit()==3)
				{
					cr=this.getCard(i);
				}
			}
			return cr;
		}
	}
	
	/**
  * Method beats
  * Checks whether the hand played by the player beats the current hand or not.
  *
  * @param hand A parameter
  * The hand to which the current hand is compared.
  * @return The return value
  * Returns true if the current hand beats the specified hand otherwise returns false.
  */
 public boolean beats(Hand hand)
	{
		if(this.getType()==hand.getType())
        {
			if(this.getType()=="Flush")
			{
				if(this.getCard(0).getSuit()>hand.getCard(0).getSuit())
					return true;
				else if(this.getCard(0).getSuit()==hand.getCard(0).getSuit())
					{
					   if(this.getTopCard().compareTo(hand.getTopCard())>0)
                         return true;
                       else 
                         return false;
                     }
                       else
                         return false;
                        }
			else if(this.getTopCard().compareTo(hand.getTopCard())>0)
            return true;
            else 
            return false;
        }
        else if(this.getType()=="StraightFlush")
        return true;
        else if (hand.getType()=="StraightFlush")
        return false;
        else if (this.getType()=="Quad")
        return true;
        else if (hand.getType()=="Quad")
        return false;
        else if (this.getType()=="FullHouse")
         return true;
        else if (hand.getType()=="FullHouse")
         return false;
        else if (this.getType()=="Flush")
         return true;
         else if (hand.getType()=="Flush") 
         return false;
         else if (this.getType()=="Straight")
          return true;
         else if (hand.getType()=="Straight")
           return false;
         else if (this.getType()=="Triple")  
           return true;
         else if (hand.getType()=="Triple")
           return false;
         else if (this.getType()=="Pair")
            return true;
         else if (hand.getType()=="Pair")
           return false;
          else if (this.getType()=="Single")
          return true;
          else if (hand.getType()=="Single")
          return false;
          else
          return false;
	}
	
	 /**
   * Method isValid
   * a method for checking if this is a valid hand.
   * @return The return value
   * returns true if the hand is valid otherwise false.
   */
  public abstract boolean isValid();
  
     /**
      * Method getType
      *a method for returning a string specifying the type of this hand.
      * @return The return value
      * returns the type of hand.
      */
     public abstract String getType();
}
