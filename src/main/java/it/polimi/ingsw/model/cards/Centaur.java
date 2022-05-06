package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.Characters;

public class Centaur {
    private static Centaur centaur = null;
    private static Characters character;

    private Centaur(Characters character){
        this.character = character;
    }

    public static Centaur getCentaur() {
        if(centaur == null) {
            centaur = new Centaur(Characters.CENTAUR);
        }
        return centaur;
    }

}