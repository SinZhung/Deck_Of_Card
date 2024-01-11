import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeckOfCards{
    private static List<Card> cards = new ArrayList<Card>();

    public List<Card> getDeckOfCards(){
        String[] cardSuit = {"d", "c", "h", "s"};
		String[] cardRank = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "X", "J", "Q", "K"};
        for (String suit: cardSuit){
            for (String rank: cardRank){
                Card card = new Card(suit,rank);
                cards.add(card);
            }
        }
        return cards;
    }
    public List<Card> shuffleCards(List<Card> cards){
        Collections.shuffle(cards);
        return cards;
    }

    public void printCards(List<Card> cards) {
        for (Card c: cards) {
            System.out.print(c.getCardSuit() + c.getCardRank() + " ");
        }
        System.out.println();
    }

}