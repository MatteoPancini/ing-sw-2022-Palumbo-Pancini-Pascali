package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.enumerations.TowerColor;

import java.io.Serializable;

/**
 * Class used to implement the towers
 */
public class Tower implements Serializable {
    private final TowerColor color;

    public Tower(TowerColor color){
        this.color = color;
    }

    public TowerColor getColor(){ return color; }
}
