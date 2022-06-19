package it.polimi.ingsw.controller;



import it.polimi.ingsw.messages.clienttoserver.FourPModeNotification;
import it.polimi.ingsw.messages.clienttoserver.actions.*;
import it.polimi.ingsw.messages.servertoclient.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.Wizards;
import it.polimi.ingsw.model.player.DiningRoom;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.server.Server;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class GameHandler {
    private static Game game;
    private final Controller controller;
    private final Server server;
    private int playersNumber;
    private ArrayList<Player> players;
    private final PropertyChangeSupport gameHandlerListener = new PropertyChangeSupport(this);
    private boolean isExpertMode;
    private boolean isTeamMode = false;
    private int currentPlayerId;
    private boolean isMatchStarted;

    private Thread timeout;
    private boolean activeTimeout = false;
    private boolean restartGame = false;


    public GameHandler(Server server){
        game = new Game();
        controller = new Controller(game, this);
        System.out.println("Istantiating new game and controller");
        this.server = server;
        gameHandlerListener.addPropertyChangeListener(controller);

    }


    public void setTeamMode(boolean teamMode) {
        isTeamMode = teamMode;
    }


    public boolean getExpertMode() {
        return isExpertMode;
    }

    public void setExpertMode(boolean expertMode) {
        isExpertMode = expertMode;
        game.setExpertMode(expertMode);
        if(isExpertMode) {
            controller.setExpertController(new ExpertController(game, game.getGameBoard(), controller.getTurnController()));
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

        /*
        System.out.println("Setto le board");
        for(int i = 0; i < playersNumber; i++) {
            System.out.println(game.getPlayers().get(i).getNickname() + " setting");
            if(game.getPlayers().get(i).isTeamLeader()) {
                System.out.println(game.getPlayers().get(i).getNickname() + " leader");
                game.getPlayers().get(i).setBoard(new SchoolBoard(game.getPlayers().get(i).getPlayerID()));
            } else {
                for(int j = 0; j < playersNumber; j++){
                    if(game.getPlayers().get(j).isTeamLeader() && game.getPlayers().get(i).getIdTeam() == game.getPlayers().get(j).getIdTeam()) {
                        game.getPlayers().get(i).setBoard(game.getPlayers().get(j).getBoard());
                    }
                }

            }
        }

         */

    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Controller getController() {
        return controller;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    /*
    public void putStudentsOnCloud() {
        for (CloudTile cloud : gameBoardCopy.getClouds()) {
            ArrayList<Student> newStudents = new ArrayList<Student>();
            Collections.shuffle(gameBoardCopy.getStudentsBag());
            int studentsNumber;
            if(game.getPlayersNumber() == 3) studentsNumber = 4;
            else studentsNumber = 3;
            for (int j = 0; j < studentsNumber; j++) {
                newStudents.get(j) = gameBoardCopy.getStudentsBag().get(0);
                gameBoard.removeStudents(0);
            }
            cloud.setStudents(newStudents);
        }
    }

     */
/*
    public void updateAssistantsState() {
        for(AssistantCard assistant : gameBoardCopy.getLastAssistantUsed()) {
            assistant.setState(CardState.PLAYED);
        }
    }

 */

    public void sendSinglePlayer(Answer serverAnswer, int clientID) {
        server.getVirtualClientFromID(clientID).sendAnswerToClient(serverAnswer);
    }

    public void sendExcept(Answer serverAnswer, int notClientID) {
        for(Player activePlayers : game.getActivePlayers()) {
            if(server.getIDFromNickname(activePlayers.getNickname()) != notClientID) {
                sendSinglePlayer(serverAnswer, activePlayers.getPlayerID());
            }
        }
    }

    public void sendBroadcast(Answer serverAnswer) {
        for(Player player : game.getActivePlayers()) {
            sendSinglePlayer(serverAnswer, server.getIDFromNickname(player.getNickname()));
        }
    }

    public void setPlayersNumber(int playersNumber) {
        System.err.println("setting players of game to " + playersNumber);
        this.playersNumber = playersNumber;
        game.setPlayersNumber(playersNumber);
    }


    public boolean isTeamMode() {
        return isTeamMode;
    }

    public void initializeWizards() {
        System.out.println("Il numero di giocatori della partita è: " + playersNumber);
        WizardAnswer chooseWizard = new WizardAnswer("Please choose your Wizard!");
        chooseWizard.setWizardsLeft(Wizards.notChosen());
        if ((playersNumber == 2 && Wizards.notChosen().size() > 2)) {
            //System.out.println("Entro nel primo if...");
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
            /*if (Wizards.notChosen().size() == 1) {
                String playerNickname = game.getActivePlayers().get(playersNumber - Wizards.notChosen().size()).getNickname();
                game.getPlayerByNickname(playerNickname).setWizard(Wizards.notChosen().get(0));
                sendSinglePlayer(new WizardAnswer("You are the last player in the lobby, so the game will choose for you."), server.getIDFromNickname(playerNickname));
                sendSinglePlayer(new WizardAnswer(null, "Wizard selection completed! You are " + Wizards.notChosen().get(0)), server.getIDFromNickname(playerNickname));
                Wizards.removeAvailableWizard(Wizards.notChosen().get(0));
                startGame();
            } else {*/
                String playerNickname = game.getActivePlayers().get(playersNumber - Wizards.notChosen().size()).getNickname();
                sendSinglePlayer(chooseWizard, server.getIDFromNickname(playerNickname));
                sendExcept(new DynamicAnswer("Please wait: player " + playerNickname + " is choosing his wizard!", false), server.getIDFromNickname(playerNickname));
            //}
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
        /*


         */

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


    /*
    public void endPlayerGame(String playerDisconnected) {
        server.getVirtualClientFromID(server.getIDFromNickname(playerDisconnected)).getSocketClientConnection().closeConnection();
        for(Player p : game.getActivePlayers()) {
            if(p.getNickname() == playerDisconnected) {
                game.getActivePlayers().remove(p);
                break;
            }
        }

        //in caso di disconessione rimuove la carta giocata

        for(AssistantCard a : game.getGameBoard().getLastAssistantUsed()) {
            if(a.getOwner().getNickname() == playerDisconnected) {
                game.getGameBoard().getLastAssistantUsed().remove(a);
            }
        }



        if(game.getActivePlayers().size() == 1) {
            startTimeoutGame();
            while(activeTimeout) {
                if(game.getActivePlayers().size() > 1) {
                    restartGame = true;
                    activeTimeout = false;
                }
            }

            if(!restartGame) {
                for(Player p : game.getActivePlayers()) {
                    sendSinglePlayer(new DynamicAnswer("TIMEOUT EXPIRED! Game is finished!", false), p.getPlayerID());
                }
            endGame();
            }
        }

    }

     */

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


    public void startTimeoutGame() {
        timeout = new Thread(() -> {
            try{
                for(int i = 1; i < 30000; i++) {
                    Thread.sleep(1000);
                }
                this.activeTimeout = false;
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        });
        timeout.start();
    }





}

/*

import it.polimi.ingsw.messages.servertoclient.Answer;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.Game;

import java.util.ArrayList;
import java.util.Collections;

//import it.polimi.ingsw.model.board.cards.AssistantCard;
import it.polimi.ingsw.model.player.*;
//import it.polimi.ingsw.server.Server;

import java.util.Random;

/*public class GameHandler {
    private static Game game;
    private Controller controller;
    private Server server;
    private int playersNumber;
    private static GameBoard gameBoard;
    private ArrayList<Player> players;
    private ArrayList<SchoolBoard> schoolBoards;
    private static GameBoard gameBoardCopy;

    public GameHandler(Server server){
        game = new Game();
        controller = new Controller(game, this);
        this.server = server;
    }

    public static Game getGame() {
        return game;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void addGamePlayer(String playerNickname, int playerID) {
        game.addPlayer(new Player(playerNickname, playerID));
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public static GameBoard getGameBoardCopy() {
        return gameBoardCopy;
    }

    public ArrayList<SchoolBoard> getSchoolBoards() {
        return schoolBoards;
    }



    public void updateAssistantsState() {
        for(AssistantCard assistant : gameBoardCopy.getLastAssistantUsed()) {
            assistant.setState(CardState.PLAYED);
        }
    }




    public void initializeGame() {
        for(int p = 1; p <= game.getPlayersNumber(); p++){
            SchoolBoard newSchoolBoard = new SchoolBoard(p);
            schoolBoards.add(newSchoolBoard);
            game.getPlayers().get(p - 1).setBoard(newSchoolBoard);
            game.getPlayers().get(p - 1).setPlayerID(p);
        }


        if(game.getPlayersNumber() == 4) {
            Collections.shuffle(game.getPlayers());
            game.getPlayers().get(0).setTeammateID(game.getPlayers().get(2).getPlayerID());
            game.getPlayers().get(2).setTeammateID(game.getPlayers().get(0).getPlayerID());
            game.getPlayers().get(1).setTeammateID(game.getPlayers().get(3).getPlayerID());
            game.getPlayers().get(3).setTeammateID(game.getPlayers().get(1).getPlayerID());
        }

        Collections.shuffle(game.getPlayers());
        game.switchPlayer(game.getPlayers().get(0));

        putStudentsOnCloud();

        int studentsNumber;
        if(game.getPlayersNumber() == 3) studentsNumber = 9;
        else studentsNumber = 7;
        for(SchoolBoard s : schoolBoards){
            for(int i = 1; i <= studentsNumber; i++){
                Collections.shuffle(gameBoard.getStudentsBag());
                s.getEntrance().getStudents().add(gameBoard.getStudentsBag().get(0));
                gameBoard.removeStudents(0);
            }
        }

        int towersNumber;
        ArrayList<TowerColor> allColors = new ArrayList<TowerColor>();
        allColors.add(TowerColor.WHITE);
        allColors.add(TowerColor.BLACK);
        allColors.add(TowerColor.GREY);
        if(game.getPlayersNumber() == 3) {
            towersNumber = 6;
            int colorsCounter3P = 0;
            for(SchoolBoard s : schoolBoards){
                for(int i = 1; i <= towersNumber; i++) {
                    s.getTowerArea().addTowers(new Tower(allColors.get(colorsCounter3P)));
                }
                colorsCounter3P++;
            }
        }

        if(game.getPlayersNumber() == 2) {
            towersNumber = 8;
            int colorsCounter2P = 0;
            for(SchoolBoard s : schoolBoards){
                for(int k = 1; k <= towersNumber; k++) {
                    s.getTowerArea().addTowers(new Tower(allColors.get(colorsCounter2P)));
                }
                colorsCounter2P++;
            }
        }

        if(game.getPlayersNumber() == 4){
            towersNumber = 8;
            int colorsCounter4P = 0;
            for(Player p : game.getPlayers()){
                if(p.getPlayerID() == 0 || p.getPlayerID() == 2) {
                    p.getBoard().getTowerArea().addTowers(new Tower(allColors.get(colorsCounter4P)));
                    colorsCounter4P++;
                }
            }
        }

        int maximum = 11;
        gameBoard.getMotherNature().setPosition(Random.nextInt(maximum) + 1);
        int n = 1;
        for(int s = 1; s <= 11; s++){
            if(n != 6){
                int pos;
                pos = (gameBoard.getMotherNature().getPosition() + s) % 12;
                Collections.shuffle(gameBoard.getStudentsBag());
                gameBoard.getIslands().get(pos - 1).addStudent(gameBoard.getStudentsBag().get(0));;
                gameBoard.removeStudents(0);
            }
            n++;
        }
    }

    public boolean endGame(){
        boolean endGame = false;

        for(Player p : game.getActivePlayers()){
            if(p.getAssistantDeck().getDeck().isEmpty() || p.getBoard().getTowerArea().getTowerArea().isEmpty()) {
                endGame = true;
                return endGame;
            }
        }

        if(game.getGameBoard().getIslands().size() == 3){
            endGame = true;
            return endGame;
        }
    }
}*/