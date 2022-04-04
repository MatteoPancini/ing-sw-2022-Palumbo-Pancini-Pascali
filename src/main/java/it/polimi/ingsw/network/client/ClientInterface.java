package it.polimi.ingsw.network.client;

import java.io.Serializable;


public interface ClientInterface {
    void sendMessageToServer(Serializable message);
}