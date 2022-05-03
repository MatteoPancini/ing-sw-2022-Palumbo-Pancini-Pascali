package it.polimi.ingsw.messages.clienttoserver;


public class GameModeChoice implements Message {
    private int playersNumber;


    public GameModeChoice(int playersNumber) {
        this.playersNumber = playersNumber;
    }


    public int getPlayersNumber() {
        return playersNumber;
    }

}
