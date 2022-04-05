package controller;

import it.polimi.ingsw.controller.PianificationHandler;
import it.polimi.ingsw.model.Game;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayAssistantTest {
    ArrayList<Player> players = new ArrayList<Player>();
    players.add(new Player("Matteo", 1));
    Game game = new Game(3, false, players, players);
    GameBoard gameBoard = new GameBoard(game);
    PianificationHandler testPianificationHandler;

    @Test
    @BeforeEach
    void setup PianificationHandler(){
        testPianificationHandler = new PianificationHandler(game, new GameBoard(game));
    }

    @Test
    @DisplayName("Control last assistant card used")
    void lastAssistantUsedTest(){
        AssistantCard card = new AssistantCard(/**/, 10, 3, /**/);
        gameBoard.setLastAssistantUsed(0, card);
        assertEquals(card, gameBoard.getLastAssistantUsed().get(0));
    }

}