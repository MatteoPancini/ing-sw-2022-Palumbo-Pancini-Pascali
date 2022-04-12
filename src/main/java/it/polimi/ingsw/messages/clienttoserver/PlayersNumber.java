package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.messages.clienttoserver.Message;

public class PlayersNumber implements Message {
    public final int numberOfPlayers;

    public PlayersNumber(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
}
