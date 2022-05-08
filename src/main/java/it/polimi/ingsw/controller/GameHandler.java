package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.servertoclient.Answer;
import it.polimi.ingsw.messages.servertoclient.DynamicAnswer;
import it.polimi.ingsw.messages.servertoclient.WizardAnswer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.Wizards;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.server.Server;

import java.util.ArrayList;

public class GameHandler {
    private static Game game;
    private Controller controller;
    private Server server;
    private int playersNumber;
    private ArrayList<Player> players;
    private boolean isExpertMode;
    private boolean isTeamMode = false;

    public GameHandler(Server server){
        game = new Game();
        controller = new Controller(game, this);
        System.out.println("Istantiating new game and controller");
        this.server = server;
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
    }



    public static Game getGame() {
        return game;
    }

    public void addGamePlayer(String playerNickname, int playerID) {
        game.createNewPlayer(playerNickname, playerID);

    }

    public void setupTeams() {
        sendBroadcast(new DynamicAnswer("Setting up teams...", false));
        int teamID1 = 1;
        int teamID2 = 2;
        for(int i = 0; i < playersNumber; i++) {
            if(i % 2 == 0) {
                game.getPlayers().get(i).setIdTeam(teamID1);
            } else {
                game.getPlayers().get(i).setIdTeam(teamID2);
            }
            sendBroadcast(new DynamicAnswer("Player " + game.getPlayers().get(i).getNickname() + " joined team " + game.getPlayers().get(i).getIdTeam(), false));
        }

    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Controller getController() {
        return controller;
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
        } else if (playersNumber == 4) {
            if (Wizards.notChosen().size() == 1) {
                String playerNickname = game.getActivePlayers().get(playersNumber - Wizards.notChosen().size()).getNickname();
                game.getPlayerByNickname(playerNickname).setWizard(Wizards.notChosen().get(0));
                sendSinglePlayer(new WizardAnswer("You are the last player in the lobby, so the game will choose for you."), server.getIDFromNickname(playerNickname));
                sendSinglePlayer(new WizardAnswer(null, "Wizard selection completed! You are " + Wizards.notChosen().get(0)), server.getIDFromNickname(playerNickname));
                Wizards.removeAvailableWizard(Wizards.notChosen().get(0));
                startGame();
            } else {
                String playerNickname = game.getActivePlayers().get(playersNumber - Wizards.notChosen().size() + 1).getNickname();
                sendSinglePlayer(chooseWizard, server.getIDFromNickname(playerNickname));
                sendExcept(new DynamicAnswer("Please wait: player " + playerNickname + " is choosing his wizard!", false), server.getIDFromNickname(playerNickname));
            }
            return;
        } else {
            startGame();

        }
    }

    public void startGame() {
        //GESTISCI MESSAGGIO PER METTERE GAMESTARTED IN MODELVIEW
        sendBroadcast(new DynamicAnswer("Game is started", false));
        controller.setupGame();
    }
        /*
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
        game.setCurrentPlayer(game.getPlayers().get(0));

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

         */

    public void unregisterPlayer(int leavingPlayer) {
        game.removePlayer(game.getPlayerByID(leavingPlayer));
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

    public void updateAssistantsState() {
        for(AssistantCard assistant : gameBoardCopy.getLastAssistantUsed()) {
            assistant.setState(CardState.PLAYED);
        }
    }

    public void sendSinglePlayer(Answer serverAnswer, int clientID) {
        server.getVirtualClientFromID(clientID).sendAnswerToClient(serverAnswer);
    }

    public void sendBroadcast(Answer serverAnswer) {
        for(Player player : game.getActivePlayers()) {
            sendSinglePlayer(serverAnswer, server.getIDFromNickname(player.getNickname()));
        }
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
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
        game.setCurrentPlayer(game.getPlayers().get(0));

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