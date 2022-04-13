package it.polimi.ingsw.model.player;

import java.lang.reflect.Array;

import java.util.ArrayList;

import it.polimi.ingsw.model.enumerations.PawnType;

public class Table {
    private ArrayList<BoardCell> table = new ArrayList<BoardCell>();

    public Table(PawnType p) {
        for (int i = 1; i <= 10; i++) {
            BoardCell cell = new BoardCell(p);
            if(i == 3 || i == 6 || i == 9) cell.setCoinCell();
            table.add(cell);
        }
    }
}

