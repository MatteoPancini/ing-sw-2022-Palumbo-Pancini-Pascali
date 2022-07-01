package it.polimi.ingsw.model.player;

import java.io.Serializable;

import java.util.ArrayList;

import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.enumerations.PawnType;

/**
 * Class used to implement the tables
 */
public class Table implements Serializable {
    private ArrayList<BoardCell> diningTable = new ArrayList<>();
    private int lastPosition = 0;

    public Table(PawnType p) {
        for (int i = 1; i <= 10; i++) {
            BoardCell cell = new BoardCell(p);
            if(i == 3 || i == 6 || i == 9) cell.setCoinCell();
            diningTable.add(cell);
        }
    }

    public int getLastPosition() {
        return lastPosition;
    }

    public ArrayList<BoardCell> getDiningTable() {
        return diningTable;
    }

    public PawnType getColor() {
        return diningTable.get(0).getBoardCellType();
    }

    /**
     * Remove a student from a table when the owner wants to move it
     */
    public void removeStudent() {
        diningTable.get(lastPosition - 1).removeStudent();
        lastPosition--;
    }

    /**
     * Put a student in a table
     * @param stud -> student to add
     */
    public void addStudent(Student stud){
        diningTable.get(lastPosition).setStudent(stud);
        lastPosition++;
    }

    /**
     * Calculate the number of students in a table
     * @return the number of students
     */
    public int getTableStudentsNum() {
        int studentsNum = 0;
        for(BoardCell b : diningTable) {
            if(b.hasStudent()) {
                studentsNum++;
            }
        }

        return studentsNum;
    }
}