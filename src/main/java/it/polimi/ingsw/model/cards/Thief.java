package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.Characters;

public class Thief {
    private static Thief thief = null;
    private static Characters character;

    private Thief(Characters character){
        this.character = character;
    }

    public static Thief getThief() {
        if(thief == null) {
            thief = new Thief(Characters.THIEF);
        }
        return thief;
    }

}