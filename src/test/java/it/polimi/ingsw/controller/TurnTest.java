package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.clienttoserver.actions.*;
import it.polimi.ingsw.messages.servertoclient.Answer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.server.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TurnTest {
    ControllerStub cStub;
    Game game;
    UserAction action1;
    UserAction action2;
    UserAction action3;
    UserAction action4;

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
        game.createNewPlayer(new PlayerStub("Francesco", 1));
        game.createNewPlayer(new PlayerStub("Matteo", 2));
        game.setCurrentPlayer(game.getActivePlayers().get(0));
        cStub = new ControllerStub(game, new GameHandlerStub(new Server()));
        action1 = new PickAssistant();
        action2 = new PickCloud();
        action3 = new PickDestination();
        action4 = new PickMovesNumber();
    }

    @Test
    void controllerTest() {


    }
}