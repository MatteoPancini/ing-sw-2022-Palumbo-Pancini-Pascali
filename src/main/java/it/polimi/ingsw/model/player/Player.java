package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.AssistantDeck;
import it.polimi.ingsw.model.enumerations.MotherNatureMoves;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.game.Island;
import it.polimi.ingsw.model.game.Student;

import java.util.ArrayList;

public class Player {
    private String nickname;
    private AssistantDeck assistantDeck;
    private ArrayList<Student> myStudents;
    private SchoolBoard board;
    private boolean isPlaying;
    private boolean isWinner;

    public Player(String name) {
        nickname = name;
        assistantDeck = new AssistantDeck();
        myStudents = new ArrayList<Student>();
        board = new SchoolBoard();
        isPlaying = true;
        isWinner = false;
    };

    public String getNickname() {
        return nickname;
    };

    public AssistantDeck getAssistantDeck() {
        return assistantDeck;
    }

    public SchoolBoard getBoard() {
        return board;
    }

    public ArrayList<Student> getMyStudents() {
        return myStudents;
    }

    public boolean isPlaying() {
        return isPlaying;
    };

    public boolean isWinner() {
        return isWinner;
    }

    public AssistantCard pickAssistant() { ... };
    public void moveMotherNature(MotherNatureMoves moves) { ... };
    public void buildTower(Island island) { ... };
    public void putStudentDiningRoom(Student stud) { ... };
    public void putStudentIsland(Student stud) { ... };
    public void takeCoin() { ... };
    public void playCharacter(CharacterCard character) { ... };
}