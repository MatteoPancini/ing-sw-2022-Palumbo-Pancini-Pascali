package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.Professor;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.Collections;

public class TurnHandler {
    private Game game;
    private ArrayList<Player> activePlayers;

    public Player islandInfluenceCheck(Island island) {

    }

    public Player professorInfluenceCheck(Professor professor) {

    }

    public void moveMotherNature(int moves) {
        int currPosition = Game.motherNature.getPosition();

        int newPosition = currPosition + moves;
        if(newPosition > 12) {
            newPosition = currPosition - 12;
        }

    }

    public void putStudentsEntrance() {
        Collections.shuffle(game.getStudentsBag());
        for (Player player : activePlayers) {
            for(int i = 0; i < NUM_STUD; i++) {
                player.getMyStudentsEntrance().add(game.getStudentsBag().get(i));
                game.getStudentsBag().remove(i);
            }
        }
    }

    public int getAssistantValue() {

    }

    public void putStudentDiningRoom(Student student) {

    }

    public void putStudentIsland(Student student, Island island) {

    }

    public void pianificationPhase() {

    }

    public void actionPhase() {

    }

}


