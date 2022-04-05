package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tower;

import java.util.ArrayList;

public class Island {
    private final GameBoard board = null;
    private int islandID;
    private Tower tower;
    private ArrayList<Island> mergedIsland;
    private ArrayList<Student> students;
    private boolean noEntry;
    private Player owner;

    public Island(GameBoard board, int ID){
        this.board = board;
        islandID = ID;
        tower = null;
        mergedIsland = new ArrayList<Island>();
        students = new ArrayList<Student>();
        noEntry = false;
        owner = null;
    }

    public boolean hasTower(){
        if(tower == null) return false;
        else return true;
    }

    public int getIslandID(){ return islandID; }

    public ArrayList<Student> getStudents(){ return students; }

    public Player getOwner(){ return owner; }

    public boolean getNoEntry(){ return noEntry; }

    public void setNoEntry(boolean noEntry){ this.noEntry = noEntry; }

    public void setOwner(Player owner){ this.owner = owner; }

    public void addStudent(Student newStudent){ students.add(newStudent); }

    public void merge(Island island){ mergedIsland.add(island); }

    public boolean hasLeft(){
        if(islandID != 1) {
            if(board.getIslands().get(islandID - 1).hasTower() == true){
                if(board.getIslands().get(islandID - 1).getTower().getColor() == tower.getColor()) return true;
            }
            else return false;
        }
        else {
            if(board.getIslands().get(12).hasTower() == true){
                if(board.getIslands().get(12).getTower().getColor() == tower.getColor()) return true;
            }
            else return false;
        }
    }

    public void hasRight() {
        if(islandID != 12) {
            if(board.getIslands().get(islandID + 1).hasTower() == true){
                if(board.getIslands().get(islandID + 1).getTower().getColor() == tower.getColor()) return true;
            }
            else return false;
        }
        else {
            if(board.getIslands().get(1).hasTower() == true){
                if(board.getIslands().get(1).getTower().getColor() == tower.getColor()) return true;
            }
            else return false;
        }
    }

    public Tower getTower(){ return tower; }

    public ArrayList<Island> getMergedIslands(){ return mergedIsland; }

    public void setTower(Tower tower) { this.tower = tower; }
}
