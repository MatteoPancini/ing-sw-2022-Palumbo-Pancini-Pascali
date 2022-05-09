package it.polimi.ingsw.model.cards;


import it.polimi.ingsw.model.enumerations.Characters;

public class CharacterCard {
    private final Characters name;
    private final String effect;
    private int initialCost;

    public CharacterCard(Characters name, String effect, int initialCost){
        this.name = name;
        this.effect = effect;
        this.initialCost = initialCost;
    }

    public void incrementPrice(){
        initialCost++;
    }
}

