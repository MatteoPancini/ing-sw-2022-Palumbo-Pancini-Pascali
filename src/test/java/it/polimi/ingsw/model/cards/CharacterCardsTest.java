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

public class CharacterCardsTest {
    private static CharacterCard card1;
    private static CharacterCard card2;
    private static CharacterCard card3;
    private static CharacterCard card4;
    private static CharacterCard card5;
    private static CharacterCard card6;
    private static CharacterCard card7;
    private static CharacterCard card8;
    private static CharacterCard card9;
    private static CharacterCard card10;
    private static CharacterCard card11;
    private static CharacterCard card12;

    private static CharacterDeck deckTest;
    private static Game game;

    @Test
    @DisplayName("Deck test")
    void deckTest(){
        game = new Game();
        game.setPlayersNumber(3);
        card1 = new CharacterCard(Characters.HERALD, " ", 3);
        card2 = new CharacterCard(Characters.KNIGHT, " ", 2);
        card3 = new CharacterCard(Characters.CENTAUR, " ", 3);
        card4 = new CharacterCard(Characters.FARMER, " ", 2);
        card5 = new CharacterCard(Characters.FUNGARUS, " ", 3);
        card6 = new CharacterCard(Characters.JESTER, " ", 1);
        card7 = new CharacterCard(Characters.THIEF, " ", 3);
        card8 = new CharacterCard(Characters.MINESTREL, " ", 1);
        card9 = new CharacterCard(Characters.MONK, " ", 1);
        card10 = new CharacterCard(Characters.GRANNY_HERBS, " ", 2);
        card11 = new CharacterCard(Characters.MAGIC_POSTMAN, " ", 1);
        card12 = new CharacterCard(Characters.SPOILED_PRINCESS, " ", 2);
        deckTest = new CharacterDeck(game);

        assertEquals(card1.getName(), deckTest.getDeck().get(0).getName());
        assertEquals(card2.getName(), deckTest.getDeck().get(1).getName());
        assertEquals(card3.getName(), deckTest.getDeck().get(2).getName());
        assertEquals(card4.getName(), deckTest.getDeck().get(3).getName());
        assertEquals(card5.getName(), deckTest.getDeck().get(4).getName());
        assertEquals(card6.getName(), deckTest.getDeck().get(5).getName());
        assertEquals(card7.getName(), deckTest.getDeck().get(6).getName());
        assertEquals(card8.getName(), deckTest.getDeck().get(7).getName());
        assertEquals(card9.getName(), deckTest.getDeck().get(8).getName());
        assertEquals(card10.getName(), deckTest.getDeck().get(9).getName());
        assertEquals(card11.getName(), deckTest.getDeck().get(10).getName());
        assertEquals(card12.getName(), deckTest.getDeck().get(11).getName());

        assertEquals(card1.getInitialCost(), deckTest.getDeck().get(0).getInitialCost());
        assertEquals(card2.getInitialCost(), deckTest.getDeck().get(1).getInitialCost());
        assertEquals(card3.getInitialCost(), deckTest.getDeck().get(2).getInitialCost());
        assertEquals(card4.getInitialCost(), deckTest.getDeck().get(3).getInitialCost());
        assertEquals(card5.getInitialCost(), deckTest.getDeck().get(4).getInitialCost());
        assertEquals(card6.getInitialCost(), deckTest.getDeck().get(5).getInitialCost());
        assertEquals(card7.getInitialCost(), deckTest.getDeck().get(6).getInitialCost());
        assertEquals(card8.getInitialCost(), deckTest.getDeck().get(7).getInitialCost());
        assertEquals(card9.getInitialCost(), deckTest.getDeck().get(8).getInitialCost());
        assertEquals(card10.getInitialCost(), deckTest.getDeck().get(9).getInitialCost());
        assertEquals(card11.getInitialCost(), deckTest.getDeck().get(10).getInitialCost());
        assertEquals(card12.getInitialCost(), deckTest.getDeck().get(11).getInitialCost());

        assertEquals(4, deckTest.getDeck().get(8).getStudents().size());
        assertEquals(4, deckTest.getDeck().get(11).getStudents().size());
        assertEquals(6, deckTest.getDeck().get(5).getStudents().size());

        card1.incrementPrice();
        assertEquals(4, card1.getInitialCost());
    }
}
