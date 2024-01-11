public class Game extends Player{
	private int playerScore;

	public Game(){}
	public Game(String playerName, int playerScore){
		super(playerName);
		this.playerScore = playerScore;
	}
	public String getPlayerName(){
		return playerName;
	}
	public int getPlayerScore(){
		return playerScore;
	}
	public void setPlayerScore(int playerScore){
		this.playerScore = playerScore;
	}
	public String toString(){
		return playerName + "," + playerScore;
	}
}