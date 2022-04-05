package it.polimi.ingsw.model.cards;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import it.polimi.ingsw.model.cards.CharacterCard;

public class CharacterCardDeck {
    private final ArrayList<CharacterCard> cards = new ArrayList<CharacterCard>();

    public CharacterCardDeck getCards(){ return cards; }

}

