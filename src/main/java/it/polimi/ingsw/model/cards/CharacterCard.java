package it.polimi.ingsw.model.cards;


import it.polimi.ingsw.model.enumerations.Characters;

public class CharacterCard {
    private final Characters name;
    private final String effect;
    private int cost;

    public CharacterCard(Characters name, String effect, int cost){
        this.name = name;
        this.effect = effect;
        this.cost = cost;
    }

    public CharacterCard() {
        name = null;
        effect = null;
    }

    public void incrementPrice(){
        cost++;
    }

    public String getEffect() {
        return effect;
    }


}

