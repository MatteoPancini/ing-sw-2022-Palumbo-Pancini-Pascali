package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.player.DiningRoom;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.SchoolBoard;
import it.polimi.ingsw.model.player.Tower;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.security.SecureRandom;
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
            turnController.setExpertController(expertController);
        } else
            expertController = null;
    }

    public Game getGame() {
        return game;
    }

    public ExpertController getExpertController() {
        return expertController;
    }

    public void setPlayerWizard(int playerID, Wizards chosenWizard) {
        game.getPlayerByID(playerID).setWizard(chosenWizard);
    }

    public TurnController getTurnController() {
        return turnController;
    }

    public void newSetupGame() {
        System.out.println("Starting setupGame");

        int studentsNumber;
        if(game.getPlayersNumber() == 3) {
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

        if(game.getPlayersNumber() == 3) {
            towersNumber = 6;
            colorsCounter3P = 0;
        } else if(game.getPlayersNumber() == 2) {
            towersNumber = 8;
            colorsCounter2P = 0;
        } else if(game.getPlayersNumber() == 4) {
            towersNumber = 8;
            colorsCounter4P = 0;
        }


        for(Player p : game.getPlayers()) {
            //System.out.println("Inizio setup di " + p.getNickname());

            //System.out.println("metto students nell'entrance");
            for(int i = 1; i <= studentsNumber; i++){
                Collections.shuffle(game.getGameBoard().getStudentsBag());
                p.getBoard().getEntrance().getStudents().add(game.getGameBoard().getStudentsBag().get(0));
                game.getGameBoard().removeStudents(0);
            }

            //System.out.println("metto torri");
            if(game.getPlayersNumber() == 3) {
                for(int i = 1; i <= towersNumber; i++) {
                    p.getBoard().getTowerArea().addTowers(new Tower(allTowerColors.get(colorsCounter3P)));
                }
                colorsCounter3P++;
            } else if(game.getPlayersNumber() == 2) {
                for(int k = 1; k <= towersNumber; k++) {
                    p.getBoard().getTowerArea().addTowers(new Tower(allTowerColors.get(colorsCounter2P)));
                }
                colorsCounter2P++;
            } else if(game.getPlayersNumber() == 4) {
                if((p.getIdTeam() == 1 && p.isTeamLeader()) || (p.getIdTeam() == 2 && p.isTeamLeader())) {
                    p.getBoard().getTowerArea().addTowers(new Tower(allTowerColors.get(colorsCounter4P)));
                    colorsCounter4P++;
                }
            }
        }

        //System.out.println("metto madre natura");

        int maximum = 11;
        SecureRandom r = new SecureRandom();
        game.getGameBoard().getMotherNature().setPosition(r.nextInt(maximum) + 1);
        //int n = 1;
        int mnPos = game.getGameBoard().getMotherNature().getPosition();

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
                Collections.shuffle(game.getGameBoard().getSetupStudentsBag());
                game.getGameBoard().getIslands().get(s - 1).addStudent(game.getGameBoard().getSetupStudentsBag().get(0));
                game.getGameBoard().removeSetupStudents(0);
            }
            //n++;
        }

        for(int p = 1; p <= 12; p++){
            if(p != mnPos && p != mnPosOpposite) {
                System.out.println(p + ", " + "Student " + game.getGameBoard().getIslands().get(p - 1).getStudents().get(0).getType());
            }

            if(p == mnPos){
                System.out.println(p + ", " + "Mother nature is here");
            }

            if(p == mnPosOpposite){
                System.out.println(p + ", " + "This island is empty");
            }
        }

        System.out.println("Finished setupGame");
        turnController.setCurrentPlayer(game.getCurrentPlayer());

        turnController.startPianificationPhase();
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("propertyChange del controller" + evt.getPropertyName());

        switch(evt.getPropertyName()) {
            case "PickAssistant" -> {
                turnController.playAssistantCard((Assistants) evt.getNewValue());
            }

            case "PickStudent" -> {
                turnController.setStudentToMove((Student) evt.getNewValue());
                turnController.askStudentDestination();
            }

            case "PickDestinationDiningRoom" -> turnController.moveStudentsToDiningRoom((DiningRoom) evt.getNewValue());

            case "PickDestinationIsland" -> turnController.moveStudentToIsland((Island) evt.getNewValue());

            case "PickMovesNumber" -> turnController.moveMotherNature((Integer) evt.getNewValue());

            case "PickCloud" -> turnController.fromCloudToEntrance((CloudTile) evt.getNewValue());


            case "PickCharacter" -> {

                if(evt.getNewValue() == Characters.HERALD) expertController.heraldEffect();
                else if(evt.getNewValue() == Characters.KNIGHT) expertController.knightEffect();
                else if(evt.getNewValue() == Characters.CENTAUR) expertController.centaurEffect();
                else if(evt.getNewValue() == Characters.FARMER) expertController.farmerEffect();
                else if(evt.getNewValue() == Characters.FUNGARUS) expertController.fungarusEffect();
                else if(evt.getNewValue() == Characters.JESTER) expertController.jesterEffect();
                else if(evt.getNewValue() == Characters.THIEF) expertController.thiefEffect();
                else if(evt.getNewValue() == Characters.MINESTREL) expertController.minestrelEffect();
                else if(evt.getNewValue() == Characters.MONK) expertController.monkEffect();
                else if(evt.getNewValue() == Characters.GRANNY_HERBS) expertController.grannyHerbsEffect();
                else if(evt.getNewValue() == Characters.MAGIC_POSTMAN) expertController.magicPostmanEffect();
                else if(evt.getNewValue() == Characters.SPOILED_PRINCESS) expertController.spoiledPrincessEffect();
                else if(evt.getNewValue() == null) turnController.askMotherNatureMoves();
            }

            case "GrannyHerbsTile" -> {
                expertController.setGrannyHerbsTile((Island) evt.getNewValue());
            }

            case "PickPawnType" -> {
                expertController.setPawnTypeChosen((PawnType) evt.getNewValue());
                expertController.activeThiefEffect();
            }

            case "CheckInfluence" -> {
                int islandId = ((Island) evt.getNewValue()).getIslandID();
                turnController.checkIslandInfluence(islandId);
            }


            default -> System.err.println("Unrecognized message!");
        }
    }

}