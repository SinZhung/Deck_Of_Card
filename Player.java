import java.util.Queue;
import java.util.Collections;
import java.util.List;

public class Player{	
	protected String playerName;

	public Player(){}
	public Player(String playerName){
		this.playerName = playerName;
	}
	public String getPlayerName(){
		return playerName;
	}

	public Queue<Card> playerCardsQueue(Queue<Card> cardsQueue, List<Card> cardsList, int start, int end){
		for (int i = start; i < end; i++)
			cardsQueue.offer(cardsList.get(i));		
		return cardsQueue;
	}
	
	public List<Card> eachRoundPlayerCardsList(List<Card> playerCardsList, Queue<Card> playerCardsQueue){
		for (int i = 0; i < 5; i++){
			playerCardsList.add(playerCardsQueue.remove());
		}
		return playerCardsList;
	}

	public void printAvailablePlayerCards(Queue<Card> playerCardsQueue){
		int num = 0;
		for (Card c: playerCardsQueue){
			System.out.print(c.getCardSuit()+c.getCardRank()+ " ");
			num++;
			if (playerCardsQueue.size() > 5){
				if (num	%5 == 0)
					System.out.print(", ");
			}
		}
		System.out.println();
	}
	public void printPlayerPlayedCards(List<Card> playerCardsList){
		for (Card c: playerCardsList){
			System.out.print(c.getCardSuit()+c.getCardRank()+ " ");
		}
	}
	public void printPlayerPoint(int playerPoints, String playerName, List<Game> playerGameDetails, List<Integer> playerPointsList){
        if (playerPoints == Collections.max(playerPointsList)) {
            System.out.println(" | Point = " + playerPoints + " | Win");
			for (Game g: playerGameDetails){
				if (g.getPlayerName() == playerName)
					g.setPlayerScore(g.getPlayerScore() + playerPoints);
			}
		}
        else
            System.out.println(" | Point = " + playerPoints);
    }
}