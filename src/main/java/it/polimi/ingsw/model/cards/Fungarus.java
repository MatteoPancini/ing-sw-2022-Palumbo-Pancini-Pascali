package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.Characters;

public class Fungarus extends CharacterCard {
    private static Fungarus fungarus = null;
    private static Characters character;
    private static int cost;

    public Fungarus() {
        this.character = Characters.FUNGARUS;
    }

    public static Fungarus getFungarus() {
        if(fungarus == null) {
            fungarus = new Fungarus();
        }
        return fungarus;
    }

    public void resetCost() {
        cost = 3;
    }

}
