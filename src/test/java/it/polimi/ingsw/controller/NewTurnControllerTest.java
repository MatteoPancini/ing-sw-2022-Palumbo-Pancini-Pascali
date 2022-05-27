package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.servertoclient.Answer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.enumerations.Wizards;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.server.VirtualClientView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.Socket;
import java.util.HashMap;

public class NewTurnControllerTest {
    final PlayerStub matteo = new PlayerStub("matteo", 1);
    final PlayerStub cisco = new PlayerStub("cisco", 2);
    final PlayerStub gigiox = new PlayerStub("gigiox", 3);
    final PlayerStub mario = new PlayerStub("mario", 3);


    final Socket socket = new Socket();
    final ServerStub server = new ServerStub();
    final GameHandlerStub gameHandlerStub = new GameHandlerStub(server);
    final SocketClientConnectionStub socketClientConnectionStub =
            new SocketClientConnectionStub(socket, server);
    final VirtualClientView virtualClient =
            new VirtualClientView(1, "matteo", socketClientConnectionStub, gameHandlerStub);
    final VirtualClientView virtualClient2 =
            new VirtualClientView(2, "cisco", socketClientConnectionStub, gameHandlerStub);
    final VirtualClientView virtualClient3 =
            new VirtualClientView(3, "gigiox", socketClientConnectionStub, gameHandlerStub);
    final VirtualClientView virtualClient4 =
            new VirtualClientView(4, "mario", socketClientConnectionStub, gameHandlerStub);
    final HashMap<Integer, VirtualClientView> idMapID =
            new HashMap<>() {
                {
                    put(1, virtualClient);
                    put(2, virtualClient2);
                    put(3, virtualClient3);
                    put(4, virtualClient4);
                }
            };

    final Game game = new Game();

    //final GameBoard board = new GameBoard(this.game);

    final ControllerStub controllerStub = new ControllerStub(game, gameHandlerStub);
    final TurnController turnController =
            new TurnController(new Controller(game, gameHandlerStub), gameHandlerStub);



    @Test
    @DisplayName("Setup 3 players")
    public void init3Players() {

        matteo.setWizard(Wizards.KING);
        cisco.setWizard(Wizards.MONACH);
        gigiox.setWizard(Wizards.FOREST);
        server.setIdMapID(idMapID);

        controllerStub.getGame().getActivePlayers().add(matteo);
        controllerStub.getGame().getActivePlayers().add(cisco);
        controllerStub.getGame().getActivePlayers().add(gigiox);

        controllerStub.getGame().setPlayersNumber(3);
        controllerStub.getGame().setCurrentPlayer(matteo);

        assertEquals(controllerStub.getGame().getCurrentPlayer().getNickname(), "matteo");

        controllerStub.getTurnController().setCurrentPlayer(matteo);



        assertEquals(game.getCurrentPlayer().getNickname(), "matteo");

    }

    @Test
    @DisplayName("Setup 3 players")
    public void init4Players() {

        matteo.setWizard(Wizards.KING);
        cisco.setWizard(Wizards.MONACH);
        gigiox.setWizard(Wizards.FOREST);
        mario.setWizard(Wizards.WITCH);
        server.setIdMapID(idMapID);

        controllerStub.getGame().getActivePlayers().add(matteo);
        controllerStub.getGame().getActivePlayers().add(cisco);
        controllerStub.getGame().getActivePlayers().add(gigiox);
        controllerStub.getGame().getActivePlayers().add(mario);


        controllerStub.getGame().setPlayersNumber(4);
        controllerStub.getGame().setCurrentPlayer(matteo);

        assertEquals(controllerStub.getGame().getCurrentPlayer().getNickname(), "matteo");

        controllerStub.getTurnController().setCurrentPlayer(matteo);



        assertEquals(game.getCurrentPlayer().getNickname(), "matteo");

    }


    @Test
    @DisplayName("Pianification Phase")
    public void pianificationPhase() {
        controllerStub.getTurnController().playAssistantCard(Assistants.ELEPHANT);
        controllerStub.getTurnController().playAssistantCard(Assistants.LIZARD);
        controllerStub.getTurnController().playAssistantCard(Assistants.CHEETAH);
        assertEquals(controllerStub.getGame().getGameBoard().getLastAssistantUsed().size(), 3);
        for(AssistantCard a : controllerStub.getGame().getGameBoard().getLastAssistantUsed()) {
            System.out.println("Card: " + a.getName() + " " + a.getValue() + " by " + a.getOwner().getNickname());
        }

        assertEquals(controllerStub.getGame().getActivePlayers().get(0).getNickname(), "gigiox");
        assertEquals(controllerStub.getGame().getActivePlayers().get(1).getNickname(), "cisco");
        assertEquals(controllerStub.getGame().getActivePlayers().get(2).getNickname(), "matteo");

        assertEquals(controllerStub.getGame().getActivePlayers().get(0).getAssistantDeck().getDeck().size(), 9);

    }



    /** Class GameHandlerStub defines a stub for GameHandler class */
    public class GameHandlerStub extends GameHandler {

        /**
         * Constructor GameHandler creates a new GameHandler instance.
         *
         * @param server of type Server - the main server class.
         */
        public GameHandlerStub(Server server) {
            super(server);
        }

        /**
         * Method singleSend sends a message to a client, identified by his ID number, through the
         * server socket.
         *
         * @param message of type Answer - the message to be sent to the client.
         * @param id of type int - the unique identification number of the client to be contacted.
         */
        @Override
        public void sendSinglePlayer(Answer message, int id) {
            String print;
            if (message.getMessage() == null) {
                print = "Start/End/Move/SelectBuild/BuildSelectMove action";
            } else print = message.getMessage().toString();
            System.out.println(print);
        }

        /**
         * Method getCurrentPlayerID returns the current player client ID, getting it from the
         * currentPlayer reference in the Game class.
         *
         * @return the currentPlayerID (type int) of this GameHandler object.
         */
        @Override
        public int getCurrentPlayerId() {
            return game.getCurrentPlayer().getPlayerID();
        }

        /**
         * Method sendAll does the same as the previous method, but it iterates on all the clients
         * present in the game. It's a full effects broadcast.
         *
         * @param message of type Answer - the message to broadcast (at single match participants'
         *     level).
         */
        @Override
        public void sendBroadcast(Answer message) {
            System.out.println(message.getMessage());
        }
    }

    /** Class ControllerStub defines a stub for Controller class. */
    public static class ControllerStub extends Controller {

        /**
         * Constructor Controller creates a new Controller instance.
         *
         */
        public ControllerStub(Game game, GameHandler gameHandler) {
            super(game, gameHandler);
        }
    }


    /** Class ServerStub defines a stub for Server class. */
    public static class ServerStub extends Server {
        private HashMap<Integer, VirtualClientView> idMapID;
        /**
         * Constructor Server creates the instance of the server, based on a socket and the mapping
         * between VirtualClient, nicknames and client ids. It also creates a new game session.
         */
        public ServerStub() {
            this.idMapID = null;
        }
        /**
         * Method setIdMapID sets the idMapID of this ServerStub object.
         *
         * @param idMapID the idMapID of this ServerStub object.
         */
        public void setIdMapID(HashMap idMapID) {
            this.idMapID = idMapID;
        }
        /**
         * Method getClientByID returns a link to the desired virtual client, in order to make
         * operations on it (like send, etc).
         *
         * @param id of type int - the id of the virtual client needed.
         * @return VirtualClient - the correct virtual client.
         */
        @Override
        public VirtualClientView getVirtualClientFromID(int id) {
            return idMapID.get(id);
        }
    }



    /** Class PlayerStub defines a stub for Player class. */
    public static class PlayerStub extends Player {

        /**
         * Constructor PlayerStub creates a new PlayerStub instance.
         *
         * @param nickname of type String - the player's nickname.
         * @param clientID of type int - the clientID.
         */
        public PlayerStub(String nickname, int clientID) {
            super(nickname, clientID);
        }
    }


    /** Class SocketClientConnectionStub defines a stub for SocketClientConnection class. */
    public static class SocketClientConnectionStub extends SocketClientConnection {

        /**
         * Constructor of the class: it instantiates an input/output stream from the socket received as
         * parameters, and add the main server to his attributes too.
         *
         * @param socket the socket which accepted the client connection.
         * @param server the main server class.
         */
        public SocketClientConnectionStub(Socket socket, Server server) {
            super(socket, server);
        }
        /**
         * Method close terminates the connection with the client, closing firstly input and output
         * streams, then invoking the server method called "unregisterClient", which will remove the
         * active virtual client from the list.
         *
         */
        @Override
        public void closeConnection() {
            System.out.println("Connection closed to client");
        }
    }
}
