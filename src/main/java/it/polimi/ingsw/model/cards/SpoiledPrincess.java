package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.Characters;

public class SpoiledPrincess {
    private static SpoiledPrincess spoiledPrincess = null;
    private static Characters character;

    private SpoiledPrincess(Characters character){
        this.character = character;
    }

    public static SpoiledPrincess getSpoiledPrincess() {
        if(spoiledPrincess == null) {
            spoiledPrincess = new SpoiledPrincess(Characters.SPOILED_PRINCESS);
        }
        return spoiledPrincess;
    }

}