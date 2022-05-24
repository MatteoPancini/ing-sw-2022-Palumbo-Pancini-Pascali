package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.player.Player;


import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {
    private final GameBoard gameBoard;
    private int playersNumber;
    private boolean expertMode;
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Player> activePlayers = new ArrayList<>();
    private Player currentPlayer;
    private int currentPlayerNumber;

    public Game() {
        this.gameBoard = new GameBoard(this);
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    public int getPlayersNumber() { return playersNumber; }

    public void setExpertMode(boolean expertMode) {
        this.expertMode = expertMode;
    }

    public boolean isExpertMode() {
        return expertMode;
    }

    public void createNewPlayer (Player newPlayer) {
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

    public void setCurrentPlayer(Player newPlayer) {
        currentPlayer = newPlayer;
        currentPlayerNumber = activePlayers.indexOf(newPlayer);
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

    public void switchToNextPlayer() {
        if(currentPlayerNumber == activePlayers.size() - 1) currentPlayerNumber = 0;
        if(currentPlayerNumber < activePlayers.size() - 1) currentPlayerNumber++;
        setCurrentPlayer(activePlayers.get(currentPlayerNumber));
    }

}