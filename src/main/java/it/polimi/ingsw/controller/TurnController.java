package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.clienttoserver.SerializedMessage;
import it.polimi.ingsw.messages.clienttoserver.actions.Action;
import it.polimi.ingsw.messages.clienttoserver.actions.PickAssistant;
import it.polimi.ingsw.messages.servertoclient.DynamicAnswer;
import it.polimi.ingsw.messages.servertoclient.RequestAction;
import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.player.Player;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;

//TODO M: Aggiungi PropertyChange + cambia la richiesta delle varie operazioni solo sulla base degli activeUsers

public class TurnController {
    private Controller controller;

    private GameHandler gameHandler;


    private boolean isActionPhase;

    private boolean isPianificationPhase;

    public TurnController(Controller controller, GameHandler gameHandler) {
        this.controller = controller;
        this.gameHandler = gameHandler;
        isPianificationPhase = false;
        isActionPhase = false;

    }

    public boolean isPianificationPhase() {
        return isPianificationPhase;
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

    public void pianificationPhase() {
        setPianificationPhase();

        putStudentsOnCloud();

        askAssistantCard();

        /*
        resetPianificationPhase();

        actionPhase();

         */
    }

    public void actionPhase() {
        setActionPhase();



        resetActionPhase();

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
            Player currentPlayer = gameHandler.getGame().getCurrentPlayer();
            gameHandler.setCurrentPlayerId(currentPlayer.getIdPlayer());
            RequestAction assistantAction = new RequestAction(Action.PICK_ASSISTANT);
            gameHandler.sendSinglePlayer(assistantAction, currentPlayer.getIdPlayer());
            gameHandler.sendExcept(new DynamicAnswer("Please wait: player " + currentPlayer.getNickname() + " is choosing an assistant card!", false), currentPlayer.getIdPlayer());
        } else {
            //TODO: Ricorda per ogni assistantCard giocata di ordinare anche gli active players
            for(int i = 0; i < gameHandler.getGame().getActivePlayers().size(); i++) {
                gameHandler.getGame().getActivePlayers().set(i, gameHandler.getGame().getGameBoard().getLastAssistantUsed().get(i).getOwner());
            }

            resetPianificationPhase();
            actionPhase();
        }




    }

    public void playAssistantCard(AssistantCard cardPlayed) {
        /*
        //inserisce in LastAssistantUsed
        for (int i = 0; i < gameHandler.getGame().getPlayersNumber(); i++) {
            AssistantCard chosenCard = gameHandler.getGame().getGameBoard().getLastAssistantUsed().get(i).getOwner().pickAssistant();
            gameHandler.getGame().getGameBoard().getLastAssistantUsed().get(i).getOwner().getAssistantDeck().removeCard(chosenCard);
            gameHandler.getGame().getGameBoard().setLastAssistantUsed(i, chosenCard);
        }
         */

        if(gameHandler.getGame().canPlayAssistant(cardPlayed.getName())) {
            gameHandler.getGame().getGameBoard().getLastAssistantUsed().add(cardPlayed);
            gameHandler.getGame().getCurrentPlayer().getAssistantDeck().removeCard(cardPlayed);


            //ordino
            for(int j = 0; j < gameHandler.getGame().getGameBoard().getLastAssistantUsed().size(); j++) {
                boolean flag = false;
                for(int k = 0; k < gameHandler.getGame().getGameBoard().getLastAssistantUsed().size() - 1; k++) {
                    if(gameHandler.getGame().getGameBoard().getLastAssistantUsed().get(j).getValue() > gameHandler.getGame().getGameBoard().getLastAssistantUsed().get(j + 1).getValue()) {
                        AssistantCard ac = gameHandler.getGame().getGameBoard().getLastAssistantUsed().get(j);
                        gameHandler.getGame().getGameBoard().setLastAssistantUsed(j, gameHandler.getGame().getGameBoard().getLastAssistantUsed().get(j + 1));
                        gameHandler.getGame().getGameBoard().setLastAssistantUsed(j + 1, ac);
                        flag = true;
                    }
                }
                if(!flag) break;
            }



        }

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
}


