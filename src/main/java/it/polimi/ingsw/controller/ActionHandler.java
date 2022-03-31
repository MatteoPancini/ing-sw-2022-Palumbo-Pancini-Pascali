package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.enumerations.PawnType;
import it.polimi.ingsw.model.board.MotherNature;
import it.polimi.ingsw.model.player.Player;

public class ActionHandler {


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

    public Player checkIslandInfluence(Island island){
        //TODO: gigiox
    }

    public void moveMotherNature(int moves) {
        //moves lo leggo lato client
        int currPosition = board.getMotherNature().getPosition();
        int newPosition = currPosition % 12;
        board.getMotherNature().setPosition(newPosition);

        checkIslandInfluence() { ... }
    }


    public void fromCloudToEntrance(CloudTile cloud) {
        //cloud lo leggo lato client
        for(Student stud : cloud.getCloudStudents())
            player.getBoard().entrance.students.add(stud);
    }
}
