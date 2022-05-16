package it.polimi.ingsw.messages.servertoclient;

import java.io.Serializable;

public interface Answer extends Serializable {
    //Answer class implements an interface for serializable messages from server to client

    Object getMessage();

}
