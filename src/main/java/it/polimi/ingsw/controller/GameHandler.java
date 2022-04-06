package it.polimi.ingsw.controller;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.Game;

import java.util.ArrayList;
import java.util.Collections;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.player.*;

import java.util.Random;

public class GameHandler {
    private static Game game;
    public Controller controller;
    private static GameBoard gameBoard;
    private ArrayList<Player> players;
    private ArrayList<SchoolBoard> schoolBoards;
    private static GameBoard gameBoardCopy;

    public GameHandler(Game game, Controller controller){
        this.game = game;
        this.controller = controller;
        gameBoard = new GameBoard(game);
        schoolBoards = new ArrayList<SchoolBoard>();
        gameBoardCopy = this.gameBoard;
    }

    public static Game getGame() {
        return game;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
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
                newStudents.add(gameBoardCopy.getStudentsBag().get(0));
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

    public void initialize() {
        for(int p = 1; p <= Constants.getPlayersNum(); p++){
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
        Random rand = new Random() ;
        gameBoard.getMotherNature().setPosition(rand.nextInt(maximum + 1));
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
}