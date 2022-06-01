package it.polimi.ingsw.model.player;

import java.io.Serializable;
import java.util.ArrayList;

import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.enumerations.TowerColor;

public class TowerArea implements Serializable {
    private ArrayList<Tower> myTowers = new ArrayList<Tower>();

    public TowerArea () {
        myTowers = new ArrayList<Tower>();
    }

    public ArrayList<Tower> getTowerArea() { return myTowers; }

    public void addTowers (Tower tower) { myTowers.add(tower); };

    public void moveTowerToIsland(Island island) {
        for(int i = 0; i < island.getMergedTowers().size(); i++) {
            island.getMergedTowers().add(myTowers.get(myTowers.size() - 1));
            myTowers.remove(myTowers.get(myTowers.size() - 1));
        }

    }

}
