package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.enumerations.TowerColor;

public class Tower {
    private final TowerColor color;

    public Tower(TowerColor color){
        this.color = color;
    }

    public TowerColor getColor(){ return color; }
}
