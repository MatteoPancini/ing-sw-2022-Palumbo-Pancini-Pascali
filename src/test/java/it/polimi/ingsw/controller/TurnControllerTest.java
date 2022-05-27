package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.clienttoserver.actions.*;
import it.polimi.ingsw.messages.servertoclient.Answer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.Wizards;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.server.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TurnControllerTest {
    //TurnControllerStub turnCStub;
    Game game;
    UserAction action1;
    UserAction action2;
    UserAction action3;
    UserAction action4;

    GameHandler gameHandler;
    Controller controller;
    TurnController turnController;



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
        GameHandlerStub gameHStub = new GameHandlerStub(new Server());
        ControllerStub cStub = new ControllerStub(game, gameHStub);
        //turnCStub = new TurnControllerStub(cStub, gameHStub);



        //action1 = new PickAssistant(Action.PICK_ASSISTANT, );
        //action2 = new PickCloud(Action.PICK_CLOUD, );
        //action3 = new PickDestination(Action.PICK_DESTINATION, );
        //action4 = new PickMovesNumber(Action.PICK_MOVES_NUMBER, );
    }
    /*

    @BeforeEach
    void initialization() {
        this.gameHandler = new GameHandler(new Server());
        this.controller = gameHandler.getController();
        this.turnController = controller.getTurnController();

        controller.getGame().setPlayersNumber(2);
        controller.getGame().setExpertMode(false);

        controller.getGame().createNewPlayer(new PlayerStub("Francesco", 1));
        controller.getGame().createNewPlayer(new PlayerStub("Matteo", 2));
        assertEquals(controller.getGame().getPlayers().get(0).getPlayerID(), 1);
        assertEquals(controller.getGame().getPlayers().get(1).getPlayerID(), 2);


        controller.getGame().getPlayers().get(0).setWizard(Wizards.KING);
        controller.getGame().getPlayers().get(1).setWizard(Wizards.MONACH);
        assertEquals(controller.getGame().getPlayers().get(0).getWizard(), Wizards.KING);
        assertEquals(controller.getGame().getPlayers().get(1).getWizard(), Wizards.MONACH);


        controller.getGame().setCurrentPlayer(controller.getGame().getPlayers().get(0));
        assertEquals(controller.getGame().getCurrentPlayer().getNickname(), "Francesco");

    }
    */



        /*action1 = new PickAssistant(Action.PICK_ASSISTANT, );
        action2 = new PickCloud(Action.PICK_CLOUD, );
        action3 = new PickDestination(Action.PICK_DESTINATION, );
        action4 = new PickMovesNumber(Action.PICK_MOVES_NUMBER, );*/

    @Test
    void playAssistant() {


    }

    /*
    @Test
    void setStudents(){
        System.out.println(game.getActivePlayers().get(1).getNickname());
        turnCStub.switchPlayer();
        //turnCStub.setStudentToMove(new Student(PawnType.BLUE));
    }

     */





}