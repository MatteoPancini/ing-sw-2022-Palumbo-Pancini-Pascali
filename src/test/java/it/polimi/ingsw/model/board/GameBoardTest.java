package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.cards.AssistantDeck;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.AssistantDeck;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.SchoolBoard;
import it.polimi.ingsw.model.player.Tower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
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

        player1.setBoard(new SchoolBoard(1));
        player2.setBoard(new SchoolBoard(2));
        player3.setBoard(new SchoolBoard(3));

    }

    @Test
    @BeforeEach
    void createGame() {
        game = new Game();
        gameBTest = new GameBoard(game);
    }

    @Test
    @DisplayName("Clouds setup")
    @BeforeEach
    void setupClouds() {
        cloud1 = new CloudTile(CloudSide.THREE);
        cloud2 = new CloudTile(CloudSide.THREE);
        cloud3 = new CloudTile(CloudSide.THREE);
        cloud1.setID(1);
        cloud2.setID(2);
        cloud3.setID(3);
        cloudsTest = new ArrayList<CloudTile>();
        cloudsTest.add(cloud1);
        cloudsTest.add(cloud2);
        cloudsTest.add(cloud3);
    }

    @Test
    @DisplayName("Array of students test")
    void studColorTest(){
        stud1 = new Student(PawnType.PINK);
        stud2 = new Student(PawnType.BLUE);
        assertEquals(stud1.getType(), gameBTest.getStudentsBag().get(119).getType());
        assertEquals(stud2.getType(), gameBTest.getStudentsBag().get(0).getType());
    }
    @Test
    @DisplayName("Mother Nature test")
    void motherNatureTest() {
        gameBTest.getMotherNature().setPosition(5);
        assertEquals(5, gameBTest.getMotherNature().getPosition());
    }

    @Test
    @DisplayName("Array of professors test")
    void profColorTest() {
        Professor prof1 = new Professor(PawnType.BLUE);
        Professor prof2 = new Professor(PawnType.PINK);
        Professor prof3 = new Professor(PawnType.GREEN);
        Professor prof4 = new Professor(PawnType.RED);
        Professor prof5 = new Professor(PawnType.YELLOW);

        prof1.setOwner(players.get(0));
        prof2.setOwner(players.get(0));
        prof3.setOwner(players.get(2));
        prof4.setOwner(players.get(2));
        prof5.setOwner(players.get(1));

        assertEquals(prof1.getType(), gameBTest.getProfessors().get(0).getType());
        assertEquals(prof2.getType(), gameBTest.getProfessors().get(4).getType());
        assertEquals(prof3.getType(), gameBTest.getProfessors().get(1).getType());
        assertEquals(prof4.getType(), gameBTest.getProfessors().get(2).getType());
        assertEquals(prof5.getType(), gameBTest.getProfessors().get(3).getType());

        assertEquals(players.get(0), prof1.getOwner());
        assertEquals(players.get(0), prof2.getOwner());
        assertEquals(players.get(2), prof3.getOwner());
        assertEquals(players.get(2), prof4.getOwner());
        assertEquals(players.get(1), prof5.getOwner());
    }

    @Test
    @DisplayName("Cloud side check, 3 or 2/4 players")
    void cloudSideTest() {
        Student stud1 = new Student(PawnType.BLUE);
        Student stud2 = new Student(PawnType.GREEN);
        ArrayList<Student> students = new ArrayList<Student>();

        students.add(stud1);
        students.add(stud2);
        cloudsTest.get(0).setStudents(students);

        assertEquals(cloud1.getSide(), cloudsTest.get(0).getSide());
        assertEquals(1, cloudsTest.get(0).getID());
        assertEquals(stud1, cloudsTest.get(0).getStudents().get(0));
    }

    @Test
    @DisplayName("Islands test")
    void islandsTest(){
        Tower tower1 = new Tower(TowerColor.BLACK);
        Tower tower2 = new Tower(TowerColor.BLACK);
        Tower tower3 = new Tower(TowerColor.BLACK);

        gameBTest.getIslands().get(2).setTower(tower1);
        gameBTest.getIslands().get(3).setTower(tower2);
        gameBTest.getIslands().get(4).setTower(tower3);

        assertEquals(3, gameBTest.getIslands().get(2).getIslandID());
        assertEquals(4, gameBTest.getIslands().get(3).getIslandID());
        assertEquals(5, gameBTest.getIslands().get(4).getIslandID());

        assertEquals(tower1, gameBTest.getIslands().get(2).getTower());
        assertEquals(tower2, gameBTest.getIslands().get(3).getTower());
        assertEquals(tower3, gameBTest.getIslands().get(4).getTower());

        assertEquals(TowerColor.BLACK, gameBTest.getIslands().get(2).getTower().getColor());
        assertEquals(TowerColor.BLACK, gameBTest.getIslands().get(3).getTower().getColor());
        assertEquals(TowerColor.BLACK, gameBTest.getIslands().get(4).getTower().getColor());

        assertEquals(true, gameBTest.getIslands().get(2).hasTower());
        assertEquals(true, gameBTest.getIslands().get(3).hasTower());
        assertEquals(true, gameBTest.getIslands().get(4).hasTower());

        assertEquals(true, gameBTest.getIslands().get(3).hasLeft());
        assertEquals(true, gameBTest.getIslands().get(3).hasRight());
        assertEquals(true, gameBTest.getIslands().get(2).hasRight());
        assertEquals(true, gameBTest.getIslands().get(4).hasLeft());

        gameBTest.getIslands().get(0).setTower(tower1);
        gameBTest.getIslands().get(0).setNoEntry(false);
        gameBTest.getIslands().get(11).setTower(tower2);
        gameBTest.getIslands().get(1).setTower(tower3);

        gameBTest.getIslands().get(11).merge(gameBTest.getIslands().get(0));
        gameBTest.getIslands().get(0).merge(gameBTest.getIslands().get(1));

        Tower tower4 = new Tower(TowerColor.WHITE);
        Tower tower5 = new Tower(TowerColor.WHITE);

        assertEquals(false, gameBTest.getIslands().get(0).getNoEntry());
        assertEquals(true, gameBTest.getIslands().get(0).hasLeft());
        assertEquals(true, gameBTest.getIslands().get(11).hasRight());
        assertEquals(true, gameBTest.getIslands().get(0).hasRight());
        assertEquals(false, gameBTest.getIslands().get(11).hasLeft());

        assertEquals(gameBTest.getIslands().get(11), gameBTest.getIslands().get(0).getMergedIslands().get(0));
        assertEquals(gameBTest.getIslands().get(1), gameBTest.getIslands().get(0).getMergedIslands().get(1));

        List<Tower> towers = new ArrayList<>();
        towers.add(tower1);
        towers.add(tower2);
        towers.add(tower3);
        assertEquals(towers, gameBTest.getIslands().get(0).getMergedTowers());

        SchoolBoard schoolB = new SchoolBoard(players.get(0).getPlayerID());
        players.get(0).setBoard(schoolB);
        gameBTest.getIslands().get(0).moveTowerToArea(players.get(0).getBoard().getTowerArea());
        assertEquals(tower3, players.get(0).getBoard().getTowerArea().getTowerArea().get(players.get(0).getBoard().getTowerArea().getTowerArea().size() - 1));

        for(Island i : gameBTest.getIslands()) {
            if(i.getMergedIslands() != null) {
                System.out.println("Island "+ i.getIslandID() + " with: " + i.getMergedIslands().size());
            }
        }
        assertEquals(gameBTest.getIslands().size(), 12);
    }

    @Test
    @DisplayName("Verify students bag size")
    void studentsBagTest(){
        gameBTest = new GameBoard(game);
        assertEquals(120, gameBTest.getStudentsBag().size());
    }
}