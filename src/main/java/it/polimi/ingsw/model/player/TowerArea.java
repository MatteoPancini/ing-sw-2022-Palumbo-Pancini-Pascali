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

    /**
     * Put a tower back to the tower area of his owner when another player conquers an island
     * @param tower -> tower to add
     */
    public void addTowers (Tower tower) {
        myTowers.add(tower);
    }

    /**
     * Move a tower from a tower area to an island
     * @param island -> destination
     */
    public void moveTowerToIsland(Island island) {
        island.setTower(myTowers.get(myTowers.size() - 1));
        myTowers.remove(myTowers.get(myTowers.size() - 1));

    }

    /**
     * Move some towers from a tower area to an island
     * @param island -> destination
     * @param mergedTowers -> number of towers to move
     */
    public void moveTowerToIsland(Island island, int mergedTowers) {
        for (int i = 0; i < mergedTowers; i++) {
            island.getMergedTowers().add(myTowers.get(myTowers.size() - 1));
            myTowers.remove(myTowers.get(myTowers.size() - 1));
        }
    }
}