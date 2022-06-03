package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.servertoclient.Answer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.GameBoard;
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
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.server.VirtualClientView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        controllerStub.getGame().setPlayersNumber(3);
        controllerStub.getGame().setCurrentPlayer(matteo);

        assertEquals(controllerStub.getGame().getCurrentPlayer().getNickname(), "Matteo");

        controllerStub.getTurnController().setCurrentPlayer(matteo);

        assertEquals(controllerStub.getGame().getCurrentPlayer().getNickname(), "Matteo");
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

        controllerStub.getGame().setPlayersNumber(4);
        assertEquals(gameHandlerStub.getController(), controllerStub);

        assertEquals(gameHandlerStub.getController(), controllerStub);
        assertEquals(gameHandlerStub.getController().getGame().getActivePlayers().size(), 4);
        gameHandlerStub.setTeamMode(true);
        gameHandlerStub.setupTeams();

        for(Player p : controllerStub.getGame().getActivePlayers()) {
            System.out.println(p.getNickname() + " " + p.getIdTeam() + " " + p.isTeamLeader());
        }

        assertEquals(controllerStub.getGame().getActivePlayers().get(0).getIdTeam(), controllerStub.getGame().getActivePlayers().get(2).getIdTeam());
        assertEquals(controllerStub.getGame().getActivePlayers().get(1).getIdTeam(), controllerStub.getGame().getActivePlayers().get(3).getIdTeam());

        assertEquals(controllerStub.getGame().getActivePlayers().get(0).isTeamLeader(), true);
        assertEquals(controllerStub.getGame().getActivePlayers().get(1).isTeamLeader(), true);
        assertEquals(controllerStub.getGame().getActivePlayers().get(2).isTeamLeader(), false);
        assertEquals(controllerStub.getGame().getActivePlayers().get(3).isTeamLeader(), false);

        assertEquals(controllerStub.getGame().getActivePlayers().get(0).getBoard(), controllerStub.getGame().getActivePlayers().get(2).getBoard());
        assertEquals(controllerStub.getGame().getActivePlayers().get(1).getBoard(), controllerStub.getGame().getActivePlayers().get(3).getBoard());
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
        //assertEquals(controllerStub.getGame().getGameBoard().getIslandById(2).getStudents().size(), 2);

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

        System.out.println("muvo mn");
        //Qui sposto in island 2
        controllerStub.getTurnController().moveMotherNature(1);
        assertEquals(controllerStub.getGame().getGameBoard().getIslands().get(1).hasTower(), true);
        //Qui sposto in island 3
        controllerStub.getTurnController().moveMotherNature(1);
        assertEquals(controllerStub.getGame().getGameBoard().getIslands().get(1).getMergedIslands().size(), 2);

        //Qui sposto in island 4
        controllerStub.getTurnController().moveMotherNature(1);
        assertEquals(controllerStub.getGame().getGameBoard().getIslands().get(1).getMergedIslands().size(), 3);
        assertEquals(controllerStub.getGame().getGameBoard().getIslands().get(1).getMergedTowers().size(), 3);




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
        PropertyChangeEvent ev2 = new PropertyChangeEvent(1, "PickAssistant", null, controllerStub.getGame().getCurrentPlayer().getAssistantDeck().getDeck().get(9).getName());
        controllerStub.propertyChange(ev2);
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
            //System.out.println("Inizio setup di " + p.getNickname());

            //System.out.println("metto students nell'entrance");
            for(int i = 1; i <= studentsNumber; i++){
                Collections.shuffle(controllerStub.getGame().getGameBoard().getStudentsBag());
                p.getBoard().getEntrance().getStudents().add(controllerStub.getGame().getGameBoard().getStudentsBag().get(0));
                controllerStub.getGame().getGameBoard().removeStudents(0);
            }

            System.out.println("metto torri");
            if(controllerStub.getGame().getPlayersNumber() == 3) {
                for(int i = 1; i <= towersNumber; i++) {
                    p.getBoard().getTowerArea().addTowers(new Tower(allTowerColors.get(colorsCounter3P)));
                    if(colorsCounter3P < 2) {
                        colorsCounter3P++;
                        System.out.println("change");

                    }
                }
            } else if(controllerStub.getGame().getPlayersNumber() == 2) {
                System.out.println("entro");
                for(int k = 1; k <= towersNumber; k++) {
                    p.getBoard().getTowerArea().addTowers(new Tower(allTowerColors.get(colorsCounter2P)));
                    if(k == towersNumber) {
                        System.out.println("change");
                        colorsCounter2P++;
                    }
                }
            } else if(controllerStub.getGame().getPlayersNumber() == 4) {
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


        /*
        for(int s = 1; s <= 12; s++) {
            if(s != mnPos && s != mnPosOpposite) {
                //pos = (game.getGameBoard().getMotherNature().getPosition() + s) % 12;

                Collections.shuffle(controllerStub.getGame().getGameBoard().getSetupStudentsBag());
                controllerStub.getGame().getGameBoard().getIslands().get(s - 1).addStudent(controllerStub.getGame().getGameBoard().getSetupStudentsBag().get(0));
                controllerStub.getGame().getGameBoard().removeSetupStudents(0);
            }
            //n++;
        }


         */
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
                print = "Start/End/Move/SelectBuild/BuildSelectMove action";
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
