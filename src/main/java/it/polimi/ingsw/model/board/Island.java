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

    public void setNoEntry(boolean noEntry) {
        this.noEntry = noEntry;
    }

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

    public void doubleMerge() {
        System.out.println("Faccio doble merge DI " + islandID);
        Island iBefore = null;
        Island iAfter = null;

        for(int i=0; i<board.getIslands().size(); i++) {
            if(islandID != 1) {
                if(board.getIslands().get(i+1).getIslandID() == islandID) {
                    iBefore = board.getIslands().get(i);
                    if(board.getIslands().get(i+1).getIslandID() == board.getIslands().get(board.getIslands().size()-1).getIslandID()) { //sono nell'ultima
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
        System.out.println("Island before: " + iBefore.getIslandID());
        System.out.println("Island after: " + iAfter.getIslandID());

        System.out.println("Aggiungo " + islandID + " e " + iAfter.getIslandID() + " a " + iBefore.getIslandID());
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

        System.out.println("Rimuovo " + iAfter.getIslandID() + " , " + islandID);

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

    public void merge(Island island) {
        System.out.println("Faccio merge");
        if(this.islandID < island.getIslandID()) {
            System.out.println("Aggiungo " + island.getIslandID() + " a " + islandID);
            for(Island i : island.getMergedIslands()) {
                System.out.println("Aggiungo isola");
                mergedIsland.add(i);
            }


            System.out.println("Merged island size " + island.getMergedIslands().size());
            for(Tower t : island.getMergedTowers()) {
                System.out.println("Aggiungo torre");
                mergedTowers.add(t);
            }
            System.out.println(island.getMergedTowers().size());

            for(Student s : island.getStudents()) {
                System.out.println("Aggiungo stude");
                students.add(s);
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
            System.out.println("Aggiungo " + islandID + " a " + island.getIslandID());
            for(Island i : mergedIsland) {
                System.out.println("Aggiungo isola");
                island.getMergedIslands().add(i);
            }

            for(Tower t : mergedTowers) {
                System.out.println("Aggiungo torre");
                island.getMergedTowers().add(t);
            }

            for(Student s : getStudents()) {
                System.out.println("Aggiungo studenti");

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


    public boolean hasLeft(){
        if(islandID != 1) {
            /*
            Island island1 = board.getIslands().get(islandID - 2);
            System.out.println("Controllo prima " + );
            Island island2 = board.getIslands().get(islandID - 1);

             */
            for(int i = 0; i < board.getIslands().size(); i++) {
                if(board.getIslands().get(i).getIslandID() == islandID) {
                    System.out.println("Analizzo "+ board.getIslands().get(i - 1).getIslandID());
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
            System.out.println("Sono nella prima");
            if(board.getIslands().get(board.getIslands().size() -1).hasTower() == true) {
                if(board.getIslands().get(board.getIslands().size() -1).getTower().getColor() == tower.getColor()) {
                    return true;
                } else {
                    return false;
                }
            }
            /*
            for(int i=0; i<board.getIslands().size(); i++) {
                System.out.println(board.getIslands().size() + " , " + i);
                if(i == board.getIslands().size() - 1) {
                    System.out.println("ENTRO");
                    if(board.getIslands().get(i).hasTower() == true) {
                        if(board.getIslands().get(i).getTower().getColor() == tower.getColor()) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }


            }

             */
        }
        return false;
    }

    public boolean hasRight() {
        for(int i= 0; i<board.getIslands().size(); i++) {
            if(board.getIslands().get(i).getIslandID() == islandID) {
                if(board.getIslands().get(i).getIslandID() == board.getIslands().get(board.getIslands().size()-1).getIslandID()) { //sono nell'ultima
                    System.out.println("Sono nell'ultima Analizzo "+ board.getIslands().get(0).getIslandID());
                    if(board.getIslands().get(0).hasTower()) {
                        if(board.getIslands().get(0).getTower().getColor() == tower.getColor()) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                } else {
                    System.out.println("Non ultima Analizzo "+ board.getIslands().get(i+1).getIslandID());
                    if(board.getIslands().get(i+1).hasTower()) {
                        if(board.getIslands().get(i+1).getTower().getColor() == tower.getColor())
                            return true;
                        else
                            return false;
                    }
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
        System.out.println("Setto torre");
        this.tower = tower;
        mergedTowers = new ArrayList<Tower>();
        mergedTowers.add(tower);
    }

    public void moveTowerToArea(TowerArea towerArea) {
        System.out.println("Muovo torre to area ");
        for(Tower t : mergedTowers) {
            System.out.println(t.getColor());
            towerArea.addTowers(t);
        }

        mergedTowers.clear();
    }
}