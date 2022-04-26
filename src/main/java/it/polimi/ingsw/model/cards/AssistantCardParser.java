package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.cards.AssistantCard;

import java.io.*;
import java.lang.IllegalArgumentException;
import com.google.gson.Gson;

import it.polimi.ingsw.model.enumerations.Wizards;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AssistantCardParser{
    //this class is used to parse the JSON file (containing the informations about characters card)

    private AssistantCardParser() {
        throw new IllegalStateException("Utility class");
    }

    public static List<AssistantCard> parseCards(Wizards wizardUser) {
        Gson gson = new Gson();
        List<AssistantCard> result = new ArrayList<AssistantCard>();
        InputStream in = AssistantCard.class.getResourceAsStream("resources/assistantCards.json");

        Reader reader = new InputStreamReader(in);
        AssistantCard[] cards = gson.fromJson(reader, AssistantCard[].class);
        for (AssistantCard card : cards) { result.add(card); }
        return result;
    }
}