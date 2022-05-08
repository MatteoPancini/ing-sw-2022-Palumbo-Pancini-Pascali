package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.enumerations.CardState;
import it.polimi.ingsw.model.enumerations.Wizards;
import it.polimi.ingsw.model.player.Player;
import com.google.gson.Gson;

public class AssistantCard {
    private final Assistants name;
    private int value = 0;
    private int moves = 0;
    private Wizards wizard = null;
    private Player owner;

    public AssistantCard(Assistants assistantName, int value, int moves, Wizards wizard) {
        name = assistantName;
        this.value = value;
        this.moves = moves;
        this.wizard = wizard;
        owner = null;
    }
    //costruttore per la model view
    public AssistantCard(Assistants assistantName, int value, int moves) {
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

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

}