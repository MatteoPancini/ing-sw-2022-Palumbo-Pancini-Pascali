package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.enumerations.CloudSide;
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
    private static AssistantDeck deckTest;
    private static AssistantCard assistant1;
    private static AssistantCard assistant2;
    private static AssistantCard assistant3;
    private static ArrayList<CloudTile> cloudsTest;
    private static CloudTile cloud_3P;
    private static CloudTile cloud_2P; //can be used for 4 players as well

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
    @BeforeEach
    void setupClouds() {
        cloud_3P = new CloudTile(CloudSide.THREE);
        cloud_2P = new CloudTile(CloudSide.TWO_FOUR);
    }

    @Test
    @BeforeEach
    void populateDeck() {
        deckTest.getDeck().add(assistant1);
        deckTest.getDeck().add(assistant2);
        deckTest.getDeck().add(assistant3);
    }

    @Test
    @BeforeEach
    void setupAssistants() {
        assistant1 = new AssistantCard(3,4);
        assistant2 = new AssistantCard(10, 2);
        assistant3 = new AssistantCard(4, 5);
    }

    @Test
    @BeforeEach
    void populateClouds() {
        cloudsTest.add(cloud_2P);
        cloudsTest.add(cloud_3P);
    }

    @Test
    @DisplayName("Array of students test")
    //can be used for professors as well
    void studColorTest() {
        assertEquals(stud1.getType(), gameBTest.getStudentsBag().get(0).getType());
        assertEquals(stud2.getType(), gameBTest.getStudentsBag().get(1).getType());
    }

    @Test
    @DisplayName("Deck value check")
    void deckValueTest() {
        assertEquals(assistant1.getValue(), deckTest.getDeck().get(0).getValue());
        assertEquals(assistant2.getValue(), deckTest.getDeck().get(1).getValue());
        assertEquals(assistant2.getValue(), deckTest.getDeck().get(2).getValue());
    }

    @Test
    @DisplayName("Deck moves check")
    void deckMovesTest() {
        assertEquals(assistant1.getMoves(), deckTest.getDeck().get(0).getMoves());
        assertEquals(assistant2.getMoves(), deckTest.getDeck().get(1).getMoves());
        assertEquals(assistant2.getMoves(), deckTest.getDeck().get(2).getMoves());
    }

    @Test
    @DisplayName("Cloud side check, 3 or 2/4 players")
    void cloudSideTest() {
        assertEquals(cloud_2P.getSide(), cloudsTest.get(0).getSide());
        assertEquals(cloud_2P.getSide(), cloudsTest.get(1).getSide());
    }




}
