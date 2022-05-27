package it.polimi.ingsw.model.board;

import java.io.Serializable;
import java.util.*;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.Game;

public class GameBoard implements Serializable {
    private Game game;
    private ArrayList<CloudTile> clouds = new ArrayList<CloudTile>();;
    private ArrayList<Island> islands;
    private ArrayList<Professor> professors;
    private MotherNature motherNature;
    private CharacterDeck playableCharacters = null;
    private ArrayList<AssistantCard> lastAssistantUsed = new ArrayList<>();
    private ArrayList<Student> studentsBag;
    private ArrayList<Student>  setupStudentsBag;

    public GameBoard (Game game) {
        this.game = game;
        System.err.println("Inizializzo GAMEBOARD");


        System.out.println("Numero di giocatori : " + game.getPlayersNumber());
        for (int i = 1; i <= game.getPlayersNumber(); i++) {
            if (game.getPlayersNumber() == 3) {
                this.clouds.add(new CloudTile(CloudSide.THREE));
            }
            else {
                this.clouds.add(new CloudTile(CloudSide.TWO_FOUR));
            }
            clouds.get(i -1).setID(i);
            System.out.println(clouds.get(i - 1).getSide().toString());
        }



        islands = new ArrayList<Island>();
        for (int j = 1; j <= 12; j++){
            islands.add(new Island(this, j));
        }

        ArrayList<PawnType> pawns = new ArrayList<PawnType>();
        pawns.add(PawnType.BLUE);
        pawns.add(PawnType.GREEN);
        pawns.add(PawnType.RED);
        pawns.add(PawnType.YELLOW);
        pawns.add(PawnType.PINK);
        professors = new ArrayList<Professor>();
        for (PawnType p : pawns) {
            professors.add(new Professor(p));
        }

        studentsBag = new ArrayList<Student>();
        setupStudentsBag = new ArrayList<>();
        for (PawnType p : pawns) {
            for(int k = 1; k <= 24; k++) {
                studentsBag.add(new Student(p));
            }
            for(int l = 1; l <=2; l++) {
                setupStudentsBag.add(new Student(p));
            }
        }

        motherNature = MotherNature.getMotherNature();

        /*
        if(game.isExpertMode() == true) {
            playableCharacters = CharacterDeck.getPlayableCards(game);
        }
        else playableCharacters = null;


         */
        //lastAssistantUsed = new ArrayList<AssistantCard>();
    }

    public Game getGame() {
        return game;
    }

    public CharacterDeck getPlayableCharacters() {
        return playableCharacters;
    }

    public ArrayList<Student> getStudentsBag() {
        return studentsBag;
    }

    public void removeStudents(int index) {
        studentsBag.remove(index);
    }

    public void setPlayableCharacters() {
        playableCharacters = CharacterDeck.getPlayableCards(game);
    }

    public void removeSetupStudents(int index) {
        setupStudentsBag.remove(index);
    }



    public ArrayList<CloudTile> getClouds() {
        return clouds;
    }


    public ArrayList<Professor> getProfessors() {
        return professors;
    }

    public Professor getProfessorByColor(PawnType pawnType) {
        for(Professor professor : professors) {
            if(professor.getType() == pawnType) {
                return professor;
            }
        }
        return null;
    }

    public ArrayList<Student> getSetupStudentsBag() {
        return setupStudentsBag;
    }

    public ArrayList<Island> getIslands() {
        return islands;
    }

    public MotherNature getMotherNature() { return motherNature; }

    public ArrayList<AssistantCard> getLastAssistantUsed(){ return lastAssistantUsed; }

    public void setLastAssistantUsed(int index, AssistantCard card){
        lastAssistantUsed.set(index, card);
    }

    public Island getIslandById(int islandId) {
        for(Island i : islands) {
            if(i.getIslandID() == islandId) {
                return i;
            }
        }
        return null;
    }
}