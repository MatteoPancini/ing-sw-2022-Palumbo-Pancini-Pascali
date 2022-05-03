package it.polimi.ingsw.model.player;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.enumerations.PawnType;
import it.polimi.ingsw.model.enumerations.TowerColor;
import org.junit.jupiter.api.BeforeAll;
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

    @Test
    void setupStudents(){
        stud1 = new Student(PawnType.YELLOW);
        stud2 = new Student(PawnType.PINK);
        stud3 = new Student(PawnType.YELLOW);
        stud4 = new Student(PawnType.BLUE);
        stud5 = new Student(PawnType.GREEN);
        stud6 = new Student(PawnType.YELLOW);
        stud7 = new Student(PawnType.BLUE);
    }

    @Test
    void setupTowers(){
        ArrayList<Tower> towers = new ArrayList<Tower>();
        towers.add(tower1);
        towers.add(tower2);
        towers.add(tower3);
        towers.add(tower4);
        towers.add(tower5);
        towers.add(tower6);
        towers.add(tower7);
        towers.add(tower8);
        for(Tower tower : towers){
            tower = new Tower(TowerColor.BLACK);
        }
    }

    @Test
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
    void pawnTypeCheck(){
        ArrayList<Student> students = new ArrayList<Student>();
        Entrance entrance = new Entrance();
        students.add(stud1);
        students.add(stud2);
        students.add(stud3);
        students.add(stud4);
        students.add(stud5);
        students.add(stud6);
        students.add(stud7);
        for(Student student : students){
            entrance.setStudents(student);
        }
        schoolBTest.setEntrance(entrance);
        assertEquals(stud1.getType(), schoolBTest.getEntrance().getStudents().get(0).getType());
        assertEquals(stud2.getType(), schoolBTest.getEntrance().getStudents().get(1).getType());
        assertEquals(stud3.getType(), schoolBTest.getEntrance().getStudents().get(2).getType());
        assertEquals(stud4.getType(), schoolBTest.getEntrance().getStudents().get(3).getType());
        assertEquals(stud5.getType(), schoolBTest.getEntrance().getStudents().get(4).getType());
        assertEquals(stud6.getType(), schoolBTest.getEntrance().getStudents().get(5).getType());
        assertEquals(stud7.getType(), schoolBTest.getEntrance().getStudents().get(6).getType());
    }

}