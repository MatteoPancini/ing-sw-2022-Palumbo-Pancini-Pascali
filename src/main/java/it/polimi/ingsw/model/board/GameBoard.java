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
    private final LinkedList<Island> islands;
    private final ArrayList<Professor> professors;
    private final MotherNature motherNature;
    private final CharacterCardDeck playableCharacters;
    private final ArrayList<AssistantCard> lastAssistantUsed;
    private final ArrayList<Student> studentsBag;

    public GameBoard {
        clouds = new ArrayList<CloudTile>;
        for (int k = 0; k < game.getPlayersNumber(); k++) {
            if (game.getPlayersNumber() == 3) clouds.add(new CloudTile(CloudSide.THREE));
            else clouds.add(new CloudTile(CloudSide.TWO_FOUR));
        }

        islands = new LinkedList<Island>;
        for (int m = 1; m <= 12; m++){
            islands.add(new Island(m));
        }

        ArrayList<PawnType> pawns = new ArrayList<PawnType>;
        pawns.add(PawnType.BLUE, PawnType.GREEN, PawnType.PINK, PawnType.RED, PawnType.YELLOW);
        professors = new ArrayList<Professor>;
        for (PawnType p : pawns) {
            professors.add(new Professor(p));
        }

        studentsBag = new ArrayList<Student>;
        for (int i = 1; i <= 5; i++) {
            for (PawnType p : pawns) {
                for (int j = 1; j <= 26; j++) {
                    studentsBag.add(new Student(p));
                }
            }
        }

        motherNature = new MotherNature;

        playableCharacters = new CharacterCardDeck;

        lastAssistantUsed = new ArrayList<>;
    }

    public ArrayList<Student> getStudentsBag() {
        return studentsBag;
    }

    public ArrayList<CloudTile> getClouds() {
        return clouds;
    }

    public ArrayList<Professor> getProfessors() {
        return professors;
    }

    public LinkedList<Island> getIslands() {
        return islands;
    }

    public MotherNature getMotherNature() {
        return motherNature;
    }
}
