package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.Characters;

public class MagicPostman {
    private static MagicPostman magicPostman = null;
    private static Characters character;

    private MagicPostman(Characters character){
        this.character = character;
    }

    public static MagicPostman getMagicPostman() {
        if(magicPostman == null) {
            magicPostman = new MagicPostman(Characters.MAGIC_POSTMAN);
        }
        return magicPostman;
    }

}