import java.util.Comparator;

public class CardSuitComparator implements Comparator<Card> {
    public int compare(Card a, Card b){
        return a.getCardSuit().compareTo(b.getCardSuit());
    }
}