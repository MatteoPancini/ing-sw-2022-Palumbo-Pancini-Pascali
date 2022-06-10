package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.servertoclient.Answer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.cards.CharacterDeck;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.SchoolBoard;
import it.polimi.ingsw.model.player.Tower;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.server.VirtualClientView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.annotation.processing.SupportedSourceVersion;
import java.beans.PropertyChangeEvent;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpertControllerTest {
    final PlayerStub matteo = new PlayerStub("Matteo", 1);
    final PlayerStub cisco = new PlayerStub("Cisco", 2);
    final PlayerStub gigiox = new PlayerStub("Gigiox", 3);
    final PlayerStub mario = new PlayerStub("Mario", 4);


    final Socket socket = new Socket();
    final ServerStub server = new ServerStub();
    final GameHandlerStub gameHandlerStub = new GameHandlerStub(server);
    final SocketClientConnectionStub socketClientConnectionStub =
            new SocketClientConnectionStub(socket, server);
    final VirtualClientView virtualClient =
            new VirtualClientView(1, "Matteo", socketClientConnectionStub, gameHandlerStub);
    final VirtualClientView virtualClient2 =
            new VirtualClientView(2, "Cisco", socketClientConnectionStub, gameHandlerStub);
    final VirtualClientView virtualClient3 =
            new VirtualClientView(3, "Gigiox", socketClientConnectionStub, gameHandlerStub);
    final VirtualClientView virtualClient4 =
            new VirtualClientView(4, "Mario", socketClientConnectionStub, gameHandlerStub);
    final HashMap<Integer, VirtualClientView> idMapID =
            new HashMap<>() {
                {
                    put(1, virtualClient);
                    put(2, virtualClient2);
                    put(3, virtualClient3);
                    put(4, virtualClient4);
                }
            };

    final Game game = new Game();

    final ControllerStub controllerStub = new ControllerStub(gameHandlerStub.getGame(), gameHandlerStub);

    final ExpertController expertController = new ExpertController(controllerStub.getGame(), controllerStub.getGame().getGameBoard(), controllerStub.getTurnController());





    @BeforeEach
    @DisplayName("Expert Mode Tets")
    public void expertModeGame() {
        matteo.setWizard(Wizards.KING);
        System.out.println("\n");
        cisco.setWizard(Wizards.MONACH);
        System.out.println("\n");
        server.setIdMapID(idMapID);
        controllerStub.getGame().getActivePlayers().add(matteo);
        controllerStub.getGame().getActivePlayers().add(cisco);
        controllerStub.getGame().setPlayersNumber(2);
        controllerStub.getGame().setCurrentPlayer(matteo);
        assertEquals(controllerStub.getGame().getCurrentPlayer().getNickname(), "Matteo");
        for(Player p : controllerStub.getGame().getActivePlayers()) {
            p.setBoard(new SchoolBoard(p.getPlayerID()));
        }
        setupGame();
        assertEquals(controllerStub.getGame().getCurrentPlayer().getNickname(), "Matteo");
        controllerStub.getTurnController().setCurrentPlayer(matteo);
        controllerStub.getTurnController().setExpertController(expertController);
        controllerStub.setExpertController(expertController);
    }



    @Test
    public void coinTest() {

        System.out.println(controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().getDiningRoom().get(PawnType.RED.getPawnID()).getTableStudentsNum());

        Student s1 = new Student(PawnType.RED);
        Student s2 = new Student(PawnType.RED);
        Student s3 = new Student(PawnType.RED);
        controllerStub.getGame().getCurrentPlayer().setMyCoins(1);
        controllerStub.getGame().getGameBoard().getPlayableCharacters().add(new CharacterCard(Characters.HERALD, " ", 3));
        controllerStub.getGame().getGameBoard().getPlayableCharacters().add(new CharacterCard(Characters.GRANNY_HERBS, " ", 2));
        controllerStub.getGame().getGameBoard().getPlayableCharacters().add(new CharacterCard(Characters.FUNGARUS, " ", 3));




        controllerStub.getTurnController().setStudentToMove(s1);
        controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(s1);
        controllerStub.getTurnController().setStudentToMove(s2);

        controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(s2);
        controllerStub.getTurnController().setStudentToMove(s3);

        controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(s3);

        if(controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().isTakeCoin()) {
            controllerStub.getGame().getCurrentPlayer().setMyCoins(controllerStub.getGame().getCurrentPlayer().getMyCoins() + 1);
            controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().setTakeCoin(false);
        }

        System.out.println(controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().getDiningRoom().get(PawnType.RED.getPawnID()).getTableStudentsNum());

        assertEquals(controllerStub.getGame().getCurrentPlayer().getMyCoins(), 2);

        System.out.println(controllerStub.getGame().getCurrentPlayer().getMyCoins());
        System.out.println("Prima" + controllerStub.getGame().getGameBoard().getPlayableCharacters().get(0).getInitialCost());

        controllerStub.getGame().getGameBoard().getPlayableCharacters().get(0).incrementPrice();

        System.out.println("Dopo" + controllerStub.getGame().getGameBoard().getPlayableCharacters().get(0).getInitialCost());

        PropertyChangeEvent ev1 = new PropertyChangeEvent(1, "PickCloud", null, controllerStub.getGame().getGameBoard().getClouds().get(0));
        controllerStub.propertyChange(ev1);
        for(int i = 0; i < controllerStub.getGame().getGameBoard().getPlayableCharacters().size(); i++) {
            controllerStub.getGame().getGameBoard().getPlayableCharacters().get(i).resetCost();
        }
        for(CharacterCard c : controllerStub.getGame().getGameBoard().getPlayableCharacters()) {
            System.out.println(c.getName() + " " + c.getInitialCost());
        }

    }



    @Test
    @DisplayName("Herald Test")
    public void heraldTest() {
        //non setto controllerStub.getGame().setExpertMode altrimenti ogni volta mi crea carte a caso e non so quali devo giocare con il propertychange
        controllerStub.getGame().getGameBoard().getPlayableCharacters().add(new CharacterCard(Characters.HERALD, " ", 3));
        /*
        System.out.println("\n" + controllerStub.getGame().getGameBoard().getPlayableCharacters().size());
        for(CharacterCard c : controllerStub.getGame().getGameBoard().getPlayableCharacters()) {
            System.out.println(c.getName() + " ");
        }

         */
        PropertyChangeEvent ev1 = new PropertyChangeEvent(1, "PickCharacter", null, Characters.HERALD);
        controllerStub.propertyChange(ev1);
        PropertyChangeEvent ev2 = new PropertyChangeEvent(1, "CheckInfluence", null, controllerStub.getGame().getGameBoard().getIslands().get(0));
        controllerStub.propertyChange(ev2);

    }

    @Test
    @DisplayName("Centaur Test")
    public void centaurTest() {
        //non setto controllerStub.getGame().setExpertMode altrimenti ogni volta mi crea carte a caso e non so quali devo giocare con il propertychange
        controllerStub.getGame().getGameBoard().getPlayableCharacters().add(new CharacterCard(Characters.CENTAUR, " ", 3));
        /*
        System.out.println("\n" + controllerStub.getGame().getGameBoard().getPlayableCharacters().size());
        for(CharacterCard c : controllerStub.getGame().getGameBoard().getPlayableCharacters()) {
            System.out.println(c.getName() + " ");
        }

         */
        System.out.println(controllerStub.getGame().getCurrentPlayer().getBoard().getTowerArea().getTowerArea().get(0).getColor().toString());


        controllerStub.getGame().getGameBoard().getIslands().get(2).setTower(controllerStub.getGame().getCurrentPlayer().getBoard().getTowerArea().getTowerArea().get(0));

        PropertyChangeEvent ev1 = new PropertyChangeEvent(1, "PickCharacter", null, Characters.CENTAUR);
        controllerStub.propertyChange(ev1);
        PropertyChangeEvent ev2 = new PropertyChangeEvent(1, "PickMovesNumber", null, 2);
        controllerStub.propertyChange(ev2);

    }

    @Test
    @DisplayName("Knight Test")
    public void knightTest() {
        //non setto controllerStub.getGame().setExpertMode altrimenti ogni volta mi crea carte a caso e non so quali devo giocare con il propertychange
        controllerStub.getGame().getGameBoard().getPlayableCharacters().add(new CharacterCard(Characters.KNIGHT, " ", 2));

        PropertyChangeEvent ev1 = new PropertyChangeEvent(1, "PickCharacter", null, Characters.KNIGHT);
        controllerStub.propertyChange(ev1);
        PropertyChangeEvent ev2 = new PropertyChangeEvent(1, "PickMovesNumber", null, 2);
        controllerStub.propertyChange(ev2);
    }

    @Test
    @DisplayName("Thief Test")
    public void thiefTest() {
        //non setto controllerStub.getGame().setExpertMode altrimenti ogni volta mi crea carte a caso e non so quali devo giocare con il propertychange
        controllerStub.getGame().getGameBoard().getPlayableCharacters().add(new CharacterCard(Characters.THIEF, " ", 3));

        System.err.println(controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().getDiningRoom().get(4).getTableStudentsNum());

        controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(new Student(PawnType.BLUE));
        controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(new Student(PawnType.BLUE));
        controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(new Student(PawnType.BLUE));
        controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(new Student(PawnType.BLUE));

        assertEquals(controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().getDiningRoom().get(4).getColor(), PawnType.BLUE);
        System.err.println(controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().getDiningRoom().get(4).getTableStudentsNum());
        assertEquals(controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().getDiningRoom().get(4).getTableStudentsNum(), 4);



        PropertyChangeEvent ev1 = new PropertyChangeEvent(1, "PickCharacter", null, Characters.THIEF);
        controllerStub.propertyChange(ev1);
        PropertyChangeEvent ev2 = new PropertyChangeEvent(1, "PickPawnType", null, PawnType.BLUE);
        controllerStub.propertyChange(ev2);

        assertEquals(controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().getDiningRoom().get(4).getTableStudentsNum(), 1);
    }

    @Test
    @DisplayName("Fungarus Test")
    public void fungarusTest() {
        //non setto controllerStub.getGame().setExpertMode altrimenti ogni volta mi crea carte a caso e non so quali devo giocare con il propertychange
        controllerStub.getGame().getGameBoard().getPlayableCharacters().add(new CharacterCard(Characters.FUNGARUS, " ", 3));



        controllerStub.getGame().getCurrentPlayer().getBoard().getProfessorTable().getCellByColor(PawnType.BLUE).setProfessor(controllerStub.getGame().getGameBoard().getProfessorByColor(PawnType.BLUE));
        controllerStub.getGame().getGameBoard().getProfessorByColor(PawnType.BLUE).setOwner(controllerStub.getGame().getCurrentPlayer());
        controllerStub.getGame().getCurrentPlayer().getBoard().getProfessorTable().getCellByColor(PawnType.RED).setProfessor(controllerStub.getGame().getGameBoard().getProfessorByColor(PawnType.RED));
        controllerStub.getGame().getGameBoard().getProfessorByColor(PawnType.RED).setOwner(controllerStub.getGame().getCurrentPlayer());

        controllerStub.getGame().getGameBoard().getIslands().get(2).getStudents().add(new Student(PawnType.BLUE));
        controllerStub.getGame().getGameBoard().getIslands().get(2).getStudents().add(new Student(PawnType.BLUE));
        controllerStub.getGame().getGameBoard().getIslands().get(2).getStudents().add(new Student(PawnType.BLUE));
        controllerStub.getGame().getGameBoard().getIslands().get(2).getStudents().add(new Student(PawnType.RED));


        PropertyChangeEvent ev1 = new PropertyChangeEvent(1, "PickCharacter", null, Characters.FUNGARUS);
        controllerStub.propertyChange(ev1);
        PropertyChangeEvent ev2 = new PropertyChangeEvent(1, "PickPawnType", null, PawnType.BLUE);
        controllerStub.propertyChange(ev2);
        PropertyChangeEvent ev3 = new PropertyChangeEvent(1, "PickMovesNumber", null, 2);
        controllerStub.propertyChange(ev3);

    }

    @Test
    @DisplayName("Farmer Test")
    public void farmerTest() {
        //non setto controllerStub.getGame().setExpertMode altrimenti ogni volta mi crea carte a caso e non so quali devo giocare con il propertychange
        controllerStub.getGame().getGameBoard().getPlayableCharacters().add(new CharacterCard(Characters.FARMER, " ", 2));



        controllerStub.getGame().getCurrentPlayer().getBoard().getProfessorTable().getCellByColor(PawnType.BLUE).setProfessor(controllerStub.getGame().getGameBoard().getProfessorByColor(PawnType.BLUE));
        controllerStub.getGame().getGameBoard().getProfessorByColor(PawnType.BLUE).setOwner(controllerStub.getGame().getActivePlayers().get(1));

        controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(new Student(PawnType.BLUE));
        controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(new Student(PawnType.BLUE));

        assertEquals(controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().getDiningRoom().get(PawnType.BLUE.getPawnID()).getTableStudentsNum(), 2);

        controllerStub.getGame().getActivePlayers().get(1).getBoard().getDiningRoom().setStudentToDiningRoom(new Student(PawnType.BLUE));
        controllerStub.getGame().getActivePlayers().get(1).getBoard().getDiningRoom().setStudentToDiningRoom(new Student(PawnType.BLUE));


        assertEquals(controllerStub.getGame().getActivePlayers().get(1).getBoard().getDiningRoom().getDiningRoom().get(PawnType.BLUE.getPawnID()).getTableStudentsNum(), 2);


        PropertyChangeEvent ev1 = new PropertyChangeEvent(1, "PickCharacter", null, Characters.FARMER);
        controllerStub.propertyChange(ev1);


        assertEquals(controllerStub.getGame().getCurrentPlayer().getBoard().getProfessorTable().getCellByColor(PawnType.BLUE).hasProfessor(), true);
    }

    @Test
    @DisplayName("MagicPostman Test")
    public void magicPostmanTest() {
        controllerStub.getGame().getGameBoard().getPlayableCharacters().add(new CharacterCard(Characters.MONK, " ", 1));
        Student s = new Student(PawnType.BLUE);


        PropertyChangeEvent ev1 = new PropertyChangeEvent(1, "PickCharacter", null, Characters.MONK);
        controllerStub.propertyChange(ev1);
        PropertyChangeEvent ev2 = new PropertyChangeEvent(1, "PickStudent", null, s);
        controllerStub.propertyChange(ev2);




    }

    @Test
    @DisplayName("Monk Test")
    public void monkTest() {
        controllerStub.getGame().getGameBoard().getPlayableCharacters().add(new CharacterCard(Characters.MAGIC_POSTMAN, " ", 1));


        PropertyChangeEvent ev1 = new PropertyChangeEvent(1, "PickCharacter", null, Characters.MAGIC_POSTMAN);
        controllerStub.propertyChange(ev1);
        PropertyChangeEvent ev2 = new PropertyChangeEvent(1, "PickMovesNumber", null, 4);
        controllerStub.propertyChange(ev2);

    }


    @Test
    @DisplayName("GrannyHerbs Test")
    public void grannyHerbsTest() {
        controllerStub.getGame().getGameBoard().getPlayableCharacters().add(new CharacterCard(Characters.GRANNY_HERBS, " ", 2));

        PropertyChangeEvent ev1 = new PropertyChangeEvent(1, "PickCharacter", null, Characters.GRANNY_HERBS);
        controllerStub.propertyChange(ev1);
        PropertyChangeEvent ev2 = new PropertyChangeEvent(1, "GrannyHerbsTile", null, controllerStub.getGame().getGameBoard().getIslands().get(6));
        controllerStub.propertyChange(ev2);
        PropertyChangeEvent ev3 = new PropertyChangeEvent(1, "PickMovesNumber", null, 6);
        controllerStub.propertyChange(ev3);

        assertEquals(controllerStub.getGame().getGameBoard().getIslands().get(6).getNoEntry(), true);
    }

    @Test
    @DisplayName("Minestrel Test")
    public void minestrelTest() {
        controllerStub.getGame().getGameBoard().getPlayableCharacters().add(new CharacterCard(Characters.MINESTREL, " ", 1));
        Student s1 = new Student(PawnType.BLUE); //DINING
        Student s2 = new Student(PawnType.RED); //ENTRANCE


        controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(s1);
        controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().removeStudent(controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().get(0));
        controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().removeStudent(controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().get(0));
        controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().removeStudent(controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().get(0));
        controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().removeStudent(controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().get(0));
        controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().removeStudent(controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().get(0));
        controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().removeStudent(controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().get(0));
        controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().removeStudent(controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().get(0));
        controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().setStudents(s2);

        PropertyChangeEvent ev1 = new PropertyChangeEvent(1, "PickCharacter", null, Characters.MINESTREL);
        controllerStub.propertyChange(ev1);
        PropertyChangeEvent ev2 = new PropertyChangeEvent(1, "PickCharacterActionsNum", null, 1);
        controllerStub.propertyChange(ev2);
        PropertyChangeEvent ev3 = new PropertyChangeEvent(1, "PickStudent", null, s1);
        controllerStub.propertyChange(ev3);
        PropertyChangeEvent ev4 = new PropertyChangeEvent(1, "PickStudent", null, s2);
        controllerStub.propertyChange(ev4);

        assertEquals(controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().getDiningRoom().get(PawnType.RED.getPawnID()).getTableStudentsNum(), 1);
        assertEquals(controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().get(0).getType(), PawnType.BLUE);

    }

    @Test
    @DisplayName("Spoiled Princess Test")
    public void spoiledPrincessTest() {
        controllerStub.getGame().getGameBoard().getPlayableCharacters().add(new CharacterCard(Characters.SPOILED_PRINCESS, " ", 2));
        Student s1 = new Student(PawnType.BLUE);


        PropertyChangeEvent ev1 = new PropertyChangeEvent(1, "PickCharacter", null, Characters.SPOILED_PRINCESS);
        controllerStub.propertyChange(ev1);
        PropertyChangeEvent ev2 = new PropertyChangeEvent(1, "PickStudent", null, s1);
        controllerStub.propertyChange(ev2);
    }

    @Test
    @DisplayName("Jester Test")
    public void jesterTest() {
        controllerStub.getGame().getGameBoard().getPlayableCharacters().add(new CharacterCard(Characters.JESTER, " ", 1));
        Student s1 = new Student(PawnType.BLUE); //JESTER
        Student s2 = new Student(PawnType.YELLOW); //JESTER

        Student s3 = new Student(PawnType.RED); //ENTRANCE
        Student s4 = new Student(PawnType.GREEN); //ENTRANCE


        for(Student s : controllerStub.getGame().getGameBoard().getPlayableCharacters().get(0).getStudents()) {
            controllerStub.getGame().getGameBoard().getPlayableCharacters().get(0).getStudents().remove(s);
        }
        controllerStub.getGame().getGameBoard().getPlayableCharacters().get(0).addStudent(s1);
        controllerStub.getGame().getGameBoard().getPlayableCharacters().get(0).addStudent(s2);

        System.out.println(controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().size());


        controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().removeStudent(controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().get(0));
        controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().removeStudent(controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().get(0));
        controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().removeStudent(controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().get(0));
        controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().removeStudent(controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().get(0));
        controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().removeStudent(controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().get(0));
        controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().removeStudent(controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().get(0));
        controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().removeStudent(controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().get(0));
        controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().setStudents(s3);
        controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().setStudents(s4);


        assertEquals(controllerStub.getGame().getGameBoard().getPlayableCharacters().get(0).getStudents().size(), 2);
        assertEquals(controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().size(), 2);



        PropertyChangeEvent ev1 = new PropertyChangeEvent(1, "PickCharacter", null, Characters.JESTER);
        controllerStub.propertyChange(ev1);
        PropertyChangeEvent ev2 = new PropertyChangeEvent(1, "PickCharacterActionsNum", null, 2);
        controllerStub.propertyChange(ev2);
        PropertyChangeEvent ev3 = new PropertyChangeEvent(1, "PickStudent", null, s1);
        controllerStub.propertyChange(ev3);
        PropertyChangeEvent ev4 = new PropertyChangeEvent(1, "PickStudent", null, s3);
        controllerStub.propertyChange(ev4);

        assertEquals(controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().get(1).getType(), PawnType.BLUE);

        PropertyChangeEvent ev5 = new PropertyChangeEvent(1, "PickStudent", null, s2);
        controllerStub.propertyChange(ev5);
        PropertyChangeEvent ev6 = new PropertyChangeEvent(1, "PickStudent", null, s4);
        controllerStub.propertyChange(ev6);

        assertEquals(controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().size(), 2);
        assertEquals(controllerStub.getGame().getGameBoard().getPlayableCharacters().get(0).getStudents().size(), 2);




    }





    public void setupGame() {
        System.out.println("Starting setupGame");

        int studentsNumber;
        if(controllerStub.getGame().getPlayersNumber() == 3) {
            studentsNumber = 9;
        }
        else {
            studentsNumber = 7;
        }

        int towersNumber = 0;
        int colorsCounter3P = 0;
        int colorsCounter2P = 0;
        int colorsCounter4P = 0;
        ArrayList<TowerColor> allTowerColors = new ArrayList<TowerColor>();
        allTowerColors.add(TowerColor.WHITE);
        allTowerColors.add(TowerColor.BLACK);
        allTowerColors.add(TowerColor.GREY);

        if(controllerStub.getGame().getPlayersNumber() == 3) {
            towersNumber = 6;
            colorsCounter3P = 0;
        } else if(controllerStub.getGame().getPlayersNumber() == 2) {
            towersNumber = 8;
            colorsCounter2P = 0;
        } else if(controllerStub.getGame().getPlayersNumber() == 4) {
            towersNumber = 8;
            colorsCounter4P = 0;
        }

        for(Player p : controllerStub.getGame().getActivePlayers()) {
            //System.out.println("Inizio setup di " + p.getNickname());

            //System.out.println("metto students nell'entrance");
            for(int i = 1; i <= studentsNumber; i++){
                Collections.shuffle(controllerStub.getGame().getGameBoard().getStudentsBag());
                p.getBoard().getEntrance().getStudents().add(controllerStub.getGame().getGameBoard().getStudentsBag().get(0));
                controllerStub.getGame().getGameBoard().removeStudents(0);
            }

            //System.out.println("metto torri");
            if(controllerStub.getGame().getActivePlayers().size() == 3) {
                for(int i = 1; i <= towersNumber; i++) {
                    p.getBoard().getTowerArea().addTowers(new Tower(allTowerColors.get(colorsCounter3P)));
                    if(colorsCounter3P < 2) colorsCounter3P++;
                }
            } else if(controllerStub.getGame().getActivePlayers().size() == 2) {
                for(int k = 1; k <= towersNumber; k++) {
                    p.getBoard().getTowerArea().addTowers(new Tower(allTowerColors.get(colorsCounter2P)));
                    if(colorsCounter2P < 1) colorsCounter2P++;
                }
            } else if(controllerStub.getGame().getActivePlayers().size() == 4) {
                if((p.getIdTeam() == 1 && p.isTeamLeader()) || (p.getIdTeam() == 2 && p.isTeamLeader())) {
                    p.getBoard().getTowerArea().addTowers(new Tower(allTowerColors.get(colorsCounter4P)));
                    if(colorsCounter4P < 3) colorsCounter4P++;
                }
            }
        }

        //System.out.println("metto madre natura");

        int maximum = 11;
        SecureRandom r = new SecureRandom();
        controllerStub.getGame().getGameBoard().getMotherNature().setPosition(1);
        //int n = 1;
        int mnPos = controllerStub.getGame().getGameBoard().getMotherNature().getPosition();

        int mnPosOpposite = -1;
        if(mnPos <= 6) {
            mnPosOpposite = mnPos + 6;
        } else {
            mnPosOpposite = (mnPos + 6) % 12;
        }

        //System.out.println("mn = " + mnPos + ", mnOpp = " + mnPosOpposite);

        for(int s = 1; s <= 12; s++) {
            if(s != mnPos && s != mnPosOpposite) {
                //pos = (game.getGameBoard().getMotherNature().getPosition() + s) % 12;
                Collections.shuffle(controllerStub.getGame().getGameBoard().getSetupStudentsBag());
                controllerStub.getGame().getGameBoard().getIslands().get(s - 1).addStudent(controllerStub.getGame().getGameBoard().getSetupStudentsBag().get(0));
                controllerStub.getGame().getGameBoard().removeSetupStudents(0);
            }
            //n++;
        }

        for(int p = 1; p <= 12; p++){
            if(p != mnPos && p != mnPosOpposite) {
                System.out.println(p + ", " + "Student " + controllerStub.getGame().getGameBoard().getIslands().get(p - 1).getStudents().get(0).getType());
            }

            if(p == mnPos){
                System.out.println(p + ", " + "Mother nature is here");
            }

            if(p == mnPosOpposite){
                System.out.println(p + ", " + "This island is empty");
            }
        }

        System.out.println("Finished setupGame");
    }

    /** Class GameHandlerStub defines a stub for GameHandler class */
    public class GameHandlerStub extends GameHandler {

        /**
         * Constructor GameHandler creates a new GameHandler instance.
         *
         * @param server of type Server - the main server class.
         */
        public GameHandlerStub(Server server) {
            super(server);
        }

        ControllerStub controllerStub;
        /**
         * Method singleSend sends a message to a client, identified by his ID number, through the
         * server socket.
         *
         * @param message of type Answer - the message to be sent to the client.
         * @param id of type int - the unique identification number of the client to be contacted.
         */
        @Override
        public void sendSinglePlayer(Answer message, int id) {
            String print;
            if (message.getMessage() == null) {
                print = "Not useraction";
            } else print = message.getMessage().toString();
            System.out.println(print);
        }

        /**
         * Method getCurrentPlayerID returns the current player client ID, getting it from the
         * currentPlayer reference in the Game class.
         *
         * @return the currentPlayerID (type int) of this GameHandler object.
         */
        @Override
        public int getCurrentPlayerId() {
            return game.getCurrentPlayer().getPlayerID();
        }

        @Override
        public ControllerStub getController() {
            return controllerStub;
        }

        public void setControllerStub(ControllerStub controllerStub) {
            this.controllerStub = controllerStub;
        }

        /**
         * Method sendAll does the same as the previous method, but it iterates on all the clients
         * present in the game. It's a full effects broadcast.
         *
         * @param message of type Answer - the message to broadcast (at single match participants'
         *     level).
         */


        @Override
        public void sendBroadcast(Answer message) {
            System.out.println(message.getMessage());
        }

        @Override
        public void sendExcept(Answer serverAnswer, int id) {
            System.out.println(serverAnswer.getMessage());
        }


    }

    /** Class ControllerStub defines a stub for Controller class. */
    public static class ControllerStub extends Controller {

        /**
         * Constructor Controller creates a new Controller instance.
         *
         */
        public ControllerStub(Game game, GameHandler gameHandler) {
            super(game, gameHandler);
        }
    }


    /** Class ServerStub defines a stub for Server class. */
    public static class ServerStub extends Server {
        private HashMap<Integer, VirtualClientView> idMapID;
        /**
         * Constructor Server creates the instance of the server, based on a socket and the mapping
         * between VirtualClient, nicknames and client ids. It also creates a new game session.
         */
        public ServerStub() {
            this.idMapID = null;
        }
        /**
         * Method setIdMapID sets the idMapID of this ServerStub object.
         *
         * @param idMapID the idMapID of this ServerStub object.
         */
        public void setIdMapID(HashMap idMapID) {
            this.idMapID = idMapID;
        }
        /**
         * Method getClientByID returns a link to the desired virtual client, in order to make
         * operations on it (like send, etc).
         *
         * @param id of type int - the id of the virtual client needed.
         * @return VirtualClient - the correct virtual client.
         */
        @Override
        public VirtualClientView getVirtualClientFromID(int id) {
            return idMapID.get(id);
        }
    }



    /** Class PlayerStub defines a stub for Player class. */
    public static class PlayerStub extends Player {

        /**
         * Constructor PlayerStub creates a new PlayerStub instance.
         *
         * @param nickname of type String - the player's nickname.
         * @param clientID of type int - the clientID.
         */
        public PlayerStub(String nickname, int clientID) {
            super(nickname, clientID);
        }
    }


    /** Class SocketClientConnectionStub defines a stub for SocketClientConnection class. */
    public static class SocketClientConnectionStub extends SocketClientConnection {

        /**
         * Constructor of the class: it instantiates an input/output stream from the socket received as
         * parameters, and add the main server to his attributes too.
         *
         * @param socket the socket which accepted the client connection.
         * @param server the main server class.
         */
        public SocketClientConnectionStub(Socket socket, Server server) {
            super(socket, server);
        }
        /**
         * Method close terminates the connection with the client, closing firstly input and output
         * streams, then invoking the server method called "unregisterClient", which will remove the
         * active virtual client from the list.
         *
         */
        @Override
        public void closeConnection() {
            System.out.println("Connection closed to client");
        }
    }
}
