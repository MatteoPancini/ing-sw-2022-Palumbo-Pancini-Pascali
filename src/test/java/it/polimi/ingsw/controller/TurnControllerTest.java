package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.OutOfBoundException;
import it.polimi.ingsw.messages.clienttoserver.actions.*;
import it.polimi.ingsw.messages.servertoclient.Answer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.enumerations.PawnType;
import it.polimi.ingsw.model.enumerations.Wizards;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.server.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TurnControllerTest {
    TurnControllerStub turnCStub;
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


    private static class TurnControllerStub extends TurnController {
        public TurnControllerStub(Controller controller, GameHandler gameHandler) {
            super(new ControllerStub(new Game(), new GameHandler(new Server())), new GameHandlerStub(new Server()));
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
        GameHandlerStub gameHStub = new GameHandlerStub(new Server());
        ControllerStub cStub = new ControllerStub(game, gameHStub);
        turnCStub = new TurnControllerStub(cStub, gameHStub);


        /*action1 = new PickAssistant(Action.PICK_ASSISTANT, );
        action2 = new PickCloud(Action.PICK_CLOUD, );
        action3 = new PickDestination(Action.PICK_DESTINATION, );
        action4 = new PickMovesNumber(Action.PICK_MOVES_NUMBER, );*/
    }

    @Test
    void setStudents(){
        System.out.println(game.getActivePlayers().get(1).getNickname());
        turnCStub.setCurrentPlayer();
        //turnCStub.setStudentToMove(new Student(PawnType.BLUE));
    }

}