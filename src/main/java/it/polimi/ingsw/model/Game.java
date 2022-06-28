package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.player.Player;


import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {
    private GameBoard gameBoard;
    private int playersNumber;
    private boolean expertMode;
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Player> activePlayers = new ArrayList<>();
    private Player currentPlayer;
    private int currentPlayerNumber;


    public void setCurrentPlayerNumber(int currentPlayerNumber) {
        this.currentPlayerNumber = currentPlayerNumber;
    }


    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
        this.gameBoard = new GameBoard(this);
        currentPlayerNumber = 0;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public void setExpertMode(boolean expertMode) {
        this.expertMode = expertMode;
        if(expertMode) {
            gameBoard.setPlayableCharacters();
        }
    }

    public boolean isExpertMode() {
        return expertMode;
    }

    public void addPlayer(Player newPlayer) {
        players.add(newPlayer);
        activePlayers.add(newPlayer);
    }
    public void createNewPlayer(Player newPlayer) {
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

    public GameBoard getGameBoard() {
        return gameBoard;
    }



    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player newPlayer) {
        currentPlayer = newPlayer;
    }


    public boolean canPlayAssistant(Assistants ass) {
        for (AssistantCard card : gameBoard.getLastAssistantUsed()) {
            if (card.getName() == ass) {
                /*
                for (AssistantCard myCard : currentPlayer.getAssistantDeck().getDeck()) {

                    if (!myCard.equals(card)) {
                        return false;
                    }



                }
                 */
                if(currentPlayer.getAssistantDeck().getDeck().size() != gameBoard.getLastAssistantUsed().size()) {
                    return false;
                } else {
                    int trovate = 0;
                    for(AssistantCard myCard : currentPlayer.getAssistantDeck().getDeck()) {
                        for(AssistantCard cardDeck : gameBoard.getLastAssistantUsed()) {
                            if(cardDeck.getName() == myCard.getName()) {
                                trovate++;
                                break;
                            }
                        }
                    }
                    if(trovate == currentPlayer.getAssistantDeck().getDeck().size()) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void switchToNextPlayer() {
        currentPlayerNumber = (currentPlayerNumber == activePlayers.size() - 1) ? 0 : currentPlayerNumber + 1;
        setCurrentPlayer(activePlayers.get(currentPlayerNumber));
    }

}