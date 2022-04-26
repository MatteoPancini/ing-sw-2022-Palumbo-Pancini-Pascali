package it.polimi.ingsw.model.cards;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.IllegalArgumentException;

import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.enumerations.Wizards;

import java.util.ArrayList;
import java.util.List;

public class AssistantDeck {
    private List<AssistantCard> deck;
    //this class is used to parse the JSON file (containing the informations about characters card)

    private AssistantDeck() {
        throw new IllegalStateException("Utility class");
    }
    public static List<AssistantCard> parseAssistantCards(Wizards wizard) {
        List<AssistantCard> cards = new ArrayList<>();
        String path = "resources/assistantCards.json";
        InputStream in = AssistantCard.class.getClassLoader().getResourceAsStream(path);
        JsonReader reader = null;
        try {
            reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray jsonCards = jsonObject.getAsJsonArray("developmentCards");
            for (JsonElement cardElem : jsonCards) {
                JsonObject card = cardElem.getAsJsonObject();

                Assistants name = Assistants.valueOf(card.get("name").getAsString());
                int orderValue = card.get("orderValue").getAsInt();
                int motherNatureMoves = card.get("motherNatureMoves").getAsInt();

                cards.add(new AssistantCard(name, orderValue, motherNatureMoves, wizard));
            }
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return cards;
    }

    public List<AssistantCard> getDeck(){
        return deck;
    }

    public void removeCard(AssistantCard card){
        for(AssistantCard c : deck){
            if(c == card) deck.remove(c);
        }
    }
}
