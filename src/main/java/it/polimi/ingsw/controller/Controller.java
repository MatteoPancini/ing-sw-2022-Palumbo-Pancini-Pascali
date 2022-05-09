package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.enumerations.Wizards;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.SchoolBoard;
import it.polimi.ingsw.model.player.Tower;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


//TODO M: aggiungi PropertyChange

public class Controller implements PropertyChangeListener {
    private final Game game;
    private final GameHandler gameHandler;
    private final TurnController turnController;


    public Controller(Game game, GameHandler gameHandler) {
        this.game = game;
        this.gameHandler = gameHandler;
        turnController = new TurnController(this, gameHandler);
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

        turnController.pianificationPhase();

    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch(evt.getPropertyName()) {
            case "PickAssistant" -> turnController.playAssistantCard();


            default -> System.err.println("Unrecognized message!");
        }
    }

}


