package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.CardState;
import it.polimi.ingsw.model.enumerations.MotherNatureMoves;
import it.polimi.ingsw.model.enumerations.OrderValue;
import it.polimi.ingsw.model.enumerations.Wizard;

public class AssistantCard {
    private Wizard type;
    private OrderValue value;
    private MotherNatureMoves moves;
    private CardState state;

    public Wizard getType() {
        return type;
    }

    public OrderValue getValue() {
        return value;
    }

    public MotherNatureMoves getMoves() {
        return moves;
    }

    public CardState getState() {
        return state;
    }

    public void setState(CardState state){
        this.state = state;
    }
}