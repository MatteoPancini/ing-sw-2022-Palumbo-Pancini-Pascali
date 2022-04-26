package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.Characters;

public class Knight {
    private static Knight knight = null;
    private static Characters character;

    private Knight(Characters character){
        this.character = character;
    }

    public static Knight getKnight() {
        if(knight == null) {
            knight = new Knight(Characters.KNIGHT);
        }
        return knight;
    }

}