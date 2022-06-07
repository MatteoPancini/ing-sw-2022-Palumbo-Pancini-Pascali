package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.clienttoserver.FourPModeNotification;
import it.polimi.ingsw.messages.clienttoserver.actions.*;
import it.polimi.ingsw.messages.servertoclient.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.player.Table;
import javafx.scene.control.Alert;

import java.beans.PropertyChangeSupport;
import java.util.Objects;

public class ActionHandler {
    private ModelView modelView;
    private CLI cli;
    private GUI gui;
    private int showGame = 0;

    private final PropertyChangeSupport view = new PropertyChangeSupport(this);


    public ActionHandler(CLI cli, ModelView modelView) {
        this.cli = cli;
        this.modelView = modelView;
        view.addPropertyChangeListener(cli);
    }

    public ActionHandler(GUI gui, ModelView modelView) {
        this.gui = gui;
        this.modelView = modelView;
        view.addPropertyChangeListener(gui);
    }

    //notifica la CLI con i listeners
    public void answerHandler() {
        Answer answer = modelView.getServerAnswer();
        //System.out.println("Analizzo la server answer: " + answer.getMessage());
        if (answer instanceof NumOfPlayerRequest) {
            view.firePropertyChange("InitialGamePhase", null, "RequestPlayerNumber");
        } else if (answer instanceof WizardAnswer) {
            if (((WizardAnswer) answer).getMessage() != null) {
                view.firePropertyChange("InitialGamePhase", null, "RequestWizard");
            } else {
                modelView.setWizardName(((WizardAnswer) answer).getWizard());
                //cli.showServerMessage();
            }
        } else if (answer instanceof ExpertModeAnswer) {
            view.firePropertyChange("InitialGamePhase", null, "ExpertModeAnswer");
        } else if (answer instanceof DynamicAnswer) {
            notifyDynamicAnswer(answer);
        } else if (answer instanceof RequestAction) {
            String actionType = answer.getMessage().toString();
            view.firePropertyChange("ActionPhase", null, actionType);
        } else if (answer instanceof GameCopy) {
            modelView.setGameCopy((Game) answer.getMessage());
            showGame = showGame + 1;
            if(showGame == 1) {
                view.firePropertyChange("UpdateModelView", null, answer.getMessage());
            }
            //System.out.println("non aggiorno");
        } else if(answer instanceof StartAction) {
            modelView.setActivateInput(true);
            modelView.setAction(true);
            modelView.setPianification(false);
            showGame = 0;
            if (cli != null) {
                cli.showServerMessage(modelView.getServerAnswer());
            } else if (gui != null) {
                gui.getInfoAlert().setTitle("INFO");
                gui.getInfoAlert().setHeaderText("Information from server");
                gui.getInfoAlert().setContentText(modelView.getServerAnswer().getMessage().toString());
                gui.getInfoAlert().show();
            }
        }else if(answer instanceof EndAction) {
            modelView.setStartPlaying(false);
            if (cli != null) {
                cli.showServerMessage(modelView.getServerAnswer());
            } else if (gui != null) {
                gui.getInfoAlert().setTitle("INFO");
                gui.getInfoAlert().setHeaderText("Information from server");
                gui.getInfoAlert().setContentText(modelView.getServerAnswer().getMessage().toString());
                gui.getInfoAlert().show();
            }
        } else if(answer instanceof StartPianification) {
            modelView.setActivateInput(true);
            modelView.setPianification(true);
            modelView.setAction(false);
            showGame = showGame + 1;
            if (cli != null) {
                cli.showServerMessage(modelView.getServerAnswer());
            } else if (gui != null) {
                gui.getInfoAlert().setTitle("INFO");
                gui.getInfoAlert().setHeaderText("Information from server");
                gui.getInfoAlert().setContentText(modelView.getServerAnswer().getMessage().toString());
                gui.getInfoAlert().show();
            }
        } else if(answer instanceof MagicPostmanAction) {
            modelView.setMagicPostmanAction(true);
        } else if(answer instanceof MinestrelAction) {
            modelView.setMinestrelAction(true);
            cli.askCharacterActionsNumber();
        } else if(answer instanceof JesterAction) {
            modelView.setJesterAction(true);
            cli.askCharacterActionsNumber();
        } else if(answer instanceof GrannyHerbsAction) {
            modelView.setGrannyHerbsAction(true);
            if (cli != null) {
                cli.askIsland(modelView.getGameCopy().getGameBoard().getIslands());
            } /*else if(gui != null) {
                gui.get
            }*/
        } else if(answer instanceof FourPModeNotification) {
            modelView.setFourPlayers(true);
        }
    }

    private void notifyDynamicAnswer(Answer answer) {
        view.firePropertyChange("DynamicAnswer", null, answer.getMessage());
        modelView.setActivateInput(((DynamicAnswer) answer).isActivateUserInput());
    }

    //viene chiamato all'interno di propertyChange della CLI, notificata dall'Action Handler
    public void makeAction(String serverCommand) {
        switch (serverCommand) {
            case "PICK_ASSISTANT" -> {
                if(cli!=null) {
                    cli.askAssistant();
                }
                else if (gui!=null) {
                    System.out.println("Entro in pick assistant");
                    gui.changeStage("actions/PickAssistant.fxml");
                }
            }
            case "PICK_CLOUD" -> {
                cli.askCloud(modelView.getGameCopy().getGameBoard().getClouds());

            }
            case "PICK_DESTINATION" -> {
                cli.askDestination();
            }
            case "PICK_STUDENT" -> {
                if(modelView.isJesterAction()) {
                    if(modelView.getCharacterAction() % 2 == 0) {
                        for(CharacterCard c : modelView.getGameCopy().getGameBoard().getPlayableCharacters()) {
                            if(Objects.equals(c.getName().toString(), "JESTER")) {
                                cli.askStudentJester(c);
                                break;
                            }
                        }
                    } else {
                        cli.askStudent(modelView.getGameCopy().getCurrentPlayer().getBoard());
                    }
                    modelView.setCharacterAction(modelView.getCharacterAction() + 1);

                } else if(modelView.isMinestrelAction()) {
                    if(modelView.getCharacterAction() % 2 == 0) {
                        for(CharacterCard c : modelView.getGameCopy().getGameBoard().getPlayableCharacters()) {
                            if(Objects.equals(c.getName().toString(), "MINESTREL")) {
                                cli.askStudentMinestrel(c);
                                break;
                            }
                        }
                    } else {
                        cli.askStudent(modelView.getGameCopy().getCurrentPlayer().getBoard());

                    }
                    modelView.setCharacterAction(modelView.getCharacterAction() + 1);

                } else {
                    cli.askStudent(modelView.getGameCopy().getCurrentPlayer().getBoard());
                }

            }
            case "PICK_MOVES_NUMBER" -> {
                cli.askMoves(modelView.getGameCopy().getCurrentPlayer().getChosenAssistant());

            }

            case "PICK_CHARACTER" -> {
                cli.askCharacterCard(modelView.getGameCopy().getGameBoard().getPlayableCharacters());

            }
            case "PICK_ISLAND" -> {
                cli.askIsland(modelView.getGameCopy().getGameBoard().getIslands());
            }

            case "PICK_PAWN_TYPE" -> {
                cli.askPawnType();

            }

            case "PICK_STUDENT_MONK" -> {
                for(CharacterCard c : modelView.getGameCopy().getGameBoard().getPlayableCharacters()) {
                    if(c.getName().toString() == "MONK") {
                        cli.askStudentMonk(c);
                        break;
                    }
                }
            }

            case "PICK_PRINCESS_STUDENT" -> {
                for(CharacterCard c : modelView.getGameCopy().getGameBoard().getPlayableCharacters()) {
                    if(c.getName().toString() == "SPOILED_PRINCESS") {
                        cli.askStudentPrincess(c);
                        break;
                    }
                }
            }


            default -> {
                cli.showError("Error: no such action");
                modelView.setActivateInput(false);

            }
        }
    }





/*
    public void updateStudentMove(String student, String dest) {
        if(((PickDestination) modelView.getDestinationUserAction()).getChosenIsland() == -1) {
            for(Table t : modelView.getVisualBoard().getDiningRoom().getDiningRoom()) {
                if(t.getColor().equals(((PickStudent) modelView.getLastUserAction()).getChosenStudent())) {
                    t.addStudent(((PickStudent) modelView.getLastUserAction()).getChosenStudent());
                }
            }
        }
        else if(((PickDestination) modelView.getDestinationUserAction()).getDiningRoom() == null) {
            modelView.getVisualBoard().getIslandsView().get(((PickDestination) modelView.getLastUserAction()).getChosenIsland())
                    .getStudents().add(((PickStudent) modelView.getLastUserAction()).getChosenStudent());
        }
        modelView.setDestinationUserAction(null);
    }
    public void updateModelView(String actionName) {
        switch(actionName) {
            case "PICKASSISTANT" -> {
                modelView.getVisualBoard().
                        setPlayedCard(takeCard(((PickAssistant) modelView.getLastUserAction()).getChosenAssistant()), modelView.getCurrentPlayer());
            }
            case "PICKCLOUD" -> {
                for(Student s : modelView.getVisualBoard().getClouds().get(((PickCloud) modelView.getLastUserAction()).getChosenCloud()).getStudents()) {
                    modelView.getCurrentPlayer().getBoard().getEntrance().getStudents().add(s);
                    modelView.getVisualBoard().getClouds().get(((PickCloud) modelView.getLastUserAction()).getChosenCloud());
                }
            }
            case "PICKMOVESNUMBER" -> {
                modelView.getVisualBoard().setMotherNature(((PickMovesNumber) modelView.getLastUserAction()).getMoves()
                        + modelView.getVisualBoard().getMotherNature().getPosition());
            }
            case "PICKCHARACTER" -> {
                modelView.getVisualBoard().getCharacters().add(((PickCharacter) modelView.getLastUserAction()).getChosenCharacter());
            }
        }
    }

    public AssistantCard takeCard (AssistantCard card){
        for(AssistantCard c : modelView.getVisualBoard().getLastAssistantUsed()){
            if(card.getName() == c.getName()){
                modelView.getVisualBoard().getLastAssistantUsed().remove(card);
                return card;
            }
        }
        return null;
    }

 */

}
