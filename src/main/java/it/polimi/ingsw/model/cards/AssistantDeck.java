package it.polimi.ingsw.model.cards;

import com.google.gson.*;
import java.io.*;
import java.lang.IllegalArgumentException;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.enumerations.Wizards;
import it.polimi.ingsw.model.player.Player;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AssistantDeck implements Serializable {
    private List<AssistantCard> deck;

    /*
    public AssistantDeck(Wizards wizard) {
        this.deck.add(new AssistantCard(Assistants.CAT, wizard));
        this.deck.add(new AssistantCard(Assistants.EAGLE, wizard));
        this.deck.add(new AssistantCard(Assistants.ELEPHANT, wizard));
        this.deck.add(new AssistantCard(Assistants.OSTRICH, wizard));
        this.deck.add(new AssistantCard(Assistants.TURTLE, wizard));
        this.deck.add(new AssistantCard(Assistants.CHEETAH, wizard));
        this.deck.add(new AssistantCard(Assistants.DOG, wizard));
        this.deck.add(new AssistantCard(Assistants.FOX, wizard));
        this.deck.add(new AssistantCard(Assistants.LIZARD, wizard));
        this.deck.add(new AssistantCard(Assistants.OCTOPUS, wizard));
    }
    */

    public AssistantDeck(Wizards wizard) {
        InputStream stream = AssistantCard.class.getResourceAsStream("/cards/assistantCards.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        Scanner myReader = new Scanner(reader);
        String data = myReader.nextLine();

        Gson gson = new Gson();
        Type userListType = new TypeToken<ArrayList<AssistantCard>>(){}.getType();
        deck = gson.fromJson(data, userListType);

        for(AssistantCard c : deck){
            c.setWizard(wizard);
        }

        myReader.close();
    }

    public void removeCard(AssistantCard card) {
        for(AssistantCard c : this.getDeck()) {
            if(card.getName() == c.getName()){
                deck.remove(c);
                c.getOwner().setChosenAssistant(c);
                return;
            }
        }
    }

    public List<AssistantCard> getDeck() {
        return this.deck;
    }
}