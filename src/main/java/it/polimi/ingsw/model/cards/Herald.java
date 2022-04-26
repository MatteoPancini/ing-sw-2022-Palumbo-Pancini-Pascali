package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.Characters;

public class Herald {
    private static Herald herald = null;
    private static Characters character;

    private Herald(Characters character){
        this.character = character;
    }

    public static Herald getHerald() {
        if(herald == null) {
            herald = new Herald(Characters.HERALD);
        }
        return herald;
    }

}
