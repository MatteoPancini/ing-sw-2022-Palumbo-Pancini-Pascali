package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.enumerations.Wizards;
import it.polimi.ingsw.model.player.Player;

public class AssistantCard {
    private final Assistants name;
    private final int orderValue;
    private final int motherNatureMoves;
    private Wizards wizard;
    private Player owner;

    public AssistantCard(){
        name = null;
        orderValue = 0;
        motherNatureMoves = 0;
        wizard = null;
        owner = null;
    }
    public AssistantCard(Assistants name, int orderValue, int motherNatureMoves) {
        this.name = name;
        this.orderValue = orderValue;
        this.motherNatureMoves = motherNatureMoves;
    }

    public Assistants getName() {
        return name;
    }

    public int getValue() {
        return orderValue;
    }

    public int getMoves() {
        return motherNatureMoves;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Wizards getWizard(){ return wizard; }

    public void setWizard(Wizards wizard){ this.wizard = wizard; }
}