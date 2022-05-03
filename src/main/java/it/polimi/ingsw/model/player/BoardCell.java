package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.enumerations.PawnType;

import java.util.ArrayList;

public class BoardCell {
    private PawnType boardCellType;
    private boolean coinCell;
    private boolean studentCell;
    private boolean professorCell;

    public BoardCell(PawnType boardCellType) {
        this.boardCellType = boardCellType;
        coinCell = false;
        studentCell = false;
        professorCell = false;
    }

    public boolean hasCoin(){
        return coinCell;
    };

    public boolean hasStudent() { return studentCell; };

    public boolean hasProfessor() { return professorCell; };

    public void setCoinCell() { coinCell = true; }

    public PawnType getBoardCellType() {
        return boardCellType;
    }
}