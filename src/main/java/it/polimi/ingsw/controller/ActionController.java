package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.board.Student;

import java.util.ArrayList;

/*public class ActionController {
    private final Game game;
    private final GameBoard board;

    public ActionController(Game game, GameBoard board){
        this.game = game;
        this.board = board;
    }

    public void moveStudents(message1, message2) {
        //message1 = colore studente, message2 = destinazione (isola o diningroom)
        Student studentToMove = new Student(message1);
        GameBoard board;
        PawnType studType = message1;
        int destinationIsland;
        //putStudentsDiningRoom o putStudentsIsland
        for(int i = 0; i < 3; i++) {
            studentToMove = player.pickStudent();

            //switch oppure if in cui il player dove spostarlo
            //se lo sposta in island -> destinationIsland:
            if(message2 == "island") {
                destinationIsland = player.pickIsland().islandID;
                for (Island island : gameBoard.getIslands()) {
                    if (island.getIslandID() == destinationIsland) {
                        island.setStudent(studentToMove);
                    }

                }
                destinationIsland.setStudent(studentToMove);
            }
            //se lo sposta nella DiningRoom -> studType
            else if(message2 == "dining room") {
                //try ... catch se ha già il tavolo pieno
                studType = studentToMove.getType();
                player.getBoard().diningRoom.setStudent(studentToMove);
            }
        }
    }

    //public Player checkIslandInfluence(Island island){

    //}

    public void moveMotherNature(int moves) {
        //moves lo leggo lato client
        int currPosition = board.getMotherNature().getPosition();
        int newPosition = currPosition % 12;
        board.getMotherNature().setPosition(newPosition);

        //checkIslandInfluence() { ... }
    }


    public void fromCloudToEntrance(CloudTile cloud) {
        int studentsToMove;
        if (game.getPlayersNumber() == 3) studentsToMove = 4;
        else studentsToMove = 3;
        ArrayList<Student> newStudents = cloud.getStudents();
    }
}*/
