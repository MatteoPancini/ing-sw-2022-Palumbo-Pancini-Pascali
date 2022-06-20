package it.polimi.ingsw.model.player;

import java.io.Serializable;
import java.util.ArrayList;

import it.polimi.ingsw.model.board.Island;

public class TowerArea implements Serializable {
    private final ArrayList<Tower> myTowers;

    public TowerArea () {
        myTowers = new ArrayList<>();
    }

    public ArrayList<Tower> getTowerArea() { return myTowers; }

    public void addTowers (Tower tower) {
        myTowers.add(tower);
        System.out.println("add tower" + tower.getColor().toString());
    }

    public void moveTowerToIsland(Island island) {
        System.out.println("Entro tower");
        island.setTower(myTowers.get(myTowers.size() - 1));
        myTowers.remove(myTowers.get(myTowers.size() - 1));

    }

    public void moveTowerToIsland(Island island, int mergedTowers) {
        System.out.println("Entro towers");
        for (int i = 0; i < mergedTowers; i++) {
            island.getMergedTowers().add(myTowers.get(myTowers.size() - 1));
            myTowers.remove(myTowers.get(myTowers.size() - 1));
        }
    }

}
