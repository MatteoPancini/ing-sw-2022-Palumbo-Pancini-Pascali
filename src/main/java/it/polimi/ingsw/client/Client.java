package it.polimi.ingsw.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

public class Client implements ClientInterface {
    private Optional<String> nickname;
    private boolean validNickname = false;

    private final String clientIPAddress;
    private final int port;

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public Client(String ipAddress, int port) {
        this.nickname = Optional.empty();
        this.clientIPAddress = ipAddress;
        this.port = port;
    }
}
