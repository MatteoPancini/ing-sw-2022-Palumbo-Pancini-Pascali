package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.clienttoserver.actions.Action;
import it.polimi.ingsw.messages.clienttoserver.actions.PickAssistant;
import it.polimi.ingsw.messages.clienttoserver.actions.UserAction;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.enumerations.Assistants;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

    public Image setAssistantImage(AssistantCard a) {
        Image img = null;
        switch (a.getName().toString()) {
            case "EAGLE" -> {
                img = new Image("@../../graphics/assistants/Assistente (4).png");
            }
            case "DOG" -> {
                img = new Image("@../../graphics/assistants/Assistente (8).png");
            }
            case "ELEPHANT" -> {
                img = new Image("@../../graphics/assistants/Assistente (9).png");
            }
            case "CAT" -> {
                img = new Image("@../../graphics/assistants/Assistente (3).png");
            }
            case "CHEETAH" -> {
                img = new Image("@../../graphics/assistants/Assistente (1).png");
            }
            case "LIZARD" -> {
                img = new Image("@../../graphics/assistants/Assistente (6).png");
            }
            case "OCTOPUS" -> {
                img = new Image("@../../graphics/assistants/Assistente (7).png");
            }
            case "OSTRICH" -> {
                img = new Image("@../../graphics/assistants/Assistente (2).png");
            }
            case "TURTLE" -> {
                img = new Image("@../../graphics/assistants/Assistente (10).png");
            }
            case "FOX" -> {
                img = new Image("@../../graphics/assistants/Assistente (5).png");
            }
        }
        return img;
    }

    public void pickAssistant(ActionEvent e) {
        UserAction action = null;
        ImageView img = (ImageView) e.getSource();
        String picked = (String) img.getId();
        Assistants assistant = null;
        switch(picked) {
            case "cheetah" -> {
                action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.CHEETAH);
                assistant = Assistants.CHEETAH;
            }
            case "ostrich" -> {
                action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.OSTRICH);
                assistant = Assistants.OSTRICH;
            }
            case "cat" -> {
                action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.CAT);
                assistant = Assistants.CAT;
            }
            case "eagle" -> {
                action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.EAGLE);
                assistant = Assistants.EAGLE;
            }
            case "fox" -> {
                action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.FOX);
                assistant = Assistants.FOX;
            }
            case "lizard" -> {
                action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.LIZARD);
                assistant = Assistants.LIZARD;
            }
            case "octopus" -> {
                action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.OCTOPUS);
                assistant = Assistants.OCTOPUS;
            }
            case "dog" -> {
                action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.DOG);
                assistant = Assistants.DOG;
            }
            case "elephant" -> {
                action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.ELEPHANT);
                assistant = Assistants.ELEPHANT;
            }
            case "turtle" -> {
                action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.TURTLE);
                assistant = Assistants.TURTLE;
            }
        }
        if(gui.getModelView().getGameCopy().canPlayAssistant(assistant)) {
            if (action != null) {
                gui.getClientConnection().sendUserInput(action);
                Platform.runLater(() -> {
                    gui.changeStage("finalBoardScene.fxml");
                    MainSceneController controller = (MainSceneController) gui.getControllerFromName("finalBoardScene.fxml");
                    controller.update("STANDARD_UPDATE");
                });

            }
        } else {
            gui.getInfoAlert().setTitle("INFO");
            gui.getInfoAlert().setHeaderText("Information from server");
            gui.getInfoAlert().setContentText("This assistant has already been chosen by an other player. Please choose another one!");
            gui.getInfoAlert().show();
        }
    }
}
