package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.enumerations.PawnType;

import java.io.Serializable;

public class Student implements Serializable {
    private PawnType type;

    public Student(PawnType type){
        this.type = type;
    }

    public PawnType getType() { return this.type; }
}
