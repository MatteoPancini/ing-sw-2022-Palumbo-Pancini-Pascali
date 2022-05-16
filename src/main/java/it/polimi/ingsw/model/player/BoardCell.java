package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.Professor;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.enumerations.PawnType;

import java.util.ArrayList;

public class BoardCell {
    private PawnType boardCellType;
    private boolean coinCell;
    private boolean studentCell;
    private boolean professorCell;
    private Professor professor;
    private Student student;

    public BoardCell(PawnType boardCellType) {
        this.boardCellType = boardCellType;
        coinCell = false;
    }

    public boolean hasCoin(){
        return coinCell;
    };

    public void setCoinCell() { coinCell = true; }

    public boolean hasProfessor() { return professorCell; }

    public PawnType getBoardCellType() {
        return boardCellType;
    }
}