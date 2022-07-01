package it.polimi.ingsw.model.player;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.AssistantDeck;
import it.polimi.ingsw.model.enumerations.Wizards;

import java.io.Serializable;

/**
 * Class Player represents the player of the game
 */
public class Player implements Serializable {
    private String nicknamePlayer;
    private int playerID;
    private Wizards wizard;
    private AssistantDeck assistantDeck;
    private SchoolBoard board;
    private int idTeam;
    private AssistantCard chosenAssistant;
    private boolean teamLeader;
    private int islandInfluence;
    private int myCoins;

    public void setMyCoins(int myCoins) {
        this.myCoins = myCoins;
    }

    public void setChosenAssistant(AssistantCard chosenAssistant) {
        this.chosenAssistant = chosenAssistant;
    }

    public Player(String nickname, int playerID) {
        this.nicknamePlayer = nickname;
        this.playerID = playerID;
        this.wizard = null;
        islandInfluence = 0;
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

    public int getPlayerID() {
        return playerID;
    }


    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public int getIdTeam() {
        return idTeam;
    }

    public int getIslandInfluence() {
        return islandInfluence;
    }

    public void setIslandInfluence(int islandInfluence) {
        this.islandInfluence = islandInfluence;
    }

    public void setWizard(Wizards wizard) {
        this.wizard = wizard;
        assistantDeck = new AssistantDeck(wizard);
        for(AssistantCard a : assistantDeck.getDeck()) {
            a.setOwner(this);
        }
    }

    public Wizards getWizard() {
        return wizard;
    }

    public boolean isTeamLeader() {
        return teamLeader;
    }

    public int getMyCoins() {
        return myCoins;
    }

    private int profInfluence;

    public void setProfInfluence(int profInfluence) {
        this.profInfluence = profInfluence;
    }

    public int getProfInfluence() {
        return profInfluence;
    }
}