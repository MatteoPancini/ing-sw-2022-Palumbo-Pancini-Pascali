package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.CardState;
import it.polimi.ingsw.model.player.Player;

//TODO: Consigliato di usare un Factory Method per il JSON nel costruttore

public class AssistantCard {
    private final int value;
    private final int moves;
    private CardState state;
    private Player owner;

    public AssistantCard(int value, int moves) {
        this.value = value;
        this.moves = moves;
    }

    public int getValue() {
        return value;
    }

    public int getMoves() {
        return moves;
    }

    public CardState getState() {
        return state;
    }

    public void setState(CardState state){
        this.state = state;
    }

    public Player getOwner() {
        return owner;
    }
}