package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.Characters;

public class MagicPostman {
    private static MagicPostman magicPostman = null;
    private static Characters character;
    private int

    private MagicPostman(Characters character){
        this.character = character;
    }

    public static MagicPostman getMagicPostman() {
        if(magicPostman == null) {
            magicPostman = new magicPostman(Characters.MAGIC_POSTMAN);
        }
        return magicPostman;
    }

}