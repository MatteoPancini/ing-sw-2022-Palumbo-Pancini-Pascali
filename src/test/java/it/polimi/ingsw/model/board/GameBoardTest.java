package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.cards.AssistantDeck;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.AssistantDeck;
import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.enumerations.CloudSide;
import it.polimi.ingsw.model.enumerations.PawnType;
import it.polimi.ingsw.model.enumerations.Wizards;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class GameBoardTest {
    private GameBoard gameBTest;
    private static Game game;
    private static ArrayList<Player> players;
    private static ArrayList<Student> stud;
    private static Student stud1;
    private static Student stud2;
    private static List<AssistantCard> deckTest;
    private static AssistantCard assistant1;
    private static AssistantCard assistant2;
    private static AssistantCard assistant3;
    private static ArrayList<CloudTile> cloudsTest;
    private static CloudTile cloud1;
    private static CloudTile cloud2;
    private static CloudTile cloud3;

    @Test
    @BeforeEach
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
    void populateArray() {
        game = new Game(3, false, players, players);
        gameBTest = new GameBoard(game);
        stud1 = new Student(PawnType.PINK);
        stud2 = new Student(PawnType.BLUE);
        gameBTest.getStudentsBag().add(stud1);
        gameBTest.getStudentsBag().add(stud2);
    }

    @Test
    @BeforeEach
    void setupClouds() {
        cloud1 = new CloudTile(CloudSide.THREE);
        cloud2 = new CloudTile(CloudSide.THREE);
        cloud3 = new CloudTile(CloudSide.THREE);
        cloudsTest = new ArrayList<CloudTile>();
        cloudsTest.add(cloud1);
        cloudsTest.add(cloud2);
        cloudsTest.add(cloud3);
    }

    @Test
    void setupAssistants() {
    }

    @Test
    @DisplayName("Array of students test")
    //can be used for professors as well
    void studColorTest() {
        assertEquals(stud1.getType(), gameBTest.getStudentsBag().get(130).getType());
        assertEquals(stud2.getType(), gameBTest.getStudentsBag().get(131).getType());
    }

    @Test
    @DisplayName("Deck value check")
    void deckValueTest(){
        assistant1 = new AssistantCard(Assistants.CAT, 3, 2);
        assistant2 = new AssistantCard(Assistants.ELEPHANT, 9, 5);
        assistant3 = new AssistantCard(Assistants.CHEETAH, 1, 1);
        deckTest = new ArrayList<AssistantCard>();
        deckTest = AssistantDeck.parseAssistantCards(Wizards.KING);
        assertEquals(assistant1.getValue(), deckTest.get(3).getValue());
        assertEquals(assistant2.getValue(), deckTest.get(2).getValue());
        assertEquals(assistant3.getValue(), deckTest.get(4).getValue());
    }

    @Test
    @DisplayName("Deck moves check")
    void deckMovesTest() {
        assistant1 = new AssistantCard(Assistants.CAT, 3, 2);
        assistant2 = new AssistantCard(Assistants.ELEPHANT, 9, 5);
        assistant3 = new AssistantCard(Assistants.CHEETAH, 1, 1);
        deckTest = new ArrayList<AssistantCard>();
        deckTest = AssistantDeck.parseAssistantCards(Wizards.KING);
        assertEquals(assistant1.getMoves(), deckTest.get(3).getMoves());
        assertEquals(assistant2.getMoves(), deckTest.get(2).getMoves());
        assertEquals(assistant3.getMoves(), deckTest.get(4).getMoves());
    }

    @Test
    @DisplayName("Cloud side check, 3 or 2/4 players")
    void cloudSideTest() {
        assertEquals(cloud1.getSide(), cloudsTest.get(0).getSide());
    }

}