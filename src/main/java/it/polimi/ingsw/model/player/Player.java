package it.polimi.ingsw.model.player;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.AssistantDeck;
import it.polimi.ingsw.model.enumerations.Wizards;

public class Player {
    private final String nickname;
    private int playerID;
    private Wizards wizard;
    private final AssistantDeck assistantDeck;
    private SchoolBoard board;
    private boolean isPlaying;
    private boolean isWinner;
    private int teammateID;

    public Player(String nickname, int playerID) {
        this.nickname = nickname;
        assistantDeck = null;
        board = new SchoolBoard(playerID); //potremmo far corrispondere l'ID della board con il client ID così da avere lo stesso identificativo
        this.playerID = playerID;
        this.wizard = null;
        //inizializzare teammateID (probabilmente if(player2) { teammateID = 2} else if(player3) { teammateID = 1 }
    }

    public String getNickname() {
        return nickname;
    }

    public AssistantDeck getAssistantDeck() {
        return assistantDeck;
    }

    public SchoolBoard getBoard() {
        return board;
    }

    public void setBoard(SchoolBoard board) {
        this.board = board;
    }

    public AssistantCard pickAssistant() {
        BufferedReader assistant = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Pick an assistant card: ");
        try {
            Assistants a = Assistants.valueOf(assistant.readLine());
            return this.getAssistantDeck().getCard(a);
        } catch(IOException e) {
            System.err.println(e.getMessage());
            return pickAssistant();
        }
    }

    public void setWizard(Wizards wiz) {
        if(this.wizard == null) {
            this.wizard = wiz;
        }
    }

    public Wizards getWizard() {
        return wizard;
    }

    public int chooseMoves(AssistantCard card) {
        BufferedReader moves = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Pick a number of moves: ");
        try {
            int n = Integer.parseInt(moves.readLine());
            return n;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    public void setPlayerID(int playerID) { this.playerID = playerID; }

    public int getPlayerID(){ return playerID; }

    public void setTeammateID(int teammateID) { this.teammateID = teammateID; }

    public int getTeammateID(){ return teammateID; }

}
