package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.enumerations.CardState;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.board.CloudTile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.player.Tower;
import it.polimi.ingsw.model.player.SchoolBoard

public class GameHandler {
    private Game game;
    public Controller controller;
    private GameBoard gameBoard;
    private ArrayList<SchoolBoard> schoolBoards;

    public GameHandler(Game game, Controller controller,
                       GameBoard gameBoard, ArrayList<SchoolBoard> schoolBoards){
        this.game = game;
        this.controller = controller;
        this.gameBoard = gameBoard;
        this.schoolBoards = schoolBoards;
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
        ArrayList<TowerColor> allColors = new ArrayList<TowerColor>;
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
        else{
            towersNumber = 8;

        }


        Collections.shuffle(gameBoard.getIslands())
        gameBoard.getMotherNature().setPosition((gameBoard.getIslands()).get(0));
    }
}












