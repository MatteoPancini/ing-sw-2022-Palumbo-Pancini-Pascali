package it.polimi.ingsw.model.cards;

import com.google.gson.*;
import java.io.*;
import java.lang.IllegalArgumentException;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.enumerations.Wizards;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AssistantDeck {
    //this class is used to parse the JSON file (containing the informations about characters card)
    private static List<AssistantCard> deck = new ArrayList<AssistantCard>();
    private AssistantDeck() {
        throw new IllegalStateException();
    }

    public static List<AssistantCard> parseAssistantCards(Wizards wizard) {
        try{
            File myObj = new File("./src/main/resources/assistantCards.txt");
            Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();

            Gson gson = new Gson();
            Type userListType = new TypeToken<ArrayList<AssistantCard>>(){}.getType();
            deck = gson.fromJson(data, userListType);

            for(AssistantCard c : deck){
                c.setWizard(wizard);
            }

            myReader.close();
        }catch(FileNotFoundException e){
            System.out.println("File not found.");
            e.printStackTrace();
        }
        return deck;
    }

    public void removeCard(AssistantCard card){
        for(AssistantCard c : deck){
            if(c == card) deck.remove(c);
        }
    }

    public List<AssistantCard> getDeck(){
        return deck;
    }
}
