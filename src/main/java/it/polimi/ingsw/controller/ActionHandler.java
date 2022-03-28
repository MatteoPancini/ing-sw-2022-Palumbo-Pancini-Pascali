package it.polimi.ingsw.controller;

public class ActionHandler {


    public void moveStudents() {
        for(int i = 0; i < 3; i++) {
            studentToMove = player.pickStudent();

            //switch oppure if in cui il player dove spostarlo
            //se lo sposta in island -> destinationIsland:
            destinationIsland.islandID = player.pickIsland();
            destinationIsland.setStudent(studentToMove);
        }
    }
}
