public class Card{
	private String cardSuit;
	private String cardRank;

	public Card(){};
	public Card(String cardSuit, String cardRank)
	{
		this.cardSuit = cardSuit;
		this.cardRank = cardRank;
	}
	public String getCardSuit(){
		return cardSuit;
	}
	public String getCardRank(){
		return cardRank;
	}
	public void setCardSuit(String cardSuit){
		this.cardSuit = cardSuit;
	}
	public void setCardRank(String cardRank){
		this.cardRank = cardRank;
	}

} 	