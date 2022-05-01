package it.polimi.ingsw.model.player;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.AssistantDeck;
import it.polimi.ingsw.model.enumerations.Wizards;

public class Player {
    private String nickname;
    private int playerID;
    private Wizards wizard;
    private final AssistantDeck assistantDeck;
    private SchoolBoard board;
    private boolean isPlaying;
    private boolean isWinner;
    private int teammateID;
    private AssistantCard chosenAssistant;

    public Player(String nickname, int playerID) {
        this.nickname = nickname;
        assistantDeck = null;
        board = new SchoolBoard(playerID); //potremmo far corrispondere l'ID della board con il client ID così da avere lo stesso identificativo
        this.playerID = playerID;
        this.wizard = null;
        //inizializzare teammateID (probabilmente if(player2) { teammateID = 2} else if(player3) { teammateID = 1 }
    }

    public AssistantCard getChosenAssistant() {
        return chosenAssistant;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public void setWizard(Wizards wiz) {
        if(this.wizard == null) {
            this.wizard = wiz;
        }
    }

    public Wizards getWizard() {
        return wizard;
    }

    public void setPlayerID(int playerID) { this.playerID = playerID; }

    public int getPlayerID(){ return playerID; }

    public void setTeammateID(int teammateID) { this.teammateID = teammateID; }

    public int getTeammateID(){ return teammateID; }

    public void removeCard(AssistantCard card){
        for(AssistantCard c : getAssistantDeck().getDeck()){
            if(c == card) getAssistantDeck().getDeck().remove(c);
        }
        return;
    }

}



