package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.player.Player;
import java.util.Collections;

import java.util.ArrayList;

public class Game {
    private final GameBoard gameBoard;
    private final int playersNumber;
    private final boolean expertMode;
    private final ArrayList<Player> players;
    private final ArrayList<Player> activePlayers;
    private Player currentPlayer;


    public Game(int playersNumber, boolean expertMode, ArrayList<Player> players, ArrayList<Player> activePlayers){
        gameBoard = new GameBoard(this);
        this.playersNumber = playersNumber;
        this.expertMode = expertMode;
        this.players = players;
        this.activePlayers = activePlayers;
        currentPlayer = null;
    }


    public void addPlayer(Player newPlayer) {
        players.add(newPlayer);
        activePlayers.add(newPlayer);
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public ArrayList<Player> getPlayers() { return players; }

    public ArrayList<Player> getActivePlayers() { return activePlayers; }

    public Player getCurrentPlayer() { return currentPlayer; }

    public int getPlayersNumber(){
        return playersNumber;
    }

    public boolean isExpertMode() { return expertMode; }

    public void setCurrentPlayer(Player newPlayer) { currentPlayer = newPlayer; }

    public void createNewPlayer (String nickname, int playerID){
        players.add(new Player(nickname, playerID));
    }
}
