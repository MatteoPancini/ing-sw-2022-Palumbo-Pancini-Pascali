package it.polimi.ingsw.model.cards;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.Characters;


import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CharacterDeck implements Serializable {
    private static List<CharacterCard> cards;
    private static Game game;

    public CharacterDeck() {
        cards = null;
        game = null;
    }

    public CharacterDeck(Game game) {
        this.game = game;

        InputStream stream = CharacterCard.class.getResourceAsStream("src/main/resources/cards/characterCards.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        Scanner myReader = new Scanner(reader);
        String data = myReader.nextLine();

        Gson gson = new Gson();
        Type userListType = new TypeToken<ArrayList<CharacterCard>>(){}.getType();
        cards = gson.fromJson(data, userListType);

        myReader.close();

        for(CharacterCard c : cards) {
            if(c.getName() == Characters.MONK || c.getName() == Characters.SPOILED_PRINCESS) {
                Collections.shuffle(game.getGameBoard().getStudentsBag());
                for(int i = 1; i <= 4; i++) {
                    c.setStudents(game.getGameBoard().getStudentsBag().get(i));
                    game.getGameBoard().getStudentsBag().remove(0);
                }
            }

            if(c.getName() == Characters.JESTER) {
                Collections.shuffle(game.getGameBoard().getStudentsBag());
                for(int i = 1; i <= 6; i++) {
                    c.setStudents(game.getGameBoard().getStudentsBag().get(i));
                    game.getGameBoard().getStudentsBag().remove(0);
                }
            }
        }
    }

    public static CharacterDeck getPlayableCards(Game game) {
        CharacterDeck deck = new CharacterDeck(game);
        //System.out.println(deck.getDeck().size());

        CharacterDeck playableCards = null;

        Collections.shuffle(deck.getDeck());

        for(int i = 1; i <= 3; i++){
            //playableCards.getDeck().add(deck.getDeck().get(i));
            //playableCards.add(deck.getDeck().get(i));
        }

        return deck;
    }

    public List<CharacterCard> getDeck(){
        return cards;
    }
}