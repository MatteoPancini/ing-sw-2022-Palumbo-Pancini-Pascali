package it.polimi.ingsw.model.player;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.enumerations.PawnType;
import it.polimi.ingsw.model.enumerations.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class SchoolBoardTest {
    private SchoolBoard schoolBTest;
    private static GameBoard gameBoard;
    private static DiningRoom diningRoom;
    private static Entrance entrance;
    private static Student stud1;
    private static Student stud2;
    private static Student stud3;
    private static Student stud4;
    private static Student stud5;
    private static Student stud6;
    private static Student stud7;
    private static Tower tower1;
    private static Tower tower2;
    private static Tower tower3;
    private static Tower tower4;
    private static Tower tower5;
    private static Tower tower6;
    private static Tower tower7;
    private static Tower tower8;
    private static ProfessorTable professorTable;
    private static TowerArea towerArea;

    @Test
    @BeforeEach
    void setupSchoolBoard(){
        schoolBTest = new SchoolBoard(1);
    }

    void setupStudents(){
        stud1 = new Student(PawnType.YELLOW);
        stud2 = new Student(PawnType.PINK);
        stud3 = new Student(PawnType.YELLOW);
        stud4 = new Student(PawnType.BLUE);
        stud5 = new Student(PawnType.GREEN);
        stud6 = new Student(PawnType.YELLOW);
        stud7 = new Student(PawnType.BLUE);
    }

    void populateEntrance(){
        ArrayList<Student> students = new ArrayList<Student>();
        students.add(stud1, stud2, stud3, stud4, stud5, stud6, stud7);
        schoolBTest.getEntrance().setStudents(students);
    }

    void setupTowers(){
        ArrayList<Tower> towers = new ArrayList<Tower>();
        towers.add(tower1, tower2, tower3, tower4, tower5, tower6, tower7, tower8);
        for(Tower tower : towers){
            tower = new Tower(TowerColor.BLACK);
        }
    }

    void populateTowerArea(){
        schoolBTest.getTowerArea().addTowers(tower1);
        schoolBTest.getTowerArea().addTowers(tower2);
        schoolBTest.getTowerArea().addTowers(tower3);
        schoolBTest.getTowerArea().addTowers(tower4);
        schoolBTest.getTowerArea().addTowers(tower5);
        schoolBTest.getTowerArea().addTowers(tower6);
        schoolBTest.getTowerArea().addTowers(tower7);
        schoolBTest.getTowerArea().addTowers(tower8);
    }

    @Test
    @DisplayName("Pawn type check")
    void PawnTypeCheck(){
        assertEquals(stud1.getType(), schoolBTest.getEntrance().getStudents().get(0).getType());
        assertEquals(stud2.getType(), schoolBTest.getEntrance().getStudents().get(1).getType());
        assertEquals(stud3.getType(), schoolBTest.getEntrance().getStudents().get(2).getType());
        assertEquals(stud4.getType(), schoolBTest.getEntrance().getStudents().get(3).getType());
        assertEquals(stud5.getType(), schoolBTest.getEntrance().getStudents().get(4).getType());
        assertEquals(stud6.getType(), schoolBTest.getEntrance().getStudents().get(5).getType());
        assertEquals(stud7.getType(), schoolBTest.getEntrance().getStudents().get(6).getType());
    }

}



