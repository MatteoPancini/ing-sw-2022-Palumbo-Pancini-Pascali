package it.polimi.ingsw.model.board;

import java.io.Serializable;
import java.util.*;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.Game;

public class GameBoard implements Serializable {
    private Game game;
    private ArrayList<CloudTile> clouds = new ArrayList<>();
    private ArrayList<Island> islands;
    private ArrayList<Professor> professors;
    private MotherNature motherNature;
    private ArrayList<CharacterCard> playableCharacters = new ArrayList<>();
    private CharacterDeck characterDeck = null;
    private ArrayList<AssistantCard> lastAssistantUsed = new ArrayList<>();
    private ArrayList<Student> studentsBag;
    private ArrayList<Student>  setupStudentsBag;
    private int islandCounter;

    public GameBoard (Game game) {
        this.game = game;

        for (int i = 1; i <= game.getPlayersNumber(); i++) {
            if (game.getPlayersNumber() == 3) {
                this.clouds.add(new CloudTile(CloudSide.THREE));
            }
            else {
                this.clouds.add(new CloudTile(CloudSide.TWO_FOUR));
            }
            clouds.get(i -1).setID(i);
        }


        islands = new ArrayList<>();
        for (int j = 1; j <= 12; j++){
            islands.add(new Island(this, j));
        }

        ArrayList<PawnType> pawns = new ArrayList<>();
        pawns.add(PawnType.BLUE);
        pawns.add(PawnType.GREEN);
        pawns.add(PawnType.RED);
        pawns.add(PawnType.YELLOW);
        pawns.add(PawnType.PINK);
        professors = new ArrayList<>();
        for (PawnType p : pawns) {
            professors.add(new Professor(p));
        }

        studentsBag = new ArrayList<>();
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

        islandCounter = 12;

    }

    public Game getGame() {
        return game;
    }

    public ArrayList<CharacterCard> getPlayableCharacters() {
        return playableCharacters;
    }

    public ArrayList<Student> getStudentsBag() {
        return studentsBag;
    }

    /**
     * Remove student from the students bag
     * @param index -> index of the student to remove
     */
    public void removeStudents(int index) {
        studentsBag.remove(index);
    }


    /**
     * Select three character cards that can be played in a game
     */
    public void setPlayableCharacters() {
        characterDeck = new CharacterDeck(game);

        Collections.shuffle(characterDeck.getDeck());

        for(int i = 0; i < 3; i++) {
            playableCharacters.add(characterDeck.getDeck().get(i));
        }

        for(CharacterCard c : playableCharacters) {
            c.setResetCost(c.getInitialCost());
        }
    }

    /**
     * Remove students from the set-up bag
     * @param index -> index of the student to remove
     */
    public void removeSetupStudents(int index) {
        setupStudentsBag.remove(index);
    }

    public ArrayList<CloudTile> getClouds() {
        return clouds;
    }

    public ArrayList<Professor> getProfessors() {
        return professors;
    }

    /**
     * Get a professor
     * @param pawnType -> pawn type of the professor
     * @return a professor
     */
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

    /**
     * Get an island
     * @param islandId -> id of the island
     * @return an island
     */
    public Island getIslandById(int islandId) {
        for(Island i : islands) {
            if(i.getIslandID() == islandId) {
                return i;
            }
        }
        return null;
    }

    public int getIslandCounter(){ return islandCounter; }

    public void decrementIslandCounter(){ islandCounter--; }
}