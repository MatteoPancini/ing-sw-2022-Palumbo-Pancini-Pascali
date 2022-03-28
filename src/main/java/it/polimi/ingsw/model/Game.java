package it.polimi.ingsw.model;

import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.enumerations.GameMode;
import it.polimi.ingsw.model.player.Player;


import java.util.ArrayList;

public class Game {
    private final GameBoard gameBoard;
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Player> activePlayers = new ArrayList<>();
    private Player currentPlayer;


    public Game(){
        gameBoard = new GameBoard();
    }
}
