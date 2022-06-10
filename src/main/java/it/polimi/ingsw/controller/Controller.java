package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.servertoclient.DynamicAnswer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.player.DiningRoom;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tower;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

public class Controller implements PropertyChangeListener {
    private final Game game;
    private final GameHandler gameHandler;
    private final TurnController turnController;
    private ExpertController expertController;

    public Controller(Game game, GameHandler gameHandler) {
        this.game = game;
        this.gameHandler = gameHandler;
        turnController = new TurnController(this, gameHandler);
        /*
        if(game.isExpertMode()) {
            System.out.println("Setto EXPERT MODE");
            expertController = new ExpertController(game, game.getGameBoard(), turnController);
            turnController.setExpertController(expertController);
        } else {
            System.out.println("NON SETTO EXPERT MODE");
            expertController = null;
            turnController.setExpertController(null);
        }

         */
    }


    public void setExpertController(ExpertController expertController) {
        this.expertController = expertController;
        this.turnController.setExpertController(expertController);
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
        System.out.println(game.getPlayersNumber());
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
            /*
            if(game.getPlayersNumber() == 4) {
                if(p.isTeamLeader()) {
                    System.err.println(p.getNickname());
                    for(int i = 1; i <= studentsNumber; i++) {
                        Collections.shuffle(game.getGameBoard().getStudentsBag());
                        p.getBoard().getEntrance().getStudents().add(game.getGameBoard().getStudentsBag().get(0));
                        game.getGameBoard().removeStudents(0);
                    }
                }
            } else {
                for(int i = 1; i <= studentsNumber; i++) {
                    Collections.shuffle(game.getGameBoard().getStudentsBag());
                    p.getBoard().getEntrance().getStudents().add(game.getGameBoard().getStudentsBag().get(0));
                    game.getGameBoard().removeStudents(0);
                }

             */
            for(int i = 1; i <= studentsNumber; i++) {
                Collections.shuffle(game.getGameBoard().getStudentsBag());
                p.getBoard().getEntrance().getStudents().add(game.getGameBoard().getStudentsBag().get(0));
                game.getGameBoard().removeStudents(0);
            }




            System.out.println("metto torri");
            if(game.getPlayersNumber() == 3) {
                for(int i = 1; i <= towersNumber; i++) {
                    p.getBoard().getTowerArea().addTowers(new Tower(allTowerColors.get(colorsCounter3P)));
                }
                if(colorsCounter3P < 2) {
                    colorsCounter3P++;
                    System.out.println("change");
                }
            } else if(game.getPlayersNumber() == 2) {
                System.out.println("entro");
                for(int k = 1; k <= towersNumber; k++) {
                    p.getBoard().getTowerArea().addTowers(new Tower(allTowerColors.get(colorsCounter2P)));
                }
                if(colorsCounter2P < 2) {
                    System.out.println("change");
                    colorsCounter2P++;
                }
            } else if(game.getPlayersNumber() == 4) {
                System.out.println("Entro QUI");
                if((p.getIdTeam() == 1 && p.isTeamLeader()) || (p.getIdTeam() == 2 && p.isTeamLeader())) {
                    for(int l=1; l<= towersNumber; l++) {
                        p.getBoard().getTowerArea().addTowers(new Tower(allTowerColors.get(colorsCounter4P)));
                    }
                    if(colorsCounter4P < 3) colorsCounter4P++;
                }
            }


            if(game.isExpertMode()) {
                p.setMyCoins(1);
            }
        }

        for(Player q : game.getPlayers()) {
            System.out.println("Player " + q.getNickname() + " has students:");
            for(Student s : q.getBoard().getEntrance().getStudents()) {
                System.out.println(s.getType().toString());
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

        gameHandler.sendBroadcast(new DynamicAnswer("REMEMBER: During your turn type \"QUIT\" to quit the game!\n", false));
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
                if(expertController == null) {
                    turnController.setStudentToMove((Student) evt.getNewValue());
                    turnController.askStudentDestination();
                } else {
                    expertController.setStudentChosen((Student) evt.getNewValue());
                    if (expertController.isMonkEffect()) {
                        expertController.activeMonkEffect();
                    } else if(expertController.isSpoiledPrincessEffect()) {
                        expertController.activeSpoiledPrincessEffect();
                    } else if(expertController.isJesterEffect()) {
                        expertController.setJesterReqNum(expertController.getJesterReqNum() - 1);
                        if(expertController.getJesterReqNum() % 2 == 1) {
                            expertController.setStudentOne((Student) evt.getNewValue());
                        } else if(expertController.getJesterReqNum() % 2 == 0) {
                            expertController.setStudentTwo((Student) evt.getNewValue());
                        }
                        expertController.activeJesterEffect();
                    } else if(expertController.isMinestrelEffect()) {
                        expertController.setMinestrelReqNum(expertController.getMinestrelReqNum() - 1);
                        if(expertController.getMinestrelReqNum() % 2 == 1) {
                            expertController.setStudentOne((Student) evt.getNewValue());
                        } else if (expertController.getMinestrelReqNum() % 2 == 0) {
                            expertController.setStudentTwo((Student) evt.getNewValue());
                        }
                        expertController.activeMinestrelEffect();
                    } else {
                        turnController.setStudentToMove((Student) evt.getNewValue());
                        turnController.askStudentDestination();
                    }
                }

            }

            case "PickDestinationDiningRoom" -> turnController.moveStudentsToDiningRoom((DiningRoom) evt.getNewValue());

            case "PickDestinationIsland" -> turnController.moveStudentToIsland((Island) evt.getNewValue());

            case "PickMovesNumber" -> turnController.moveMotherNature((Integer) evt.getNewValue());

            case "PickCloud" -> turnController.fromCloudToEntrance((CloudTile) evt.getNewValue());

            case "PickCharacter" -> {
                System.out.println(evt.getNewValue());
                for(CharacterCard c : game.getGameBoard().getPlayableCharacters()) {
                    if(c.getName() == evt.getNewValue()) {
                        c.incrementPrice();
                        break;
                    }
                }
                if(evt.getNewValue() == Characters.HERALD) {
                    expertController.heraldEffect();

                    game.getCurrentPlayer().setMyCoins(game.getCurrentPlayer().getMyCoins() - 3);
                }
                else if(evt.getNewValue() == Characters.KNIGHT) {
                    expertController.knightEffect();
                    game.getCurrentPlayer().setMyCoins(game.getCurrentPlayer().getMyCoins() - 2);

                }
                else if(evt.getNewValue() == Characters.CENTAUR) {
                    System.out.println("Entro qui");
                    expertController.centaurEffect();
                    game.getCurrentPlayer().setMyCoins(game.getCurrentPlayer().getMyCoins() - 3);

                }
                else if(evt.getNewValue() == Characters.FARMER) {
                    expertController.farmerEffect();
                    game.getCurrentPlayer().setMyCoins(game.getCurrentPlayer().getMyCoins() - 2);

                }
                else if(evt.getNewValue() == Characters.FUNGARUS) {
                    expertController.fungarusEffect();
                    game.getCurrentPlayer().setMyCoins(game.getCurrentPlayer().getMyCoins() - 3);

                }
                else if(evt.getNewValue() == Characters.JESTER) {
                    expertController.jesterEffect();
                    game.getCurrentPlayer().setMyCoins(game.getCurrentPlayer().getMyCoins() - 1);

                }
                else if(evt.getNewValue() == Characters.THIEF) {
                    expertController.thiefEffect();
                    game.getCurrentPlayer().setMyCoins(game.getCurrentPlayer().getMyCoins() - 3);

                }
                else if(evt.getNewValue() == Characters.MINESTREL) {
                    expertController.minestrelEffect();
                    game.getCurrentPlayer().setMyCoins(game.getCurrentPlayer().getMyCoins() - 1);

                }
                else if(evt.getNewValue() == Characters.MONK) {
                    expertController.monkEffect();
                    game.getCurrentPlayer().setMyCoins(game.getCurrentPlayer().getMyCoins() - 1);

                }
                else if(evt.getNewValue() == Characters.GRANNY_HERBS) {
                    expertController.grannyHerbsEffect();
                    game.getCurrentPlayer().setMyCoins(game.getCurrentPlayer().getMyCoins() - 2);

                }
                else if(evt.getNewValue() == Characters.MAGIC_POSTMAN) {
                    expertController.magicPostmanEffect();
                    game.getCurrentPlayer().setMyCoins(game.getCurrentPlayer().getMyCoins() - 1);

                }
                else if(evt.getNewValue() == Characters.SPOILED_PRINCESS) {
                    expertController.spoiledPrincessEffect();
                    game.getCurrentPlayer().setMyCoins(game.getCurrentPlayer().getMyCoins() - 2);

                }
                else if(evt.getNewValue() == null) turnController.askMotherNatureMoves();
            }

            case "GrannyHerbsTile" -> {
                expertController.setGrannyHerbsTile((Island) evt.getNewValue());
            }

            case "PickPawnType" -> {
                expertController.setPawnTypeChosen((PawnType) evt.getNewValue());
                if (expertController.isThiefEffect()) {
                    expertController.activeThiefEffect();
                } else if(expertController.isFungarusEffect()) {
                    turnController.askMotherNatureMoves();
                }

            }

            case "CheckIslandInfluence" -> {
                int islandId = ((Island) evt.getNewValue()).getIslandID();
                turnController.checkIslandInfluence(islandId);
            }

            case "PickCharacterActionsNum" -> {
                if(expertController.isMinestrelEffect()) {
                    expertController.setMinestrelReqNum((Integer) evt.getNewValue() * 2);
                    expertController.activeMinestrelEffect();
                } else if(expertController.isJesterEffect()) {
                    expertController.setJesterReqNum((Integer) evt.getNewValue() * 2);
                    expertController.activeJesterEffect();
                }
            }


            default -> System.err.println("Unrecognized message!");
        }
    }
}