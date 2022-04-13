package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.enumerations.CardState;
import it.polimi.ingsw.model.enumerations.Wizards;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LastAssistantUsedTest {
    private PianificationHandler testPianificationHandler;
    private static ArrayList<Player> players;
    private static Game game;
    private static AssistantCard card1;
    private static AssistantCard card2;
    private static AssistantCard card3;
    private static GameBoard gameBoard;

    @Test
    @BeforeEach
    void setupPlayers(){
        Player player1 = new Player("Matteo", 1);
        Player player2 = new Player("Francesco", 2);
        Player player3 = new Player("Luigi", 3);
        players = new ArrayList<Player>();
        //players.add(player1, player2, player3);
    }

    @Test
    @BeforeEach
    void setupGame(){
        game = new Game(3, false, players, players);
    }

    @Test
    @BeforeEach
    void setupGameBoard(){ gameBoard = new GameBoard(game); }

    @Test
    @BeforeEach
    void setupPianificationHandler(){
        testPianificationHandler = new PianificationHandler(game, gameBoard);
    }

    @Test
    @BeforeEach
    void setupCards(){
        card1 = new AssistantCard(Assistants.DOG, 10, 3, Wizards.FOREST);
        card2 = new AssistantCard(Assistants.CHEETAH, 8, 2, Wizards.KING);
        card3 = new AssistantCard(Assistants.EAGLE, 4, 4, Wizards.MONACH);
    }

    @Test
    @DisplayName("Control last assistant card used")
    void lastAssistantUsedTest(){
        ArrayList<AssistantCard> cards = new ArrayList<AssistantCard>();
        //cards.add(card1, card2, card3);
        for (int i = 0; i < 3; i++) {
            gameBoard.getLastAssistantUsed().get(i).setState(CardState.PLAYED);
            gameBoard.setLastAssistantUsed(i, cards.get(i));
            cards.get(i).setState(CardState.IN_USE);
        }
        assertEquals(card1, gameBoard.getLastAssistantUsed().get(0));
    }

}