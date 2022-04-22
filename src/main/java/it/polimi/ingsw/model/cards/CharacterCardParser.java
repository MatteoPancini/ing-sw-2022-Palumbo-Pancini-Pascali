package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.enumerations.Characters;
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

public class CharacterCardParser {
    private CharacterCardParser() {
        throw new IllegalStateException();
    }


    public static List<CharacterCard> parseAssistantCards() {
        List<CharacterCard> characterCards = new ArrayList<>();
        String jsonPath = "json/characterCards.json";

        InputStream in = CharacterCardParser.class.getClassLoader().getResourceAsStream(jsonPath);

        JsonReader reader = null;

        try {
            reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray jsonCards = jsonObject.getAsJsonArray("characterCards");
            for(JsonElement cardElem : jsonCards) {
                JsonObject card = cardElem.getAsJsonObject();

                Characters characterName = Characters.valueOf(card.get("name").getAsString());
                String effect = card.get("effect").getAsString();
                int initialCost = card.get("initialCost").getAsInt();

                characterCards.add(new CharacterCard(characterName, effect, initialCost));
            }
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }


        return characterCards;


    }
}

