package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.Professor;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.enumerations.PawnType;

import java.util.ArrayList;

public class BoardCell {
    private PawnType boardCellType;
    private boolean coinCell;
    private Student student;
    private Professor professor;

    public BoardCell(PawnType boardCellType) {
        this.boardCellType = boardCellType;
        coinCell = false;
    }

    public boolean hasCoin(){
        return coinCell;
    };

    public void setCoinCell() { coinCell = true; }

    public PawnType getBoardCellType() {
        return boardCellType;
    }

    public void setBoardCellType(PawnType boardCellType) {
        this.boardCellType = boardCellType;
    }

    public void setStudent(Student student){
        this.student = student;
    }


    public void setProfessor(Professor professor){
        this.professor = professor;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void resetProfessor() {
        this.professor = null;
    }
}