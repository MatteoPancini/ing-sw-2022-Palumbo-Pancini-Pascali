package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.enumerations.CardState;
import it.polimi.ingsw.model.enumerations.Wizards;
import it.polimi.ingsw.model.player.Player;
import com.google.gson.Gson;


//TODO: Consigliato di usare un Factory Method per il JSON nel costruttore

public class AssistantCard {
    private final Assistants name;
    private final int value;
    private final int moves;
    private final Wizards wizard;
    private CardState state;
    private Player owner;

    public AssistantCard(Assistants assistantName, int value, int moves, Wizards wizard) {
        name = assistantName;
        this.value = value;
        this.moves = moves;
        this.wizard = wizard;
        owner = null;
    }

    public Assistants getName() {
        return name;
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

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}