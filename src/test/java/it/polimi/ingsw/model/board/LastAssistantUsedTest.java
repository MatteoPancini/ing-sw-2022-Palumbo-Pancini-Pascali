package it.polimi.ingsw.model.board;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class LastAssistantUsedTest {
    private static GameBoard testGBoard;
    private static Game game;
    private static Player player1;
    private static Player player2;
    private static Player player3;
    private static AssistantCard card1;
    private static AssistantCard card2;
    private static AssistantCard card3;
    private static ArrayList<Player> players;

    @Test
    @BeforeEach
    void setupPlayers(){
        player1 = new Player("Matteo", 1);
        player2 = new Player("Francesco", 2);
        player3 = new Player("Luigi", 3);
        players = new ArrayList<Player>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
    }

    @Test
    @BeforeEach
    void setupGame(){
        game = new Game(3, false, players, players);
    }

    @Test
    @DisplayName("Verify last assistant used")
    void cardStateTest(){
        testGBoard = new GameBoard(game);
        card1 = new AssistantCard(Assistants.EAGLE, 8, 4);
        card2 = new AssistantCard(Assistants.OSTRICH, 7, 1);
        card3 = new AssistantCard(Assistants.TURTLE, 2, 3);
        card1.setWizard(Wizards.KING);
        card2.setWizard(Wizards.MONACH);
        card3.setWizard(Wizards.FOREST);
        testGBoard.getLastAssistantUsed().add(card1);
        testGBoard.getLastAssistantUsed().add(card2);
        testGBoard.getLastAssistantUsed().add(card3);
        assertEquals(Assistants.EAGLE, testGBoard.getLastAssistantUsed().get(0).getName());
        assertEquals(Assistants.OSTRICH, testGBoard.getLastAssistantUsed().get(1).getName());
        assertEquals(Assistants.TURTLE, testGBoard.getLastAssistantUsed().get(2).getName());
        assertEquals(Wizards.KING, testGBoard.getLastAssistantUsed().get(0).getWizard());
        assertEquals(Wizards.MONACH, testGBoard.getLastAssistantUsed().get(1).getWizard());
        assertEquals(Wizards.FOREST, testGBoard.getLastAssistantUsed().get(2).getWizard());

    }
}