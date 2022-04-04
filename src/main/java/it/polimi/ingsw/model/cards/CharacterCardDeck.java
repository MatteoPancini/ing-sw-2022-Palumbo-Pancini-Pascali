package it.polimi.ingsw.model.cards;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import it.polimi.ingsw.model.cards.CharacterCard;

public class CharacterCardDeck {
    private final ArrayList<CharacterCard> cards = new ArrayList<CharacterCard>();

    public CharacterCardDeck{
        ArrayList<CharacterCard> allCards = new ArrayList<CharacterCard>();
        allCards.add(
                CharacterCard cavalier = new Cavalier();
                CharacterCard cheeseMan = new CheeseMan();
                CharacterCard priest = new Priest();
                CharacterCard princess = new Princess();
                CharacterCard letterMan = new LetterMan();
                CharacterCard mushroomLover = new MushroomLover();
                CharacterCard guitarist = new Guitarist();
                CharacterCard joker = new Joker();
                CharacterCard prohibitioner = new Prohibitioner();
                CharacterCard theReader = new TheReader();
                CharacterCard drWitch = new DrWitch();
                CharacterCard centaur = new Centaur();
        )

        Collections.shuffle(allCards);
        for(int i = 0; i < 3; i++){
            cards.get(i) = allCards.get(i);
        }
    }

    public CharacterCardDeck getCards(){ return cards; }

}

