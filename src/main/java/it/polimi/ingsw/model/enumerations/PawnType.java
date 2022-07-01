package it.polimi.ingsw.model.enumerations;

/**
 * Enumeration containing the 5 pawn types of the game's pawns, with an ID used for cycles in the game
 */
public enum PawnType {
    GREEN(0), RED(1), YELLOW(2), PINK(3), BLUE(4);

    private int pawnID;

    PawnType(int pawnID) {
        this.pawnID = pawnID;
    }

    public int getPawnID(){
        return pawnID;
    }



}
