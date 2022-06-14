package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.clienttoserver.actions.Action;
import it.polimi.ingsw.messages.servertoclient.*;
import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.enumerations.Characters;
import it.polimi.ingsw.model.enumerations.PawnType;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.player.DiningRoom;
import it.polimi.ingsw.model.player.Player;


import java.util.ArrayList;
import java.util.Collections;


public class TurnController {
    private final Controller controller;

    private final GameHandler gameHandler;

    private Player currentPlayer;

    private ExpertController expertController;

    private int studentRequest;

    private boolean lastRound;


    private boolean isActionPhase;

    private int actionPhaseNum;

    private boolean isPianificationPhase;


    private Student studentToMove;

    public Controller getController() {
        return controller;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public TurnController(Controller controller, GameHandler gameHandler) {
        this.controller = controller;
        this.gameHandler = gameHandler;
        isPianificationPhase = false;
        isActionPhase = false;
        actionPhaseNum = 0;

    }

    public void setExpertController(ExpertController expertController) {
        System.out.println("SETTO EXPERT CONTROLLER");
        this.expertController = expertController;
    }

    public boolean isPianificationPhase() {
        return isPianificationPhase;
    }

    public int getStudentRequest() {
        return studentRequest;
    }

    public void setStudentRequest(int studentRequest) {
        this.studentRequest = studentRequest;
    }


    public Student getStudentToMove() {
        return studentToMove;
    }

    public void setStudentToMove(Student studentToMove) {
        this.studentToMove = studentToMove;
    }

    public boolean isActionPhase() {
        return isActionPhase;
    }

    public void setPianificationPhase() {
        isPianificationPhase = true;
    }

    public void setActionPhase() {
        isActionPhase = true;
    }

    public void resetPianificationPhase() {
        isPianificationPhase = false;
    }

    public void resetActionPhase() {
        isActionPhase = false;
    }

    public void setActionPhaseNum(int actionPhaseNum) {
        this.actionPhaseNum = actionPhaseNum;
    }

    public void startPianificationPhase() {
        System.out.println("Start Pianification Phase");
        setPianificationPhase();

        gameHandler.sendBroadcast(new DynamicAnswer(" ___  _   _   _  _  _  ___  _   __   _  ___  _   _   _  _   ___  _ _   _   __  ___ \n" +
                "| o \\| | / \\ | \\| || || __|| | / _| / \\|_ _|| | / \\ | \\| | | o \\| U | / \\ / _|| __|\n" +
                "|  _/| || o || \\\\ || || _| | |( (_ | o || | | |( o )| \\\\ | |  _/|   || o |\\_ \\| _| \n" +
                "|_|  |_||_n_||_|\\_||_||_|  |_| \\__||_n_||_| |_| \\_/ |_|\\_| |_|  |_n_||_n_||__/|___|\n" +
                "                                                                                   ", false));



        putStudentsOnCloud();

        /*
        for(AssistantCard a : controller.getGame().getCurrentPlayer().getAssistantDeck().getDeck()) {
                System.out.println("(Name: " + String.valueOf(a.getName()) + ", " + "Value: " + a.getValue() + ", " + "Moves: " + a.getMoves());
        }

         */
        /*
        for(int i = 0; i < ; i++) {
            System.out.println("(Name: " + String.valueOf(controller.getGame().getCurrentPlayer().getAssistantDeck().getDeck().get(i).getName()));

        }

         */

        //System.out.println(controller.getGame().getCurrentPlayer().getAssistantDeck().getDeck().size());


        askAssistantCard();

        /*
        resetPianificationPhase();

        startActionPhase();

        */
    }

    public void startActionPhase() {

        if(actionPhaseNum == 0) {
            gameHandler.sendBroadcast(new DynamicAnswer("  _    __  ___  _   _   _  _   ___  _ _   _   __  ___ \n" +
                    " / \\  / _||_ _|| | / \\ | \\| | | o \\| U | / \\ / _|| __|\n" +
                    "| o |( (_  | | | |( o )| \\\\ | |  _/|   || o |\\_ \\| _| \n" +
                    "|_n_| \\__| |_| |_| \\_/ |_|\\_| |_|  |_n_||_n_||__/|___|\n" +
                    "                                                      ", false));
        }

        if(actionPhaseNum == controller.getGame().getActivePlayers().size()) {
            startPianificationPhase();
        } else {
            setActionPhase();
            gameHandler.sendSinglePlayer(new StartAction(), currentPlayer.getPlayerID());
            //gameHandler.sendSinglePlayer(new GameCopy(controller.getGame()), currentPlayer.getPlayerID());
            gameHandler.sendBroadcast(new GameCopy(controller.getGame()));

            gameHandler.sendExcept(new DynamicAnswer("Please wait: " + currentPlayer.getNickname() + " is playing his action phase", false), currentPlayer.getPlayerID());

            studentRequest = 1;

            askStudent(studentRequest);
        }



        //resetActionPhase();

    }

    public void askStudent(int studentNum) {
        if(studentNum <= 3) {
            RequestAction studentAction = new RequestAction(Action.PICK_STUDENT);
            gameHandler.sendSinglePlayer(studentAction, currentPlayer.getPlayerID());
            //gameHandler.sendExcept(new DynamicAnswer("Please wait: player " + currentPlayer.getNickname() + " is choosing a student to move!", false), currentPlayer.getPlayerID());
        } else {
            if(controller.getGame().isExpertMode()) {
                askCharacterCard();
            } else {
                askMotherNatureMoves();
            }
        }
        return;


    }

    public void askStudent() {
        RequestAction studentAction = new RequestAction(Action.PICK_STUDENT);
        gameHandler.sendSinglePlayer(studentAction, currentPlayer.getPlayerID());
    }


    public void askCharacterCard() {
        RequestAction characterAction = new RequestAction(Action.PICK_CHARACTER);
        gameHandler.sendSinglePlayer(characterAction, currentPlayer.getPlayerID());
    }

    public void askMotherNatureMoves() {
        RequestAction moveMotherNatureAction = new RequestAction(Action.PICK_MOVES_NUMBER);
        gameHandler.sendSinglePlayer(moveMotherNatureAction, currentPlayer.getPlayerID());
        //gameHandler.sendExcept(new DynamicAnswer("Please wait: player " + currentPlayer.getNickname() + " is choosing where to move mother nature!", false), currentPlayer.getPlayerID());

    }

    public void putStudentsOnCloud() {
        //System.out.println("entro in PutStudentsOnCloud");
        for(CloudTile cloud : controller.getGame().getGameBoard().getClouds()) {
            System.out.println("Putting students on cloud");
            ArrayList<Student> newStudents = new ArrayList<>();
            if(controller.getGame().getGameBoard().getStudentsBag().size() == 0) {
                lastRound = true;
                return;
            }
            Collections.shuffle(controller.getGame().getGameBoard().getStudentsBag());

            int studentsNumber;
            if(controller.getGame().getPlayersNumber() == 3) studentsNumber = 4;
            else studentsNumber = 3;
            for (int j = 0; j < studentsNumber; j++) {
                if(controller.getGame().getGameBoard().getStudentsBag().size() == 0) {
                    lastRound = true;
                    return;
                }
                newStudents.add(controller.getGame().getGameBoard().getStudentsBag().get(0));
                //System.out.println(newStudents.get(j).getType());
                //newStudents.get(j) = gameHandler.getGame().getGameBoard().getStudentsBag().get(0);
                controller.getGame().getGameBoard().removeStudents(0);
            }
            cloud.setStudents(newStudents);
        }

        /*
        for(CloudTile cloud : controller.getGame().getGameBoard().getClouds()) {
            System.out.println("Cloud " + cloud.getID() + " has students: ");
            for (Student s : cloud.getStudents()) {
                System.out.println(s.getType());
            }
        }

         */

    }


    public void askAssistantCard() {
        //System.out.println("Chiedo assitant");
        //System.out.println("LA" + controller.getGame().getGameBoard().getLastAssistantUsed().size() + " " + controller.getGame().getActivePlayers().size());
        //currentPlayer = controller.getGame().getCurrentPlayer();
        //System.out.println(currentPlayer.getNickname());
        if(controller.getGame().getGameBoard().getLastAssistantUsed().size() != controller.getGame().getActivePlayers().size()) {
            //System.out.println("Entro");
            gameHandler.sendSinglePlayer(new StartPianification(), currentPlayer.getPlayerID());
            //System.out.println("Mando StartPianification");
            //gameHandler.sendSinglePlayer(new GameCopy(controller.getGame()), currentPlayer.getPlayerID());
            gameHandler.sendBroadcast(new GameCopy(controller.getGame()));
            //System.out.println("Mando Game");


            RequestAction assistantAction = new RequestAction(Action.PICK_ASSISTANT);
            gameHandler.sendSinglePlayer(assistantAction, currentPlayer.getPlayerID());
            gameHandler.sendExcept(new DynamicAnswer("Please wait: player " + currentPlayer.getNickname() + " is choosing an assistant card!", false), currentPlayer.getPlayerID());
        } else {
            //riordina per ogni assistantCard giocata anche gli active players
            for(int i = 0; i < controller.getGame().getActivePlayers().size(); i++) {
                controller.getGame().getActivePlayers().set(i, controller.getGame().getGameBoard().getLastAssistantUsed().get(i).getOwner());
                System.out.println(controller.getGame().getActivePlayers().get(i).getNickname());
            }

            resetPianificationPhase();

            gameHandler.sendBroadcast(new DynamicAnswer("This action phase round winner is: " + controller.getGame().getActivePlayers().get(0).getNickname(), false));

            controller.getGame().setCurrentPlayer(controller.getGame().getActivePlayers().get(0));
            controller.getGame().setCurrentPlayerNumber(0);
            currentPlayer = controller.getGame().getCurrentPlayer();

            System.out.println("Current player is: " + controller.getGame().getCurrentPlayer().getNickname());


            startActionPhase();
        }
    }

    public void switchPlayer() {
        controller.getGame().switchToNextPlayer();
        this.currentPlayer = controller.getGame().getCurrentPlayer();
        gameHandler.setCurrentPlayerId(currentPlayer.getPlayerID());

    }

    public void askStudentDestination() {
        RequestAction studentDestinationAction = new RequestAction(Action.PICK_DESTINATION);
        gameHandler.sendSinglePlayer(studentDestinationAction, currentPlayer.getPlayerID());
        //gameHandler.sendExcept(new DynamicAnswer("Please wait: player " + currentPlayer.getNickname() + " is choosing where to move his student!", false), currentPlayer.getPlayerID());
    }

    public void playAssistantCard(Assistants nameCardPlayed) {
        AssistantCard cardPlayed = null;
        for(AssistantCard c : controller.getGame().getCurrentPlayer().getAssistantDeck().getDeck()) {
            if(c.getName() == nameCardPlayed) {
                cardPlayed = c;
            }
        }
        if(cardPlayed == null) {
            gameHandler.sendSinglePlayer(new DynamicAnswer("Error: card already played. Please type another card!", false), controller.getGame().getCurrentPlayer().getPlayerID());
            askAssistantCard();
            return;
        }
        System.out.println("Sono in playAssistantCard di " + cardPlayed.getName());

        if(controller.getGame().canPlayAssistant(cardPlayed.getName())) {
            System.out.println("aggiungo carta");

            controller.getGame().getGameBoard().getLastAssistantUsed().add(cardPlayed);
            System.out.println("Sono in playAssistantCard");

            controller.getGame().getCurrentPlayer().getAssistantDeck().removeCard(cardPlayed);


            //ordino
            if(controller.getGame().getGameBoard().getLastAssistantUsed().size() > 1) {
                for (int j = 0; j < controller.getGame().getGameBoard().getLastAssistantUsed().size(); j++) {
                    //boolean flag = false;
                    for (int k = j; k < controller.getGame().getGameBoard().getLastAssistantUsed().size(); k++) {
                        if (controller.getGame().getGameBoard().getLastAssistantUsed().get(j).getValue() > controller.getGame().getGameBoard().getLastAssistantUsed().get(k).getValue()) {
                            AssistantCard ac = controller.getGame().getGameBoard().getLastAssistantUsed().get(j);
                            controller.getGame().getGameBoard().setLastAssistantUsed(j, controller.getGame().getGameBoard().getLastAssistantUsed().get(k));
                            controller.getGame().getGameBoard().setLastAssistantUsed(k, ac);
                            //flag = true;
                        }
                    }
                    //if (!flag) break;
                }
            }
            if(controller.getGame().getCurrentPlayer().getAssistantDeck().getDeck().size() == 0) {
                lastRound = true;
            }

            switchPlayer();

            askAssistantCard();
        } else {
            askAssistantCard();
        }


    }

    public void checkProfessorInfluence() {
        int currentPlayerStudentsMax = 0;
        int professorWinnerId = 0;


        for(Player p : controller.getGame().getActivePlayers()) {
            if(controller.getGame().getGameBoard().getProfessorByColor(studentToMove.getType()).getOwner() != null) {
                if(controller.getGame().getGameBoard().getProfessorByColor(studentToMove.getType()).getOwner().getNickname() == p.getNickname()) {
                    currentPlayerStudentsMax = p.getBoard().getDiningRoom().getDiningRoom().get(studentToMove.getType().getPawnID()).getTableStudentsNum();
                    professorWinnerId = p.getPlayerID();
                }
            }
        }

        for (Player p : controller.getGame().getActivePlayers()) {
            System.out.println("P_ " + p.getNickname());
            for (int i = 0; i < 5; i++) {
                if (p.getBoard().getDiningRoom().getDiningRoom().get(i).getColor() == studentToMove.getType()) {
                    System.out.println("Player " + p.getNickname() + " has " + p.getBoard().getDiningRoom().getDiningRoom().get(i).getTableStudentsNum() + " students of type " + p.getBoard().getDiningRoom().getDiningRoom().get(i).getColor());

                    if (p.getBoard().getDiningRoom().getDiningRoom().get(i).getTableStudentsNum() > currentPlayerStudentsMax) {
                        currentPlayerStudentsMax = p.getBoard().getDiningRoom().getDiningRoom().get(i).getTableStudentsNum();
                        professorWinnerId = p.getPlayerID();

                    }
                }
            }
        }

        System.out.println("WINNER " + professorWinnerId);
        System.out.println("WINNER ID" + controller.getGame().getCurrentPlayer().getPlayerID());


        if (controller.getGame().getCurrentPlayer().getPlayerID() == professorWinnerId) {
            if (controller.getGame().getGameBoard().getProfessorByColor(studentToMove.getType()).getOwner() == null) {
                System.out.println("Setto prof " + studentToMove.getType().toString() + " to" + controller.getGame().getCurrentPlayer().getNickname());
                currentPlayer.getBoard().getProfessorTable().getCellByColor(studentToMove.getType()).setProfessor(controller.getGame().getGameBoard().getProfessorByColor(studentToMove.getType()));
                controller.getGame().getGameBoard().getProfessorByColor(studentToMove.getType()).setOwner(controller.getGame().getCurrentPlayer());
            } else {
                if(controller.getGame().getGameBoard().getProfessorByColor(studentToMove.getType()).getOwner().getPlayerID() != professorWinnerId) {
                    System.out.println("Setto prof " + studentToMove.getType().toString() + " to" + controller.getGame().getCurrentPlayer().getNickname());

                    controller.getGame().getGameBoard().getProfessorByColor(studentToMove.getType()).getOwner().getBoard().getProfessorTable().getCellByColor(studentToMove.getType()).resetProfessor();

                    currentPlayer.getBoard().getProfessorTable().getCellByColor(studentToMove.getType()).setProfessor(controller.getGame().getGameBoard().getProfessorByColor(studentToMove.getType()));
                    controller.getGame().getGameBoard().getProfessorByColor(studentToMove.getType()).setOwner(controller.getGame().getCurrentPlayer());
                }

            }
        }

        /*
            for(Player p : controller.getGame().getActivePlayers()) {
                System.out.println(p.getBoard().getProfessorTable().getCellByColor(studentToMove.getType()).hasProfessor());
                if(controller.getGame().getGameBoard().getProfessorByColor(studentToMove.getType()).getOwner() == null) {
                    if(p.getPlayerID() == controller.getGame().getCurrentPlayer().getPlayerID()) {
                        p.getBoard().getProfessorTable().getCellByColor(studentToMove.getType()).resetProfessor();
                        currentPlayer.getBoard().getProfessorTable().getCellByColor(studentToMove.getType()).setProfessor(controller.getGame().getGameBoard().getProfessorByColor(studentToMove.getType()));
                        controller.getGame().getGameBoard().getProfessorByColor(studentToMove.getType()).setOwner(controller.getGame().getCurrentPlayer());
                        System.err.println("setting prof " + studentToMove.getType() + " to: " + controller.getGame().getCurrentPlayer().getNickname());
                        break;
                    }
                }
                else {
                    System.err.println("entro qui");
                    if (controller.getGame().getCurrentPlayer().getBoard().getProfessorTable().getCellByColor(studentToMove.getType()).hasProfessor() == true && p.getPlayerID() != controller.getGame().getCurrentPlayer().getPlayerID()) {
                        p.getBoard().getProfessorTable().getCellByColor(studentToMove.getType()).resetProfessor();
                        currentPlayer.getBoard().getProfessorTable().getCellByColor(studentToMove.getType()).setProfessor(controller.getGame().getGameBoard().getProfessorByColor(studentToMove.getType()));
                        controller.getGame().getGameBoard().getProfessorByColor(studentToMove.getType()).setOwner(controller.getGame().getCurrentPlayer());
                        System.err.println("setting prof " + studentToMove.getType() + " to: " + controller.getGame().getCurrentPlayer().getNickname());
                        break;
                    }
                }
            }

             */
    }





    public void moveStudentsToDiningRoom(DiningRoom chosenDiningRoom) {
        System.out.println("Studente " + studentToMove.getType());
        if(controller.getGame().getCurrentPlayer().getBoard().getDiningRoom().getDiningRoom().get(studentToMove.getType().getPawnID()).getTableStudentsNum() == 9) {
            gameHandler.sendSinglePlayer(new DynamicAnswer("Your DiningRoom is full. Please choose an island!", false), controller.getGame().getCurrentPlayer().getPlayerID());
            askStudentDestination();
        } else {
            controller.getGame().getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(studentToMove);
        }

        //System.out.println(controller.getGame().getCurrentPlayer().getBoard().getDiningRoom().isTakeCoin());

        if(expertController != null) {
            if(controller.getGame().getCurrentPlayer().getBoard().getDiningRoom().isTakeCoin()) {
                System.out.println("Aggiungo coin");
                controller.getGame().getCurrentPlayer().setMyCoins(controller.getGame().getCurrentPlayer().getMyCoins() + 1);
                controller.getGame().getCurrentPlayer().getBoard().getDiningRoom().setTakeCoin(false);
            }
        }

        controller.getGame().getCurrentPlayer().getBoard().getEntrance().removeStudent(studentToMove);
        checkProfessorInfluence();
        studentRequest++;
        //gameHandler.sendSinglePlayer(new GameCopy(controller.getGame()), currentPlayer.getPlayerID());
        gameHandler.sendBroadcast(new GameCopy(controller.getGame()));

        askStudent(studentRequest);

        /*
        chosenDiningRoom.getDiningRoom()
        //message1 = colore studente, message2 = destinazione (isola o diningroom)
        Student studentToMove = new Student(message1);
        GameBoard board;
        PawnType studType = message1;
        int destinationIsland;
        //putStudentsDiningRoom o putStudentsIsland
        for(int i = 0; i < 3; i++) {
            studentToMove = player.pickStudent();

            //switch oppure if in cui il player dove spostarlo
            //se lo sposta in island -> destinationIsland:
            if(message2 == "island") {
                destinationIsland = player.pickIsland().islandID;
                for (Island island : gameBoard.getIslands()) {
                    if (island.getIslandID() == destinationIsland) {
                        island.setStudent(studentToMove);
                    }

                }
                destinationIsland.setStudent(studentToMove);
            }
            //se lo sposta nella DiningRoom -> studType
            else if(message2 == "dining room") {
                //try ... catch se ha già il tavolo pieno
                studType = studentToMove.getType();
                player.getBoard().diningRoom.setStudent(studentToMove);
            }
        }

         */

    }

    /**
     * @param chosenIsland
     */
    public void moveStudentToIsland(Island chosenIsland) {
        //Island chosenIsland = gameHandler.getGame().getGameBoard().getIslands().get(chosenIslandId - 1);
        for(Island i : controller.getGame().getGameBoard().getIslands()){
            if(chosenIsland.getIslandID() == i.getIslandID()) {
                System.out.println("Put student on island " + i.getIslandID());
                if(expertController.isMonkEffect()) {
                    i.addStudent(expertController.getStudentChosen());
                } else {
                    i.addStudent(studentToMove);
                }

            }
        }


        if(expertController != null) {
            if(expertController.isMonkEffect()) {
                for(CharacterCard c : controller.getGame().getGameBoard().getPlayableCharacters()) {
                    if(c.getName() == Characters.MONK) {
                        for(Student s : c.getStudents()) {
                            if(s.getType() == expertController.getStudentChosen().getType()) {
                                c.removeStudent(s);
                                break;
                            }
                        }
                        System.out.println("Ora aggiungo");
                        c.addStudent(controller.getGame().getGameBoard().getStudentsBag().get(0));
                        controller.getGame().getGameBoard().removeStudents(0);
                        break;
                    }
                }
                expertController.setMonkEffect(false);
                askMotherNatureMoves();
            } else {
                controller.getGame().getCurrentPlayer().getBoard().getEntrance().removeStudent(studentToMove);
                studentRequest++;
                //gameHandler.sendSinglePlayer(new GameCopy(controller.getGame()), currentPlayer.getPlayerID());
                gameHandler.sendBroadcast(new GameCopy(controller.getGame()));

                askStudent(studentRequest);
            }
        } else {
            controller.getGame().getCurrentPlayer().getBoard().getEntrance().removeStudent(studentToMove);
            studentRequest++;
            //gameHandler.sendSinglePlayer(new GameCopy(controller.getGame()), currentPlayer.getPlayerID());
            gameHandler.sendBroadcast(new GameCopy(controller.getGame()));

            askStudent(studentRequest);
        }

    }


    public void moveMotherNature(int moves) {
        System.out.println("entro in moveMotherNature");
        int currPosition = controller.getGame().getGameBoard().getMotherNature().getPosition();
        int newPosition;

        if(currPosition + moves > 12) {
            newPosition = (currPosition + moves) % 12;
        } else {
            newPosition = currPosition + moves;
        }

        /*
        if(controller.getGame().getGameBoard().getIslands().get(newPosition-1).isMerged()) {
            newPosition = controller.getGame().getGameBoard().getIslands().get(newPosition-1).getLeaderIsland();
            System.out.println("Leader island is: " + controller.getGame().getGameBoard().getIslands().get(newPosition-1).getLeaderIsland());
        }
         */

        controller.getGame().getGameBoard().getMotherNature().setPosition(newPosition);
        System.out.println("Sposto mn in island " + controller.getGame().getGameBoard().getMotherNature().getPosition());


        if(expertController != null) {
            System.out.println("Non devo esserci entrato");
            /*
            if(!expertController.isGrannyHerbsEffect()) {
                for(int i = 0; i< controller.getGame().getGameBoard().getIslands().size(); i++) {
                    if(controller.getGame().getGameBoard().getIslands().get(i).getIslandID() == newPosition) {
                        checkIslandInfluence(i+1);
                    }
                }
            } else {
                for(int i = 0; i< controller.getGame().getGameBoard().getIslands().size(); i++) {
                    if(controller.getGame().getGameBoard().getIslands().get(i).getIslandID() == newPosition) {
                        if(controller.getGame().getGameBoard().getIslands().get(i).getNoEntry()) {
                            System.out.println("Me ne vado senza fare niente");
                            //expertController.setGrannyHerbsEffect(false);
                            controller.getGame().getGameBoard().getIslands().get(i).setNoEntry(false);
                        } else {
                            checkIslandInfluence(i+1);
                        }
                    }
                }

            }

             */
            for(int i = 0; i< controller.getGame().getGameBoard().getIslands().size(); i++) {
                if(controller.getGame().getGameBoard().getIslands().get(i).getIslandID() == newPosition) {
                    if(controller.getGame().getGameBoard().getIslands().get(i).getNoEntry()) {
                        System.out.println("Me ne vado senza fare niente");
                        //expertController.setGrannyHerbsEffect(false);
                        controller.getGame().getGameBoard().getIslands().get(i).setNoEntry(false);
                    } else {
                        checkIslandInfluence(i+1);
                    }
                }
            }

        } else {
            System.out.println("Entro pre-checkIslandInfluence");
            //checkIslandInfluence(newPosition);
            for(int i = 0; i< controller.getGame().getGameBoard().getIslands().size(); i++) {
                if(controller.getGame().getGameBoard().getIslands().get(i).getIslandID() == newPosition) {
                    checkIslandInfluence(i+1);
                    break;
                }
            }
        }


        if(lastRound) {
            fromCloudToEntrance(null);
        } else {
            askCloud();
        }

    }

    public void checkIslandInfluence(int islandId) {
        //verifica se l'isola viene conquistata o controllata
        /*
        for(Player player : gameHandler.getGame().getActivePlayers()) {
            for(BoardCell professorCell : player.getBoard().getProfessorTable().getProfessorTable()) {
                if(professorCell.hasProfessor()) {

                }
            }
        }

         */

        System.out.println("Entro in checkIsland di " + controller.getGame().getGameBoard().getIslands().get(islandId-1).getIslandID());

        System.out.println("island ha " + controller.getGame().getGameBoard().getIslands().get(islandId-1).getStudents().size());


        for(Student student : controller.getGame().getGameBoard().getIslands().get(islandId-1).getStudents()) {
            PawnType studentType = student.getType();
            System.out.println(studentType.toString());
            if(expertController != null) {
                if(expertController.isFungarusEffect()) {
                    if(!studentType.equals(expertController.getPawnTypeChosen())) {
                        if(controller.getGame().getGameBoard().getProfessorByColor(studentType).getOwner() != null) {
                            Player studentOwner = controller.getGame().getGameBoard().getProfessorByColor(studentType).getOwner();
                            studentOwner.setIslandInfluence(studentOwner.getIslandInfluence() + 1);
                        }

                    }
                } else {
                    if(controller.getGame().getGameBoard().getProfessorByColor(studentType).getOwner() != null) {
                        Player studentOwner = controller.getGame().getGameBoard().getProfessorByColor(studentType).getOwner();
                        studentOwner.setIslandInfluence(studentOwner.getIslandInfluence() + 1);
                    }

                }
            } else {
                System.out.println("DEVO ESSER QUI");
                if(controller.getGame().getGameBoard().getProfessorByColor(studentType).getOwner() != null) {
                    Player studentOwner = controller.getGame().getGameBoard().getProfessorByColor(studentType).getOwner();
                    System.out.println("Player " + studentOwner.getNickname() + " has professor of type " + studentType.toString());
                    studentOwner.setIslandInfluence(studentOwner.getIslandInfluence() + 1);
                    System.out.print(studentOwner.getNickname() + " influence: " + studentOwner.getIslandInfluence());
                }
            }
            //System.out.println("vado avanti");
        }

        if(expertController != null) {
            if(!expertController.isCentaurEffect()) {
                if(controller.getGame().getGameBoard().getIslands().get(islandId - 1).getMergedTowers() != null) {
                    TowerColor towerColor = controller.getGame().getGameBoard().getIslands().get(islandId - 1).getMergedTowers().get(0).getColor();
                    for (Player p : controller.getGame().getActivePlayers()) {
                        if (p.getBoard().getTowerArea().getTowerArea().get(0).getColor() == towerColor) {
                            p.setIslandInfluence(p.getIslandInfluence() + controller.getGame().getGameBoard().getIslands().get(islandId - 1).getMergedTowers().size());
                        }
                    }
                }

            }
        }

        int islandInfluence = 0;
        Player influenceWinner = null;

        for(Player player : controller.getGame().getActivePlayers()) {
            if(player.getIslandInfluence() > islandInfluence || (controller.getGame().getGameBoard().getIslands().get(islandId - 1).getTower() != null && player.getBoard().getTowerArea().getTowerArea().get(0).getColor() == controller.getGame().getGameBoard().getIslands().get(islandId - 1).getTower().getColor()) && player.getIslandInfluence() >= islandInfluence) {
                islandInfluence = player.getIslandInfluence();
                influenceWinner = player;
            }
        }

        int team1 = 0;
        int team2 = 0;
        if(controller.getGame().getPlayers().size() == 4) {
            for(Player p : controller.getGame().getActivePlayers()) {
                if(p.getIdTeam() == 1) {
                    team1 = team1 + p.getIslandInfluence();
                } else if(p.getIdTeam() == 2) {
                    team2 = team2 + p.getIslandInfluence();
                }
            }

            if(team1 > team2) {
                for(Player p : controller.getGame().getActivePlayers()) {
                    if(p.getIdTeam() == 1 && p.isTeamLeader()) {
                        influenceWinner = p;
                        break;
                    }
                }
            } else if(team2 > team1) {
                for(Player p : controller.getGame().getActivePlayers()) {
                    if(p.getIdTeam() == 2 && p.isTeamLeader()) {
                        influenceWinner = p;
                        break;
                    }
                }
            }
        }

        System.out.println("\nTeam 1: "+ team1);
        System.out.println("Team 2: "+ team2);

        if(influenceWinner != null)
            System.out.println("\nMax island influence " + islandInfluence + " di " + influenceWinner.getNickname() + " in isola " + controller.getGame().getGameBoard().getIslands().get(islandId - 1).getIslandID() + " che ha tower "+ controller.getGame().getGameBoard().getIslands().get(islandId-1).hasTower());



        if(islandInfluence != 0) {
            //if (controller.getGame().getCurrentPlayer().getIslandInfluence() == islandInfluence) {
                //controllare che non abbia già costruito
                if (controller.getGame().getGameBoard().getIslands().get(islandId - 1).getTower() == null) {
                    //aggiunge torre ev
                    System.out.println("aggiungo torre");
                    influenceWinner.getBoard().getTowerArea().moveTowerToIsland(controller.getGame().getGameBoard().getIslands().get(islandId - 1));
                } else {
                    if(influenceWinner.getBoard().getTowerArea().getTowerArea().get(0).getColor() != controller.getGame().getGameBoard().getIslands().get(islandId - 1).getMergedTowers().get(0).getColor()) {
                        System.out.println("Cambio di influenza");
                        int mergedTowers = controller.getGame().getGameBoard().getIslands().get(islandId - 1).getMergedTowers().size();
                        for (Player p : controller.getGame().getActivePlayers()) {
                            if (p.getBoard().getTowerArea().getTowerArea().get(0).getColor() == controller.getGame().getGameBoard().getIslandById(islandId).getTower().getColor()) {
                                controller.getGame().getGameBoard().getIslands().get(islandId - 1).moveTowerToArea(p.getBoard().getTowerArea());
                                System.out.println("Spostata in tower area di " + p.getNickname());
                                break;
                            }
                        }
                        influenceWinner.getBoard().getTowerArea().moveTowerToIsland(controller.getGame().getGameBoard().getIslands().get(islandId - 1), mergedTowers);
                    }
                }

                if (controller.getGame().getGameBoard().getIslands().get(islandId - 1).hasLeft()) {
                    System.out.println("merge a sx");
                    if (controller.getGame().getGameBoard().getIslands().get(islandId - 1).hasRight()) {
                        System.out.println("merge anche a dx");
                        controller.getGame().getGameBoard().getIslands().get(islandId -1).doubleMerge();
                        /*
                        controller.getGame().getGameBoard().getIslands().get(islandId - 1).merge(controller.getGame().getGameBoard().getIslands().get(islandId - 2));
                        controller.getGame().getGameBoard().getIslands().get(islandId - 1).merge(controller.getGame().getGameBoard().getIslands().get(islandId - 1));

                         */
                    } else {
                        if(islandId != 1) {
                            controller.getGame().getGameBoard().getIslands().get(islandId - 1).merge(controller.getGame().getGameBoard().getIslands().get(islandId - 2));
                        } else {
                            System.out.println("Merge a sx di isola 1");
                            controller.getGame().getGameBoard().getIslands().get(islandId - 1).merge(controller.getGame().getGameBoard().getIslands().get(controller.getGame().getGameBoard().getIslands().size() -1));
                        }

                    }
                } else if (controller.getGame().getGameBoard().getIslands().get(islandId - 1).hasRight()) {
                    System.out.println("merge a dx");
                    controller.getGame().getGameBoard().getIslands().get(islandId - 1).merge(controller.getGame().getGameBoard().getIslandById(islandId + 2));
                }


            //}
        }

        //System.err.println(controller.getGame().getCurrentPlayer().getIslandInfluence());

        //RESET ISLAND INFLUENCE
        for(Player p : controller.getGame().getActivePlayers()) {
            p.setIslandInfluence(0);
            System.out.println(p.getNickname() + " influence is reset to " + p.getIslandInfluence());
        }


        if(influenceWinner != null) {
            if(influenceWinner.getBoard().getTowerArea().getTowerArea().size() == 0) {
                System.out.println("Entro qua");
                if(controller.getGame().getPlayers().size() == 4) {
                    for(int i = 0; i < controller.getGame().getActivePlayers().size(); i++) {
                        if(controller.getGame().getActivePlayers().get(i).getIdTeam() == controller.getGame().getCurrentPlayer().getIdTeam()) {
                            gameHandler.sendSinglePlayer(new WinNotification(), controller.getGame().getActivePlayers().get(i).getPlayerID());
                        } else {
                            gameHandler.sendSinglePlayer(new LoseNotification(controller.getGame().getCurrentPlayer().getNickname()), controller.getGame().getActivePlayers().get(i).getPlayerID());
                        }
                    }
                } else {
                    gameHandler.sendSinglePlayer(new WinNotification(), controller.getGame().getCurrentPlayer().getPlayerID());
                    gameHandler.sendExcept(new LoseNotification(controller.getGame().getCurrentPlayer().getNickname()), controller.getGame().getCurrentPlayer().getPlayerID());
                }

                gameHandler.endGame();

            }
        }

        if(expertController != null) {
            if(expertController.isCentaurEffect()) {
                expertController.setCentaurEffect(false);
            }
            if(expertController.isFungarusEffect()) {
                expertController.setFungarusEffect(false);
            }
            if(expertController.isHeraldEffect()) {
                expertController.setHeraldEffect(false);
                askMotherNatureMoves();

            }
        }


    }

    public void askCloud() {
        RequestAction cloudAction = new RequestAction(Action.PICK_CLOUD);
        gameHandler.sendSinglePlayer(cloudAction, currentPlayer.getPlayerID());
        //gameHandler.sendExcept(new DynamicAnswer("Please wait: player " + currentPlayer.getNickname() + " is choosing a cloud to take!", false), currentPlayer.getPlayerID());
    }

    /*
    private Game game;
    private final GameHandler gameHandler;
    private final PianificationHandler pianificationHandler;
    private final ActionHandler actionHandler;
    private ArrayList<Player> activePlayers;

    public TurnHandler(PianificationHandler pianificationHandler, ActionHandler actionHandler, GameHandler gameHandler){
        this.pianificationHandler = pianificationHandler;
        this.actionHandler = actionHandler;
        this.gameHandler = gameHandler;
    }

    public void startPianification(){
        gameHandler.putStudentsOnCloud();

        for (int i = 0; i < game.getPlayersNumber(); i++) {
            AssistantCard chosenCard = board.getLastAssistantUsed().get(i).getOwner().pickAssistant();
            board.getLastAssistantUsed().get(i).getOwner().getAssistantDeck().removeCard(chosenCard);
            board.setLastAssistantUsed(i, chosenCard);
        }

        for (int j = 0; j < board.getLastAssistantUsed().size(); j++) {
            boolean flag = false;
            for(int k = 0; k < board.getLastAssistantUsed().size() - 1; k++) {
                if(board.getLastAssistantUsed().get(j).getValue() > board.getLastAssistantUsed().get(j + 1).getValue()) {
                    AssistantCard ac = board.getLastAssistantUsed().get(j);
                    board.setLastAssistantUsed(j, board.getLastAssistantUsed().get(j + 1));
                    board.setLastAssistantUsed(j + 1, ac);
                    flag = true;
                }
            }
            if(!flag) break;
        }
    }

    }

    public void startAction(){
        actionHandler.moveMotherNature();
        actionHandler.moveStudents();
        actionHandler.fromCloudToEntrance();
    }

     */

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void fromCloudToEntrance(CloudTile cloud) {
        /*
        int studentsToMove;
        if (gameHandler.getPlayersNumber() == 3)
            studentsToMove = 4;
        else
            studentsToMove = 3;

         */
        if(cloud != null) {
            ArrayList<Student> newStudents = cloud.getStudents();
            for (Student s : newStudents) {
                controller.getGame().getCurrentPlayer().getBoard().getEntrance().setStudents(s);
            }

            for(CloudTile cloudTile : controller.getGame().getGameBoard().getClouds()){
                if(cloudTile.getID() == cloud.getID()){
                    cloudTile.removeStudents();
                }
            }
        }

        //cloud.removeStudents();


        if(checkWin()) {
            Player winner = checkWinner();
            if(controller.getGame().getPlayers().size() == 4) {
                for(int i = 0; i < controller.getGame().getActivePlayers().size(); i++) {
                    if(controller.getGame().getActivePlayers().get(i).getIdTeam() == winner.getIdTeam()) {
                        gameHandler.sendSinglePlayer(new WinNotification(), controller.getGame().getActivePlayers().get(i).getPlayerID());
                    } else {
                        gameHandler.sendSinglePlayer(new LoseNotification(winner.getNickname()), controller.getGame().getActivePlayers().get(i).getPlayerID());
                    }
                }
            } else {
                gameHandler.sendSinglePlayer(new WinNotification(), winner.getPlayerID());
                gameHandler.sendExcept(new LoseNotification(winner.getNickname()), winner.getPlayerID());
            }

            gameHandler.endGame();
        }

        //System.err.println(checkWin());






        for(int i= 0; i< controller.getGame().getGameBoard().getLastAssistantUsed().size(); i++) {
            controller.getGame().getGameBoard().getLastAssistantUsed().remove(i);
        }

        /*
        for(AssistantCard card : controller.getGame().getGameBoard().getLastAssistantUsed()) {
            controller.getGame().getGameBoard().getLastAssistantUsed().remove(card);
        }

         */
        switchPlayer();


        if(actionPhaseNum == controller.getGame().getActivePlayers().size() - 1) {
            actionPhaseNum = 0;
            if(expertController != null) {
                for(int i = 0; i < controller.getGame().getGameBoard().getPlayableCharacters().size(); i++) {
                    controller.getGame().getGameBoard().getPlayableCharacters().get(i).resetCost();
                }
            }

            startPianificationPhase();
        } else {
            actionPhaseNum++;
            startActionPhase();
        }

    }

    public boolean checkWin() {
        System.out.println("Entro in checkWin");



        if(controller.getGame().getGameBoard().getIslandCounter() == 3) {
            System.out.println("Enrtro 1");
            return true;
        }

        for(Player p: controller.getGame().getActivePlayers()) {
            if(controller.getGame().getPlayers().size() == 4) {
                if(p.getBoard().getTowerArea().getTowerArea().size() == 0 && p.isTeamLeader()) {
                    System.out.println("Enrtro 2");

                    return true;
                }
            } else {
                if(p.getBoard().getTowerArea().getTowerArea().size() == 0) {
                    System.out.println("Enrtro 2");

                    return true;
                }
            }

            //if(p.getAssistantDeck().getDeck().size() == 0) return true;
        }

        if(lastRound) { // se si è avverata la condizione di lastRound faccio il check solo per l'ultimo giocatore
            System.out.println("Enrtro 3");

            for(int i = 0; i< controller.getGame().getActivePlayers().size(); i++) {
                if(i != controller.getGame().getActivePlayers().size() - 1) {
                    return false;
                } else {
                    return true;
                }
            }
        }

        return false;
    }

    public Player checkWinner() {
        if(controller.getGame().getCurrentPlayer().getBoard().getTowerArea().getTowerArea().size() == 0) {
            return controller.getGame().getCurrentPlayer();
        }

        //1 controllo il numero di torri messe -> se ce ne sono di numero uguale vado avanti
        boolean twoEquals = false;
        Player towerPWinner = controller.getGame().getActivePlayers().get(0);

        for(Player p : controller.getGame().getActivePlayers()) {
            if(p.getBoard().getTowerArea().getTowerArea().size() < towerPWinner.getBoard().getTowerArea().getTowerArea().size()) {
                towerPWinner = p;
                twoEquals = false;
            } else if(p.getBoard().getTowerArea().getTowerArea().size() == towerPWinner.getBoard().getTowerArea().getTowerArea().size()) {
                twoEquals = true;
            }
        }

        if(!twoEquals) {
            return towerPWinner;
        }

        //2 controllo l'influenza dei professori
        int profInfluence = 0;
        Player influenceWinner = null;

        for(Player player : controller.getGame().getActivePlayers()) {
            player.setProfInfluence(0);
            for(PawnType p : PawnType.values()) {
                if(player.getBoard().getProfessorTable().getCellByColor(p).hasProfessor() == true) {
                    player.setProfInfluence(player.getProfInfluence() + 1);
                }
            }
        }

        for(Player p : controller.getGame().getActivePlayers()) {
            if(p.getProfInfluence() > profInfluence) {
                profInfluence = p.getProfInfluence();
                influenceWinner = p;
            }
        }

        return influenceWinner;
    }
}