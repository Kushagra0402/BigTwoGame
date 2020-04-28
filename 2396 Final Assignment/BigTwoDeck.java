/**
 * The BigTwoDeck class is a subclass of the Deck class, and is used to model a deck of cards used in a Big Two card game.
 * It basically overrides the initialize() method of Deck class.
 * @author kushkan
 */public class BigTwoDeck extends Deck {
   
    /**
     * BigTwoDeck Constructor
     *  Calls the constructor of the Deck class.
     */
    BigTwoDeck()
    {
        super();
    }
    /**
     * Method initialize
     * Overrides the method of the Deck class and and initializes the deck of 52 BigTwo cards.
     */
    public void initialize() {
        removeAllCards();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                BigTwoCard card = new BigTwoCard(i, j);
                addCard(card);
            }
        }
    }
}
