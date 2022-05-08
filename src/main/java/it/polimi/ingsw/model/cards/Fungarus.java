package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.Characters;

public class Fungarus {
    private static Fungarus fungarus = null;
    private static Characters character;
    private static int cost;

    private Fungarus(){
        this.character = Characters.FUNGARUS;
    }

    public static Fungarus getFungarus() {
        if(fungarus == null) {
            fungarus = new Fungarus();
        }
        return fungarus;
    }

}
