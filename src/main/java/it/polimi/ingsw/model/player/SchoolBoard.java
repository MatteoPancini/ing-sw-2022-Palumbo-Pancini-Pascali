package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Student;

public class SchoolBoard {
    private int boardID;
    private DiningRoom diningRoom;
    private Entrance entrance;
    private ProfessorTable professorTable;
    private TowerArea towerArea;

    public SchoolBoard(int boardID){
        this.boardID = boardID;
        diningRoom = new DiningRoom();
        entrance = new Entrance();
        professorTable = new ProfessorTable();
        towerArea = new TowerArea();
    }

    public int getBoardID() {
        return boardID;
    }

    public DiningRoom getDiningRoom() {
        return diningRoom;
    }

    public Entrance getEntrance() {
        return entrance;
    }

    public ProfessorTable getProfessorTable() {
        return professorTable;
    }

    public TowerArea getTowerArea() {
        return towerArea;
    }

    public void setEntrance(Entrance entrance){ this.entrance = entrance; }

    public void setTowerArea(TowerArea towerArea){ this.towerArea = towerArea; }

    /*public boolean isStudentInEntrance(String input) {

    }*/
}
