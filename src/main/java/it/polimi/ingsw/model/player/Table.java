package it.polimi.ingsw.model.player;

import java.io.Serializable;

import java.util.ArrayList;

import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.enumerations.PawnType;

public class Table implements Serializable {
    private ArrayList<BoardCell> table = new ArrayList<>();
    private int lastPosition = 0;

    public Table(PawnType p) {
        for (int i = 1; i <= 10; i++) {
            BoardCell cell = new BoardCell(p);
            if(i == 3 || i == 6 || i == 9) cell.setCoinCell();
            table.add(cell);
        }
    }

    public int getLastPosition() {
        return lastPosition;
    }

    public ArrayList<BoardCell> getTable() {
        return table;
    }

    public PawnType getColor() {
        return table.get(0).getBoardCellType();
    }

    public void removeStudent() {
        table.get(lastPosition - 1).removeStudent();
        lastPosition--;
        System.err.println(lastPosition);

    }



    public void addStudent(Student stud){
        table.get(lastPosition).setStudent(stud);
        lastPosition++;
    }

    public int getTableStudentsNum() {
        int studentsNum = 0;
        for(BoardCell b : table) {
            if(b.hasStudent()) {
                studentsNum++;
            }
        }

        return studentsNum;
    }

}