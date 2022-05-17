package it.polimi.ingsw.model.cards;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Student;
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
    private static Game game;
    private CharacterDeck() {
        throw new IllegalStateException();
    }

    public CharacterDeck(Game game) {
        this.game = game;

        try{
            File myObj = new File("./src/main/resources/characterCards.txt");
            Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();

            Gson gson = new Gson();
            Type userListType = new TypeToken<ArrayList<CharacterCard>>(){}.getType();
            cards = gson.fromJson(data, userListType);

            myReader.close();
        }catch(FileNotFoundException e){
            System.out.println("File not found.");
            e.printStackTrace();
        }

        for(CharacterCard c : cards){
            if(c.getName() == Characters.MONK || c.getName() == Characters.SPOILED_PRINCESS){
                Collections.shuffle(game.getGameBoard().getStudentsBag());
                for(int i = 1; i <= 4; i++){
                    c.setStudents(game.getGameBoard().getStudentsBag().get(i));
                    game.getGameBoard().getStudentsBag().remove(0);
                }
            }

            if(c.getName() == Characters.JESTER){
                Collections.shuffle(game.getGameBoard().getStudentsBag());
                for(int i = 1; i <= 6; i++){
                    c.setStudents(game.getGameBoard().getStudentsBag().get(i));
                    game.getGameBoard().getStudentsBag().remove(0);
                }
            }
        }
    }

    public static List<CharacterCard> getPlayableCards(){
        List<CharacterCard> playableCards = new ArrayList<CharacterCard>();

        Collections.shuffle(cards);

        for(int i = 1; i <= 3; i++){
            playableCards.add(cards.get(i));
        }

        return playableCards;
    }

    public List<CharacterCard> getDeck(){
        return cards;
    }
}