package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.enumerations.PawnType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class GameBoardTest {
    private GameBoard gameBTest;
    private static ArrayList<Student> stud;
    private static Student stud1;
    private static Student stud2;


    @Test
    @BeforeEach
    void setupStudent() {
        stud1 = new Student(PawnType.PINK);
        stud2 = new Student(PawnType.BLUE);
    }

    @Test
    @BeforeEach
    void populateArray() {
        gameBTest.getStudentsBag().add(stud1);
        gameBTest.getStudentsBag().add(stud2);
    }

    @Test
    @DisplayName("Array of students test")
    void studColorTest() {
        assertEquals(stud1.getType(), gameBTest.getStudentsBag().get(0).getType());
        assertEquals(stud2.getType(), gameBTest.getStudentsBag().get(1).getType());
    }

    @Test

}
