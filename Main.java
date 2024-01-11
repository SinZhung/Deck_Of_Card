import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args){
    Scanner input = new Scanner (System.in);
		System.out.println("=================== CARD GAME =================== ");
        threePlayerPhases(input);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void threePlayerPhases(Scanner input){
        System.out.println("------------------");
		System.out.println("| 3-Player Phase |");
		System.out.println("------------------");
        DeckOfCards d = new DeckOfCards();
        Player p = new Player();
		List<Card> cardsList = d.getDeckOfCards();
        TreeSet<Integer> playerNameLengthSet = new TreeSet<>();
        List<Game> playerGameDetails = new ArrayList<>();
        Queue<Card> player1CardsQueue = new LinkedList<>(); 
        Queue<Card> player2CardsQueue = new LinkedList<>(); 
        Queue<Card> player3CardsQueue = new LinkedList<>();

        System.out.print("Enter player 1 name: ");
		String player1 = input.nextLine();
        System.out.print("Enter player 2 name: ");
		String player2 = input.nextLine();
        System.out.print("Enter player 3 name: ");
		String player3 = input.nextLine();
        playerNameLengthSet.add(player1.length());
        playerNameLengthSet.add(player2.length());
        playerNameLengthSet.add(player3.length());
        playerGameDetails.add(new Game(player1,0));
        playerGameDetails.add(new Game(player2,0));
        playerGameDetails.add(new Game(player3,0));
        d.shuffleCards(cardsList);
        do{
            try{           
                player1CardsQueue = p.playerCardsQueue(player1CardsQueue,cardsList,0,18);
                player2CardsQueue = p.playerCardsQueue(player2CardsQueue,cardsList,18,35);
                player3CardsQueue = p.playerCardsQueue(player3CardsQueue,cardsList,35,52);
                System.out.println();
                System.out.println("[Available Cards]");         
                printPlayerAvailableCards(player1, player1CardsQueue,playerNameLengthSet);
                printPlayerAvailableCards(player2, player2CardsQueue,playerNameLengthSet);
                printPlayerAvailableCards(player3, player3CardsQueue,playerNameLengthSet);
                System.out.println();
                System.out.println("Press [S] To Shuffle or Press [ENTER] To Start.");
                String userInput = input.nextLine();
                if (userInput.toUpperCase().equals("S")){
                    player1CardsQueue.clear(); player2CardsQueue.clear(); player3CardsQueue.clear();
                    d.shuffleCards(cardsList);
                }
                else if (userInput.length() == 0){
                    for (int i = 1; i < 4; i++)
                        threePlayerRound(input, i, player1, player2, player3, cardsList, player1CardsQueue, player2CardsQueue, player3CardsQueue, playerGameDetails, playerNameLengthSet);
                    System.exit(0);
                }
                else{
                    player1CardsQueue.clear(); player2CardsQueue.clear(); player3CardsQueue.clear();
                    throw new IllegalArgumentException("Input Error: Invalid Input.");
                }
            }
            catch (IllegalArgumentException ex){
                System.out.println(ex.getMessage());
                pressEnterToContinue(input);
            }         
        } while(true);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void threePlayerRound(Scanner input, int num, String player1, String player2, String player3, List<Card> cardsList, Queue<Card> player1CardsQueue, Queue<Card> player2CardsQueue, Queue<Card> player3CardsQueue, List<Game> playerGameDetails, TreeSet<Integer> playerNameLengthSet){
        Player p = new Player();
        List<Integer> playerPointsList = new ArrayList<>();
        List<Card> eachRoundPlayer1CardsList = new ArrayList<>(); List<Card> eachRoundPlayer2CardsList = new ArrayList<>(); List<Card> eachRoundPlayer3CardsList = new ArrayList<>();
        eachRoundPlayer1CardsList = p.eachRoundPlayerCardsList(eachRoundPlayer1CardsList, player1CardsQueue); 
        eachRoundPlayer2CardsList = p.eachRoundPlayerCardsList(eachRoundPlayer2CardsList, player2CardsQueue); 
        eachRoundPlayer3CardsList = p.eachRoundPlayerCardsList(eachRoundPlayer3CardsList, player3CardsQueue); 
        String strNum = Integer.toString(num);
        System.out.println();
        System.out.println("================= Round " + strNum + " =================");
        System.out.println("[Cards At Hand]");
        eachRoundPlayer1CardsList = convertPlayerCardsRankToNum(eachRoundPlayer1CardsList);
        eachRoundPlayer2CardsList = convertPlayerCardsRankToNum(eachRoundPlayer2CardsList);
        eachRoundPlayer3CardsList = convertPlayerCardsRankToNum(eachRoundPlayer3CardsList);
        eachRoundPlayer1CardsList = sortPlayerCardsSuit(sortPlayerCardsRank(eachRoundPlayer1CardsList));
        eachRoundPlayer2CardsList = sortPlayerCardsSuit(sortPlayerCardsRank(eachRoundPlayer2CardsList));
        eachRoundPlayer3CardsList = sortPlayerCardsSuit(sortPlayerCardsRank(eachRoundPlayer3CardsList));
        eachRoundPlayer1CardsList = convertPlayerCardsRankToString(eachRoundPlayer1CardsList);
        eachRoundPlayer2CardsList = convertPlayerCardsRankToString(eachRoundPlayer2CardsList);
        eachRoundPlayer3CardsList = convertPlayerCardsRankToString(eachRoundPlayer3CardsList);
        int player1Points = calculateCardsPoints(eachRoundPlayer1CardsList);
        int player2Points = calculateCardsPoints(eachRoundPlayer2CardsList);
        int player3Points = calculateCardsPoints(eachRoundPlayer3CardsList);
        playerPointsList.add(player1Points); playerPointsList.add(player2Points); playerPointsList.add(player3Points);
        printPlayerPlayedCards(player1, player1Points, eachRoundPlayer1CardsList, playerGameDetails, playerPointsList, playerNameLengthSet);
        printPlayerPlayedCards(player2, player2Points, eachRoundPlayer2CardsList, playerGameDetails, playerPointsList, playerNameLengthSet);
        printPlayerPlayedCards(player3, player3Points, eachRoundPlayer3CardsList, playerGameDetails, playerPointsList, playerNameLengthSet);
        System.out.println();
        pressEnterToContinue(input);
        printPlayerScore(playerGameDetails, playerPointsList, playerNameLengthSet);
        System.out.println();
        System.out.println("[Available Cards]");
        printPlayerAvailableCards(player1, player1CardsQueue,playerNameLengthSet);
        printPlayerAvailableCards(player2, player2CardsQueue,playerNameLengthSet);
        printPlayerAvailableCards(player3, player3CardsQueue,playerNameLengthSet);
        System.out.println();
        if (num == 3){
            int player1Score = playerPhasesWinnerScore(0, playerGameDetails);
            int player2Score = playerPhasesWinnerScore(1, playerGameDetails);
            if (player1Score == player2Score){
                String finalWinner = playerPhasesWinner(2, playerGameDetails);
                System.out.println("------ " + finalWinner + " is the WINNER! ------");
            }
            else{
                String firstWinner = playerPhasesWinner(2, playerGameDetails);
                int firstWinnerScore = playerPhasesWinnerScore(2, playerGameDetails);
                String secondWinner = playerPhasesWinner(1, playerGameDetails);
                int secondWinnerScore = playerPhasesWinnerScore(1, playerGameDetails);
                System.out.println("------ " + firstWinner + " and " + secondWinner + " proceed to 2-Player phase ------");
                twoPlayerPhases(input, firstWinner, secondWinner,  firstWinnerScore, secondWinnerScore, cardsList, playerNameLengthSet, player1CardsQueue, player2CardsQueue);
            }
        }
        else
            pressEnterToNextRound(input);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void printPlayerPlayedCards(String player, int playerPoints, List<Card> playerCardsList, List<Game> playerGameDetails, List<Integer> playerPointsList, TreeSet<Integer> playerNameLengthSet){
        Player p = new Player();
        String strPlayerNameSize = Integer.toString(maxPlayerNameLength(playerNameLengthSet));
        System.out.printf("%" + strPlayerNameSize + "s : ", player);
        p.printPlayerPlayedCards(playerCardsList);
        p.printPlayerPoint(playerPoints, player, playerGameDetails, playerPointsList);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void printPlayerAvailableCards(String player, Queue<Card> playerCardsQueue, TreeSet<Integer> playerNameLengthSet){
        Player p = new Player();
        String strPlayerNameSize = Integer.toString(maxPlayerNameLength(playerNameLengthSet));
        System.out.printf("%" + strPlayerNameSize + "s : ", player);
        p.printAvailablePlayerCards(playerCardsQueue);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static String playerPhasesWinner(int count, List<Game> playerGameDetails){
        String winner = "";
        List<Integer> playerScoreList = new ArrayList<>();
        for (Game g: playerGameDetails){
            playerScoreList.add(g.getPlayerScore());
        }
        Collections.sort(playerScoreList);
        for (Game g: playerGameDetails){
            if (playerScoreList.get(count) == g.getPlayerScore())
                winner = g.getPlayerName();
        }
        return winner;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static int playerPhasesWinnerScore(int count, List<Game> playerGameDetails){
        int winnerScore = 0;
        List<Integer> playerScoreList = new ArrayList<>();
        for (Game g: playerGameDetails){
            playerScoreList.add(g.getPlayerScore());
        }
        Collections.sort(playerScoreList);
        for (Game g: playerGameDetails){
            if (playerScoreList.get(count) == g.getPlayerScore())
                winnerScore = g.getPlayerScore();
        }
        return winnerScore;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void printPlayerScore(List<Game> playerGameDetails, List<Integer> playerPointsList, TreeSet<Integer> playerNameLengthSet){
        System.out.println();
        System.out.println("Score:");
        String strPlayerNameSize = Integer.toString(maxPlayerNameLength(playerNameLengthSet));
        for (Game g: playerGameDetails){
            System.out.printf("%" + strPlayerNameSize + "s : ", g.getPlayerName());
            System.out.println(g.getPlayerScore());
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void twoPlayerPhases(Scanner input, String player1, String player2, int player1Score, int player2Score, List<Card> cardsList, TreeSet<Integer> playerNameLengthSet, Queue<Card> player1CardsQueue, Queue<Card> player2CardsQueue){
        DeckOfCards d = new DeckOfCards();
        Player p = new Player();
        player1CardsQueue.clear(); player2CardsQueue.clear(); playerNameLengthSet.clear();
        List<Game> playerGameDetails = new ArrayList<>();
        playerGameDetails.add(new Game(player1, player1Score));
        playerGameDetails.add(new Game(player2, player2Score));
        playerNameLengthSet.add(player1.length()); playerNameLengthSet.add(player2.length());
        System.out.println();
        System.out.println("------------------");
		System.out.println("| 2-Player Phase |");
		System.out.println("------------------");
        d.shuffleCards(cardsList);
        do{
            try{           
                player1CardsQueue = p.playerCardsQueue(player1CardsQueue,cardsList,0,26);
                player2CardsQueue = p.playerCardsQueue(player2CardsQueue,cardsList,26,52);  
                System.out.println();
                System.out.println("[Available Cards]");         
                printPlayerAvailableCards(player1, player1CardsQueue,playerNameLengthSet);
                printPlayerAvailableCards(player2, player2CardsQueue,playerNameLengthSet);
                System.out.println();
                System.out.println("Press [S] To Shuffle or Press [ENTER] To Start.");
                String userInput = input.nextLine();
                if (userInput.toUpperCase().equals("S")){
                    player1CardsQueue.clear(); player2CardsQueue.clear();
                    d.shuffleCards(cardsList);
                }
                else if (userInput.length() == 0){
                    for (int i = 1; i < 6; i++)
                        twoPlayerRound(input, i, player1, player2, cardsList, player1CardsQueue, player2CardsQueue, playerGameDetails, playerNameLengthSet);
                    System.exit(0);
                }
                else{
                    player1CardsQueue.clear(); player2CardsQueue.clear(); 
                    throw new IllegalArgumentException("Input Error: Invalid Input.");
                }
            }
            catch (IllegalArgumentException ex){
                System.out.println(ex.getMessage());
                pressEnterToContinue(input);
            }         
        } while(true);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void twoPlayerRound(Scanner input, int num, String player1, String player2, List<Card> cardsList, Queue<Card> player1CardsQueue, Queue<Card> player2CardsQueue, List<Game> playerGameDetails, TreeSet<Integer> playerNameLengthSet){
        Player p = new Player();
        List<Integer> playerPointsList = new ArrayList<>();
        List<Card> eachRoundPlayer1CardsList = new ArrayList<>(); List<Card> eachRoundPlayer2CardsList = new ArrayList<>(); 
        eachRoundPlayer1CardsList = p.eachRoundPlayerCardsList(eachRoundPlayer1CardsList, player1CardsQueue); 
        eachRoundPlayer2CardsList = p.eachRoundPlayerCardsList(eachRoundPlayer2CardsList, player2CardsQueue); 
        String strNum = Integer.toString(num);
        System.out.println();
        System.out.println("================= Round " + strNum + " =================");
        System.out.println("[Cards At Hand]");
        eachRoundPlayer1CardsList = convertPlayerCardsRankToNum(eachRoundPlayer1CardsList);
        eachRoundPlayer2CardsList = convertPlayerCardsRankToNum(eachRoundPlayer2CardsList);
        eachRoundPlayer1CardsList = sortPlayerCardsSuit(sortPlayerCardsRank(eachRoundPlayer1CardsList));
        eachRoundPlayer2CardsList = sortPlayerCardsSuit(sortPlayerCardsRank(eachRoundPlayer2CardsList));
        eachRoundPlayer1CardsList = convertPlayerCardsRankToString(eachRoundPlayer1CardsList);
        eachRoundPlayer2CardsList = convertPlayerCardsRankToString(eachRoundPlayer2CardsList);
        int player1Points = calculateCardsPoints(eachRoundPlayer1CardsList);
        int player2Points = calculateCardsPoints(eachRoundPlayer2CardsList);
        playerPointsList.add(player1Points); playerPointsList.add(player2Points); 
        printPlayerPlayedCards(player1, player1Points, eachRoundPlayer1CardsList, playerGameDetails, playerPointsList, playerNameLengthSet);
        printPlayerPlayedCards(player2, player2Points, eachRoundPlayer2CardsList, playerGameDetails, playerPointsList, playerNameLengthSet);
        System.out.println();
        pressEnterToContinue(input);
        printPlayerScore(playerGameDetails, playerPointsList, playerNameLengthSet);
        System.out.println();
        System.out.println("[Available Cards]");
        printPlayerAvailableCards(player1, player1CardsQueue,playerNameLengthSet);
        printPlayerAvailableCards(player2, player2CardsQueue,playerNameLengthSet);
        System.out.println();
        if (num == 5){
            int player1Score = playerPhasesWinnerScore(0, playerGameDetails);
            int player2Score = playerPhasesWinnerScore(1, playerGameDetails);
            if (player1Score == player2Score){
                String finalWinner1 = playerPhasesWinner(0, playerGameDetails);
                String finalWinner2 = playerPhasesWinner(1, playerGameDetails);
                System.out.println("------ " + finalWinner1 + " and " +  finalWinner2 + " is the WINNER! ------");
            }
            else{
                String finalWinner = playerPhasesWinner(1, playerGameDetails);
                System.out.println("------ " + finalWinner + " is the WINNER! ------");
            }
        }
        else
            pressEnterToNextRound(input);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static int calculateCardsPoints(List<Card> playerCardsList){
        int club = 0, diamond = 0, heart = 0, spade = 0;
        List<Integer> cardsPoint = new ArrayList<>();
        for (Card c: playerCardsList){
            if (c.getCardSuit().equals("c"))
                club = club + intCardRank(c.getCardRank());
            if (c.getCardSuit().equals("d"))
                diamond = diamond + intCardRank(c.getCardRank());
            if (c.getCardSuit().equals("h"))
                heart = heart + intCardRank(c.getCardRank());
            if (c.getCardSuit().equals("s"))
                spade = spade + intCardRank(c.getCardRank());
        }
        cardsPoint.add(club); cardsPoint.add(diamond); cardsPoint.add(heart); cardsPoint.add(spade);
        return Collections.max(cardsPoint);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static int intCardRank(String cardRank) {
        switch (cardRank){
			case "A" : return 1; 
			case "2" : return 2; 
			case "3" : return 3; 
			case "4" : return 4;	
			case "5" : return 5;
			case "6" : return 6;
			case "7" : return 7;
			case "8" : return 8;
			case "9" : return 9;
			case "X" : return 10;
			case "J" : return 10;
			case "Q" : return 10;
			case "K" : return 10;
			default  : return Integer.parseInt(cardRank);
		}
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static List<Card> convertPlayerCardsRankToNum(List<Card> playerCardsList){
        for (Card c: playerCardsList){
            if (c.getCardRank().equals("A"))
                c.setCardRank("1");
            if (c.getCardRank().equals("X"))
                c.setCardRank("a"); 
            if (c.getCardRank().equals("J"))
                c.setCardRank("b");
            if (c.getCardRank().equals("Q"))
                c.setCardRank("c");
            if (c.getCardRank().equals("K"))
                c.setCardRank("d");
        }
        return playerCardsList;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static List<Card> convertPlayerCardsRankToString(List<Card> playerCardsList){
        for (Card c: playerCardsList){
            if (c.getCardRank().equals("1"))
                c.setCardRank("A");
            if (c.getCardRank().equals("a"))
                c.setCardRank("X");
            if (c.getCardRank().equals("b"))
                c.setCardRank("J");
            if (c.getCardRank().equals("c"))
                c.setCardRank("Q");
            if (c.getCardRank().equals("d"))
                c.setCardRank("K");
        }
        return playerCardsList;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static List<Card> sortPlayerCardsSuit(List<Card> playerCardsList){
        Collections.sort(playerCardsList, new CardSuitComparator());
        return playerCardsList;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static List<Card> sortPlayerCardsRank(List<Card> playerCardsList){
        Collections.sort(playerCardsList, new CardRankComparator());
        return playerCardsList;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static int maxPlayerNameLength(TreeSet<Integer> playerNameLengthSet){
        return playerNameLengthSet.last();
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     public static void pressEnterToNextRound(Scanner input) {
        System.out.print("Press [ENTER] to Next Round.");
        input.nextLine();            
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void pressEnterToContinue(Scanner input) {
        System.out.print("Press [ENTER] to Continue.");
        input.nextLine();            
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
