package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.enumerations.CardState;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.board.CloudTile;

import java.util.ArrayList;
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
            for (int i = 0; i < game.getPlayersNumber(); i++) { //costante definita in fase di inizializzazione
                List<Student> newStudents = new ArrayList<Student>();
                //chiamo random/shuffle su Game.studentsBag + assegno a newStudents i primi/ultimi N studenti
                cloud.setStudents(newStudents);
            }
        }
    }

    public void updateAssistantsState() {
        for(AssistantCard assistant : board.getLastAssistantUsed()) {
            assistant.setState(CardState.PLAYED);
        }
    }

    public void initialize() {
        ...
        /*
            Collections.shuffle(players);
            currentPlayer = players.get(0);
         */
    }
}
