package it.polimi.ingsw.model.cards;

import com.google.gson.*;
import java.io.*;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.enumerations.Wizards;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class used to implement and instantiate the assistant deck of the game
 */
public class AssistantDeck implements Serializable {
    private List<AssistantCard> deck;

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