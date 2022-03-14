package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.enumerations.PawnType;

public class BoardCell {
    private PawnType boardCellType;
    private boolean coinCell;
    private boolean studentCell;
    private boolean professorCell;
    //TODO: pensare a possibile hashmap tra BoardCell e PawnType

    public BoardCell() {

    }
    public boolean hasCoin(){
        return coinCell;
    };

    public boolean hasStudent(){
        return studentCell;
    };

    public boolean hasProfessor(){
        return professorCell;
    };
}
