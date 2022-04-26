package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.Characters;

public class Fungarus {
    private static Fungarus fungarus = null;
    private static Characters character;

    private Fungarus(Characters character){
        this.character = character;
    }

    public static Fungarus getFungarus() {
        if(fungarus == null) {
            fungarus = new Fungarus(Characters.FUNGARUS);
        }
        return fungarus;
    }

}
