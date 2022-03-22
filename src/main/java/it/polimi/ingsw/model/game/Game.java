package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.player.Player;


import java.lang.reflect.Array;
import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;
    private ArrayList<CloudTile> clouds;
    private ArrayList<Island> islands;
    private ArrayList<Student> students;
    private ArrayList<Professor> professors;
    private MotherNature motherNature;
    private ArrayList<CharacterCard> characterCards;
    private GameMode gameMode;
    private boolean expertMode;

    public Game() {
        //initialize game
    }


    public ArrayList<Player> getPlayers() {

        return players;
    }

    public ArrayList<Island> getIslands() {
        return islands;
    }

    public boolean isExpertMode() {
        return expertMode;
    }

    public ArrayList<CharacterCard> getCharacterCards() {
        return characterCards;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public MotherNature getMotherNature() {
        return motherNature;
    }

    public ArrayList<CloudTile> getClouds() {
        return clouds;
    }

    public ArrayList<Professor> getProfessors() {
        return professors;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }
}
