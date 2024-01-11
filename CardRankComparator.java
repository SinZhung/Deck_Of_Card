import java.util.Comparator;

public class CardRankComparator implements Comparator<Card> {
    public int compare(Card a, Card b){
        return a.getCardRank().compareTo(b.getCardRank());
    }
}