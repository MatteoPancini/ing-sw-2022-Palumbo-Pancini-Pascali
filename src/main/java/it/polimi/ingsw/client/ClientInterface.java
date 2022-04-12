package it.polimi.ingsw.client;

import java.io.Serializable;


public interface ClientInterface {
    void sendMessageToServer(Serializable message);
}