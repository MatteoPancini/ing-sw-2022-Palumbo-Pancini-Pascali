package it.polimi.ingsw.messages.clienttoserver;


/**
 * Class PlayersNumberChoice is a serializable message used to send the number of players chosen by the user
 * from client to server
 */
public class PlayersNumberChoice implements Message {
    public final int numberOfPlayers;

    /**
     * Constructor of the class
     * @param numberOfPlayers players number
     */
    public PlayersNumberChoice(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
