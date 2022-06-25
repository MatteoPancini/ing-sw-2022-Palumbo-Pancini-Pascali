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

public class GameHandler {
    private static Game game;
    private final Controller controller;
    private final Server server;
    private int playersNumber;
    private final PropertyChangeSupport gameHandlerListener = new PropertyChangeSupport(this);
    private boolean isExpertMode;
    private int currentPlayerId;
    private boolean isMatchStarted;


    public GameHandler(Server server){
        game = new Game();
        controller = new Controller(game, this);
        System.out.println("Istantiating new game and controller");
        this.server = server;
        gameHandlerListener.addPropertyChangeListener(controller);

    }



    public boolean getExpertMode() {
        return isExpertMode;
    }

    public void setExpertMode(boolean expertMode) {
        isExpertMode = expertMode;
        game.setExpertMode(expertMode);
        if(isExpertMode) {
            controller.setExpertController(new ExpertController(game, controller.getTurnController()));
        } else {
            System.out.println("NON SETTO EXPERT");
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

    public void setupTeams() {
        sendBroadcast(new DynamicAnswer("Setting up teams...", false));
        int teamID1 = 1;
        int teamID2 = 2;
        System.out.println("Arrivo qui " + game.getPlayers().size());

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
            System.out.println("Player " + game.getPlayers().get(i).getNickname() + " joined team " + game.getPlayers().get(i).getIdTeam());

            sendBroadcast(new DynamicAnswer("Player " + game.getPlayers().get(i).getNickname() + " joined team " + game.getPlayers().get(i).getIdTeam(), false));
        }

    }

    public Controller getController() {
        return controller;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }


    public void sendSinglePlayer(Answer serverAnswer, int clientID) {
        server.getVirtualClientFromID(clientID).sendAnswerToClient(serverAnswer);
    }

    public void sendExcept(Answer serverAnswer, int notClientID) {
        for(Player activePlayers : controller.getGame().getActivePlayers()) {
            if(server.getIDFromNickname(activePlayers.getNickname()) != notClientID) {
                sendSinglePlayer(serverAnswer, activePlayers.getPlayerID());
            }
        }
    }

    public void sendBroadcast(Answer serverAnswer) {
        for(Player player : controller.getGame().getActivePlayers()) {
            sendSinglePlayer(serverAnswer, server.getIDFromNickname(player.getNickname()));
        }
    }

    public void setPlayersNumber(int playersNumber) {
        System.err.println("setting players of game to " + playersNumber);
        this.playersNumber = playersNumber;
        game.setPlayersNumber(playersNumber);
    }


    public void initializeWizards() {
        System.out.println("Il numero di giocatori della partita è: " + playersNumber);
        WizardAnswer chooseWizard = new WizardAnswer("Please choose your Wizard!");
        chooseWizard.setWizardsLeft(Wizards.notChosen());
        if ((playersNumber == 2 && Wizards.notChosen().size() > 2)) {
            String playerNickname = game.getActivePlayers().get(playersNumber - Wizards.notChosen().size() + 2).getNickname();
            System.out.println(playerNickname);
            sendSinglePlayer(chooseWizard, server.getIDFromNickname(playerNickname));
            sendExcept(new DynamicAnswer("Please wait: player " + playerNickname + " is choosing his wizard!", false), server.getIDFromNickname(playerNickname));
            return;
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

    public void startGame() {
        //GESTISCI MESSAGGIO PER METTERE GAMESTARTED IN MODELVIEW
        setMatchStarted();
        sendBroadcast(new DynamicAnswer("Game is started", false));
        if(playersNumber == 4) {
            sendBroadcast(new FourPModeNotification());
            setupTeams();
        }
        //game.setCurrentPlayer(game.getActivePlayers().get(randomGenerator.nextInt(playersNumber)));
        game.setCurrentPlayer(game.getActivePlayers().get(0));
        System.out.println("Current player is " + game.getCurrentPlayer().getNickname());
        controller.newSetupGame();
    }

    public void unregisterPlayer(int leavingPlayer) {
        game.removePlayer(game.getPlayerByID(leavingPlayer));
    }

    public void parseActions(UserAction userAction, String actionType) {
        System.out.println(actionType);

        switch(actionType) {
            case "PickAssistant" -> {
                System.out.println("Entro nel gameHandler e parso pickassistnat");
                System.out.println(((PickAssistant) userAction).getChosenAssistant().toString());

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
                        System.out.println("ENTRO QUA");
                        gameHandlerListener.firePropertyChange("PickDestinationIsland", null, ((PickDestination) userAction).getDestination());
                    }
                }
                else if (((PickDestination) userAction).getDestination() instanceof DiningRoom) {
                    gameHandlerListener.firePropertyChange("PickDestinationDiningRoom", null, ((PickDestination) userAction).getDestination());
                }


            }

            case "GrannyHerbsTile" -> {
                gameHandlerListener.firePropertyChange("GrannyHerbsTile", null, ((PickDestination) userAction).getChosenIsland());
            }

            case "PickMovesNumber" -> gameHandlerListener.firePropertyChange("PickMovesNumber", null, ((PickMovesNumber) userAction).getMoves());

            case "PickCloud" -> gameHandlerListener.firePropertyChange("PickCloud", null, ((PickCloud) userAction).getChosenCloud());

            case "PickCharacter" -> gameHandlerListener.firePropertyChange("PickCharacter", null, ((PickCharacter) userAction).getChosenCharacter());

            case "PickPawnType" -> gameHandlerListener.firePropertyChange("PickPawnType", null, ((PickPawnType) userAction).getType());


            case "PickCharacterActionsNum" -> gameHandlerListener.firePropertyChange("PickCharacterActionsNum", null, ((PickCharacterActionsNum) userAction).getNumber());

        }

    }


    public void endPlayerGame(String playerDisconnected) {
        sendBroadcast(new DynamicAnswer("Player " + playerDisconnected + " has disconnected :( Game will finish without a winner! Thanks to have played Eriantys! Hope to see you soon ;)", false));
        sendBroadcast(new NoWinnerGameNotification());
        for(Player p  : game.getActivePlayers()) {
            server.getVirtualClientFromID(p.getPlayerID()).getSocketClientConnection().closeConnection();
        }
    }


    public void endGame() {
        while(!game.getActivePlayers().isEmpty()) {
            server.getVirtualClientFromID(game.getActivePlayers().get(0).getPlayerID()).getSocketClientConnection().closeConnection();
        }

    }


}