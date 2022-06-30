package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.clienttoserver.actions.Action;
import it.polimi.ingsw.messages.clienttoserver.actions.PickAssistant;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.enumerations.Assistants;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * PickAssistantController handles the assistant choice by showing a new stage
 */
public class PickAssistantController implements GUIController {

    private GUI gui;

    @FXML ImageView cheetah;
    @FXML ImageView ostrich;
    @FXML ImageView turtle;
    @FXML ImageView elephant;
    @FXML ImageView dog;
    @FXML ImageView octopus;
    @FXML ImageView lizard;
    @FXML ImageView cat;
    @FXML ImageView eagle;
    @FXML ImageView fox;

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /**
     * Method askAssistant asks the user to choose an assistant between the available ones
     */
    public void askAssistant() {
        gui.changeStage("PickAssistant.fxml");
        cheetah.setVisible(false);
        ostrich.setVisible(false);
        cat.setVisible(false);
        eagle.setVisible(false);
        fox.setVisible(false);
        lizard.setVisible(false);
        octopus.setVisible(false);
        dog.setVisible(false);
        elephant.setVisible(false);
        turtle.setVisible(false);

        for(AssistantCard a : gui.getModelView().getGameCopy().getCurrentPlayer().getAssistantDeck().getDeck()) {
            if(a.getName().equals(Assistants.CHEETAH)) {
                if(gui.getModelView().getGameCopy().canPlayAssistant(a.getName())) {
                    cheetah.setVisible(true);
                    cheetah.setImage(setAssistantImage(a));
                }
            } else if(a.getName().equals(Assistants.OSTRICH)) {
                if(gui.getModelView().getGameCopy().canPlayAssistant(a.getName())) {
                    ostrich.setVisible(true);
                    ostrich.setImage(setAssistantImage(a));
                }
            } else if(a.getName().equals(Assistants.CAT)) {
                if(gui.getModelView().getGameCopy().canPlayAssistant(a.getName())) {
                    cat.setVisible(true);
                    cat.setImage(setAssistantImage(a));
                }
            } else if(a.getName().equals(Assistants.EAGLE)) {
                if(gui.getModelView().getGameCopy().canPlayAssistant(a.getName())) {
                    eagle.setVisible(true);
                    eagle.setImage(setAssistantImage(a));
                }
            } else if(a.getName().equals(Assistants.FOX)) {
                if(gui.getModelView().getGameCopy().canPlayAssistant(a.getName())) {
                    fox.setVisible(true);
                    fox.setImage(setAssistantImage(a));
                }
            } else if(a.getName().equals(Assistants.LIZARD)) {
                if(gui.getModelView().getGameCopy().canPlayAssistant(a.getName())) {
                    lizard.setVisible(true);
                    lizard.setImage(setAssistantImage(a));
                }
            } else if(a.getName().equals(Assistants.OCTOPUS)) {
                if(gui.getModelView().getGameCopy().canPlayAssistant(a.getName())) {
                    octopus.setVisible(true);
                    octopus.setImage(setAssistantImage(a));
                }
            } else if(a.getName().equals(Assistants.DOG)) {
                if(gui.getModelView().getGameCopy().canPlayAssistant(a.getName())) {
                    dog.setVisible(true);
                    dog.setImage(setAssistantImage(a));
                }
            } else if(a.getName().equals(Assistants.ELEPHANT)) {
                if(gui.getModelView().getGameCopy().canPlayAssistant(a.getName())) {
                    elephant.setVisible(true);
                    elephant.setImage(setAssistantImage(a));
                }
            } else if(a.getName().equals(Assistants.TURTLE)) {
                if(gui.getModelView().getGameCopy().canPlayAssistant(a.getName())) {
                    turtle.setVisible(true);
                    turtle.setImage(setAssistantImage(a));
                }
            }
        }
    }

    /**
     * Method setAssistantImage sets a new assistant image to the corresponding assistant card
     * @param a assistant card
     * @return corresponding image
     */
    public Image setAssistantImage(AssistantCard a) {
        Image img = null;
        switch (a.getName().toString()) {
            case "EAGLE" -> {
                img = new Image("/graphics/assistants/Assistente (4).png");
            }
            case "DOG" -> {
                img = new Image("/graphics/assistants/Assistente (8).png");
            }
            case "ELEPHANT" -> {
                img = new Image("/graphics/assistants/Assistente (9).png");
            }
            case "CAT" -> {
                img = new Image("/graphics/assistants/Assistente (3).png");
            }
            case "CHEETAH" -> {
                img = new Image("/graphics/assistants/Assistente (1).png");
            }
            case "LIZARD" -> {
                img = new Image("/graphics/assistants/Assistente (6).png");
            }
            case "OCTOPUS" -> {
                img = new Image("/graphics/assistants/Assistente (7).png");
            }
            case "OSTRICH" -> {
                img = new Image("/graphics/assistants/Assistente (2).png");
            }
            case "TURTLE" -> {
                img = new Image("/graphics/assistants/Assistente (10).png");
            }
            case "FOX" -> {
                img = new Image("/graphics/assistants/Assistente (5).png");
            }
        }
        return img;
    }

    /**
     * Method backToMainScene changes the stage going back to the main board scene
     */
    public void backToMainScene() {
        Platform.runLater(() -> {
            gui.changeStage("finalBoardScene.fxml");
            MainSceneController controller = (MainSceneController) gui.getControllerFromName("finalBoardScene.fxml");
            controller.update("STANDARD_UPDATE");
        });
    }

    /**
     * Method pickAssistant1 handles the click event on the assistant 1 (Cheetah)
     */
    public void pickAssistant1() {
        if(gui.getModelView().getGameCopy().canPlayAssistant(Assistants.CHEETAH)) {
            gui.getClientConnection().sendUserInput(new PickAssistant(Action.PICK_ASSISTANT, Assistants.CHEETAH));
            backToMainScene();
        } else {
            gui.getInfoAlert().setTitle("INFO");
            gui.getInfoAlert().setHeaderText("Information from server");
            gui.getInfoAlert().setContentText("This assistant has already been chosen by an other player. Please choose another one!");
            gui.getInfoAlert().show();
        }
    }
    /**
     * Method pickAssistant2 handles the click event on the assistant 2 (Ostrich)
     */
    public void pickAssistant2() {
        if(gui.getModelView().getGameCopy().canPlayAssistant(Assistants.OSTRICH)) {
            gui.getClientConnection().sendUserInput(new PickAssistant(Action.PICK_ASSISTANT, Assistants.OSTRICH));
            backToMainScene();
        } else {
            gui.getInfoAlert().setTitle("INFO");
            gui.getInfoAlert().setHeaderText("Information from server");
            gui.getInfoAlert().setContentText("This assistant has already been chosen by an other player. Please choose another one!");
            gui.getInfoAlert().show();
        }
    }
    /**
     * Method pickAssistant3 handles the click event on the assistant 3 (Cat)
     */
    public void pickAssistant3() {
        if(gui.getModelView().getGameCopy().canPlayAssistant(Assistants.CAT)) {
            gui.getClientConnection().sendUserInput(new PickAssistant(Action.PICK_ASSISTANT, Assistants.CAT));
            backToMainScene();
        } else {
            gui.getInfoAlert().setTitle("INFO");
            gui.getInfoAlert().setHeaderText("Information from server");
            gui.getInfoAlert().setContentText("This assistant has already been chosen by an other player. Please choose another one!");
            gui.getInfoAlert().show();
        }
    }
    /**
     * Method pickAssistant4 handles the click event on the assistant 4 (Eagle)
     */
    public void pickAssistant4() {
        if(gui.getModelView().getGameCopy().canPlayAssistant(Assistants.EAGLE)) {
            gui.getClientConnection().sendUserInput(new PickAssistant(Action.PICK_ASSISTANT, Assistants.EAGLE));
            backToMainScene();
        } else {
            gui.getInfoAlert().setTitle("INFO");
            gui.getInfoAlert().setHeaderText("Information from server");
            gui.getInfoAlert().setContentText("This assistant has already been chosen by an other player. Please choose another one!");
            gui.getInfoAlert().show();
        }
    }
    /**
     * Method pickAssistant5 handles the click event on the assistant 5 (Fox)
     */
    public void pickAssistant5() {
        if(gui.getModelView().getGameCopy().canPlayAssistant(Assistants.FOX)) {
            gui.getClientConnection().sendUserInput(new PickAssistant(Action.PICK_ASSISTANT, Assistants.FOX));
            backToMainScene();
        } else {
            gui.getInfoAlert().setTitle("INFO");
            gui.getInfoAlert().setHeaderText("Information from server");
            gui.getInfoAlert().setContentText("This assistant has already been chosen by an other player. Please choose another one!");
            gui.getInfoAlert().show();
        }
    }
    /**
     * Method pickAssistant6 handles the click event on the assistant 6 (Lizard)
     */
    public void pickAssistant6() {
        if(gui.getModelView().getGameCopy().canPlayAssistant(Assistants.LIZARD)) {
            gui.getClientConnection().sendUserInput(new PickAssistant(Action.PICK_ASSISTANT, Assistants.LIZARD));
            backToMainScene();
        } else {
            gui.getInfoAlert().setTitle("INFO");
            gui.getInfoAlert().setHeaderText("Information from server");
            gui.getInfoAlert().setContentText("This assistant has already been chosen by an other player. Please choose another one!");
            gui.getInfoAlert().show();
        }
    }
    /**
     * Method pickAssistant7 handles the click event on the assistant 7 (Octopus)
     */
    public void pickAssistant7() {
        if(gui.getModelView().getGameCopy().canPlayAssistant(Assistants.OCTOPUS)) {
            gui.getClientConnection().sendUserInput(new PickAssistant(Action.PICK_ASSISTANT, Assistants.OCTOPUS));
            backToMainScene();
        } else {
            gui.getInfoAlert().setTitle("INFO");
            gui.getInfoAlert().setHeaderText("Information from server");
            gui.getInfoAlert().setContentText("This assistant has already been chosen by an other player. Please choose another one!");
            gui.getInfoAlert().show();
        }
    }
    /**
     * Method pickAssistant8 handles the click event on the assistant 8 (Dog)
     */
    public void pickAssistant8() {
        if(gui.getModelView().getGameCopy().canPlayAssistant(Assistants.DOG)) {
            gui.getClientConnection().sendUserInput(new PickAssistant(Action.PICK_ASSISTANT, Assistants.DOG));
            backToMainScene();
        } else {
            gui.getInfoAlert().setTitle("INFO");
            gui.getInfoAlert().setHeaderText("Information from server");
            gui.getInfoAlert().setContentText("This assistant has already been chosen by an other player. Please choose another one!");
            gui.getInfoAlert().show();
        }
    }
    /**
     * Method pickAssistant9 handles the click event on the assistant 9 (Elephant)
     */
    public void pickAssistant9() {
        if(gui.getModelView().getGameCopy().canPlayAssistant(Assistants.ELEPHANT)) {
            gui.getClientConnection().sendUserInput(new PickAssistant(Action.PICK_ASSISTANT, Assistants.ELEPHANT));
            backToMainScene();
        } else {
            gui.getInfoAlert().setTitle("INFO");
            gui.getInfoAlert().setHeaderText("Information from server");
            gui.getInfoAlert().setContentText("This assistant has already been chosen by an other player. Please choose another one!");
            gui.getInfoAlert().show();
        }
    }
    /**
     * Method pickAssistant10 handles the click event on the assistant 10 (Turtle)
     */
    public void pickAssistant10() {
        if(gui.getModelView().getGameCopy().canPlayAssistant(Assistants.TURTLE)) {
            gui.getClientConnection().sendUserInput(new PickAssistant(Action.PICK_ASSISTANT, Assistants.TURTLE));
            backToMainScene();
        } else {
            gui.getInfoAlert().setTitle("INFO");
            gui.getInfoAlert().setHeaderText("Information from server");
            gui.getInfoAlert().setContentText("This assistant has already been chosen by an other player. Please choose another one!");
            gui.getInfoAlert().show();
        }
    }
}
