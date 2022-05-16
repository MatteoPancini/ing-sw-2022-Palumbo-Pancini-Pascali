package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.enumerations.Wizards;
import it.polimi.ingsw.model.player.DiningRoom;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.SchoolBoard;
import it.polimi.ingsw.model.player.Tower;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;



public class Controller implements PropertyChangeListener {
    private final Game game;
    private final GameHandler gameHandler;
    private final TurnController turnController;
    private final ExpertController expertController;


    public Controller(Game game, GameHandler gameHandler) {
        this.game = game;
        this.gameHandler = gameHandler;
        turnController = new TurnController(this, gameHandler);
        if(gameHandler.getExpertMode()) {
            expertController = new ExpertController(game, game.getGameBoard(), turnController);
        } else
            expertController = null;
    }


    public void setPlayerWizard(int playerID, Wizards chosenWizard) {
        game.getPlayerByID(playerID).setWizard(chosenWizard);
    }


    public void setupGame() {
        ArrayList<SchoolBoard> schoolBoards = new ArrayList<SchoolBoard>();
        for(int p = 1; p <= game.getPlayersNumber(); p++){
            SchoolBoard newSchoolBoard = new SchoolBoard(p);

            schoolBoards.add(newSchoolBoard);
            game.getPlayers().get(p - 1).setBoard(newSchoolBoard);
            game.getPlayers().get(p - 1).setIdPlayer(p);
        }


        Collections.shuffle(game.getPlayers());
        game.setCurrentPlayer(game.getPlayers().get(0));

        turnController.putStudentsOnCloud();

        int studentsNumber;
        if(game.getPlayersNumber() == 3) {
            studentsNumber = 9;
        }
        else {
            studentsNumber = 7;
        }

        for(SchoolBoard s : schoolBoards){
            for(int i = 1; i <= studentsNumber; i++){
                Collections.shuffle(game.getGameBoard().getStudentsBag());
                s.getEntrance().getStudents().add(game.getGameBoard().getStudentsBag().get(0));
                game.getGameBoard().removeStudents(0);
            }
        }

        int towersNumber;
        ArrayList<TowerColor> allColors = new ArrayList<TowerColor>();
        allColors.add(TowerColor.WHITE);
        allColors.add(TowerColor.BLACK);
        allColors.add(TowerColor.GREY);

        if(game.getPlayersNumber() == 3) {
            towersNumber = 6;
            int colorsCounter3P = 0;
            for(SchoolBoard s : schoolBoards){
                for(int i = 1; i <= towersNumber; i++) {
                    s.getTowerArea().addTowers(new Tower(allColors.get(colorsCounter3P)));
                }
                colorsCounter3P++;
            }
        }

        if(game.getPlayersNumber() == 2) {
            towersNumber = 8;
            int colorsCounter2P = 0;
            for(SchoolBoard s : schoolBoards){
                for(int k = 1; k <= towersNumber; k++) {
                    s.getTowerArea().addTowers(new Tower(allColors.get(colorsCounter2P)));
                }
                colorsCounter2P++;
            }
        }

        if(game.getPlayersNumber() == 4){
            towersNumber = 8;
            int colorsCounter4P = 0;
            for(Player p : game.getPlayers()){
                if((p.getIdTeam() == 1 && p.isTeamLeader()) || (p.getIdTeam() == 2 && p.isTeamLeader())) {
                    p.getBoard().getTowerArea().addTowers(new Tower(allColors.get(colorsCounter4P)));
                    colorsCounter4P++;
                }
            }
        }

        int maximum = 11;
        Random r = new Random();
        game.getGameBoard().getMotherNature().setPosition(r.nextInt(maximum) + 1);
        int n = 1;
        for(int s = 1; s <= 11; s++){
            if(n != 6){
                int pos;
                pos = (game.getGameBoard().getMotherNature().getPosition() + s) % 12;
                Collections.shuffle(game.getGameBoard().getStudentsBag());
                game.getGameBoard().getIslands().get(pos - 1).addStudent(game.getGameBoard().getStudentsBag().get(0));;
                game.getGameBoard().removeStudents(0);
            }
            n++;
        }

        turnController.startPianificationPhase();

    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch(evt.getPropertyName()) {
            case "PickAssistant" -> turnController.playAssistantCard((AssistantCard) evt.getNewValue());

            case "PickStudent" -> {
                turnController.setStudentToMove((Student) evt.getNewValue());
                turnController.askStudentDestination();
            }

            case "PickDestinationDiningRoom" -> turnController.moveStudentsToDiningRoom((DiningRoom) evt.getNewValue());

            case "PickDestinationIsland" -> turnController.moveStudentToIsland((Island) evt.getNewValue());


            case "PickMovesNumber" -> turnController.moveMotherNature((Integer) evt.getNewValue());

            case "PickCloud" -> turnController.fromCloudToEntrance((CloudTile) evt.getNewValue());


            case "PickCharacter" -> {
                //TODO GIGIOX -> QUI BISOGNA CAPIRE CHIAMARE I METODI DI "ActionController" che fanno le azioni dei personaggi
                /*
                if(evt.getNewValue() instanceof Centaur) actionController.xxx;
                else if(evt.getNewValue() instanceof Farmer) actionController.xxx;
                else if(evt.getNewValue() instanceof Fungarus) actionController.xxx;
                else if(evt.getNewValue() instanceof GrannyHerbs) actionController.xxx;
                else if(evt.getNewValue() instanceof Herald) actionController.xxx;
                else if(evt.getNewValue() instanceof Jester) actionController.xxx;
                else if(evt.getNewValue() instanceof Knight) actionController.xxx;
                else if(evt.getNewValue() instanceof MagicPostman) actionController.xxx;
                else if(evt.getNewValue() instanceof Minestrel) actionController.xxx;
                else if(evt.getNewValue() instanceof Monk) actionController.xxx;
                else if(evt.getNewValue() instanceof SpoiledPrincess) actionController.xxx;
                else if(evt.getNewValue() instanceof Thief) actionController.xxx;


                 */
            }

            case "CheckInfluence" -> {
                int islandId = ((Island) evt.getNewValue()).getIslandID();
                turnController.checkIslandInfluence(islandId);
            }


            default -> System.err.println("Unrecognized message!");
        }
    }

}


