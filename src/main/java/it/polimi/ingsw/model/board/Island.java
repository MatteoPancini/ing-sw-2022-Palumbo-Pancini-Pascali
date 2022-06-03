package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tower;
import it.polimi.ingsw.model.player.TowerArea;

import java.io.Serializable;
import java.util.ArrayList;

public class Island implements Serializable {
    private GameBoard board = null;
    private int islandID;
    private Tower tower;
    private ArrayList<Island> mergedIsland;
    private ArrayList<Student> students;
    private ArrayList<Tower> mergedTowers;
    private boolean noEntry;
    private Player owner;
    private boolean motherNature;
    private boolean isMerged;
    private int leaderIsland = -1;

    public Island(GameBoard board, int ID){
        this.board = board;
        islandID = ID;
        tower = null;
        mergedIsland = new ArrayList<Island>();
        mergedIsland.add(this);
        System.out.println(mergedIsland.size());
        students = new ArrayList<Student>();
        noEntry = false;
        owner = null;
    }

    public void setMotherNature(boolean motherNature) {
        this.motherNature = motherNature;
    }

    public boolean isMotherNature() {
        return motherNature;
    }

    public boolean hasTower(){
        if(tower != null) return true;
        else return false;
    }

    public int getIslandID(){ return islandID; }

    public ArrayList<Student> getStudents(){ return students; }

    public Player getOwner(){ return owner; }

    public boolean getNoEntry(){ return noEntry; }

    public void setNoEntry(boolean noEntry){ this.noEntry = noEntry; }

    public void setOwner(Player owner){ this.owner = owner; }

    public void addStudent(Student newStudent) {
        students.add(newStudent);
    }

    public int getLeaderIsland() {
        return leaderIsland;
    }

    public void setMerged(boolean merged) {
        isMerged = merged;
    }

    public void setLeaderIsland(int leaderIsland) {
        this.leaderIsland = leaderIsland;
    }

    public boolean isMerged() {
        return isMerged;
    }

    public void merge(Island island) {
        System.out.println("Faccio merge");
        if(this.islandID < island.getIslandID()) {
            System.out.println("Aggiungo " + island.getIslandID() + " a " + islandID);
            mergedIsland.add(island);
            System.err.println("Merged island " + mergedIsland.size());
            mergedTowers.add(island.getTower());
            for(Student s : island.getStudents()) {
                students.add(s);
            }
            island.setMergedIsland(null);
            island.setStudents(null);
            for(int i=0; i<board.getIslands().size(); i++) {
                if(board.getIslands().get(i).getIslandID() == island.getIslandID()) {
                    board.getIslands().remove(i);
                    break;
                }
            }
        } else {
            System.out.println("Aggiungo " + islandID + " a " + island.getIslandID());
            island.getMergedIslands().add(this);
            System.err.println("Merged island " + island.getMergedIslands().size());
            island.getMergedTowers().add(this.tower);
            for(Student s : getStudents()) {
                island.addStudent(s);
            }
            this.mergedIsland = null;
            this.students = null;
            for(int i=0; i<board.getIslands().size(); i++) {
                if(board.getIslands().get(i).getIslandID() == islandID) {
                    board.getIslands().remove(i);
                    break;
                }
            }
        }

        board.decrementIslandCounter();
    }


    public boolean hasLeft(){
        if(islandID != 1) {
            /*
            Island island1 = board.getIslands().get(islandID - 2);
            System.out.println("Controllo prima " + );
            Island island2 = board.getIslands().get(islandID - 1);

             */
            for(int i=0; i<board.getIslands().size(); i++) {
                if(board.getIslands().get(i).getIslandID() == islandID) {
                    System.out.println("Analizzo "+ board.getIslands().get(i-1).getIslandID());
                    if(board.getIslands().get(i-1).hasTower() == true){
                        if(board.getIslands().get(i-1).getTower().getColor() == tower.getColor())
                            return true;
                        else
                            return false;
                    }
                    else return false;
                }
            }
        }

        else {
            /*
            if(board.getIslands().get(11).hasTower() == true) {
                if(board.getIslands().get(11).getTower().getColor() == board.getIslands().get(0).getTower().getColor()) return true;
                else return false;
            }

            else return false;

             */
            for(int i=0; i<board.getIslands().size(); i++) {
                if(board.getIslands().get(i) == null) {
                    if(board.getIslands().get(i-1).hasTower() == true) {
                        if(board.getIslands().get(i-1).getTower().getColor() == tower.getColor()) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean hasRight() {
        if(islandID != 12) {
            for(int i= 0; i<board.getIslands().size(); i++) {
                if(board.getIslands().get(i).getIslandID() == islandID) {
                    System.out.println("Analizzo "+ board.getIslands().get(i+1).getIslandID());
                    if(board.getIslands().get(i+1).hasTower()) {
                        if(board.getIslands().get(i+1).getTower().getColor() == tower.getColor())
                            return true;
                        else
                            return false;
                    }

                }
            }
            /*
            Island island1 = board.getIslands().get(islandID);
            Island island2 = board.getIslands().get(islandID - 1);
            if(island1.hasTower() == true) {
                if(island1.getTower().getColor() == island2.getTower().getColor()) return true;
                else return false;
            }

            else return false;
        } else {
            if(board.getIslands().get(0).hasTower() == true){
                if(board.getIslands().get(0).getTower().getColor() == board.getIslands().get(11).getTower().getColor()) return true;
                else return false;
            }
            else return false;

             */
        } else {
            if(board.getIslands().get(0).hasTower()) {
                if(board.getIslands().get(0).getTower().getColor() == tower.getColor()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;

    }

    public Tower getTower() {
        if(hasTower()) {
            return mergedTowers.get(0);
        } else
            return tower;

    }

    private void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public ArrayList<Island> getMergedIslands() {
        return mergedIsland;
    }

    private void setMergedIsland(ArrayList<Island> mergedIsland) {
        this.mergedIsland = mergedIsland;
    }

    public ArrayList<Tower> getMergedTowers() {
        return mergedTowers;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
        mergedTowers = new ArrayList<Tower>();
        mergedTowers.add(tower);
    }

    public void moveTowerToArea(TowerArea towerArea) {
        for(Tower t : mergedTowers) {
            towerArea.addTowers(t);
        }

        mergedTowers.clear();
    }
}