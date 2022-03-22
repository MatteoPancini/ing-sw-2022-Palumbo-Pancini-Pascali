package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.AssistantDeck;
import it.polimi.ingsw.model.enumerations.MotherNatureMoves;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.game.CloudTile;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.Island;
import it.polimi.ingsw.model.game.Student;


import java.util.ArrayList;

public class Player {
    private String nickname;
    private AssistantDeck assistantDeck;
    private ArrayList<Student> myStudentsEntrance;
    private SchoolBoard board;
    private boolean isPlaying;
    private boolean isWinner;
    private Player teammate;

    //TODO: aggiungere monete -> int o List

    public Player(String name) {
        nickname = name;
        assistantDeck = new AssistantDeck();
        myStudentsEntrance = new ArrayList<Student>();
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
        return myStudentsEntrance;
    }

    public boolean isPlaying() {
        return isPlaying;
    };

    public boolean isWinner() {
        return isWinner;
    }

    public AssistantCard pickAssistant() { ... };

    //sceglie un numero compreso tra 1 e card.moves
    public int chooseMoves(AssistantCard card) { ... };

    public void buildTower(Island island) { ... };

    //
    public ArrayList<Student> pickStudentsToDiningRoom() { ... };
    public void putStudentIsland(Student stud) { ... };
    public void takeCoin() { ... };
    public CharacterCard playCharacter(CharacterCard character) { ... };
    public CloudTile pickCloud(ArrayList<CloudTile> clouds) { };

}