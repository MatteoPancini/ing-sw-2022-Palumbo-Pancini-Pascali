package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.Game;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import it.polimi.ingsw.model.player.*;
import java.lang.Object;
import java.util.Random;

public class GameHandler {
    private Game game;
    public Controller controller;
    private GameBoard gameBoard;
    private ArrayList<Player> players;
    private ArrayList<SchoolBoard> schoolBoards;

    public GameHandler(Game game, Controller controller){
        this.game = game;
        this.controller = controller;
        gameBoard = new GameBoard();
        schoolBoards = new ArrayList<SchoolBoard>();
    }

    public void putStudentsOnCloud() {
        for (CloudTile cloud : gameBoard.getClouds()) {
            ArrayList<Student> newStudents = new ArrayList<Student>();
            Collections.shuffle(gameBoard.getStudentsBag());
            int studentsNumber;
            if(game.getPlayersNumber() == 3) studentsNumber = 4;
            else studentsNumber = 3;
            for (int j = 0; j < studentsNumber; j++) {
                newStudents.get(j) = gameBoard.getStudentsBag().get(0);
                gameBoard.removeStudents(0);
            }
            cloud.setStudents(newStudents);
        }
    }

    public void initialize() {
        for(int p = 1; p <= game.getPlayersNumber(); p++){
            SchoolBoard newSchoolBoard = new SchoolBoard(p);
            schoolBoards.add(newSchoolBoard);
            game.getPlayers().get(p - 1).setBoard(newSchoolBoard);
            game.getPlayers().get(p - 1).setID(p);
        }

        if(game.getPlayersNumber() == 4) {
            Collections.shuffle(game.getPlayers());
            game.getPlayers().get(0).setTeammateID(game.getPlayers().get(2).getID());
            game.getPlayers().get(2).setTeammateID(game.getPlayers().get(0).getID());
            game.getPlayers().get(1).setTeammateID(game.getPlayers().get(3).getID());
            game.getPlayers().get(3).setTeammateID(game.getPlayers().get(1).getID());
        }

        Collections.shuffle(game.getPlayers());
        game.setCurrentPlayer(game.getPlayers().get(0));

        putStudentsOnCloud();

        int studentsNumber;
        if(game.getPlayersNumber() == 3) studentsNumber = 9;
        else studentsNumber = 7;
        for(SchoolBoard sB : schoolBoards){
            for(int i = 1; i <= studentsNumber; i++){
                Collections.shuffle(gameBoard.getStudentsBag());
                sB.getEntrance().getStudents().add(gameBoard.getStudentsBag().get(0));
                gameBoard.removeStudents(0);
            }
        }

        int towersNumber;
        ArrayList<TowerColor> allColors = new ArrayList<TowerColor>();
        allColors.add(TowerColor.WHITE, TowerColor.BLACK, TowerColor.GREY);

        if(game.getPlayersNumber() == 3) {
            towersNumber = 6;
            int colorsCounter3P = 0;
            for(SchoolBoard s : schoolBoards){
                for(int j = 1; j <= towersNumber; j++) {
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
                if(p.getID() == 0 || p.getID() == 2) {
                    p.getBoard().getTowerArea().addTowers(new Tower(allColors.get(colorsCounter4P)));
                    colorsCounter4P++;
                }
            }
        }

        int maximum = 11;
        gameBoard.getMotherNature().setPosition(Random.nextInt(maximum) + 1);
        int n = 1;
        for(int k = 1; k <= 11; k++){
            if(n != 6){
                int pos;
                pos = (gameBoard.getMotherNature().getPosition() + k) % 12;
                Collections.shuffle(gameBoard.getStudentsBag());
                gameBoard.getIslands().get(pos - 1).addStudent(gameBoard.getStudentsBag().get(0)));
                gameBoard.removeStudents(0);
            }
            n++;
        }
    }
}