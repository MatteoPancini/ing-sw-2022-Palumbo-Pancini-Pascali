package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.enumerations.Wizards;
import it.polimi.ingsw.model.player.Player;
import com.google.gson.Gson;

import java.io.Serializable;

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
    //costruttore per la model view
    /*public AssistantCard(Assistants assistantName, int value, int moves) {
        name = assistantName;
        this.value = value;
        this.moves = moves;
        this.wizard = null;
        owner = null;
    }

    public AssistantCard(Assistants ass) {
        this.name = ass;
        if(ass.equals(Assistants.EAGLE)) {
            this.moves = 2;
            this.value = 4;
        }
        else if(ass.equals(Assistants.DOG)) {
            this.moves = 4;
            this.value = 8;
        }
        else if(ass.equals(Assistants.ELEPHANT)) {
            this.moves = 5;
            this.value = 9;
        }
        else if(ass.equals(Assistants.CAT)) {
            this.moves = 2;
            this.value = 3;
        }
        else if(ass.equals(Assistants.CHEETAH)) {
            this.moves = 1;
            this.value = 1;
        }
        else if(ass.equals(Assistants.LIZARD)) {
            this.moves = 3;
            this.value = 6;
        }
        else if(ass.equals(Assistants.OCTOPUS)) {
            this.moves = 4;
            this.value = 7;
        }
        else if(ass.equals(Assistants.OSTRICH)) {
            this.moves = 1;
            this.value = 2;
        }
        else if(ass.equals(Assistants.TURTLE)) {
            this.moves = 5;
            this.value = 10;
        }
        else if(ass.equals(Assistants.FOX)) {
            this.moves = 3;
            this.value = 5;
        }
    }*/

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

    public void setWizard(Wizards wizard) {
        this.wizard = wizard;
    }

    public Wizards getWizard() { return wizard; }
}