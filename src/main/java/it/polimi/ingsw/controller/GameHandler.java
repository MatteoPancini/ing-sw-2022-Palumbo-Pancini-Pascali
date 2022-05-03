package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.servertoclient.Answer;
import it.polimi.ingsw.messages.servertoclient.DynamicAnswer;
import it.polimi.ingsw.messages.servertoclient.WizardAnswer;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.Game;

import java.util.ArrayList;
import java.util.Collections;

import it.polimi.ingsw.model.board.cards.AssistantCard;
import it.polimi.ingsw.model.player.*;
import it.polimi.ingsw.server.Server;

import java.util.Random;

public class GameHandler {
    private static Game game;
    private Controller controller;
    private Server server;
    private int playersNumber;
    private static GameBoard gameBoard;
    private ArrayList<Player> players;
    private ArrayList<SchoolBoard> schoolBoards;
    private static GameBoard gameBoardCopy;
    private boolean isExpertMode;

    public GameHandler(Server server){
        game = new Game();
        controller = new Controller(game, this);
        this.server = server;
    }


    public boolean getExpertMode() {
        return isExpertMode;
    }

    public void setExpertMode(boolean expertMode) {
        isExpertMode = expertMode;
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

    public void updateAssistantsState() {
        for(AssistantCard assistant : gameBoardCopy.getLastAssistantUsed()) {
            assistant.setState(CardState.PLAYED);
        }
    }

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
    }



    public void initializeGame() {
        Wizards.reset();
        WizardAnswer chooseWizard = new WizardAnswer("Please choose your Wizard!\nType 1 for");
        chooseWizard.setWizardsLeft(Wizards.notChosen());

        for(Player currPlayer : game.getActivePlayers()) {
            String currNick = currPlayer.getNickname();
            sendSinglePlayer(chooseWizard, currPlayer.getPlayerID());
            sendExcept(new DynamicAnswer("Please wait: player " + currPlayer.getNickname() + " is choosing a wizard!"), currPlayer.getPlayerID());


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
    }

    public void unregisterPlayer(int id) {
        game.removePlayer(game.getPlayerByID(id));
    }
}