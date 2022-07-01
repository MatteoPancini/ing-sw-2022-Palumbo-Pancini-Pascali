package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.controllers.MainSceneController;
import it.polimi.ingsw.messages.clienttoserver.FourPModeNotification;
import it.polimi.ingsw.messages.servertoclient.*;
import it.polimi.ingsw.messages.servertoclient.errors.ServerError;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.enumerations.Characters;
import javafx.application.Platform;

import java.beans.PropertyChangeSupport;

/**
 * Class that handles the answers from the server notifying the corresponding part of the GUI or
 * CLI through property change listeners.
 */
public class ActionHandler {
    private final ModelView modelView;
    private CLI cli;
    private GUI gui;
    private int showGame = 0;

    private final PropertyChangeSupport view = new PropertyChangeSupport(this);


    /**
     * Constructor of the class with CLI
     * @param cli cli instance
     * @param modelView model view instance
     */
    public ActionHandler(CLI cli, ModelView modelView) {
        this.cli = cli;
        this.modelView = modelView;
        view.addPropertyChangeListener(cli);
    }

    /**
     * Constructor of the class with GUI
     * @param gui gui instance
     * @param modelView model view instance
     */
    public ActionHandler(GUI gui, ModelView modelView) {
        this.gui = gui;
        this.modelView = modelView;
        view.addPropertyChangeListener(gui);
    }

    /**
     * Method answerHandler handles the server answers by notifying the correct part of the cli/gui
     */
    public void answerHandler() {
        Answer answer = modelView.getServerAnswer();
        String initialProperty = "InitialGamePhase";
        String updateProperty = "UpdateModelView";
        if (answer instanceof NumOfPlayerRequest) {
            view.firePropertyChange(initialProperty, null, "RequestPlayerNumber");
        } else if (answer instanceof WizardAnswer) {
            if (((WizardAnswer) answer).getMessage() != null) {
                view.firePropertyChange(initialProperty, null, "RequestWizard");
            }
        } else if (answer instanceof ExpertModeAnswer) {
            view.firePropertyChange(initialProperty, null, "ExpertModeAnswer");
        } else if (answer instanceof DynamicAnswer) {
            notifyDynamicAnswer(answer);
        } else if (answer instanceof RequestAction) {
            String actionType = answer.getMessage().toString();
            view.firePropertyChange("ActionPhase", null, actionType);
        } else if (answer instanceof GameCopy) {
            modelView.setGameCopy((Game) answer.getMessage());
            showGame = showGame + 1;
            if(showGame == 1 || gui != null) {
                view.firePropertyChange(updateProperty, null, answer.getMessage());
            }
        } else if(answer instanceof StartAction) {
            modelView.setActivateInput(true);
            modelView.setAction(true);
            modelView.setPianification(false);
            showGame = 0;
            if (cli != null) {
                cli.showServerMessage(modelView.getServerAnswer());
            }
        }else if(answer instanceof EndAction) {
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
        } else if(answer instanceof MagicPostmanAction) {
            modelView.setMagicPostmanAction(true);
        } else if(answer instanceof MinestrelAction) {
            modelView.setMinestrelAction(true);
            if(cli != null) {
                cli.askCharacterActionsNumber();
            } else if(gui != null) {
                view.firePropertyChange(updateProperty, null, answer.getMessage());
            }
        } else if(answer instanceof JesterAction) {
            modelView.setJesterAction(true);
            if(cli != null) {
                cli.askCharacterActionsNumber();
            } else if(gui != null) {
                view.firePropertyChange(updateProperty, null, answer.getMessage());
            }
        } else if(answer instanceof GrannyHerbsAction) {
            modelView.setGrannyHerbsAction(true);
            if (cli != null) {
                cli.askIsland();
            } else if(gui != null) {
                view.firePropertyChange(updateProperty, null, answer.getMessage());
            }
        } else if(answer instanceof FourPModeNotification) {
            modelView.setFourPlayers(true);
        } else if(answer instanceof NoWinnerGameNotification) {
            if(cli != null) {
                cli.endGameMessage();
            } else if(gui != null) {
                gui.showEndGameNoWinner();
            }
        } else if(answer instanceof PrincessAction) {
            modelView.setPrincessAction(true);
        } else if(answer instanceof MonkAction) {
            modelView.setMonkAction(true);
        } else if(answer instanceof WinNotification) {
            view.firePropertyChange("WinMessage", null, null);
        } else if(answer instanceof LoseNotification) {
            view.firePropertyChange("LoseMessage", null, answer.getMessage());
        } else if(answer instanceof ServerError) {
            if(cli != null) {
                cli.showServerError();
            } else if(gui != null) {
                gui.showServerError();
            }
        }
    }

    /**
     * Method notifyDynamicAnswer notifies the cli/gui with the received dynamic answer from the server
     * @param answer dynamic answer from the server
     */
    private void notifyDynamicAnswer(Answer answer) {
        view.firePropertyChange("DynamicAnswer", null, answer.getMessage());
        modelView.setActivateInput(((DynamicAnswer) answer).isActivateUserInput());
    }


    /**
     * Method makeAction handles the right action to do between all the possible user actions,
     * it is called in the CLI property change
     * @param serverCommand command received from the server
     */
    public void makeAction(String serverCommand) {
        String boardScene = "finalBoardScene.fxml";
        switch (serverCommand) {
            case "PICK_ASSISTANT" -> {
                if(cli!=null) {
                    view.firePropertyChange("UpdateModelView", null, modelView.getServerAnswer());
                    cli.askAssistant();
                }
                else if (gui!=null) {
                    Platform.runLater(() -> {
                        MainSceneController controller = (MainSceneController) gui.getControllerFromName(boardScene);
                        controller.update(serverCommand);
                    });
                }
            }
            case "PICK_CLOUD" -> {
                if(cli!=null) {
                    cli.askCloud();
                } else if (gui!=null) {
                    Platform.runLater(() -> {
                        MainSceneController controller = (MainSceneController) gui.getControllerFromName(boardScene);
                        controller.update(serverCommand);
                    });
                }

            }
            case "PICK_DESTINATION" -> {
                if(cli!=null) {
                    cli.askDestination();
                } else if (gui!=null) {
                    Platform.runLater(() -> {
                        MainSceneController controller = (MainSceneController) gui.getControllerFromName(boardScene);
                        controller.update(serverCommand);
                    });
                }
            }
            case "PICK_STUDENT" -> {
                if(cli != null) {
                    if(modelView.isJesterAction()) {
                        System.out.println(modelView.getCharacterAction());
                        if(modelView.getCharacterAction() % 2 == 0) {
                            for(CharacterCard c : modelView.getGameCopy().getGameBoard().getPlayableCharacters()) {
                                if(c.getName() == Characters.JESTER) {
                                    cli.askStudentJester(c);
                                    break;
                                }
                            }
                        } else {
                            cli.askStudent();
                        }
                        modelView.setCharacterAction(modelView.getCharacterAction() + 1);

                    } else if(modelView.isMinestrelAction()) {
                        if(modelView.getCharacterAction() % 2 == 0) {
                            cli.askPawnType();
                        } else {
                            cli.askStudent();

                        }
                        modelView.setCharacterAction(modelView.getCharacterAction() + 1);

                    } else if(modelView.isPrincessAction()) {
                        for(CharacterCard c : modelView.getGameCopy().getGameBoard().getPlayableCharacters()) {
                            if(c.getName() == Characters.SPOILED_PRINCESS) {
                                cli.askStudentPrincess(c);
                                modelView.setPrincessAction(false);
                                break;
                            }
                        }
                    } else if(modelView.isMonkAction()) {
                        for(CharacterCard c : modelView.getGameCopy().getGameBoard().getPlayableCharacters()) {
                            if(c.getName() == Characters.MONK) {
                                cli.askStudentMonk(c);
                                break;
                            }
                        }
                    } else {
                        cli.askStudent();
                    }
                } else if (gui!=null) {
                    Platform.runLater(() -> {
                        MainSceneController controller = (MainSceneController) gui.getControllerFromName(boardScene);
                        controller.update(serverCommand);
                    });
                }
            }
            case "PICK_MOVES_NUMBER" -> {
               if(cli!=null) {
                   if (modelView.isJesterAction()) {
                       modelView.setJesterAction(false);
                   } else if (modelView.isMinestrelAction()) {
                       modelView.setMinestrelAction(false);
                   }
                   cli.askMoves();
               } else if (gui!=null) {
                   Platform.runLater(() -> {
                       MainSceneController controller = (MainSceneController) gui.getControllerFromName(boardScene);
                       controller.update(serverCommand);
                   });
               }
            }

            case "PICK_CHARACTER" -> {
               if(cli!=null) {
                   cli.askCharacterCard();
               } else if (gui!=null) {
                   Platform.runLater(() -> {
                       MainSceneController controller = (MainSceneController) gui.getControllerFromName(boardScene);
                       controller.update(serverCommand);
                   });
               }
            }
            case "PICK_ISLAND" -> {
                if(cli!=null) {
                    cli.askIsland();
                } else if (gui!=null) {
                    Platform.runLater(() -> {
                        MainSceneController controller = (MainSceneController) gui.getControllerFromName(boardScene);
                        controller.update(serverCommand);
                    });
                }
            }
            case "PICK_PAWN_TYPE" -> {
                if(cli!=null) {
                    cli.askPawnType();
                } else if (gui!=null) {
                    Platform.runLater(() -> {
                        MainSceneController controller = (MainSceneController) gui.getControllerFromName(boardScene);
                        controller.update(serverCommand);
                    });
                }
            }
            default -> {
                cli.showError("Error: no such action");
                modelView.setActivateInput(false);

            }
        }
    }

}
