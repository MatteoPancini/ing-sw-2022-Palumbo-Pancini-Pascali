package it.polimi.ingsw.messages.servertoclient;

import java.io.Serializable;

/**
 * Interface for serializable messages from server to client
 */
public interface Answer extends Serializable {

    Object getMessage();

}
