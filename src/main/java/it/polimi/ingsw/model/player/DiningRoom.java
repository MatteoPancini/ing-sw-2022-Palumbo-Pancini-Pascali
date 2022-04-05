package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.enumerations.PawnType;

import java.util.ArrayList;

public class DiningRoom {
    private ArrayList<Table> diningRoom;

    public DiningRoom () {
        ArrayList<PawnType> pawns = new ArrayList<PawnType>();
        pawns.add(PawnType.BLUE, PawnType.GREEN, PawnType.PINK, PawnType.RED, PawnType.YELLOW);
        for(PawnType p : pawns){
            Table table = new Table(p);
            diningRoom.add(table);
        }
    }
}
