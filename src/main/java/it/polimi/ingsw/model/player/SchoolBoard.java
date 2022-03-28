package it.polimi.ingsw.model.player;

public class SchoolBoard {
    private BoardID idBoard;
    private DiningRoom diningRoom;
    private Entrance entrance;
    private ProfessorTable professorTable;
    private TowerArea towerArea;

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
