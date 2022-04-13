package it.polimi.ingsw.model.board;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentsBagTest {
    private GameBoard testGameBoard;
    private static ArrayList<Player> players;
    private final Game game;

    @Test
    @BeforeEach
    void setupPlayers(){
        Player player1 = new Player("Matteo", 1);
        Player player2 = new Player("Francesco", 2);
        Player player3 = new Player("Luigi", 3);
        players.add(player1, player2, player3);
    }

    @Test
    @BeforeEach
    void setupGame(){
        game = new Game(3, false, players, players)
    }

    @Test
    @BeforeEach
    void setupGameBoard() { testGameBoard = new GameBoard(game); }

    @Test
    @DisplayName("Verify students bag size")
    void studentsBagTest(){
        assertEquals(130, testGameBoard.getStudentsBag().size());
    }
}