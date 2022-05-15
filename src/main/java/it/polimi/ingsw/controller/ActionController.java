package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.board.Student;

import java.util.ArrayList;

public class ActionController {
    private final Game game;
    private final GameBoard board;

    public ActionController(Game game, GameBoard board){
        this.game = game;
        this.board = board;
    }

    /*public void moveStudents(message1, message2) {

    }

    public Player checkIslandInfluence(Island island){

    }

    public void moveMotherNature(int moves) {
        //moves lo leggo lato client
        int currPosition = board.getMotherNature().getPosition();
        int newPosition = currPosition % 12;
        board.getMotherNature().setPosition(newPosition);

        checkIslandInfluence() { ... }
    }*/


    public void fromCloudToEntrance(CloudTile cloud) {
        int studentsToMove;
        if (game.getPlayersNumber() == 3) studentsToMove = 4;
        else studentsToMove = 3;
        ArrayList<Student> newStudents = cloud.getStudents();
    }
}
