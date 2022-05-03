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
    private static Island testIsland1;
    private static Island testIsland2;
    private GameBoard board;
    private Game game;
    private ArrayList<Player> players;

    @Test
    void setupPlayers(){
        Player player1 = new Player("Matteo", 1);
        Player player2 = new Player("Francesco", 2);
        Player player3 = new Player("Luigi", 3);
        players = new ArrayList<Player>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
    }

    @Test
    @BeforeEach
    void setupGame(){
        game = new Game(3, false, players, players);
    }

/*    @Test
    @DisplayName("Verify if islands are merged")
    void islandTest(){
        board = new GameBoard(game);
        ArrayList<Island> islands = new ArrayList<Island>();
        islands.add(board.getIslands().get(0));
        islands.add(board.getIslands().get(1));
        board.getIslands().get(0).setTower(new Tower(TowerColor.BLACK));
        board.getIslands().get(1).setTower(new Tower(TowerColor.BLACK));
        assertEquals(islands, board.getIslands().get(1).getMergedIslands());
    }*/
}
