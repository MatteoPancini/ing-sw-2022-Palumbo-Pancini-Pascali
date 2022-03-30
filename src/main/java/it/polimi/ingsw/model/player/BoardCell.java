package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.enumerations.PawnType;

import java.util.ArrayList;

public class BoardCell {
    private PawnType boardCellType;
    private boolean coinCell;
    private boolean studentCell;
    private boolean professorCell;
    //TODO: pensare a possibile hashmap tra BoardCell e PawnType

    public BoardCell() {
        ArrayList<PawnType> pawns = new ArrayList<PawnType>;
        pawns.add(PawnType.BLUE, PawnType.GREEN, PawnType.PINK, PawnType.RED, PawnType.YELLOW);
        for (PawnType p : pawns) {
            for (int j = 1; j <= 11; j++) {
                this.boardCellType = p;
                if(j == 3 || j == 6 || j == 9) coinCell = true;
                else coinCell = false;
                studentCell = false;
                professorCell = false;
            }
        }
    }

    public boolean hasCoin(){
        return coinCell;
    };

    public boolean hasStudent(){ return studentCell; };

    public boolean hasProfessor(){ return professorCell; };

}
