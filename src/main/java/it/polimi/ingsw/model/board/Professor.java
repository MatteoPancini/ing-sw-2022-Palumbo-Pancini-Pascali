package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.enumerations.PawnType;

import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;

/**
 * Class used to implement the professors
 */
public class Professor implements Serializable {
    private final PawnType type;
    private Player owner;

    public Professor(PawnType type){
        this.type = type;
    }

    public void setOwner(Player owner){ this.owner = owner; }

    public Player getOwner(){ return owner; }

    public PawnType getType(){ return type; }
}