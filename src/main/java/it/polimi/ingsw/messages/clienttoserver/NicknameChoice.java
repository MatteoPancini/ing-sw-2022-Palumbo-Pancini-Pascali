package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.messages.clienttoserver.Message;

/**
 * Class NicknameChoice is used to send a message from client to server, containing the nickname typed by the user
 */
public class NicknameChoice implements Message {

    //This class represents a message from the client to server
    private final String nicknameChosen;

    /**
     * Constructor of the class
     * @param nicknameChosen chosen nickname
     */
    public NicknameChoice (String nicknameChosen) {

        this.nicknameChosen = nicknameChosen;
    }

    public String getNicknameChosen() {

        return nicknameChosen;
    }
}
