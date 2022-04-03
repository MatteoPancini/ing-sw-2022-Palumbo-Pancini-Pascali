package it.polimi.ingsw.network.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

public class Client implements ClientInterface {
    private Optional<String> nickname;
    private boolean validNickname = false;

    private final String clientID;
    private final int port;

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public Client(){}
}
