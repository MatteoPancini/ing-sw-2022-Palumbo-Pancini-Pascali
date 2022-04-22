package it.polimi.ingsw.model.cards;

//TODO: Consigliato di usare un Factory Method per il JSON nel costruttore


import it.polimi.ingsw.model.enumerations.Characters;

public class CharacterCard {
    private final Characters name;
    private final String effect;
    private final int initialCost;

    public CharacterCard(Characters name, String effect, int initialCost){
        this.name = name;
        this.effect = effect;
        this.initialCost = initialCost;
    }
}

