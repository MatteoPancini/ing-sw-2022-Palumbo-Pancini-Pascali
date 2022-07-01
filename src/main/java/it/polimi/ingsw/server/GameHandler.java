package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.ExpertController;
import it.polimi.ingsw.messages.clienttoserver.FourPModeNotification;
import it.polimi.ingsw.messages.clienttoserver.actions.*;
import it.polimi.ingsw.messages.servertoclient.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.Wizards;
import it.polimi.ingsw.model.player.DiningRoom;
import it.polimi.ingsw.model.player.Player;

import java.beans.PropertyChangeSupport;

/**
 * GameHandler class is used to instantiate a new game, handling the forwarding of messages from a server perspective
 */
public class GameHandler {
    private static Game game;
    private final Controller controller;
    private final Server server;
    private int playersNumber;
    private final PropertyChangeSupport gameHandlerListener = new PropertyChangeSupport(this);
    private boolean isExpertMode;
    private int currentPlayerId;
    private boolean isMatchStarted;


    /**
     * constructor of the GameHandler, which instantiates a new game and a new controller for the game
     *
     * @param server -> server who created the game handler
     */
    public GameHandler(Server server) {
        game = new Game();
        controller = new Controller(game, this);
        this.server = server;
        gameHandlerListener.addPropertyChangeListener(controller);

    }


    public boolean getExpertMode() {
        return isExpertMode;
    }

    /**
     * method used to set expert mode and instantiate (eventually) an expert controller
     *
     * @param expertMode -> true (expert mode), false (standard mode)
     */
    public void setExpertMode(boolean expertMode) {
        isExpertMode = expertMode;
        game.setExpertMode(expertMode);
        if(isExpertMode) {
            controller.setExpertController(new ExpertController(game, controller.getTurnController()));
        }
    }

    public void setMatchStarted() {
        isMatchStarted = true;
    }

    public boolean isMatchStarted() {
        return isMatchStarted;
    }

    public static Game getGame() {
        return game;
    }

    public void addGamePlayer(String playerNickname, int playerID) {
        game.createNewPlayer(new Player(playerNickname, playerID));

    }

    public void setCurrentPlayerId(int currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    /**
     * method used to create teams of a 4 player game
     */
    public void setupTeams() {
        sendBroadcast(new DynamicAnswer("Setting up teams...", false));
        int teamID1 = 1;
        int teamID2 = 2;
        for(int i = 0; i < game.getPlayers().size(); i++) {

            if(i % 2 == 0) {
                game.getPlayers().get(i).setIdTeam(teamID1);
            } else {
                game.getPlayers().get(i).setIdTeam(teamID2);
            }

            if(i == 0) {
                game.getPlayers().get(i).setTeamLeader(true);
            } else if(i == 1) {
                game.getPlayers().get(i).setTeamLeader(true);
            }
            sendBroadcast(new DynamicAnswer("Player " + game.getPlayers().get(i).getNickname() + " joined team " + game.getPlayers().get(i).getIdTeam(), false));
        }

    }

    public Controller getController() {
        return controller;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    /**
     * method used to send a server answer to a single player
     *
     * @param serverAnswer -> answer to send to the player
     * @param clientID -> player ID
     */
    public void sendSinglePlayer(Answer serverAnswer, int clientID) {
        server.getVirtualClientFromID(clientID).sendAnswerToClient(serverAnswer);
    }

    /**
     * method used to send a server answer to everyone except a single player
     *
     * @param serverAnswer -> answer to send to the player
     * @param notClientID -> id of the player excluded
     */
    public void sendExcept(Answer serverAnswer, int notClientID) {
        for(Player activePlayers : controller.getGame().getActivePlayers()) {
            if(server.getIDFromNickname(activePlayers.getNickname()) != notClientID) {
                sendSinglePlayer(serverAnswer, activePlayers.getPlayerID());
            }
        }
    }

    /**
     * method used to send a server answer to everyone in the game
     *
     * @param serverAnswer -> answer to send to the player
     */
    public void sendBroadcast(Answer serverAnswer) {
        for(Player player : controller.getGame().getActivePlayers()) {
            sendSinglePlayer(serverAnswer, server.getIDFromNickname(player.getNickname()));
        }
    }

    /**
     * method used to set player's number of the game in this class and in the game class
     *
     * @param playersNumber -> player's number of the game
     */
    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
        game.setPlayersNumber(playersNumber);
    }


    /**
     * method used to initialize wizards during the pre-game phase
     */
    public void initializeWizards() {
        WizardAnswer chooseWizard = new WizardAnswer("Please choose your Wizard!");
        chooseWizard.setWizardsLeft(Wizards.notChosen());
        if ((playersNumber == 2 && Wizards.notChosen().size() > 2)) {
            String playerNickname = game.getActivePlayers().get(playersNumber - Wizards.notChosen().size() + 2).getNickname();
            sendSinglePlayer(chooseWizard, server.getIDFromNickname(playerNickname));
            sendExcept(new DynamicAnswer("Please wait: player " + playerNickname + " is choosing his wizard!", false), server.getIDFromNickname(playerNickname));
        } else if(playersNumber == 3 && Wizards.notChosen().size() > 1) {
            String playerNickname = game.getActivePlayers().get(playersNumber - Wizards.notChosen().size() + 1).getNickname();
            sendSinglePlayer(chooseWizard, server.getIDFromNickname(playerNickname));
            sendExcept(new DynamicAnswer("Please wait: player " + playerNickname + " is choosing his wizard!", false), server.getIDFromNickname(playerNickname));
        } else if (playersNumber == 4 && Wizards.notChosen().size() > 0) {
                String playerNickname = game.getActivePlayers().get(playersNumber - Wizards.notChosen().size()).getNickname();
                sendSinglePlayer(chooseWizard, server.getIDFromNickname(playerNickname));
                sendExcept(new DynamicAnswer("Please wait: player " + playerNickname + " is choosing his wizard!", false), server.getIDFromNickname(playerNickname));

        } else {
            startGame();
        }
    }


    /**
     * method used to start the game on the controller
     */
    public void startGame() {
        setMatchStarted();
        sendBroadcast(new DynamicAnswer("Game is started", false));
        if(playersNumber == 4) {
            sendBroadcast(new FourPModeNotification());
            setupTeams();
        }
        game.setCurrentPlayer(game.getActivePlayers().get(0));
        controller.newSetupGame();
    }


    /**
     * method used to unregister a player from the game
     *
     * @param leavingPlayer -> ID of the player to unregister from the game
     */
    public void unregisterPlayer(int leavingPlayer) {
        game.removePlayer(game.getPlayerByID(leavingPlayer));
    }


    /**
     * method used to parse actions sent by a player
     *
     * @param userAction -> user action sent from the player
     * @param actionType -> string used to switch the action types in order to trigger the controller
     */
    public void parseActions(UserAction userAction, String actionType) {
        switch(actionType) {
            case "PickAssistant" -> {
                gameHandlerListener.firePropertyChange("PickAssistant", null, ((PickAssistant) userAction).getChosenAssistant());
            }

            case "PickStudent" -> gameHandlerListener.firePropertyChange("PickStudent", null, ((PickStudent) userAction).getChosenStudent());

            case "PickDestination" -> {
                if(((PickDestination) userAction).getAction() == Action.PICK_ISLAND) {
                    if(controller.getExpertController() != null) {
                        if(controller.getExpertController().isHeraldEffect()) {
                            gameHandlerListener.firePropertyChange("CheckIslandInfluence", null, ((PickDestination) userAction).getChosenIsland());
                        } else {
                            gameHandlerListener.firePropertyChange("PickDestinationIsland", null, ((PickDestination) userAction).getDestination());
                        }
                    } else {
                        gameHandlerListener.firePropertyChange("PickDestinationIsland", null, ((PickDestination) userAction).getDestination());
                    }
                }
                else if (((PickDestination) userAction).getDestination() instanceof DiningRoom) {
                    gameHandlerListener.firePropertyChange("PickDestinationDiningRoom", null, ((PickDestination) userAction).getDestination());
                }


            }

            case "GrannyHerbsTile" -> gameHandlerListener.firePropertyChange("GrannyHerbsTile", null, ((PickDestination) userAction).getChosenIsland());

            case "PickMovesNumber" -> gameHandlerListener.firePropertyChange("PickMovesNumber", null, ((PickMovesNumber) userAction).getMoves());

            case "PickCloud" -> gameHandlerListener.firePropertyChange("PickCloud", null, ((PickCloud) userAction).getChosenCloud());

            case "PickCharacter" -> gameHandlerListener.firePropertyChange("PickCharacter", null, ((PickCharacter) userAction).getChosenCharacter());

            case "PickPawnType" -> gameHandlerListener.firePropertyChange("PickPawnType", null, ((PickPawnType) userAction).getType());


            case "PickCharacterActionsNum" -> gameHandlerListener.firePropertyChange("PickCharacterActionsNum", null, ((PickCharacterActionsNum) userAction).getNumber());

        }

    }


    /**
     * method used when a player leave the game, disconnecting everyone else and sending a noWinnerGame notification
     *
     * @param playerDisconnected -> nickname of player who disconnected from the game
     */
    public void endPlayerGame(String playerDisconnected) {
        sendBroadcast(new DynamicAnswer("Player " + playerDisconnected + " has disconnected :( Game will finish without a winner! Thanks to have played Eriantys! Hope to see you soon ;)", false));
        sendBroadcast(new NoWinnerGameNotification());
        for(Player p  : game.getActivePlayers()) {
            server.getVirtualClientFromID(p.getPlayerID()).getSocketClientConnection().closeConnection();
        }
    }


    /**
     * method used to disconnect every player when the game ends with a winner
     */
    public void endGame() {
        while(!game.getActivePlayers().isEmpty()) {
            server.getVirtualClientFromID(game.getActivePlayers().get(0).getPlayerID()).getSocketClientConnection().closeConnection();
        }

    }


}