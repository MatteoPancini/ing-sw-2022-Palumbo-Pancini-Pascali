package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.enumerations.PawnType;

import java.util.Locale;

public class Student {
    private PawnType type;

    public Student(PawnType type){
        this.type = type;
    }

    public PawnType getType() {
        System.out.println("Boh");
        return this.type;
    }
}
