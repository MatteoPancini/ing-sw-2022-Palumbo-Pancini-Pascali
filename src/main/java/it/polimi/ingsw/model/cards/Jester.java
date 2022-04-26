package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.Characters;

public class Jester {
    private static Jester jester = null;
    private static Characters character;
    private static String effect;
    private static int cost;

    private Jester(Characters character){
        this.character = character;
    }

    public static Jester getJester() {
        if(jester == null) {
            jester = new Jester(Characters.JESTER);
        }
        return jester;
    }

}