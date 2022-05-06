package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.cards.AssistantDeck;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.AssistantDeck;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssistantCardsTest {
    private static AssistantDeck deckTest;
    private static AssistantCard assistant1;
    private static AssistantCard assistant2;
    private static AssistantCard assistant3;

    @Test
    @DisplayName("Deck check")
    void deckValueTest(){
        assistant1 = new AssistantCard(Assistants.CAT, 3, 2);
        assistant2 = new AssistantCard(Assistants.ELEPHANT, 9, 5);
        assistant3 = new AssistantCard(Assistants.CHEETAH, 1, 1);
        deckTest = new AssistantDeck(Wizards.FOREST);

        assertEquals(assistant1.getValue(), deckTest.getDeck().get(3).getValue());
        assertEquals(assistant2.getValue(), deckTest.getDeck().get(2).getValue());
        assertEquals(assistant3.getValue(), deckTest.getDeck().get(4).getValue());

        assertEquals(assistant1.getMoves(), deckTest.getDeck().get(3).getMoves());
        assertEquals(assistant2.getMoves(), deckTest.getDeck().get(2).getMoves());
        assertEquals(assistant3.getMoves(), deckTest.getDeck().get(4).getMoves());

        assertEquals(assistant1.getName(), deckTest.getDeck().get(3).getName());
        assertEquals(assistant2.getName(), deckTest.getDeck().get(2).getName());
        assertEquals(assistant3.getName(), deckTest.getDeck().get(4).getName());

        Player player1 = new Player("Matteo", 1);
        Player player2 = new Player("Francesco", 2);
        Player player3 = new Player("Luigi", 3);

        assistant1.setOwner(player1);
        assistant2.setOwner(player2);
        assistant3.setOwner(player3);

        assertEquals(player1, assistant1.getOwner());
        assertEquals(player2, assistant2.getOwner());
        assertEquals(player3, assistant3.getOwner());

        assertEquals(Wizards.FOREST, deckTest.getDeck().get(0).getWizard());
        assertEquals(10, deckTest.getDeck().size());
        deckTest.removeCard(assistant1);
        assertEquals(9, deckTest.getDeck().size());
    }

}