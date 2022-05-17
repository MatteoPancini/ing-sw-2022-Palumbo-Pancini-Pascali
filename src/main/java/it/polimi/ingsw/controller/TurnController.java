package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.clienttoserver.actions.Action;
import it.polimi.ingsw.messages.servertoclient.*;
import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.enumerations.PawnType;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.player.DiningRoom;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.Collections;


public class TurnController {
    private Controller controller;

    private GameHandler gameHandler;

    private Player currentPlayer;

    private ExpertController expertController;

    private int studentRequest;


    private boolean isActionPhase;

    private int actionPhaseNum;

    private boolean isPianificationPhase;


    private Student studentToMove;

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
        askStudentDestination();
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
        setPianificationPhase();

        gameHandler.sendBroadcast(new DynamicAnswer(" ___  _   _   _  _  _  ___  _   __   _  ___  _   _   _  _   ___  _ _   _   __  ___ \n" +
                "| o \\| | / \\ | \\| || || __|| | / _| / \\|_ _|| | / \\ | \\| | | o \\| U | / \\ / _|| __|\n" +
                "|  _/| || o || \\\\ || || _| | |( (_ | o || | | |( o )| \\\\ | |  _/|   || o |\\_ \\| _| \n" +
                "|_|  |_||_n_||_|\\_||_||_|  |_| \\__||_n_||_| |_| \\_/ |_|\\_| |_|  |_n_||_n_||__/|___|\n" +
                "                                                                                   ", false));

        putStudentsOnCloud();

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

        if(actionPhaseNum == gameHandler.getPlayersNumber()) {

            startPianificationPhase();
        } else {
            setActionPhase();

            studentRequest = 0;

            askStudent(studentRequest);
        }



        //resetActionPhase();

    }

    public void askStudent(int studentNum) {
        if(studentNum == 0) {
            setCurrentPlayer();
        }
        if(studentNum < 3) {
            RequestAction studentAction = new RequestAction(Action.PICK_STUDENT);
            gameHandler.sendSinglePlayer(studentAction, currentPlayer.getPlayerID());
            gameHandler.sendExcept(new DynamicAnswer("Please wait: player " + currentPlayer.getNickname() + " is choosing a student to move!", false), currentPlayer.getPlayerID());
        } else {
            askMotherNatureMoves();
        }
        return;


    }

    public void askMotherNatureMoves() {
        RequestAction moveMotherNatureAction = new RequestAction(Action.PICK_MOVES_NUMBER);
        gameHandler.sendSinglePlayer(moveMotherNatureAction, currentPlayer.getPlayerID());
        gameHandler.sendExcept(new DynamicAnswer("Please wait: player " + currentPlayer.getNickname() + " is choosing where to move mother nature!", false), currentPlayer.getPlayerID());

    }

    public void putStudentsOnCloud() {
        for (CloudTile cloud : gameHandler.getGame().getGameBoard().getClouds()) {
            ArrayList<Student> newStudents = new ArrayList<>();
            Collections.shuffle(gameHandler.getGame().getGameBoard().getStudentsBag());
            int studentsNumber;
            if(gameHandler.getGame().getPlayersNumber() == 3) studentsNumber = 4;
            else studentsNumber = 3;
            for (int j = 0; j < studentsNumber; j++) {
                newStudents.add(gameHandler.getGame().getGameBoard().getStudentsBag().get(0));
                //newStudents.get(j) = gameHandler.getGame().getGameBoard().getStudentsBag().get(0);
                gameHandler.getGame().getGameBoard().removeStudents(0);
            }
            cloud.setStudents(newStudents);
        }
    }


    public void askAssistantCard() {
        if(gameHandler.getGame().getGameBoard().getLastAssistantUsed().size() != gameHandler.getGame().getActivePlayers().size()) {
            setCurrentPlayer();

            RequestAction assistantAction = new RequestAction(Action.PICK_ASSISTANT);
            gameHandler.sendSinglePlayer(assistantAction, currentPlayer.getPlayerID());
            gameHandler.sendExcept(new DynamicAnswer("Please wait: player " + currentPlayer.getNickname() + " is choosing an assistant card!", false), currentPlayer.getPlayerID());
        } else {
            //riordina per ogni assistantCard giocata anche gli active players
            for(int i = 0; i < gameHandler.getGame().getActivePlayers().size(); i++) {
                gameHandler.getGame().getActivePlayers().set(i, gameHandler.getGame().getGameBoard().getLastAssistantUsed().get(i).getOwner());
            }

            resetPianificationPhase();

            GameCopy gameCopy = new GameCopy(gameHandler.getGame());
            gameHandler.sendBroadcast(gameCopy);

            startActionPhase();
        }

    }

    public void setCurrentPlayer() {
        gameHandler.getGame().switchToNextPlayer();
        this.currentPlayer = gameHandler.getGame().getCurrentPlayer();
        gameHandler.setCurrentPlayerId(currentPlayer.getPlayerID());

    }

    public void askStudentDestination() {
        RequestAction studentDestinationAction = new RequestAction(Action.PICK_DESTINATION);
        gameHandler.sendSinglePlayer(studentDestinationAction, currentPlayer.getPlayerID());
        gameHandler.sendExcept(new DynamicAnswer("Please wait: player " + currentPlayer.getNickname() + " is choosing where to move his student!", false), currentPlayer.getPlayerID());
    }

    public void playAssistantCard(AssistantCard cardPlayed) {

        if(gameHandler.getGame().canPlayAssistant(cardPlayed.getName())) {
            gameHandler.getGame().getGameBoard().getLastAssistantUsed().add(cardPlayed);
            gameHandler.getGame().getCurrentPlayer().getAssistantDeck().removeCard(cardPlayed);


            //ordino
            for(int j = 0; j < gameHandler.getGame().getGameBoard().getLastAssistantUsed().size(); j++) {
                boolean flag = false;
                for (int k = 0; k < gameHandler.getGame().getGameBoard().getLastAssistantUsed().size() - 1; k++) {
                    if (gameHandler.getGame().getGameBoard().getLastAssistantUsed().get(j).getValue() > gameHandler.getGame().getGameBoard().getLastAssistantUsed().get(j + 1).getValue()) {
                        AssistantCard ac = gameHandler.getGame().getGameBoard().getLastAssistantUsed().get(j);
                        gameHandler.getGame().getGameBoard().setLastAssistantUsed(j, gameHandler.getGame().getGameBoard().getLastAssistantUsed().get(j + 1));
                        gameHandler.getGame().getGameBoard().setLastAssistantUsed(j + 1, ac);
                        flag = true;
                    }
                }
                if (!flag) break;
            }

            askAssistantCard();
        }


    }

    public void checkProfessorInfluence() {
        int currentPlayerStudents = 0;
        int professorWinnerId = 0;

        for(Player p : gameHandler.getGame().getActivePlayers()) {
            for(int i = 0; i < 5; i++) {
                if(p.getBoard().getDiningRoom().getDiningRoom().get(i).getColor() == studentToMove.getType()) {
                    if(p.getBoard().getDiningRoom().getDiningRoom().get(i).getTable().size() > currentPlayerStudents) {
                        professorWinnerId = p.getPlayerID();
                    }
                }
            }
        }

        if(currentPlayer.getPlayerID() == professorWinnerId) {
            for(Player p : gameHandler.getGame().getActivePlayers()) {
                if(p.getBoard().getProfessorTable().getCellByColor(studentToMove.getType()) != null && p.getPlayerID() != currentPlayer.getPlayerID()) {
                    p.getBoard().getProfessorTable().getCellByColor(studentToMove.getType()).resetProfessor();
                    currentPlayer.getBoard().getProfessorTable().getCellByColor(studentToMove.getType()).setProfessor(gameHandler.getGame().getGameBoard().getProfessorByColor(studentToMove.getType()));
                }
            }

        }

    }


    public void moveStudentsToDiningRoom(DiningRoom chosenDiningRoom) {
        chosenDiningRoom.setDiningRoom(studentToMove);
        checkProfessorInfluence();
        studentRequest++;
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
        chosenIsland.addStudent(studentToMove);
        studentRequest++;
        askStudent(studentRequest);


    }


    public void moveMotherNature(int moves) {
        int currPosition = gameHandler.getGame().getGameBoard().getMotherNature().getPosition();
        int newPosition = (currPosition + moves) % 12;
        gameHandler.getGame().getGameBoard().getMotherNature().setPosition(newPosition);

        checkIslandInfluence(newPosition);

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
        for(Student student : gameHandler.getGame().getGameBoard().getIslands().get(islandId - 1).getStudents()) {
            PawnType studentType = student.getType();
            Player studentOwner = gameHandler.getGame().getGameBoard().getProfessorByColor(studentType).getOwner();
            studentOwner.setIslandInfluence(studentOwner.getIslandInfluence() + 1);

        }

        TowerColor towerColor = gameHandler.getGame().getGameBoard().getIslands().get(islandId - 1).getMergedTowers().get(0).getColor();
        for(Player p : gameHandler.getGame().getActivePlayers()) {
            if(p.getBoard().getTowerArea().getTowerArea().get(0).getColor() == towerColor) {
                p.setIslandInfluence(p.getIslandInfluence() + gameHandler.getGame().getGameBoard().getIslands().get(islandId - 1).getMergedTowers().size());
            }
        }



        int islandInfluence = 0;


        for(Player player : gameHandler.getGame().getActivePlayers()) {
            if(player.getIslandInfluence() > islandInfluence || (player.getBoard().getTowerArea().getTowerArea().get(0).getColor() == gameHandler.getGame().getGameBoard().getIslands().get(islandId - 1).getTower().getColor()) && player.getIslandInfluence() >= islandInfluence) {
                islandInfluence = player.getIslandInfluence();
            }
        }

        if(currentPlayer.getIslandInfluence() == islandInfluence) {
            //controllare che non abbia già costruito
            if(gameHandler.getGame().getGameBoard().getIslands().get(islandId - 1).getTower() != null) {
                //aggiunge torre ev
                currentPlayer.getBoard().getTowerArea().moveTowerToIsland(gameHandler.getGame().getGameBoard().getIslandById(islandId));
            } else {
                for(Player p : gameHandler.getGame().getActivePlayers()) {
                    if(p.getBoard().getTowerArea().getTowerArea().get(0).getColor() == gameHandler.getGame().getGameBoard().getIslandById(islandId).getTower().getColor()) {
                        gameHandler.getGame().getGameBoard().getIslandById(islandId).moveTowerToArea(p.getBoard().getTowerArea());
                        break;
                    }
                }
                currentPlayer.getBoard().getTowerArea().moveTowerToIsland(gameHandler.getGame().getGameBoard().getIslandById(islandId));

            }

            if(gameHandler.getGame().getGameBoard().getIslandById(islandId).hasLeft()) {
                gameHandler.getGame().getGameBoard().getIslandById(islandId).merge(gameHandler.getGame().getGameBoard().getIslandById(islandId - 1));
            }
            if(gameHandler.getGame().getGameBoard().getIslandById(islandId).hasRight()) {
                gameHandler.getGame().getGameBoard().getIslandById(islandId).merge(gameHandler.getGame().getGameBoard().getIslandById(islandId + 1));
            }

        }


    }

    public void askCloud() {
        RequestAction cloudAction = new RequestAction(Action.PICK_CLOUD);
        gameHandler.sendSinglePlayer(cloudAction, currentPlayer.getPlayerID());
        gameHandler.sendExcept(new DynamicAnswer("Please wait: player " + currentPlayer.getNickname() + " is choosing a cloud to take!", false), currentPlayer.getPlayerID());
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
        cloud.removeStudents();

        if(checkWin()) {
            gameHandler.sendSinglePlayer(new WinNotification(), currentPlayer.getPlayerID());
            gameHandler.sendExcept(new LoseNotification(currentPlayer.getNickname()), currentPlayer.getPlayerID());
            gameHandler.endGame();
        }

        for(AssistantCard card : controller.getGame().getGameBoard().getLastAssistantUsed()) {
            controller.getGame().getGameBoard().getLastAssistantUsed().remove(card);
        }
        setCurrentPlayer();

        actionPhaseNum++;
        startActionPhase();
    }

    public boolean checkWin() {
        if(gameHandler.getGame().getGameBoard().getIslands().size() == 3){
            return true;
        }

        for(Player p: gameHandler.getGame().getActivePlayers()){
            if(p.getBoard().getTowerArea().getTowerArea().size() == 0) return true;
            if(p.getAssistantDeck().getDeck().size() == 0) return true;
        }

        return false;
    }
}


