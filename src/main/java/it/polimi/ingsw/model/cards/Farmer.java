package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.Characters;

public class Farmer {
    private static Farmer farmer = null;
    private static Characters character;
    private static String effect;
    private static int cost;

    private Farmer(Characters character){
        this.character = character;
    }

    public static Farmer getFarmer() {
        if(farmer == null) {
            farmer = new Farmer(Characters.FARMER);
        }
        return farmer;
    }

}