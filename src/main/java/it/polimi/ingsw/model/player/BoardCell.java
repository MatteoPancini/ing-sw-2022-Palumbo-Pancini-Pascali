package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.Professor;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.enumerations.PawnType;

import java.io.Serializable;

public class BoardCell implements Serializable {
    private PawnType boardCellType;
    private boolean coinCell;
    private Student student;
    private Professor professor;

    public BoardCell(PawnType boardCellType) {
        this.boardCellType = boardCellType;
        coinCell = false;
    }
    public boolean hasStudent() {
        if(this.student!=null) {
            return true;
        }
        return false;
    }

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

    public void removeStudent() {
        this.student = null;
    }


    public void setProfessor(Professor professor){
        this.professor = professor;
    }

    public boolean hasProfessor() {
        if(professor != null) {
            return true;
        }
        return false;
    }

    public void resetProfessor() {
        this.professor = null;
    }
}