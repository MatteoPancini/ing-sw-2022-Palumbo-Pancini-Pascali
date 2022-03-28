package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.enumerations.CardState;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Student;

import java.util.ArrayList;
import java.util.List;

public class GameHandler {




    public void putStudentsOnCloud() {
        for (cloud in Game.clouds) {
            for (int i = 0; i < NUM_STUDENTS; i++) { //costante definita in fase di inizializzazione
                List<Student> newStudents = new ArrayList<Student>();
                //chiamo random/shuffle su Game.studentsBag + assegno a newStudents i primi/ultimi N studenti
                cloud.setStudents(newStudents);
            }
        }
    }

    public void updateAssistantsState() {
        for(AssistantCard assistant : Game.lastAssistantUsed) {
            assistant.setState(CardState.PLAYED);
        }
    }

    public void initialize() {
        ...
    }
}
