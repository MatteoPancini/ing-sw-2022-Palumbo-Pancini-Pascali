package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.enumerations.PawnType;

import java.util.ArrayList;

public class DiningRoom {
    private ArrayList<Table> diningRoom = new ArrayList<Table>();

    public DiningRoom () {
        ArrayList<PawnType> pawns = new ArrayList<PawnType>();
        pawns.add(PawnType.BLUE);
        pawns.add(PawnType.GREEN);
        pawns.add(PawnType.PINK);
        pawns.add(PawnType.RED);
        pawns.add(PawnType.YELLOW);
        for(PawnType p : pawns){
            Table table = new Table(p);
            diningRoom.add(table);
        }
    }
}
