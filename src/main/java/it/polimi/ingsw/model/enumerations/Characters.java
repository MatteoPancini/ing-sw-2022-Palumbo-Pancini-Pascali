package it.polimi.ingsw.model.enumerations;

import java.util.HashMap;
import java.util.Map;

public enum Characters {
    HERALD(1),
    KNIGHT(2),
    CENTAUR(3),
    FARMER(4),
    FUNGARUS(5),
    JESTER(6),
    THIEF(7),
    MINESTREL(8),
    MONK(9),
    GRANNY_HERBS(10),
    MAGIC_POSTMAN(11),
    SPOILED_PRINCESS(12);


    private int characterID;
    private static Map<Integer, Characters> map= new HashMap<>();

    Characters(int characterID) {
        this.characterID = characterID;
    }

    public static Characters getCharacterFromID(int characterID) {

        return map.get(characterID);
    }

    public int getCharacterID(){

        return characterID;
    }

}
