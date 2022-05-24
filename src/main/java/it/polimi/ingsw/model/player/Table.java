package it.polimi.ingsw.model.player;

import java.io.Serializable;
import java.lang.reflect.Array;

import java.util.ArrayList;

import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.enumerations.PawnType;

public class Table implements Serializable {
    private ArrayList<BoardCell> table = new ArrayList<BoardCell>();
    private int lastPosition = 0;

    public Table(PawnType p) {
        for (int i = 1; i <= 10; i++) {
            BoardCell cell = new BoardCell(p);
            if(i == 3 || i == 6 || i == 9) cell.setCoinCell();
            table.add(cell);
        }
    }

    public ArrayList<BoardCell> getTable() {
        return table;
    }

    public PawnType getColor() {
        return table.get(0).getBoardCellType();
    }

    public void addStudent(Student stud){
        this.getTable().get(lastPosition).setStudent(stud);
        lastPosition++;
        return;
    }

}