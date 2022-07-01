package it.polimi.ingsw.model.board;

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

    public Island(GameBoard board, int ID){
        this.board = board;
        islandID = ID;
        tower = null;
        mergedIsland = new ArrayList<>();
        mergedIsland.add(this);
        students = new ArrayList<>();
        noEntry = false;
        owner = null;
    }


    /**
     * Check if an island has a tower
     * @return a boolean that says whether the island has a tower or not
     */
    public boolean hasTower(){
        if(tower != null) return true;
        else return false;
    }

    public int getIslandID(){ return islandID; }

    public ArrayList<Student> getStudents(){ return students; }

    public Player getOwner(){ return owner; }

    public boolean getNoEntry(){ return noEntry; }

    public void setNoEntry(boolean noEntry) {
        this.noEntry = noEntry;
    }

    public void addStudent(Student newStudent) {
        students.add(newStudent);
    }

    /**
     * Merge three contiguous islands that both have a tower of the same color
     */
    public void doubleMerge() {
        Island iBefore = null;
        Island iAfter = null;

        for(int i=0; i < board.getIslands().size(); i++) {
            if(islandID != 1) {
                if(board.getIslands().get(i+1).getIslandID() == islandID) {
                    iBefore = board.getIslands().get(i);
                    if(board.getIslands().get(i+1).getIslandID() == board.getIslands().get(board.getIslands().size() - 1).getIslandID()) {
                        iBefore = board.getIslands().get(0);
                        iAfter = board.getIslands().get(i);
                    } else {
                        iAfter = board.getIslands().get(i+2);
                    }
                    break;
                }
            } else {
                iAfter = board.getIslands().get(1);
                iBefore = board.getIslands().get(board.getIslands().size() - 1);
            }

        }

        for(Island i : mergedIsland) {
            iBefore.getMergedIslands().add(i);
        }
        for(Island i : iAfter.getMergedIslands()) {
            iBefore.getMergedIslands().add(i);
        }

        for(Tower t : mergedTowers) {
            iBefore.getMergedTowers().add(t);
        }

        for(Tower t : iAfter.getMergedTowers()) {
            iBefore.getMergedTowers().add(t);
        }

        for(Student s : students) {
            iBefore.getStudents().add(s);
        }
        for(Student s : iAfter.getStudents()) {
            iBefore.getStudents().add(s);
        }

        this.mergedIsland = null;
        iAfter.setMergedIsland(null);
        this.students = null;
        iAfter.setStudents(null);

        for(int i=0; i<board.getIslands().size(); i++) {
            if(board.getIslands().get(i).getIslandID() == islandID) {
                board.getIslands().remove(i);
            }
            if(board.getIslands().get(i).getIslandID() == iAfter.getIslandID()) {
                board.getIslands().remove(i);
            }
        }
        board.decrementIslandCounter();
        board.decrementIslandCounter();
    }

    /**
     * Merge two contiguous islands that both have a tower of the same color
     * @param island
     */
    public void merge(Island island) {
        if(this.islandID < island.getIslandID()) {
            for(Island i : island.getMergedIslands()) {
                mergedIsland.add(i);
            }

            for(Tower t : island.getMergedTowers()) {
                mergedTowers.add(t);
            }

            island.setTower(null);
            island.setMergedIsland(null);
            island.setStudents(null);
            for(int i=0; i<board.getIslands().size(); i++) {
                if(board.getIslands().get(i).getIslandID() == island.getIslandID()) {
                    board.getIslands().remove(i);
                    break;
                }
            }
        } else {
            for(Island i : mergedIsland) {
                island.getMergedIslands().add(i);
            }

            for(Tower t : mergedTowers) {
                island.getMergedTowers().add(t);
            }

            for(Student s : getStudents()) {
                island.addStudent(s);
            }
            this.tower = null;
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

    /**
     * Check if the island on the left has a tower of the same color
     * @return a boolean that says whether the island on the left has a tower or not
     */
    public boolean hasLeft(){
        if(islandID != 1) {
            for(int i = 0; i < board.getIslands().size(); i++) {
                if(board.getIslands().get(i).getIslandID() == islandID) {
                    if(board.getIslands().get(i - 1).hasTower() == true){
                        if(board.getIslands().get(i - 1).getTower().getColor() == tower.getColor())
                            return true;
                        else
                            return false;
                    }
                    else return false;
                }
            }
        } else {
            if(board.getIslands().get(board.getIslands().size() -1).hasTower() == true) {
                if(board.getIslands().get(board.getIslands().size() -1).getTower().getColor() == tower.getColor()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Check if the island on the right has a tower of the same color
     * @return a boolean that says whether the island on the right has a tower or not
     */
    public boolean hasRight() {
        for(int i= 0; i<board.getIslands().size(); i++) {
            if(board.getIslands().get(i).getIslandID() == islandID) {
                if(board.getIslands().get(i).getIslandID() == board.getIslands().get(board.getIslands().size()-1).getIslandID()) {
                    if(board.getIslands().get(0).hasTower()) {
                        if(board.getIslands().get(0).getTower().getColor() == tower.getColor()) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                } else {
                    if(board.getIslands().get(i+1).hasTower()) {
                        if(board.getIslands().get(i+1).getTower().getColor() == tower.getColor())
                            return true;
                        else
                            return false;
                    }
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
        mergedTowers = new ArrayList<>();
        mergedTowers.add(tower);
    }

    /**
     * Put towers back into their area when an island is conquered
     * @param towerArea
     */
    public void moveTowerToArea(TowerArea towerArea) {
        for(Tower t : mergedTowers) {
            towerArea.addTowers(t);
        }

        mergedTowers.clear();
    }
}