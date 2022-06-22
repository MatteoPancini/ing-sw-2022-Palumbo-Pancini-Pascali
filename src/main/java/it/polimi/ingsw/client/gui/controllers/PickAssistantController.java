package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.clienttoserver.actions.Action;
import it.polimi.ingsw.messages.clienttoserver.actions.PickAssistant;
import it.polimi.ingsw.messages.clienttoserver.actions.UserAction;
import it.polimi.ingsw.model.enumerations.Assistants;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;

public class PickAssistantController implements GUIController {

    private GUI gui;

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
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
            }
        } else {
            gui.getInfoAlert().setTitle("INFO");
            gui.getInfoAlert().setHeaderText("Information from server");
            gui.getInfoAlert().setContentText("This assistant has already been chosen by an other player. Please choose another one!");
            gui.getInfoAlert().show();
        }
    }
}
