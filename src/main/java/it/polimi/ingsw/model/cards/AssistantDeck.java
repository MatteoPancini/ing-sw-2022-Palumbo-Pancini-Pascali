package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.enumerations.CardState;

import java.util.ArrayList;

public class AssistantDeck {
    private ArrayList<AssistantCard> deck;

    public ArrayList<AssistantCard> getDeck() {
        return deck;
    }

    public AssistantCard getCard(Assistants name){
        for(AssistantCard card : deck){
            if(card.getName() == name) return card;
        }
        return null;
    }

    public void removeAssistant(Assistants name){
        for(AssistantCard card : deck){
            if(card.getName() == name) card.setState(CardState.PLAYED);
        }
        return null;
    }


    //TODO: pensare a possibile implementazione tramite hashmap tra Wizard e OrderValue
}