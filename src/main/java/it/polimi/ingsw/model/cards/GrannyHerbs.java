package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.Characters;

public class GrannyHerbs {
    private static GrannyHerbs grannyHerbs = null;
    private static Characters character;

    private GrannyHerbs(Characters character){
        this.character = character;
    }

    public static GrannyHerbs getGrannyHerbs() {
        if(grannyHerbs == null) {
            grannyHerbs = new GrannyHerbs(Characters.GRANNY_HERBS);
        }
        return grannyHerbs;
    }

}