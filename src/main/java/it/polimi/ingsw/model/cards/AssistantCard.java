package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.enumerations.Wizards;
import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;

/**
 * Class used to implement an assistant card and assign its attributes
 */
public class AssistantCard implements Serializable {
    private final Assistants name;
    private int orderValue;
    private int motherNatureMoves;
    private Wizards wizard = null;
    private Player owner;


    public AssistantCard(Assistants name, int orderValue, int motherNatureMoves) {
        this.name = name;
        this.orderValue = orderValue;
        this.motherNatureMoves = motherNatureMoves;
        owner = null;
    }

    public Assistants getName() { return name; }

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

    public void setWizard(Wizards wizard) {
        this.wizard = wizard;
    }

    public Wizards getWizard() { return wizard; }
}