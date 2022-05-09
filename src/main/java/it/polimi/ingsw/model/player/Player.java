package it.polimi.ingsw.model.player;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.AssistantDeck;
import it.polimi.ingsw.model.enumerations.Wizards;

public class Player {
    private String nicknamePlayer;
    private int idPlayer;
    private Wizards wizard;
    private final AssistantDeck assistantDeck;
    private SchoolBoard board;
    private boolean isPlaying;
    private boolean isWinner;
    private int idTeam;
    private AssistantCard chosenAssistant;
    private boolean teamLeader;

    public Player(String nickname, int playerID) {
        this.nicknamePlayer = nickname;
        assistantDeck = null;
        board = new SchoolBoard(playerID); //potremmo far corrispondere l'ID della board con il client ID così da avere lo stesso identificativo
        this.idPlayer = playerID;
        this.wizard = null;
        //inizializzare teammateID (probabilmente if(player2) { teammateID = 2} else if(player3) { teammateID = 1 }
    }

    public AssistantCard getChosenAssistant() {
        return chosenAssistant;
    }

    public String getNickname() {
        return nicknamePlayer;
    }

    public void setNickname(String nickname) {
        this.nicknamePlayer = nickname;
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

    public void setTeamLeader(boolean teamLeader) {
        this.teamLeader = teamLeader;
    }

    public boolean isTeamLeader() {
        return teamLeader;
    }


    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public int getIdTeam() {
        return idTeam;
    }



    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public void setNicknamePlayer(String nicknamePlayer) {
        this.nicknamePlayer = nicknamePlayer;
    }

    public void setWizard(Wizards wizard) {
        this.wizard = wizard;
    }

    public Wizards getWizard() {
        return wizard;
    }
}