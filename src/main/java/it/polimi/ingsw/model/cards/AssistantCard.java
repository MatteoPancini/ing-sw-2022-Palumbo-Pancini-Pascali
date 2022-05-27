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
    /*
    public AssistantCard(Assistants assistantName, int orderValue, int motherNatureMoves) {
        name = assistantName;
        this.orderValue = orderValue;
        this.motherNatureMoves = motherNatureMoves;
        this.wizard = null;
        owner = null;
    }

    public AssistantCard(Assistants ass, Wizards wizard) {
        this.name = ass;
        if(ass.equals(Assistants.EAGLE)) {
            this.motherNatureMoves = 2;
            this.orderValue = 4;
            this.wizard = wizard;
        }
        else if(ass.equals(Assistants.DOG)) {
            this.motherNatureMoves = 4;
            this.orderValue = 8;
            this.wizard = wizard;

        }
        else if(ass.equals(Assistants.ELEPHANT)) {
            this.motherNatureMoves = 5;
            this.orderValue = 9;
            this.wizard = wizard;

        }
        else if(ass.equals(Assistants.CAT)) {
            this.motherNatureMoves = 2;
            this.orderValue = 3;
            this.wizard = wizard;

        }
        else if(ass.equals(Assistants.CHEETAH)) {
            this.motherNatureMoves = 1;
            this.orderValue = 1;
            this.wizard = wizard;

        }
        else if(ass.equals(Assistants.LIZARD)) {
            this.motherNatureMoves = 3;
            this.orderValue = 6;
            this.wizard = wizard;

        }
        else if(ass.equals(Assistants.OCTOPUS)) {
            this.motherNatureMoves = 4;
            this.orderValue = 7;
            this.wizard = wizard;

        }
        else if(ass.equals(Assistants.OSTRICH)) {
            this.motherNatureMoves = 1;
            this.orderValue = 2;
            this.wizard = wizard;

        }
        else if(ass.equals(Assistants.TURTLE)) {
            this.motherNatureMoves = 5;
            this.orderValue = 10;
            this.wizard = wizard;

        }
        else if(ass.equals(Assistants.FOX)) {
            this.motherNatureMoves = 3;
            this.orderValue = 5;
            this.wizard = wizard;

        }
    }

     */

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