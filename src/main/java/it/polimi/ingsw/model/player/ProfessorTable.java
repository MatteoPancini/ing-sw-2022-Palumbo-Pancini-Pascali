package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.enumerations.PawnType;

import java.io.Serializable;
import java.util.ArrayList;

public class ProfessorTable implements Serializable {
    private ArrayList<BoardCell> profTable = new ArrayList<>();

    public ProfessorTable (){
        ArrayList<PawnType> pawns = new ArrayList<>();
        pawns.add(PawnType.BLUE);
        pawns.add(PawnType.GREEN);
        pawns.add(PawnType.PINK);
        pawns.add(PawnType.RED);
        pawns.add(PawnType.YELLOW);
        for (PawnType p : pawns) {
            BoardCell cell = new BoardCell(p);
            profTable.add(cell);
        }
    }

    public ArrayList<BoardCell> getProfTable() {
        return profTable;
    }

    public BoardCell getCellByColor(PawnType color) {
        for(BoardCell b : profTable) {
            if(b.getBoardCellType() == color){
                return b;
            }
        }
        return null;
    }

}
