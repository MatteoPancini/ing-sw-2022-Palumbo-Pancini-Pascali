package it.polimi.ingsw.model.player;

public class SchoolBoard {
    private int idBoard;
    private DiningRoom diningRoom;
    private Entrance entrance;
    private ProfessorTable professorTable;
    private TowerArea towerArea;

    public class BoardID(int idBoard, DiningRoom diningRoom, Entrance entrance,
                         ProfessorTable professorTable, TowerArea towerArea){
        this.idBoard = idBoard;
        this.diningRoom = diningRoom;
        this.entrance = entrance;
        this.towerArea = towerArea;
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

}
