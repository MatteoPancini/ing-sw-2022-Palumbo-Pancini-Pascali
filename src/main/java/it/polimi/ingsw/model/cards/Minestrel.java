package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.Characters;

public class Minestrel {
    private static Minestrel minestrel = null;
    private static Characters character;

    private Minestrel(Characters character){
        this.character = character;
    }

    public static Minestrel getMinestrel() {
        if(minestrel == null) {
            minestrel = new Minestrel(Characters.MINESTREL);
        }
        return minestrel;
    }

}