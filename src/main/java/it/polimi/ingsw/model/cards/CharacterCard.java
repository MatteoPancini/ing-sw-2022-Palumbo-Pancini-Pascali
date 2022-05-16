package it.polimi.ingsw.model.cards;


import it.polimi.ingsw.model.enumerations.Characters;

public class CharacterCard {
    private final Characters name;
    private final String effect;
    private final int defaultCost;
    private int cost;

    public CharacterCard(Characters name, String effect, int cost){
        this.name = name;
        this.effect = effect;
        this.defaultCost = cost;
    }

    public CharacterCard() {
        name = null;
        effect = null;
        defaultCost = 0;
    }

    public void incrementPrice(){
        cost++;
    }

    public void resetCost() {
        this.cost = defaultCost;
    }

    public String getEffect() {
        return effect;
    }

    public Characters getName() {
        return name;
    }
}

