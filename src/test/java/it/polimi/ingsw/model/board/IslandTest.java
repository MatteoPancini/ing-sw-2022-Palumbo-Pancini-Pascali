package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Tower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IslandTest {
    Island testIsland;
    private GameBoard board;
    private Game game;
    private ArrayList<Player> players;

    @Test
    @BeforeEach
    void setupPlayers(){
        Player player1 = new Player("Matteo", 1);
        Player player2 = new Player("Francesco", 2);
        Player player3 = new Player("Luigi", 3);
        players = new ArrayList<Player>();
        //players.add(player1, player2, player3);
    }

    @Test
    @BeforeEach
    void setupGame(){
        game = new Game(3, false, players, players);
    }

    @Test
    @BeforeEach
    void setupGameBoard(){
        board = new GameBoard(game);
    }

    @Test
    @DisplayName("Verify if islands are merged")
    void islandTest(){
        ArrayList<Island> islands = new ArrayList<Island>();
        islands.add(board.getIslands().get(2));
        islands.add(board.getIslands().get(3));
        board.getIslands().get(2).setTower(new Tower(TowerColor.BLACK));
        board.getIslands().get(3).setTower(new Tower(TowerColor.BLACK));
        assertEquals(islands, board.getIslands().get(2).getMergedIslands());
    }
}
