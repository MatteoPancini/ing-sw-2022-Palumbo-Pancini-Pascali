package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.SchoolBoard;

import java.util.Collections;

import java.util.ArrayList;

public class Game {
    private final GameBoard gameBoard;
    private int playersNumber;
    //private final boolean expertMode;
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Player> activePlayers = new ArrayList<>();
    private Player currentPlayer;



    public Game() {
        this.gameBoard = new GameBoard();
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    /*
    public Game(int playersNumber, boolean expertMode, ArrayList<Player> players, ArrayList<Player> activePlayers) {
        gameBoard = new GameBoard(this);
        this.playersNumber = playersNumber;
        this.expertMode = expertMode;
        this.players = players;
        this.activePlayers = activePlayers;
        currentPlayer = null;
    }

     */


    public void addPlayer(Player newPlayer) {
        players.add(newPlayer);
        activePlayers.add(newPlayer);
    }
    public void createNewPlayer (String nickname, int playerID) {
        Player newPlayer = new Player(nickname, playerID);
        players.add(newPlayer);
        activePlayers.add(newPlayer);
    }

    public ArrayList<Player> getActivePlayers() {
        return activePlayers;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void removePlayer(Player player) {
        activePlayers.remove(player);
        /*
        if (player.getWorkers().isEmpty()) {
            activePlayers.remove(player);
            if (!activePlayers.isEmpty()) {
                if (currentPlayerN == activePlayers.size()) currentPlayerN = 0;
                setCurrentPlayer(activePlayers.get(currentPlayerN));
            }
            return;
        }
        for (int i = Constants.GRID_MIN_SIZE; i < Constants.GRID_MAX_SIZE; i++) {
            for (int j = Constants.GRID_MIN_SIZE; j < Constants.GRID_MAX_SIZE; j++) {
                if (gameBoard.getSpace(i, j).getWorker() == player.getWorkers().get(0)
                        || gameBoard.getSpace(i, j).getWorker() == player.getWorkers().get(1)) {
                    gameBoard.getSpace(i, j).setWorker(null);
                }
            }
        }
        activePlayers.remove(player);
        if (!activePlayers.isEmpty()) {
            if (currentPlayerN == activePlayers.size()) currentPlayerN = 0;
            setCurrentPlayer(activePlayers.get(currentPlayerN));
        }

         */
    }

    public Player getPlayerByID(int idPlayer) {
        for (Player player : activePlayers) {
            if (player.getPlayerID() == idPlayer) {
                return player;
            }
        }
        return null;
    }

    public Player getPlayerByNickname(String playerNickname) {
        for (Player player : activePlayers) {
            if (player.getNickname() == playerNickname) {
                return player;
            }
        }
        return null;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }



    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    /*

    public int getPlayersNumber() {
        return playersNumber;
    }

    public boolean isExpertMode() {
        return expertMode;
    }

     */

    public void setCurrentPlayer(Player newPlayer) {
        currentPlayer = newPlayer;
    }


    public boolean canPlayAssistant(Assistants ass) {
        for (AssistantCard card : gameBoard.getLastAssistantUsed()) {
            if (card.getName() == ass) {
                for (AssistantCard myCard : currentPlayer.getAssistantDeck().getDeck()) {
                    if (!myCard.equals(card)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
