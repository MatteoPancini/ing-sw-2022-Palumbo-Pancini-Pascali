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

    public void updateAssistantsState() {
        for(AssistantCard assistant : gameBoard.getLastAssistantUsed()) {
            assistant.setState(CardState.PLAYED);
        }
    }

    public void initialize() {
        for(int p = 1; p <= game.getPlayersNumber(); p++){
            SchoolBoard newSchoolBoard = new SchoolBoard(p);
            schoolBoards.add(newSchoolBoard);
            game.getPlayers().get(p - 1).setBoard(newSchoolBoard);
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
        allColors.add(TowerColor.WHITE, TowerColor.BLACK, TowerColor.GREY);
        if(game.getPlayersNumber() == 3) {
            towersNumber = 6;
            int j = 0;
            for(SchoolBoard s : schoolBoards){
                for(int i = 1; i <= towersNumber; i++) {
                    s.getTowerArea().addTowers(new Tower(allColors.get(j)));
                }
                j++;
            }
        }

        if(game.getPlayersNumber() == 2) {
            towersNumber = 8;
            int z = 0;
            for(SchoolBoard s : schoolBoards){
                for(int k = 1; k <= towersNumber; k++) {
                    s.getTowerArea().addTowers(new Tower(allColors.get(z)));
                }
                z++;
            }
        }

        if(game.getPlayersNumber() == 4){
            towersNumber = 8;

        }

         //BOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOH
        gameBoard.getMotherNature().setPosition((gameBoard.getIslands()).get(0).getIslandID());
        int n = 0;
        for(int s = 1; s <= 11; s++){
            if(n != 6){
                int pos;
                pos = gameBoard.getMotherNature().getPosition() + s;
                gameBoard.getIslands().get(pos).addStudent(Collections.shuffle(gameBoard.getStudentsBag().get(0)));
                gameBoard.removeStudents(0);
            }
            n++;
        }
    }
}












