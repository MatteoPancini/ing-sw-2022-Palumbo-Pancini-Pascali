package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.clienttoserver.actions.*;
import it.polimi.ingsw.messages.servertoclient.Answer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.Wizards;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.SchoolBoard;
import it.polimi.ingsw.server.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControllerTest {
    ControllerStub cStub;
    Game game;

    private static class ControllerStub extends Controller {
        public ControllerStub(Game game, GameHandler gameHandler) {
            super(game, gameHandler);
        }
    }

    private static class GameHandlerStub extends GameHandler {
        public GameHandlerStub(Server server) {
            super(server);
        }

        public int getCurrentPlayerID() {
            return 1;
        }

        @Override
        public void sendSinglePlayer(Answer message, int id) {
            // NOTHING
        }

        @Override
        public void sendBroadcast(Answer message) {
            // NOTHING
        }

        @Override
        public void sendExcept(Answer message, int excludedID) {
            // NOTHING
        }
    }

    private static class PlayerStub extends Player {
        public PlayerStub(String nickname, int clientID) {
            super(nickname, clientID);
        }

    }

    @BeforeEach
    void initialization() {
        game = new Game();
        game.setPlayersNumber(2);
        PlayerStub player1 = new PlayerStub("Francesco", 1);
        PlayerStub player2 = new PlayerStub("Matteo", 2);
        SchoolBoard schoolB1 = new SchoolBoard(player1.getPlayerID());
        SchoolBoard schoolB2 = new SchoolBoard(player2.getPlayerID());
        player1.setBoard(schoolB1);
        player2.setBoard(schoolB2);
        game.createNewPlayer(player1);
        game.createNewPlayer(player2);
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.setCurrentPlayer(game.getActivePlayers().get(0));
        cStub = new ControllerStub(game, new GameHandlerStub(new Server()));
    }

    @Test
    void setupTest() {
        cStub.newSetupGame();
        assertEquals(2, cStub.getGame().getPlayersNumber());
        System.out.println(cStub.getGame().getCurrentPlayer().getNickname() + ", " + cStub.getGame().getCurrentPlayer().getPlayerID());
        //cStub.getGame().switchToNextPlayer();
        //System.out.println(cStub.getGame().getCurrentPlayer().getNickname() + ", " + cStub.getGame().getCurrentPlayer().getPlayerID());
    }

    @Test
    void wizardsTest(){
        cStub.setPlayerWizard(1, Wizards.KING);
        cStub.setPlayerWizard(2, Wizards.MONACH);

        assertEquals(Wizards.KING, cStub.getGame().getActivePlayers().get(0).getWizard());
        assertEquals(Wizards.MONACH, cStub.getGame().getActivePlayers().get(1).getWizard());
    }

}