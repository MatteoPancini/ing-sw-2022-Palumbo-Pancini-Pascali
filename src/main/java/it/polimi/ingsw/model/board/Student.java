package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.enumerations.PawnType;

public class Student {
    private PawnType type;

    public Student(PawnType studentType) {
        this.type = studentType;
    }

    public PawnType getType() {
        return this.type;
    }
}
