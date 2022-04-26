package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.Characters;

public class Monk {
    private static Monk monk = null;
    private static Characters character;
    private static String effect;
    private static int cost;

    private Monk(Characters character){
        this.character = character;
    }

    public static Monk getMonk() {
        if(monk == null) {
            monk = new Monk(Characters.MONK);
        }
        return monk;
    }

}