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

public class GameHandler {
    private Game game;
    public Controller controller;
    private GameBoard board;

    public GameHandler{
        this.game = game;
        this.controller = controller;
        this.board = board;
    }

    public void putStudentsOnCloud() {
        for (CloudTile cloud : board.getClouds()) {
            ArrayList<Student> newStudents = new ArrayList<Student>();
            Collections.shuffle(board.getStudentsBag());
            int studentsNumber;
            if(game.getPlayersNumber() == 3) studentsNumber = 4;
            else studentsNumber = 3;
            for (int j = 0; j < studentsNumber; j++) {
                newStudents.get(j) = board.getStudentsBag().get(0);
                board.removeStudents(0);
            }
            cloud.setStudents(newStudents);
        }
    }

    public void updateAssistantsState() {
        for(AssistantCard assistant : board.getLastAssistantUsed()) {
            assistant.setState(CardState.PLAYED);
        }
    }

    public void initialize() {
        /*
            Collections.shuffle(players);
            currentPlayer = players.get(0);
         */
    }
}
