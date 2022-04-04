package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameBoardTest {
    GameBoard testGameBoard;
    private static final Game game;

    @Test
    @BeforeEach
    void setupGameBoard() { testGameBoard = new GameBoard(game); }

    @Test
    @DisplayName("Verify students bag size")
    void studentsBagTest(){
        assertEquals(130, testGameBoard.getStudentsBag().size());
    }


}