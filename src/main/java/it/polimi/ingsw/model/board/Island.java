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

    public Island(GameBoard board, int ID){
        this.board = board;
        islandID = ID;
        tower = null;
        mergedIsland = new ArrayList<Island>();
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
        if(mergedTowers != null) return true;
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

    public void merge(Island island) {
        mergedIsland.add(island);
        if(this.islandID < island.islandID) {
            mergedTowers.add(island.getTower());
            for(Student s : island.getStudents()) {
                students.add(s);
            }
            island.setMergedIsland(null);
            island.setStudents(null);
        } else {
            island.getMergedTowers().add(this.tower);
            for(Student s : getStudents()) {
                island.addStudent(s);
            }
            island.mergedIsland.add(this);
            this.mergedIsland = null;
            this.students = null;
        }


    }


    public boolean hasLeft(){
        if(islandID != 1) {
            Island island1 = board.getIslands().get(islandID - 2);
            Island island2 = board.getIslands().get(islandID - 1);
            if(island1.hasTower() == true){
                if(island1.getTower().getColor() == island2.getTower().getColor())
                    return true;
                else
                    return false;
            }

            else return false;
        }

        else {
            if(board.getIslands().get(11).hasTower() == true) {
                if(board.getIslands().get(11).getTower().getColor() == board.getIslands().get(0).getTower().getColor()) return true;
                else return false;
            }

            else return false;
        }
    }

    public boolean hasRight() {
        if(islandID != 12) {
            Island island1 = board.getIslands().get(islandID);
            Island island2 = board.getIslands().get(islandID - 1);
            if(island1.hasTower() == true){
                if(island1.getTower().getColor() == island2.getTower().getColor()) return true;
                else return false;
            }

            else return false;
        }

        else {
            if(board.getIslands().get(0).hasTower() == true){
                if(board.getIslands().get(0).getTower().getColor() == board.getIslands().get(11).getTower().getColor()) return true;
                else return false;
            }
            else return false;
        }
    }

    public Tower getTower() {
        return mergedTowers.get(0);
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