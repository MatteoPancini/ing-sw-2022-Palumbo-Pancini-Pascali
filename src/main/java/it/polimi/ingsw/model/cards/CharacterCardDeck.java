package it.polimi.ingsw.model.cards;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import it.polimi.ingsw.model.cards.CharacterCard;

public class CharacterCardDeck {
    private final ArrayList<CharacterCard> cards = new ArrayList<CharacterCard>;

    public CharacterCardDeck{
        ArrayList<CharacterCard> allCards = new ArrayList<CharacterCard>;
        allCards.add(
                CharacterCard new Cavalier;
                CharacterCard new CheeseMan;
                CharacterCard new Priest;
                CharacterCard new Princess;
                CharacterCard new LetterMan;
                CharacterCard new MushroomLover;
                CharacterCard new Guitarist;
                CharacterCard new Joker;
                CharacterCard new Prohibitioner;
                CharacterCard new TheReader;
                CharacterCard new DrWitch;
                CharacterCard new Centaur;
        )

        Collections.shuffle(allCards);
        for(int i = 0; i < 3; i++){
            cards.get(i) = allCards.get(i);
        }
    }

    public CharacterCardDeck getCards(){ return cards; }

}

