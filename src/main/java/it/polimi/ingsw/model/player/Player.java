package it.polimi.ingsw.model.player;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.AssistantDeck;
import it.polimi.ingsw.model.enumerations.Wizards;

import java.io.Serializable;

public class Player implements Serializable {
    private String nicknamePlayer;
    private int playerID;
    private Wizards wizard;
    private AssistantDeck assistantDeck;
    private SchoolBoard board;
    private boolean isPlaying;
    private boolean isWinner;
    private int idTeam;
    private AssistantCard chosenAssistant;
    private boolean teamLeader;
    private int coins;
    private int islandInfluence;

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    public void setChosenAssistant(AssistantCard chosenAssistant) {
        this.chosenAssistant = chosenAssistant;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Player(String nickname, int playerID) {
        this.nicknamePlayer = nickname;
        this.playerID = playerID;
        this.wizard = null;
        board = new SchoolBoard(playerID);
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

    public void setIdPlayer(int playerID) {
        this.playerID = playerID;
    }

    public void setNicknamePlayer(String nicknamePlayer) {
        this.nicknamePlayer = nicknamePlayer;
    }

    public void setWizard(Wizards wizard) {
        this.wizard = wizard;
        this.assistantDeck = new AssistantDeck(wizard);
        for(AssistantCard a : assistantDeck.getDeck()){
            a.setOwner(this);
        }

    }

    public Wizards getWizard() {
        return wizard;
    }

    public boolean isTeamLeader() {
        return teamLeader;
    }
}