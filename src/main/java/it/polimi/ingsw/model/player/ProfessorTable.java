package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.enumerations.PawnType;

import it.polimi.ingsw.model.player.BoardCell;

import java.util.ArrayList;

public class ProfessorTable {
    private ArrayList<BoardCell> professorTable;

    public ProfessorTable (){
        ArrayList<PawnType> pawns = new ArrayList<PawnType>;
        pawns.add(PawnType.BLUE, PawnType.GREEN, PawnType.PINK, PawnType.RED, PawnType.YELLOW);
        for (PawnType p : pawns) {
            BoardCell cell = new BoardCell(p);
        }
    }
}
