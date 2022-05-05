package it.polimi.ingsw.model.player;

import java.util.ArrayList;

import it.polimi.ingsw.model.enumerations.TowerColor;

public class TowerArea {
    private ArrayList<Tower> myTowers = new ArrayList<Tower>();

    public TowerArea () {
        myTowers = new ArrayList<Tower>();
    }

    public ArrayList<Tower> getTowerArea() { return myTowers; }

    public void addTowers (Tower tower) { myTowers.add(tower); };
}
