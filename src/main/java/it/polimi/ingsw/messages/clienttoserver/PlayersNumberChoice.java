package it.polimi.ingsw.messages.clienttoserver;

public class PlayersNumberChoice implements Message {
    public final int numberOfPlayers;

    public PlayersNumberChoice(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
