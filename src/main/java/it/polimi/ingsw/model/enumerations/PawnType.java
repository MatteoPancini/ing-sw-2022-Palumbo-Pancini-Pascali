package it.polimi.ingsw.model.enumerations;

//hashMap tra pawntype e intero che indica il tavolo della diningroom a cui appartiene:
// verde = 0, rosso = 1, giallo = 2, rosa = 3, blu = 4;

import java.util.HashMap;
import java.util.Map;

public enum PawnType {
    GREEN(0), RED(1), YELLOW(2), PINK(3), BLUE(4);

    private int pawnID;
    private static Map<Integer, PawnType> idMapType= new HashMap<>();

    PawnType(int pawnID) {
        this.pawnID = pawnID;
    }

    public static PawnType getPawnFromID(int pawnID){
        return idMapType.get(pawnID);
    }

    public int getPawnID(){
        return pawnID;
    }



}
