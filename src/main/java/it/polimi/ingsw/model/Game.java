package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.player.Player;


import java.util.ArrayList;

public class Game {
    private final GameBoard gameBoard;
    private final int playersNumber;
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Player> activePlayers = new ArrayList<>();
    private Player currentPlayer;


    public Game(int playersNumber){
        gameBoard = new GameBoard();
        this.playersNumber = playersNumber;
    }

    public int getPlayersNumber(){
        return playersNumber;
    }
}





