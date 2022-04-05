package it.polimi.ingsw.model.board;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentsBagTest {
    GameBoard testGameBoard;
    ArrayList<Player> players = new ArrayList<Player>;
    private final Game game = new Game(3, false, players, players);


    @Test
    @BeforeEach
    void setupGameBoard() { testGameBoard = new GameBoard(game); }

    @Test
    @DisplayName("Verify students bag size")
    void studentsBagTest(){
        assertEquals(130, testGameBoard.getStudentsBag().size());
    }

}