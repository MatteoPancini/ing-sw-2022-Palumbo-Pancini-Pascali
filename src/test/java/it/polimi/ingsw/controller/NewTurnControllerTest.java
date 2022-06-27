package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.servertoclient.Answer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.enumerations.PawnType;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.enumerations.Wizards;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.SchoolBoard;
import it.polimi.ingsw.model.player.Tower;
import it.polimi.ingsw.server.GameHandler;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.server.VirtualClientView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.beans.PropertyChangeEvent;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class NewTurnControllerTest {
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

    @Test
    @DisplayName("Setup 3 players")
    public void init3Players() {
        matteo.setWizard(Wizards.KING);
        System.out.println("\n");
        cisco.setWizard(Wizards.MONACH);
        System.out.println("\n");
        gigiox.setWizard(Wizards.FOREST);
        System.out.println("\n");
        server.setIdMapID(idMapID);

        controllerStub.getGame().getActivePlayers().add(matteo);
        controllerStub.getGame().getActivePlayers().add(cisco);
        controllerStub.getGame().getActivePlayers().add(gigiox);
        controllerStub.getGame().getPlayers().add(matteo);
        controllerStub.getGame().getPlayers().add(cisco);
        controllerStub.getGame().getPlayers().add(gigiox);

        for(Player p : controllerStub.getGame().getPlayers()) {
            p.setBoard(new SchoolBoard(p.getPlayerID()));
        }

        controllerStub.getGame().setPlayersNumber(3);
        controllerStub.getGame().setCurrentPlayer(matteo);
        controllerStub.newSetupGame();

        assertEquals(controllerStub.getGame().getCurrentPlayer().getNickname(), "Matteo");

        controllerStub.getTurnController().setCurrentPlayer(matteo);

        assertEquals(controllerStub.getGame().getCurrentPlayer().getNickname(), "Matteo");

        PropertyChangeEvent ev16 = new PropertyChangeEvent(1, "PickAssistant", null, controllerStub.getGame().getCurrentPlayer().getAssistantDeck().getDeck().get(0).getName());
        controllerStub.propertyChange(ev16);
    }

    @Test
    @DisplayName("Setup 4 players")
    public void init4Players() {
        gameHandlerStub.setControllerStub(controllerStub);

        matteo.setWizard(Wizards.KING);
        System.out.println("\n");
        cisco.setWizard(Wizards.MONACH);
        System.out.println("\n");
        gigiox.setWizard(Wizards.FOREST);
        System.out.println("\n");
        mario.setWizard(Wizards.WITCH);
        System.out.println("\n");
        server.setIdMapID(idMapID);

        controllerStub.getGame().addPlayer(matteo);
        controllerStub.getGame().addPlayer(cisco);
        controllerStub.getGame().addPlayer(gigiox);
        controllerStub.getGame().addPlayer(mario);

        matteo.setTeamLeader(true);
        cisco.setTeamLeader(true);
        gigiox.setTeamLeader(false);
        mario.setTeamLeader(false);

        gameHandlerStub.setPlayersNumber(4);
        assertEquals(gameHandlerStub.getController(), controllerStub);
        assertEquals(gameHandlerStub.getController(), controllerStub);
        assertEquals(gameHandlerStub.getController().getGame().getActivePlayers().size(), 4);

        for(Player p : controllerStub.getGame().getPlayers()) {
            p.setBoard(new SchoolBoard(p.getPlayerID()));
        }

        for(Player p : controllerStub.getGame().getActivePlayers()) {
            System.out.println(p.getNickname() + " " + p.getIdTeam() + " " + p.isTeamLeader());
        }

        assertEquals(controllerStub.getGame().getActivePlayers().get(0).getIdTeam(), controllerStub.getGame().getActivePlayers().get(2).getIdTeam());
        assertEquals(controllerStub.getGame().getActivePlayers().get(1).getIdTeam(), controllerStub.getGame().getActivePlayers().get(3).getIdTeam());

        assertEquals(controllerStub.getGame().getActivePlayers().get(0).isTeamLeader(), true);
        assertEquals(controllerStub.getGame().getActivePlayers().get(1).isTeamLeader(), true);
        assertEquals(controllerStub.getGame().getActivePlayers().get(2).isTeamLeader(), false);
        assertEquals(controllerStub.getGame().getActivePlayers().get(3).isTeamLeader(), false);

        controllerStub.getGame().setCurrentPlayer(gigiox);
        controllerStub.getTurnController().setCurrentPlayer(gigiox);
        controllerStub.newSetupGame();

        System.out.println(gigiox.getBoard().getTowerArea().getTowerArea().isEmpty());
        System.out.println(gigiox.getBoard().getTowerArea().getTowerArea().size());

        assertEquals(gigiox.getBoard().getTowerArea().getTowerArea().size(), 0);

        gigiox.getAssistantDeck().getDeck().remove(0);
        gigiox.getAssistantDeck().getDeck().remove(0);
        gigiox.getAssistantDeck().getDeck().remove(0);
        gigiox.getAssistantDeck().getDeck().remove(0);
        gigiox.getAssistantDeck().getDeck().remove(0);
        gigiox.getAssistantDeck().getDeck().remove(0);
        gigiox.getAssistantDeck().getDeck().remove(0);
        gigiox.getAssistantDeck().getDeck().remove(0);
        gigiox.getAssistantDeck().getDeck().remove(0);
        PropertyChangeEvent ev16 = new PropertyChangeEvent(1, "PickAssistant", null, controllerStub.getGame().getCurrentPlayer().getAssistantDeck().getDeck().get(0).getName());
        controllerStub.propertyChange(ev16);
        PropertyChangeEvent ev17 = new PropertyChangeEvent(1, "CheckIslandInfluence", null, controllerStub.getGame().getGameBoard().getIslands().get(5));
        controllerStub.propertyChange(ev17);

        System.out.println(gigiox.getAssistantDeck().getDeck().size());
        System.out.println(gigiox.getAssistantDeck().getDeck().isEmpty());
    }

    @Test
    @DisplayName("Turn 4 players")
    public void fourPTurn() {
        gameHandlerStub.setControllerStub(controllerStub);

        matteo.setWizard(Wizards.KING);
        System.out.println("\n");
        cisco.setWizard(Wizards.MONACH);
        System.out.println("\n");
        gigiox.setWizard(Wizards.FOREST);
        System.out.println("\n");
        mario.setWizard(Wizards.WITCH);
        System.out.println("\n");
        server.setIdMapID(idMapID);

        controllerStub.getGame().addPlayer(matteo);
        controllerStub.getGame().addPlayer(cisco);
        controllerStub.getGame().addPlayer(gigiox);
        controllerStub.getGame().addPlayer(mario);

        matteo.setTeamLeader(true);
        cisco.setTeamLeader(true);
        gigiox.setTeamLeader(false);
        mario.setTeamLeader(false);
        matteo.setIdTeam(1);
        cisco.setIdTeam(2);
        gigiox.setIdTeam(1);
        mario.setIdTeam(2);


        gameHandlerStub.setPlayersNumber(4);
        assertEquals(gameHandlerStub.getController(), controllerStub);
        assertEquals(gameHandlerStub.getController(), controllerStub);
        assertEquals(gameHandlerStub.getController().getGame().getActivePlayers().size(), 4);

        for(Player p : controllerStub.getGame().getPlayers()) {
            p.setBoard(new SchoolBoard(p.getPlayerID()));
        }

        for(Player p : controllerStub.getGame().getActivePlayers()) {
            System.out.println(p.getNickname() + " " + p.getIdTeam() + " " + p.isTeamLeader());
        }

        assertEquals(controllerStub.getGame().getActivePlayers().get(0).getIdTeam(), controllerStub.getGame().getActivePlayers().get(2).getIdTeam());
        assertEquals(controllerStub.getGame().getActivePlayers().get(1).getIdTeam(), controllerStub.getGame().getActivePlayers().get(3).getIdTeam());

        //assertEquals(controllerStub.getGame().getActivePlayers().get(0).isTeamLeader(), true);
        //assertEquals(controllerStub.getGame().getActivePlayers().get(1).isTeamLeader(), true);
        //assertEquals(controllerStub.getGame().getActivePlayers().get(2).isTeamLeader(), false);
        //assertEquals(controllerStub.getGame().getActivePlayers().get(3).isTeamLeader(), false);

        controllerStub.getGame().setCurrentPlayer(matteo);
        controllerStub.getTurnController().setCurrentPlayer(matteo);
        setupGame();

        PropertyChangeEvent ev1 = new PropertyChangeEvent(1, "PickAssistant", null, controllerStub.getGame().getCurrentPlayer().getAssistantDeck().getDeck().get(7).getName());
        controllerStub.propertyChange(ev1);
        PropertyChangeEvent ev2 = new PropertyChangeEvent(1, "PickAssistant", null, controllerStub.getGame().getCurrentPlayer().getAssistantDeck().getDeck().get(9).getName());
        controllerStub.propertyChange(ev2);
        PropertyChangeEvent ev3 = new PropertyChangeEvent(1, "PickAssistant", null, controllerStub.getGame().getCurrentPlayer().getAssistantDeck().getDeck().get(1).getName());
        controllerStub.propertyChange(ev3);
        PropertyChangeEvent ev4 = new PropertyChangeEvent(1, "PickAssistant", null, controllerStub.getGame().getCurrentPlayer().getAssistantDeck().getDeck().get(2).getName());
        controllerStub.propertyChange(ev4);
        PropertyChangeEvent ev5 = new PropertyChangeEvent(1, "PickStudent", null, new Student(PawnType.RED));
        controllerStub.propertyChange(ev5);
        PropertyChangeEvent ev6 = new PropertyChangeEvent(1, "PickDestinationDiningRoom", null, controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom());
        controllerStub.propertyChange(ev6);

        PropertyChangeEvent ev7 = new PropertyChangeEvent(1, "PickStudent", null, new Student(PawnType.RED));
        controllerStub.propertyChange(ev7);
        PropertyChangeEvent ev8 = new PropertyChangeEvent(1, "PickDestinationDiningRoom", null, controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom());
        controllerStub.propertyChange(ev8);

        assertEquals(controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().getDiningRoom().get(PawnType.RED.getPawnID()).getTableStudentsNum(), 2);

        PropertyChangeEvent ev9 = new PropertyChangeEvent(1, "PickStudent", null, new Student(PawnType.RED));
        controllerStub.propertyChange(ev9);
        PropertyChangeEvent ev10 = new PropertyChangeEvent(1, "PickDestinationIsland", null, controllerStub.getGame().getGameBoard().getIslands().get(1));
        controllerStub.propertyChange(ev10);

        assertEquals(controllerStub.getGame().getGameBoard().getIslands().get(1).getStudents().size(), 2);

        PropertyChangeEvent ev11 = new PropertyChangeEvent(1, "PickMovesNumber", null, 1);
        controllerStub.propertyChange(ev11);

        PropertyChangeEvent ev12 = new PropertyChangeEvent(1, "PickCloud", null, controllerStub.getGame().getGameBoard().getClouds().get(0));
        controllerStub.propertyChange(ev12);
    }

    @Test
    @DisplayName("Pianification Phase")
    public void pianificationPhase() {
        matteo.setWizard(Wizards.KING);
        cisco.setWizard(Wizards.MONACH);
        gigiox.setWizard(Wizards.FOREST);
        server.setIdMapID(idMapID);

        controllerStub.getGame().getActivePlayers().add(matteo);
        controllerStub.getGame().getActivePlayers().add(cisco);
        controllerStub.getGame().getActivePlayers().add(gigiox);

        controllerStub.getGame().setPlayersNumber(3);
        controllerStub.getGame().setCurrentPlayer(matteo);

        assertEquals(controllerStub.getGame().getCurrentPlayer().getNickname(), "Matteo");

        controllerStub.getTurnController().setCurrentPlayer(matteo);

        assertEquals(controllerStub.getGame().getCurrentPlayer().getNickname(), "Matteo");
        controllerStub.getTurnController().playAssistantCard(Assistants.ELEPHANT);
        controllerStub.getTurnController().playAssistantCard(Assistants.LIZARD);
        controllerStub.getTurnController().playAssistantCard(Assistants.CHEETAH);
        assertEquals(controllerStub.getGame().getGameBoard().getLastAssistantUsed().size(), 3);
        for(AssistantCard a : controllerStub.getGame().getGameBoard().getLastAssistantUsed()) {
            System.out.println("Card: " + a.getName() + " " + a.getValue() + " by " + a.getOwner().getNickname());
        }

        assertEquals(controllerStub.getGame().getActivePlayers().get(0).getNickname(), "Gigiox");
        assertEquals(controllerStub.getGame().getActivePlayers().get(1).getNickname(), "Cisco");
        assertEquals(controllerStub.getGame().getActivePlayers().get(2).getNickname(), "Matteo");

        assertEquals(controllerStub.getGame().getActivePlayers().get(0).getAssistantDeck().getDeck().size(), 9);
    }

    @Test
    @DisplayName("Action Phase Test")
    public void standardActionPhase() {
        matteo.setWizard(Wizards.KING);
        cisco.setWizard(Wizards.MONACH);
        server.setIdMapID(idMapID);

        controllerStub.getGame().getActivePlayers().add(matteo);
        controllerStub.getGame().getActivePlayers().add(cisco);

        controllerStub.getGame().setPlayersNumber(2);
        controllerStub.getGame().setCurrentPlayer(matteo);

        for(Player p : controllerStub.getGame().getActivePlayers()) {
            p.setBoard(new SchoolBoard(p.getPlayerID()));
        }

        setupGame();

        assertEquals(controllerStub.getGame().getCurrentPlayer().getNickname(), "Matteo");

        controllerStub.getTurnController().setCurrentPlayer(matteo);

        for(Student t : controllerStub.getTurnController().getCurrentPlayer().getBoard().getEntrance().getStudents()) {
            System.out.println(t.getType());
        }

        assertEquals(controllerStub.getGame().getCurrentPlayer().getNickname(), "Matteo");

        controllerStub.getTurnController().startActionPhase();

        System.out.println(matteo.getBoard().getEntrance().getStudents().get(0).getType());
        controllerStub.getTurnController().setStudentToMove(matteo.getBoard().getEntrance().getStudents().get(0));
        controllerStub.getTurnController().moveStudentToIsland(controllerStub.getGame().getGameBoard().getIslandById(2));

        assertEquals(controllerStub.getTurnController().getController().getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().size(), 6);

        for(Island i : controllerStub.getGame().getGameBoard().getIslands()) {
            System.out.println("Island " + i.getIslandID() + ": " + i.getStudents().size());
        }

        controllerStub.getTurnController().setStudentToMove(matteo.getBoard().getEntrance().getStudents().get(0));
        controllerStub.getTurnController().moveStudentToIsland(controllerStub.getGame().getGameBoard().getIslandById(3));
        for(Island i : controllerStub.getGame().getGameBoard().getIslands()) {
            System.out.println("Island " + i.getIslandID() + ": " + i.getStudents().size());
        }
        controllerStub.getTurnController().setStudentToMove(matteo.getBoard().getEntrance().getStudents().get(0));
        controllerStub.getTurnController().moveStudentToIsland(controllerStub.getGame().getGameBoard().getIslandById(10));
        for(Island i : controllerStub.getGame().getGameBoard().getIslands()) {
            System.out.println("Island " + i.getIslandID() + ": " + i.getStudents().size());
        }
        assertEquals(controllerStub.getTurnController().getController().getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().size(), 4);

        controllerStub.getTurnController().moveMotherNature(2);

        System.out.println("MN is in island: " + controllerStub.getGame().getGameBoard().getMotherNature().getPosition());

        controllerStub.getTurnController().fromCloudToEntrance(controllerStub.getGame().getGameBoard().getClouds().get(0));

        assertEquals(controllerStub.getTurnController().getCurrentPlayer().getBoard().getEntrance().getStudents().size(), 7);

    }

    @Test
    @DisplayName("Check professor test")
    public void professorCheck() {
        matteo.setWizard(Wizards.KING);
        cisco.setWizard(Wizards.MONACH);
        server.setIdMapID(idMapID);

        controllerStub.getGame().getActivePlayers().add(matteo);
        controllerStub.getGame().getActivePlayers().add(cisco);

        controllerStub.getGame().setPlayersNumber(2);
        controllerStub.getGame().setCurrentPlayer(matteo);

        for(Player p : controllerStub.getGame().getActivePlayers()) {
            p.setBoard(new SchoolBoard(p.getPlayerID()));
        }

        setupGame();

        assertEquals(controllerStub.getGame().getCurrentPlayer().getNickname(), "Matteo");

        controllerStub.getTurnController().setCurrentPlayer(matteo);

        Student s1 = new Student(PawnType.RED);
        Student s2 = new Student(PawnType.RED);
        Student s3 = new Student(PawnType.BLUE);
        Student s4 = new Student(PawnType.RED);
        Student s5 = new Student(PawnType.RED);

        controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(s1);
        controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(s3);

        controllerStub.getTurnController().setStudentToMove(s2);
        controllerStub.getTurnController().checkProfessorInfluence();

        System.out.println(controllerStub.getGame().getCurrentPlayer().getBoard().getProfessorTable().getCellByColor(PawnType.RED).hasProfessor());

        controllerStub.getTurnController().setCurrentPlayer(cisco);
        controllerStub.getGame().setCurrentPlayer(cisco);

        controllerStub.getGame().getActivePlayers().get(1).getBoard().getDiningRoom().setStudentToDiningRoom(s4);
        controllerStub.getGame().getActivePlayers().get(1).getBoard().getDiningRoom().setStudentToDiningRoom(s5);
        controllerStub.getTurnController().checkProfessorInfluence();

        assertEquals(controllerStub.getGame().getCurrentPlayer().getBoard().getProfessorTable().getCellByColor(PawnType.RED).hasProfessor(), true);
        System.out.println(controllerStub.getGame().getActivePlayers().get(0).getNickname());
        assertEquals(controllerStub.getGame().getActivePlayers().get(0).getBoard().getProfessorTable().getCellByColor(PawnType.RED).hasProfessor(), false);
    }

    @Test
    @DisplayName("Check Winner Test")
    public void winnerCheck() {
        matteo.setWizard(Wizards.KING);
        cisco.setWizard(Wizards.MONACH);
        server.setIdMapID(idMapID);

        controllerStub.getGame().getActivePlayers().add(matteo);
        controllerStub.getGame().getActivePlayers().add(cisco);

        controllerStub.getGame().setPlayersNumber(2);
        controllerStub.getGame().setCurrentPlayer(matteo);

        for (Player p : controllerStub.getGame().getActivePlayers()) {
            p.setBoard(new SchoolBoard(p.getPlayerID()));
        }

        setupGame();

        controllerStub.getTurnController().setCurrentPlayer(matteo);

        Student s1 = new Student(PawnType.RED);
        Student s2 = new Student(PawnType.RED);
        Student s3 = new Student(PawnType.BLUE);
        Student s4 = new Student(PawnType.RED);
        Student s5 = new Student(PawnType.RED);

        controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(s1);
        controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(s3);

        controllerStub.getTurnController().setStudentToMove(s2);
        controllerStub.getTurnController().checkProfessorInfluence();

        System.out.println(controllerStub.getGame().getCurrentPlayer().getBoard().getProfessorTable().getCellByColor(PawnType.RED).hasProfessor());

        controllerStub.getTurnController().setCurrentPlayer(cisco);
        controllerStub.getGame().setCurrentPlayer(cisco);

        controllerStub.getGame().getActivePlayers().get(1).getBoard().getDiningRoom().setStudentToDiningRoom(s4);
        controllerStub.getGame().getActivePlayers().get(1).getBoard().getDiningRoom().setStudentToDiningRoom(s5);
        controllerStub.getTurnController().checkProfessorInfluence();

        controllerStub.getTurnController().getCurrentPlayer().getBoard().getTowerArea().getTowerArea().clear();

        controllerStub.getTurnController().checkWinner();
        assertEquals(cisco, controllerStub.getTurnController().checkWinner());

        for(int i = 1; i <= 8; i++){
            controllerStub.getTurnController().getCurrentPlayer().getBoard().getTowerArea().getTowerArea().add(new Tower(TowerColor.BLACK));
        }

        for(int j = 1; j <= 8; j++){
            if(j % 2 != 0){
                controllerStub.getTurnController().getCurrentPlayer().getBoard().getTowerArea().moveTowerToIsland(controllerStub.getGame().getGameBoard().getIslands().get(j));
            }

            if(j % 2 == 0){
                controllerStub.getGame().getActivePlayers().get(0).getBoard().getTowerArea().moveTowerToIsland(controllerStub.getGame().getGameBoard().getIslands().get(j));
            }
        }

        controllerStub.getTurnController().checkWinner();
        assertEquals(cisco, controllerStub.getTurnController().checkWinner());
    }

    @Test
    @DisplayName("Check Win Test")
    public void winCheck() {
        matteo.setWizard(Wizards.KING);
        cisco.setWizard(Wizards.MONACH);
        server.setIdMapID(idMapID);

        controllerStub.getGame().getActivePlayers().add(matteo);
        controllerStub.getGame().getActivePlayers().add(cisco);

        controllerStub.getGame().setPlayersNumber(2);
        controllerStub.getGame().setCurrentPlayer(matteo);

        for (Player p : controllerStub.getGame().getActivePlayers()) {
            p.setBoard(new SchoolBoard(p.getPlayerID()));
        }

        setupGame();

        controllerStub.getTurnController().setCurrentPlayer(matteo);

        Student s1 = new Student(PawnType.RED);
        Student s2 = new Student(PawnType.RED);
        Student s3 = new Student(PawnType.BLUE);
        Student s4 = new Student(PawnType.RED);
        Student s5 = new Student(PawnType.RED);

        controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(s1);
        controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(s3);

        controllerStub.getTurnController().setStudentToMove(s2);
        controllerStub.getTurnController().checkProfessorInfluence();

        System.out.println(controllerStub.getGame().getCurrentPlayer().getBoard().getProfessorTable().getCellByColor(PawnType.RED).hasProfessor());

        controllerStub.getTurnController().setCurrentPlayer(cisco);
        controllerStub.getGame().setCurrentPlayer(cisco);

        controllerStub.getGame().getActivePlayers().get(1).getBoard().getDiningRoom().setStudentToDiningRoom(s4);
        controllerStub.getGame().getActivePlayers().get(1).getBoard().getDiningRoom().setStudentToDiningRoom(s5);
        controllerStub.getTurnController().checkProfessorInfluence();

        controllerStub.getTurnController().getCurrentPlayer().getBoard().getTowerArea().getTowerArea().clear();

        controllerStub.getTurnController().checkWin();
        assertEquals(true, controllerStub.getTurnController().checkWin());

        for(int i = 1; i <= 8; i++){
            controllerStub.getTurnController().getCurrentPlayer().getBoard().getTowerArea().getTowerArea().add(new Tower(TowerColor.BLACK));
        }
        controllerStub.getTurnController().checkWin();
        assertEquals(false, controllerStub.getTurnController().checkWin());

        for(int i = 9; i >= 1; i--){
            controllerStub.getTurnController().getCurrentPlayer().getAssistantDeck().getDeck().remove(controllerStub.getTurnController().getCurrentPlayer().getAssistantDeck().getDeck().get(i));
        }
        Assistants assistant = controllerStub.getTurnController().getCurrentPlayer().getAssistantDeck().getDeck().get(0).getName();
        controllerStub.getTurnController().playAssistantCard(assistant);
        controllerStub.getTurnController().checkWin();
        assertEquals(true, controllerStub.getTurnController().checkWin());

        controllerStub.getGame().getGameBoard().getStudentsBag().clear();
        controllerStub.getTurnController().putStudentsOnCloud();
        controllerStub.getTurnController().checkWin();
        assertEquals(true, controllerStub.getTurnController().checkWin());
    }

    @Test
    @DisplayName("Check Win4P Test")
    public void win4PCheck() {
        matteo.setWizard(Wizards.KING);
        cisco.setWizard(Wizards.MONACH);
        server.setIdMapID(idMapID);

        controllerStub.getGame().getActivePlayers().add(matteo);
        controllerStub.getGame().getActivePlayers().add(cisco);
        controllerStub.getGame().getActivePlayers().add(gigiox);
        controllerStub.getGame().getActivePlayers().add(mario);

        controllerStub.getGame().getPlayers().add(matteo);
        controllerStub.getGame().getPlayers().add(cisco);
        controllerStub.getGame().getPlayers().add(gigiox);
        controllerStub.getGame().getPlayers().add(mario);

        matteo.setTeamLeader(true);
        cisco.setTeamLeader(true);
        gigiox.setTeamLeader(false);
        mario.setTeamLeader(false);


        controllerStub.getGame().setPlayersNumber(4);
        controllerStub.getGame().setCurrentPlayer(matteo);

        for (Player p : controllerStub.getGame().getActivePlayers()) {
            p.setBoard(new SchoolBoard(p.getPlayerID()));
        }

        setupGame();

        controllerStub.getTurnController().setCurrentPlayer(matteo);

        Student s1 = new Student(PawnType.RED);
        Student s2 = new Student(PawnType.RED);
        Student s3 = new Student(PawnType.BLUE);
        Student s4 = new Student(PawnType.RED);
        Student s5 = new Student(PawnType.RED);

        controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(s1);
        controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(s3);

        controllerStub.getTurnController().setStudentToMove(s2);
        controllerStub.getTurnController().checkProfessorInfluence();

        System.out.println(controllerStub.getGame().getCurrentPlayer().getBoard().getProfessorTable().getCellByColor(PawnType.RED).hasProfessor());

        controllerStub.getTurnController().setCurrentPlayer(cisco);
        controllerStub.getGame().setCurrentPlayer(cisco);

        controllerStub.getGame().getActivePlayers().get(1).getBoard().getDiningRoom().setStudentToDiningRoom(s4);
        controllerStub.getGame().getActivePlayers().get(1).getBoard().getDiningRoom().setStudentToDiningRoom(s5);
        controllerStub.getTurnController().checkProfessorInfluence();

        //controllerStub.getTurnController().checkIslandInfluence(2);
        controllerStub.getGame().getActivePlayers().get(1).getBoard().getTowerArea().getTowerArea().clear();
        controllerStub.getTurnController().checkWin();
        assertEquals(true, controllerStub.getTurnController().checkWin());
    }

    @Test
    @DisplayName("Check islands Test")
    public void checkIslands() {
        matteo.setWizard(Wizards.KING);
        cisco.setWizard(Wizards.MONACH);
        server.setIdMapID(idMapID);

        controllerStub.getGame().getActivePlayers().add(matteo);
        controllerStub.getGame().getActivePlayers().add(cisco);

        controllerStub.getGame().setPlayersNumber(2);
        controllerStub.getGame().setCurrentPlayer(matteo);

        for(Player p : controllerStub.getGame().getActivePlayers()) {
            p.setBoard(new SchoolBoard(p.getPlayerID()));
        }

        setupGame();

        assertEquals(controllerStub.getGame().getCurrentPlayer().getNickname(), "Matteo");

        controllerStub.getTurnController().setCurrentPlayer(matteo);

        Student s1 = new Student(PawnType.RED);
        Student s2 = new Student(PawnType.RED);
        Student s3 = new Student(PawnType.BLUE);
        Student s4 = new Student(PawnType.RED);

        controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(s1);
        controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(s3);
        controllerStub.getGame().getActivePlayers().get(1).getBoard().getDiningRoom().setStudentToDiningRoom(s2);

        controllerStub.getTurnController().setStudentToMove(s2);
        controllerStub.getTurnController().moveStudentsToDiningRoom(controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom());
        controllerStub.getTurnController().checkProfessorInfluence();
        controllerStub.getTurnController().setStudentToMove(s4);
        controllerStub.getTurnController().checkProfessorInfluence();

        controllerStub.getTurnController().setStudentToMove(s2);
        controllerStub.getTurnController().checkProfessorInfluence();
        controllerStub.getTurnController().setStudentToMove(s3);
        controllerStub.getTurnController().checkProfessorInfluence();

        System.out.println(controllerStub.getGame().getGameBoard().getProfessorByColor(PawnType.BLUE).getOwner().getNickname());
        System.out.println(controllerStub.getGame().getGameBoard().getProfessorByColor(PawnType.RED).getOwner().getNickname());


        assertEquals(controllerStub.getGame().getCurrentPlayer().getBoard().getProfessorTable().getCellByColor(PawnType.RED).hasProfessor(), true);
        assertEquals(controllerStub.getGame().getCurrentPlayer().getBoard().getProfessorTable().getCellByColor(PawnType.BLUE).hasProfessor(), true);

        controllerStub.getGame().getGameBoard().getIslands().get(2).addStudent(new Student(PawnType.RED));
        controllerStub.getGame().getGameBoard().getIslands().get(2).addStudent(new Student(PawnType.RED));
        controllerStub.getGame().getGameBoard().getIslands().get(2).addStudent(new Student(PawnType.RED));
        System.out.println(controllerStub.getGame().getGameBoard().getIslands().get(2).getStudents().size());

        controllerStub.getTurnController().moveMotherNature(1);
        assertEquals(controllerStub.getGame().getGameBoard().getIslands().get(1).hasTower(), true);

        controllerStub.getTurnController().moveMotherNature(2);
        assertEquals(controllerStub.getGame().getGameBoard().getIslands().get(1).getMergedIslands().size(), 1);

        controllerStub.getTurnController().moveMotherNature(8);

        controllerStub.getTurnController().moveMotherNature(3);

        assertEquals(controllerStub.getGame().getGameBoard().getIslands().size(), 10);

        cisco.getBoard().getProfessorTable().getCellByColor(PawnType.GREEN).setProfessor(controllerStub.getGame().getGameBoard().getProfessorByColor(PawnType.GREEN));
        cisco.getBoard().getDiningRoom().getDiningRoom().get(PawnType.GREEN.getPawnID()).addStudent(new Student(PawnType.GREEN));
        cisco.getBoard().getDiningRoom().getDiningRoom().get(PawnType.GREEN.getPawnID()).addStudent(new Student(PawnType.GREEN));
        controllerStub.getGame().getGameBoard().getProfessorByColor(PawnType.GREEN).setOwner(cisco);
        controllerStub.getGame().getGameBoard().getIslands().get(8).addStudent(new Student(PawnType.RED));
        controllerStub.getGame().getGameBoard().getIslands().get(8).setTower(new Tower(TowerColor.WHITE));
        controllerStub.getGame().getGameBoard().getIslands().get(7).addStudent(new Student(PawnType.RED));
        controllerStub.getTurnController().moveMotherNature(7);
    }

    @Test
    @DisplayName("Action Phase Test with PropertyChange")
    public void actionWithPropertyChange() {
        matteo.setWizard(Wizards.KING);
        cisco.setWizard(Wizards.MONACH);
        server.setIdMapID(idMapID);

        controllerStub.getGame().getPlayers().add(matteo);
        controllerStub.getGame().getPlayers().add(cisco);

        controllerStub.getGame().getActivePlayers().add(matteo);
        controllerStub.getGame().getActivePlayers().add(cisco);

        controllerStub.getGame().setPlayersNumber(2);
        controllerStub.getGame().setCurrentPlayer(matteo);

        for(Player p : controllerStub.getGame().getActivePlayers()) {
            p.setBoard(new SchoolBoard(p.getPlayerID()));
        }

        setupGame();

        assertEquals(controllerStub.getGame().getCurrentPlayer().getNickname(), "Matteo");

        controllerStub.getTurnController().setCurrentPlayer(matteo);

        controllerStub.getTurnController().startPianificationPhase();
        PropertyChangeEvent ev1 = new PropertyChangeEvent(1, "PickAssistant", null, controllerStub.getGame().getCurrentPlayer().getAssistantDeck().getDeck().get(7).getName());
        controllerStub.propertyChange(ev1);
        PropertyChangeEvent ev2 = new PropertyChangeEvent(1, "PickAssistant", null, controllerStub.getGame().getCurrentPlayer().getAssistantDeck().getDeck().get(7).getName());
        controllerStub.propertyChange(ev2);
        PropertyChangeEvent ev21 = new PropertyChangeEvent(1, "PickAssistant", null, controllerStub.getGame().getCurrentPlayer().getAssistantDeck().getDeck().get(9).getName());
        controllerStub.propertyChange(ev21);
        PropertyChangeEvent ev3 = new PropertyChangeEvent(1, "PickStudent", null, controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().get(0));
        controllerStub.propertyChange(ev3);
        PropertyChangeEvent ev4 = new PropertyChangeEvent(1, "PickDestinationDiningRoom", null, controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom());
        controllerStub.propertyChange(ev4);
        PropertyChangeEvent ev5 = new PropertyChangeEvent(1, "PickStudent", null, controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().get(0));
        controllerStub.propertyChange(ev5);
        PropertyChangeEvent ev6 = new PropertyChangeEvent(1, "PickDestinationIsland", null, controllerStub.getGame().getGameBoard().getIslands().get(0));
        controllerStub.propertyChange(ev6);
        PropertyChangeEvent ev7 = new PropertyChangeEvent(1, "PickStudent", null, controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().get(0));
        controllerStub.propertyChange(ev7);
        PropertyChangeEvent ev8 = new PropertyChangeEvent(1, "PickDestinationDiningRoom", null, controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom());
        controllerStub.propertyChange(ev8);
        PropertyChangeEvent ev9 = new PropertyChangeEvent(1, "PickMovesNumber", null, 1);
        controllerStub.propertyChange(ev9);
        PropertyChangeEvent ev10 = new PropertyChangeEvent(1, "PickCloud", null, controllerStub.getGame().getGameBoard().getClouds().get(0));
        controllerStub.propertyChange(ev10);

        assertEquals(controllerStub.getGame().getActivePlayers().get(0).getBoard().getEntrance().getStudents().size(), 7);
        for(int i=0; i< controllerStub.getGame().getGameBoard().getIslands().size(); i++) {
            System.out.println("Island " + controllerStub.getGame().getGameBoard().getIslands().get(i).getIslandID() + " has tower: " + controllerStub.getGame().getGameBoard().getIslands().get(i).hasTower());
            if(controllerStub.getGame().getGameBoard().getIslands().get(i).hasTower()) System.out.println(controllerStub.getGame().getGameBoard().getIslands().get(i).getTower().getColor().toString());
        }

        PropertyChangeEvent ev11 = new PropertyChangeEvent(1, "PickStudent", null, controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().get(0));
        controllerStub.propertyChange(ev11);
        PropertyChangeEvent ev12 = new PropertyChangeEvent(1, "PickDestinationDiningRoom", null, controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom());
        controllerStub.propertyChange(ev12);
        PropertyChangeEvent ev13 = new PropertyChangeEvent(1, "PickStudent", null, controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().get(0));
        controllerStub.propertyChange(ev13);
        PropertyChangeEvent ev14 = new PropertyChangeEvent(1, "PickDestinationIsland", null, controllerStub.getGame().getGameBoard().getIslands().get(0));
        controllerStub.propertyChange(ev14);
        PropertyChangeEvent ev15 = new PropertyChangeEvent(1, "PickStudent", null, controllerStub.getGame().getCurrentPlayer().getBoard().getEntrance().getStudents().get(0));
        controllerStub.propertyChange(ev15);
        PropertyChangeEvent ev16 = new PropertyChangeEvent(1, "PickDestinationDiningRoom", null, controllerStub.getGame().getCurrentPlayer().getBoard().getDiningRoom());
        controllerStub.propertyChange(ev16);
        PropertyChangeEvent ev17 = new PropertyChangeEvent(1, "PickMovesNumber", null, 1);
        controllerStub.propertyChange(ev17);
        PropertyChangeEvent ev18 = new PropertyChangeEvent(1, "PickCloud", null, controllerStub.getGame().getGameBoard().getClouds().get(1));
        controllerStub.propertyChange(ev18);

    }

    @Test
    @DisplayName("Change Island Influence Test")
    public void changeIslandInfluence() {
        matteo.setWizard(Wizards.KING);
        cisco.setWizard(Wizards.MONACH);
        server.setIdMapID(idMapID);

        controllerStub.getGame().getActivePlayers().add(matteo);
        controllerStub.getGame().getActivePlayers().add(cisco);

        controllerStub.getGame().setPlayersNumber(2);
        controllerStub.getGame().setCurrentPlayer(matteo);

        for(Player p : controllerStub.getGame().getActivePlayers()) {
            p.setBoard(new SchoolBoard(p.getPlayerID()));
        }

        setupGame();
        assertEquals(controllerStub.getGame().getCurrentPlayer().getNickname(), "Matteo");

        controllerStub.getTurnController().setCurrentPlayer(matteo);


        for(Student t : controllerStub.getTurnController().getCurrentPlayer().getBoard().getEntrance().getStudents()) {
            System.out.println(t.getType());
        }

        assertEquals(controllerStub.getGame().getCurrentPlayer().getNickname(), "Matteo");

        cisco.getBoard().getProfessorTable().getCellByColor(PawnType.RED).setProfessor(controllerStub.getGame().getGameBoard().getProfessorByColor(PawnType.RED));
        controllerStub.getGame().getGameBoard().getProfessorByColor(PawnType.RED).setOwner(cisco);
        cisco.getBoard().getDiningRoom().getDiningRoom().get(PawnType.RED.getPawnID()).addStudent(new Student(PawnType.RED));
        controllerStub.getGame().getGameBoard().getIslands().get(1).addStudent(new Student(PawnType.RED));

        controllerStub.getTurnController().checkIslandInfluence(2);

        System.err.println(controllerStub.getGame().getGameBoard().getIslands().get(1).getMergedTowers().get(0).getColor().toString());
        System.out.println(controllerStub.getGame().getGameBoard().getIslands().get(1).getStudents().size());

        matteo.getBoard().getDiningRoom().setStudentToDiningRoom(new Student(PawnType.RED));
        matteo.getBoard().getDiningRoom().setStudentToDiningRoom(new Student(PawnType.RED));
        matteo.getBoard().getDiningRoom().setStudentToDiningRoom(new Student(PawnType.RED));
        controllerStub.getTurnController().setStudentToMove(new Student(PawnType.RED));
        controllerStub.getTurnController().checkProfessorInfluence();
        controllerStub.getTurnController().moveMotherNature(1);

        System.out.println("Island 1 has: " + controllerStub.getGame().getGameBoard().getIslands().get(1).getMergedTowers().size() + " towers di colore " + controllerStub.getGame().getGameBoard().getIslands().get(1).getMergedTowers().get(0).getColor().toString());
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

        if(controllerStub.getGame().getActivePlayers().size() == 3) {
            towersNumber = 6;
            colorsCounter3P = 0;
        } else if(controllerStub.getGame().getActivePlayers().size() == 2) {
            towersNumber = 8;
            colorsCounter2P = 0;
        } else if(controllerStub.getGame().getActivePlayers().size() == 4) {
            towersNumber = 8;
            colorsCounter4P = 0;
        }

        for(Player p : controllerStub.getGame().getActivePlayers()) {
            for(int i = 1; i <= studentsNumber; i++){
                Collections.shuffle(controllerStub.getGame().getGameBoard().getStudentsBag());
                p.getBoard().getEntrance().getStudents().add(controllerStub.getGame().getGameBoard().getStudentsBag().get(0));
                controllerStub.getGame().getGameBoard().removeStudents(0);
            }

            if(controllerStub.getGame().getPlayersNumber() == 3) {
                for(int i = 1; i <= towersNumber; i++) {
                    p.getBoard().getTowerArea().addTowers(new Tower(allTowerColors.get(colorsCounter3P)));
                }
                if(colorsCounter3P < 2) {
                    colorsCounter3P++;
                }
            } else if(controllerStub.getGame().getPlayersNumber() == 2) {
                for(int k = 1; k <= towersNumber; k++) {
                    p.getBoard().getTowerArea().addTowers(new Tower(allTowerColors.get(colorsCounter2P)));
                }
                if(colorsCounter2P < 2) {
                    colorsCounter2P++;
                }
            } else if(controllerStub.getGame().getPlayersNumber() == 4) {
                if((p.getIdTeam() == 1 && p.isTeamLeader()) || (p.getIdTeam() == 2 && p.isTeamLeader())) {
                    for(int l=1; l<= towersNumber; l++) {
                        p.getBoard().getTowerArea().addTowers(new Tower(allTowerColors.get(colorsCounter4P)));
                    }
                    if(colorsCounter4P < 3) colorsCounter4P++;
                }
            }

        }

        int maximum = 11;
        SecureRandom r = new SecureRandom();
        controllerStub.getGame().getGameBoard().getMotherNature().setPosition(1);
        int mnPos = controllerStub.getGame().getGameBoard().getMotherNature().getPosition();

        int mnPosOpposite = -1;
        if(mnPos <= 6) {
            mnPosOpposite = mnPos + 6;
        } else {
            mnPosOpposite = (mnPos + 6) % 12;
        }

        controllerStub.getGame().getGameBoard().getIslands().get(1).addStudent(new Student(PawnType.RED));
        controllerStub.getGame().getGameBoard().getIslands().get(2).addStudent(new Student(PawnType.RED));
        controllerStub.getGame().getGameBoard().getIslands().get(3).addStudent(new Student(PawnType.BLUE));
        controllerStub.getGame().getGameBoard().getIslands().get(4).addStudent(new Student(PawnType.BLUE));
        controllerStub.getGame().getGameBoard().getIslands().get(5).addStudent(new Student(PawnType.YELLOW));
        controllerStub.getGame().getGameBoard().getIslands().get(7).addStudent(new Student(PawnType.YELLOW));
        controllerStub.getGame().getGameBoard().getIslands().get(8).addStudent(new Student(PawnType.PINK));
        controllerStub.getGame().getGameBoard().getIslands().get(9).addStudent(new Student(PawnType.PINK));
        controllerStub.getGame().getGameBoard().getIslands().get(10).addStudent(new Student(PawnType.GREEN));
        controllerStub.getGame().getGameBoard().getIslands().get(11).addStudent(new Student(PawnType.GREEN));

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

    public class GameHandlerStub extends GameHandler {
    public GameHandlerStub(Server server) {
            super(server);
        }

        ControllerStub controllerStub;
        @Override
        public void sendSinglePlayer(Answer message, int id) {
            String print;
            if (message.getMessage() == null) {
                print = "Start/End/Move/SelectBuild/BuildSelectMove action";
            } else print = message.getMessage().toString();
            System.out.println(print);
        }

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

        @Override
        public void sendBroadcast(Answer message) {
            System.out.println(message.getMessage());
        }

        @Override
        public void sendExcept(Answer serverAnswer, int id) {
            System.out.println(serverAnswer.getMessage());
        }
    }
    public static class ControllerStub extends Controller {
        public ControllerStub(Game game, GameHandler gameHandler) {
            super(game, gameHandler);
        }
    }

    public static class ServerStub extends Server {
        private HashMap<Integer, VirtualClientView> idMapID;

        public ServerStub() {
            this.idMapID = null;
        }

        public void setIdMapID(HashMap idMapID) {
            this.idMapID = idMapID;
        }

        @Override
        public VirtualClientView getVirtualClientFromID(int id) {
            return idMapID.get(id);
        }
    }

    public static class PlayerStub extends Player {
        public PlayerStub(String nickname, int clientID) {
            super(nickname, clientID);
        }
    }

    public static class SocketClientConnectionStub extends SocketClientConnection {
        public SocketClientConnectionStub(Socket socket, Server server) {
            super(socket, server);
        }
        @Override
        public void closeConnection() {
            System.out.println("Connection closed to client");
        }
    }
}
