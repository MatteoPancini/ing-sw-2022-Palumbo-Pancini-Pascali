package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.Professor;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.enumerations.PawnType;

import java.io.Serializable;

/**
 * Class BoardCell represents the single cell of a table in the board, containing the students
 */
public class BoardCell implements Serializable {
    private PawnType boardCellType;
    private boolean coinCell;
    private Student student;
    private Professor professor;

    public BoardCell(PawnType boardCellType) {
        this.boardCellType = boardCellType;
        coinCell = false;
    }

    /**
     * Check if a board has a student
     * @return a boolean that says whether a board cell has a student or not
     */
    public boolean hasStudent() {
        if(this.student!=null) {
            return true;
        }
        return false;
    }

    /**
     * Check if a board has a coin
     * @return a boolean that says whether a board cell has a coin or not
     */
    public boolean hasCoin() {
        return coinCell;
    }

    public void setCoinCell() { coinCell = true; }

    public PawnType getBoardCellType() {
        return boardCellType;
    }

    public void setStudent(Student student){
        this.student = student;
    }

    /**
     * Remove student from a board cell
     */
    public void removeStudent() {
        this.student = null;
    }

    public void setProfessor(Professor professor){
        this.professor = professor;
    }

    /**
     * Check if a board has a professor
     * @return a boolean that says whether a board cell has a professor or not
     */
    public boolean hasProfessor() {
        if(professor != null) {
            return true;
        }
        return false;
    }


    /**
     * Empty a board cell that contains a professor when that professor is moved
     */
    public void resetProfessor() {
        this.professor = null;
    }
}