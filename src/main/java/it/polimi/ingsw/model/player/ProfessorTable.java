package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.enumerations.PawnType;

import it.polimi.ingsw.model.player.BoardCell;

import java.util.ArrayList;

public class ProfessorTable {
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
}