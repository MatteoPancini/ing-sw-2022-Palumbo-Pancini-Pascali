package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.enumerations.PawnType;

import it.polimi.ingsw.model.player.BoardCell;

import java.io.Serializable;
import java.util.ArrayList;

public class ProfessorTable implements Serializable {
    private ArrayList<BoardCell> professorTable = new ArrayList<BoardCell>();

    public ProfessorTable (){
        ArrayList<PawnType> pawns = new ArrayList<PawnType>();
        pawns.add(PawnType.BLUE);
        pawns.add(PawnType.GREEN);
        pawns.add(PawnType.PINK);
        pawns.add(PawnType.RED);
        pawns.add(PawnType.YELLOW);
        for (PawnType p : pawns) {
            BoardCell cell = new BoardCell(p);
            professorTable.add(cell);
        }
    }

    public ArrayList<BoardCell> getProfessorTable() {
        return professorTable;
    }

    public BoardCell getCellByColor(PawnType color) {
        for(BoardCell b : professorTable) {
            if(b.getBoardCellType() == color){
                return b;
            }
        }
        return null;
    }

}
