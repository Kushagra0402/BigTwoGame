/**
 * The BigTwoCard class is the sub-class of Card class which basically overrides the methods of Card class and
 * makes necessary changes or the BigTwo game such as in the ordering of cards.
 * @author kushkan
 */
public class BigTwoCard extends Card {
    /**
     * BigTwoCard Constructor
     * Constructor for building a card with specified rank and suit.
     *
     * @param suit A parameter
     * It is the suit provided for building a card.
     * @param rank A parameter
     * The rank of the card created.
     */
    public BigTwoCard(int suit, int rank)
    {
        super(suit,rank);
    }
    /**
     * Method compareTo
     * It compares the card with a given card according to the rank and if rank is same, then suit is returned.
     * @param card A parameter
     * The card with which the current card is compared.
     * @return The return value
     * returns true if the current card is higher than the given card otherwise returns false. 
     */
    public int compareTo(Card card)
    {
        int r=rank;int r1=card.rank;
        if(rank==0)
            r=13;
        if (rank==1)
            r=14;
        if(r1==0)
            r1=13;
        if (r1==1)
            r1=14;
               
        if (r> r1) {
            return 1;
        } else if (r< r1) {
            return -1;
        } else if (suit > card.suit) {
            return 1;
        } else if (suit < card.suit) {
            return -1;
        } else {
            return 0;
        }
    }
}
