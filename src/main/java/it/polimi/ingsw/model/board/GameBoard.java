package it.polimi.ingsw.model.board;

import java.util.*;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.AssistantDeck;
import it.polimi.ingsw.model.cards.CharacterCardDeck;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;

public class GameBoard extends Observer implements Observable {
    //perché final madre natura? cambia la sua position durante il gioco, idem per bag (dava errore quando
    //si cerca di assegnare un valore nelle righe successive
    private final Game game = null;
    private final ArrayList<CloudTile> clouds = null;
    private ArrayList<Island> islands = null;
    private ArrayList<Professor> professors = null;
    private MotherNature motherNature = null;
    private CharacterCardDeck playableCharacters = null;
    private AssistantDeck lastAssistantUsed;
    private final ArrayList<Student> studentsBag = null;

    public GameBoard (){
        clouds = new ArrayList<CloudTile>();
        for (int i = 1; i <= game.getPlayersNumber(); i++) {
            if (game.getPlayersNumber() == 3) clouds.add(new CloudTile(CloudSide.THREE));
            else clouds.add(new CloudTile(CloudSide.TWO_FOUR));
        }

        islands = new ArrayList<Island>();
        for (int j = 1; j <= 12; j++){
            islands.add(new Island(game.getGameBoard(), j));
        }

        ArrayList<PawnType> pawns = new ArrayList<PawnType>();
        pawns.add(PawnType.BLUE);
        pawns.add(PawnType.GREEN);
        pawns.add(PawnType.RED);
        pawns.add(PawnType.YELLOW);
        professors = new ArrayList<Professor>();
        for (PawnType p : pawns) {
            professors.add(new Professor(p));
        }

        studentsBag = new ArrayList<Student>();
        for (PawnType p : pawns) {
            for (int k = 1; k <= 26; k++) {
                studentsBag.add(new Student(p));
            }
        }

        motherNature = getMotherNature();

        playableCharacters = new CharacterCardDeck();

        lastAssistantUsed = new AssistantDeck();
    }

    public Game getGame() {
        return game;
    }

    public CharacterCardDeck getPlayableCharacters() {
        return playableCharacters;
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
        lastAssistantUsed.getDeck().set(index, card);
    }
}