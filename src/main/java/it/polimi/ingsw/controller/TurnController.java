package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.clienttoserver.actions.Action;
import it.polimi.ingsw.messages.servertoclient.*;
import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.enumerations.Assistants;
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

        System.out.println(controller.getGame().getCurrentPlayer().getAssistantDeck().getDeck().size());


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
            gameHandler.sendSinglePlayer(new GameCopy(controller.getGame()), currentPlayer.getPlayerID());

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
            Collections.shuffle(controller.getGame().getGameBoard().getStudentsBag());
            int studentsNumber;
            if(controller.getGame().getPlayersNumber() == 3) studentsNumber = 4;
            else studentsNumber = 3;
            for (int j = 0; j < studentsNumber; j++) {
                newStudents.add(controller.getGame().getGameBoard().getStudentsBag().get(0));
                //System.out.println(newStudents.get(j).getType());
                //newStudents.get(j) = gameHandler.getGame().getGameBoard().getStudentsBag().get(0);
                controller.getGame().getGameBoard().removeStudents(0);
            }
            cloud.setStudents(newStudents);
        }

        for(CloudTile cloud : controller.getGame().getGameBoard().getClouds()) {
            System.out.println("Cloud " + cloud.getID() + " has students: ");
            for (Student s : cloud.getStudents()) {
                System.out.println(s.getType());
            }
        }

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
            gameHandler.sendSinglePlayer(new GameCopy(controller.getGame()), currentPlayer.getPlayerID());
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
            currentPlayer = controller.getGame().getActivePlayers().get(0);

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
        System.out.println("Sono in playAssistantCard di " + cardPlayed);

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

            switchPlayer();

            askAssistantCard();
        }


    }

    public void checkProfessorInfluence() {
        int currentPlayerStudents = 0;
        int professorWinnerId = 0;

        for(Player p : controller.getGame().getActivePlayers()) {
            for(int i = 0; i < 5; i++) {
                if(p.getBoard().getDiningRoom().getDiningRoom().get(i).getColor() == studentToMove.getType()) {
                    if(p.getBoard().getDiningRoom().getDiningRoom().get(i).getTable().size() > currentPlayerStudents) {
                        professorWinnerId = p.getPlayerID();
                    }
                }
            }
        }

        if(currentPlayer.getPlayerID() == professorWinnerId) {
            for(Player p : controller.getGame().getActivePlayers()) {
                if(p.getBoard().getProfessorTable().getCellByColor(studentToMove.getType()) != null && p.getPlayerID() != currentPlayer.getPlayerID()) {
                    p.getBoard().getProfessorTable().getCellByColor(studentToMove.getType()).resetProfessor();
                    currentPlayer.getBoard().getProfessorTable().getCellByColor(studentToMove.getType()).setProfessor(controller.getGame().getGameBoard().getProfessorByColor(studentToMove.getType()));
                }
            }

        }

    }


    public void moveStudentsToDiningRoom(DiningRoom chosenDiningRoom) {
        currentPlayer.getBoard().getDiningRoom().setStudentToDiningRoom(studentToMove);
        currentPlayer.getBoard().getEntrance().removeStudent(studentToMove);
        checkProfessorInfluence();
        studentRequest++;
        gameHandler.sendSinglePlayer(new GameCopy(controller.getGame()), currentPlayer.getPlayerID());
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

    public void moveStudentToIsland(Island chosenIsland) {
        //Island chosenIsland = gameHandler.getGame().getGameBoard().getIslands().get(chosenIslandId - 1);
        for(Island i : controller.getGame().getGameBoard().getIslands()){
            if(chosenIsland.getIslandID() == i.getIslandID()) i.addStudent(studentToMove);
        }

        controller.getGame().getCurrentPlayer().getBoard().getEntrance().removeStudent(studentToMove);
        if(expertController != null) {
            if(expertController.isMonkEffect()) {
                expertController.setMonkEffect(false);
                askMotherNatureMoves();
            }
        } else {
            studentRequest++;
            gameHandler.sendSinglePlayer(new GameCopy(controller.getGame()), currentPlayer.getPlayerID());
            askStudent(studentRequest);
        }

    }


    public void moveMotherNature(int moves) {
        int currPosition = controller.getGame().getGameBoard().getMotherNature().getPosition();
        int newPosition;

        if(currPosition + moves > 12) {
            newPosition = (currPosition + moves) % 12;
        } else {
            newPosition = currPosition + moves;
        }

        controller.getGame().getGameBoard().getMotherNature().setPosition(newPosition);

        if(expertController != null) {
            if(!expertController.isGrannyHerbsEffect()) {
                checkIslandInfluence(newPosition);
            } else {
                System.err.println("Me ne vado senza fare niente");
                expertController.setGrannyHerbsEffect(false);
            }

        } else {
            checkIslandInfluence(newPosition);
        }

        askCloud();
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

        for(Student student : controller.getGame().getGameBoard().getIslands().get(islandId - 1).getStudents()) {
            PawnType studentType = student.getType();
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
                if(controller.getGame().getGameBoard().getProfessorByColor(studentType).getOwner() != null) {
                    Player studentOwner = controller.getGame().getGameBoard().getProfessorByColor(studentType).getOwner();
                    studentOwner.setIslandInfluence(studentOwner.getIslandInfluence() + 1);
                }
            }
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

        for(Player player : controller.getGame().getActivePlayers()) {
            if(player.getIslandInfluence() > islandInfluence || (controller.getGame().getGameBoard().getIslands().get(islandId - 1).getTower() != null && player.getBoard().getTowerArea().getTowerArea().get(0).getColor() == controller.getGame().getGameBoard().getIslands().get(islandId - 1).getTower().getColor()) && player.getIslandInfluence() >= islandInfluence) {
                islandInfluence = player.getIslandInfluence();
            }
        }

        if(currentPlayer.getIslandInfluence() == islandInfluence) {
            //controllare che non abbia già costruito
            if(controller.getGame().getGameBoard().getIslands().get(islandId - 1).getTower() != null) {
                //aggiunge torre ev
                currentPlayer.getBoard().getTowerArea().moveTowerToIsland(controller.getGame().getGameBoard().getIslandById(islandId));
            } else {
                for(Player p : controller.getGame().getActivePlayers()) {
                    if(controller.getGame().getGameBoard().getIslandById(islandId).getTower() != null && p.getBoard().getTowerArea().getTowerArea().get(0).getColor() == controller.getGame().getGameBoard().getIslandById(islandId).getTower().getColor()) {
                        controller.getGame().getGameBoard().getIslandById(islandId).moveTowerToArea(p.getBoard().getTowerArea());
                        break;
                    }
                }
                currentPlayer.getBoard().getTowerArea().moveTowerToIsland(controller.getGame().getGameBoard().getIslandById(islandId));

            }

            if(controller.getGame().getGameBoard().getIslandById(islandId).hasLeft()) {
                controller.getGame().getGameBoard().getIslandById(islandId).merge(controller.getGame().getGameBoard().getIslandById(islandId - 1));
            }
            if(controller.getGame().getGameBoard().getIslandById(islandId).hasRight()) {
                controller.getGame().getGameBoard().getIslandById(islandId).merge(controller.getGame().getGameBoard().getIslandById(islandId + 1));
            }

        }

        System.err.println(controller.getGame().getCurrentPlayer().getIslandInfluence());

        //RESET ISLAND INFLUENCE
        for(Player p : controller.getGame().getPlayers()) {
            p.setIslandInfluence(0);
        }

        if(expertController != null) {
            if(expertController.isCentaurEffect()) {
                expertController.setCentaurEffect(false);
            }
            if(expertController.isFungarusEffect()) {
                expertController.setFungarusEffect(false);
            }
            if(expertController.isHeraldEffect()) {
                askMotherNatureMoves();
                expertController.setHeraldEffect(false);
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
        ArrayList<Student> newStudents = cloud.getStudents();
        for (Student s : newStudents) {
            currentPlayer.getBoard().getEntrance().setStudents(s);
        }

        for(CloudTile cloudTile : controller.getGame().getGameBoard().getClouds()){
            if(cloudTile.getID() == cloud.getID()){
                cloudTile.removeStudents();
            }
        }
        //cloud.removeStudents();

        if(checkWin()) {
            gameHandler.sendSinglePlayer(new WinNotification(), currentPlayer.getPlayerID());
            gameHandler.sendExcept(new LoseNotification(currentPlayer.getNickname()), currentPlayer.getPlayerID());
            gameHandler.endGame();
        }

        for(AssistantCard card : controller.getGame().getGameBoard().getLastAssistantUsed()) {
            controller.getGame().getGameBoard().getLastAssistantUsed().remove(card);
        }
        switchPlayer();


        if(actionPhaseNum == controller.getGame().getPlayersNumber() - 1) {
            actionPhaseNum = 0;
        } else {
            actionPhaseNum++;
        }
        startActionPhase();
    }

    public boolean checkWin() {
        if(controller.getGame().getGameBoard().getIslandCounter() == 3){
            return true;
        }

        for(Player p: controller.getGame().getActivePlayers()){
            if(p.getBoard().getTowerArea().getTowerArea().size() == 0) return true;
            if(p.getAssistantDeck().getDeck().size() == 0) return true;
        }

        return false;
    }
}