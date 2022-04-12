package it.polimi.ingsw.messages.clienttoserver;


public class GameModeChoice implements Message {
    private int playersNumber;
    private boolean expertMode;


    public GameModeChoice(int playersNumber) {
        this.playersNumber = playersNumber;
        expertMode = false;
    }

    public void setExpertMode(boolean expertMode) {
        this.expertMode = expertMode;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public boolean isExpertMode() {
        return expertMode;
    }
}
