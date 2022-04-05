package it.polimi.ingsw.model.cards;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import it.polimi.ingsw.model.cards.CharacterCard;

public class CharacterCardDeck {
    private final ArrayList<CharacterCard> cards = new ArrayList<CharacterCard>();
    CharacterCard priest;
    CharacterCard cheeseman;
    CharacterCard princess;
    CharacterCard letterman;
    CharacterCard mushroomLover;
    CharacterCard guitarist;
    CharacterCard joker;
    CharacterCard prohibitioner;
    CharacterCard theReader;
    CharacterCard drWitch;
    CharacterCard centaur;
    CharacterCard cavalier;

    public CharacterCardDeck() {
        ArrayList<CharacterCard> allCards = new ArrayList<CharacterCard>();
        allCards.add(
                cavalier = new Cavalier();
                cheeseman = new CheeseMan();
                priest = new Priest();
                princess = new Princess();
                letterman = new LetterMan();
                mushroomLover = new MushroomLover();
                guitarist = new Guitarist();
                joker = new Joker();
                prohibitioner = new Prohibitioner();
                theReader = new TheReader();
                drWitch = new DrWitch();
                centaur = new Centaur();
        );

        Collections.shuffle(allCards);
        for(int i = 0; i < 3; i++){
            cards.get(i) = allCards.get(i);
        }
    }

    public  ArrayList<CharacterCard> getCards(){ return cards; }

}

