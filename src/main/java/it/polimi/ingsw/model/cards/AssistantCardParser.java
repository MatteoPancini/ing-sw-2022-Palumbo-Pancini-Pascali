package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.enumerations.Wizards;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class AssistantCardParser {
    //this class is used to parse the JSON file (containing the informations about characters card)

    private AssistantCardParser() {
        throw new IllegalStateException();
    }


    public static List<AssistantCard> createAssistantDeck(Wizards wizardUser) {
        List<AssistantCard> assistantCards = new ArrayList<>();
        String jsonPath = "json/assistantsCards.json";

        InputStream in = AssistantCardParser.class.getClassLoader().getResourceAsStream(jsonPath);

        JsonReader reader = null;

        try {
            reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray jsonCards = jsonObject.getAsJsonArray("assistantCards");
            for(JsonElement cardElem : jsonCards) {
                JsonObject card = cardElem.getAsJsonObject();

                Assistants assistantName = Assistants.valueOf(card.get("name").getAsString());
                int value = card.get("orderValue").getAsInt();
                int moves = card.get("motherNatureMoves").getAsInt();


                assistantCards.add(new AssistantCard(assistantName, value, moves, wizardUser));
            }
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        return assistantCards;

    }
}
