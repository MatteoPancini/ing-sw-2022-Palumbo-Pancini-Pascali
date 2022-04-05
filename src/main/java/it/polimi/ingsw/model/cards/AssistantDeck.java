package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.cards.AssistantCard;

import java.util.ArrayList;

public class AssistantDeck {
    private ArrayList<AssistantCard> deck;

    public ArrayList<AssistantCard> getDeck() {
        return deck;
    }

    public void removeAssistant(AssistantCard card){
        //cambia lo stato, non rimuove
    }


    //TODO: pensare a possibile implementazione tramite hashmap tra Wizard e OrderValue
}
