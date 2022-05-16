package it.polimi.ingsw.model.cards;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.enumerations.Characters;

import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CharacterDeck {
    private static List<CharacterCard> cards = null;
    private CharacterDeck() {
        throw new IllegalStateException();
    }

    public static List<CharacterCard> parseCharacterCards() {
        try{
            File myObj = new File("./src/main/resources/characterCards.txt");
            Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();

            Gson gson = new Gson();
            Type userListType = new TypeToken<ArrayList<AssistantCard>>(){}.getType();
            cards = gson.fromJson(data, userListType);

            myReader.close();
        }catch(FileNotFoundException e){
            System.out.println("File not found.");
            e.printStackTrace();
        }
        return cards;
    }

    public static List<CharacterCard> getPlayableCards(){
        List<CharacterCard> playableCards = new ArrayList<CharacterCard>();

        Collections.shuffle(cards);

        for(int i = 1; i <= 3; i++){
            playableCards.add(cards.get(i));
        }

        return playableCards;
    }
}