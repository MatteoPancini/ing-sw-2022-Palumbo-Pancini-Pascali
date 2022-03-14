package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.player.Player;


import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;
    private CloudTile clouds;
    private Island islands;
    private Student students;
    private Professor professors;
    private MotherNature motherNature;
    private CharacterCard characterCards;

    public Game() {
        //initialize game
    }


    public ArrayList<Player> getPlayers() {

        return players;
    }
}
