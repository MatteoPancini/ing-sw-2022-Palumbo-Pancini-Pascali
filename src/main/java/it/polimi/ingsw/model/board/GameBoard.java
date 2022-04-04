package it.polimi.ingsw.model.board;

import java.lang.reflect.Array;
import java.util.*;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.Game;

public class GameBoard {
    private final Game game;
    private final ArrayList<CloudTile> clouds;
    private final ArrayList<Island> islands;
    private final ArrayList<Professor> professors;
    private final MotherNature motherNature;
    private final CharacterCardDeck playableCharacters;
    private ArrayList<AssistantCard> lastAssistantUsed;
    private final ArrayList<Student> studentsBag;

    public GameBoard (){
        clouds = new ArrayList<CloudTile>;
        for (int i = 1; i <= game.getPlayersNumber(); i++) {
            if (game.getPlayersNumber() == 3) clouds.add(new CloudTile(CloudSide.THREE));
            else clouds.add(new CloudTile(CloudSide.TWO_FOUR));
        }

        islands = new ArrayList<Island>;
        for (int j = 1; j <= 12; j++){
            islands.add(new Island(j));
        }

        ArrayList<PawnType> pawns = new ArrayList<PawnType>;
        pawns.add(PawnType.BLUE, PawnType.GREEN, PawnType.PINK, PawnType.RED, PawnType.YELLOW);
        professors = new ArrayList<Professor>;
        for (PawnType p : pawns) {
            professors.add(new Professor(p));
        }

        studentsBag = new ArrayList<Student>;
        for (PawnType p : pawns) {
            for (int k = 1; k <= 26; k++) {
                studentsBag.add(new Student(p));
            }
        }

        motherNature = getMotherNature();

        playableCharacters = new CharacterCardDeck;

        lastAssistantUsed = new ArrayList<AssistantCard>;
    }

    public ArrayList<Student> getStudentsBag() {
        return studentsBag;
    }

    public void removeStudents(int index){ studentsBag.remove(index); }

    public ArrayList<CloudTile> getClouds() {
        return clouds;
    }

    public ArrayList<Professor> getProfessors() {
        return professors;
    }

    public ArrayList<Island> getIslands() {
        return islands;
    }

    public MotherNature getMotherNature() { return motherNature; }

    public ArrayList<AssistantCard> getLastAssistantUsed(){ return lastAssistantUsed; }

    public void setLastAssistantUsed(int index, AssistantCard card){
        lastAssistantUsed.get(index) = card;
    }

}