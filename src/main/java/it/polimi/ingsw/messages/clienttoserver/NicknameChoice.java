package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.messages.clienttoserver.Message;

public class NicknameChoice implements Message {

    //This class represents a message from the client to server
    private final String nicknameChosen;

    public NicknameChoice (String nicknameChosen) {

        this.nicknameChosen = nicknameChosen;
    }

    public String getNicknameChosen() {

        return nicknameChosen;
    }
}
