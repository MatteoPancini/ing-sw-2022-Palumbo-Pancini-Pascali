package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.clienttoserver.actions.*;
import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.enumerations.Wizards;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.prefs.NodeChangeEvent;

public class PickController implements GUIController{

    private GUI gui;

    @FXML Button redButton;
    @FXML Button yellowButton;
    @FXML Button blueButton;
    @FXML Button greenButton;
    @FXML Button pinkButton;
    @FXML ImageView redImage;
    @FXML ImageView yellowImage;
    @FXML ImageView blueImage;
    @FXML ImageView greenImage;
    @FXML ImageView pinkImage;
    @FXML ImageView island1;
    @FXML ImageView island2;
    @FXML ImageView island3;
    @FXML ImageView island4;
    @FXML ImageView island5;
    @FXML ImageView island6;
    @FXML ImageView island7;
    @FXML ImageView island8;
    @FXML ImageView island9;
    @FXML ImageView island10;
    @FXML ImageView island11;
    @FXML ImageView island12;
    @FXML ImageView cloud1;
    @FXML ImageView cloud2;
    @FXML ImageView cloud3;
    @FXML ImageView cloud4;

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    //TODO controllo che l'assistant scelto non sia stato già preso???
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

    //funziona se le imageView sono ordinate come l'arraylist di playableCharacters
    public void playCharacter(ActionEvent e) {
        UserAction action = null;
        ImageView img = (ImageView) e.getSource();
        CharacterCard character = null;
        if (img.getId().equals("character1")) {
            character = gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(0);
        } else if (img.getId().equals("character2")) {
            character = gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(1);
        } else if (img.getId().equals("character3")) {
            character = gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(2);
        }
        for(CharacterCard c : gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters()) {
            if(c.equals(character)) {
                action = new PickCharacter(character.getName());
            }
        }
        if(action!=null) {
            gui.getClientConnection().sendUserInput(action);
        }
    }

    public void showEffect(ActionEvent e) {
        Button b = (Button) e.getSource();
        String effect = b.getId();
        switch(effect) {
            case "effect1" -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Characters Effect");
                alert.setHeaderText(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(0).getName() + "'s effect");
                alert.setContentText(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(0).getEffect());
            }
            case "effect2" -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Characters Effect");
                alert.setHeaderText(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(1).getName() + "'s effect");
                alert.setContentText(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(1).getEffect());
            }
            case "effect3" -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Characters Effect");
                alert.setHeaderText(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(2).getName() + "'s effect");
                alert.setContentText(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(2).getEffect());
            }
        }
    }

    public void pickCloud(ActionEvent e) {
        UserAction action = null;
        ImageView img = (ImageView) e.getSource();
        String picked = img.getId();
        switch (picked) {
            case "cloud1" -> {
                action = new PickCloud(gui.getModelView().getGameCopy().getGameBoard().getClouds().get(0));
            }
            case "cloud2" -> {
                action = new PickCloud(gui.getModelView().getGameCopy().getGameBoard().getClouds().get(1));
            }
            case "cloud3" -> {
                action = new PickCloud(gui.getModelView().getGameCopy().getGameBoard().getClouds().get(2));
            }
            case "cloud4" -> {
                action = new PickCloud(gui.getModelView().getGameCopy().getGameBoard().getClouds().get(3));
            }
        }
        if (action != null) {
            gui.getClientConnection().sendUserInput(action);
        }
    }

    //TODO controllare la selection box delle isole
    public void pickIsland(ActionEvent e) {
        gui.changeStage("/actions/PickIsland.fxml");
    }

    //TODO come faccio a passargli le isole se sono mergiate? es: se faccio islands.get(2)
    // potrebbe essere stata mergiata con la 1 e non la trova
    public void pickID(ActionEvent e) {
        UserAction action = null;
        ImageView img = (ImageView) e.getSource();
        String island = img.getId();
        switch(island) {
            case "island1" -> {
                action = new PickDestination(gui.getModelView().getGameCopy().getGameBoard().getIslands().get(0));
            } //copiare aumentando l'indice
        }
    }

    public void askAssistant(ArrayList<AssistantCard> assistants) {
        gui.changeStage("PickAssistant.fxml");
    }
    public void askDestination() {
        gui.changeStage("PickDestination.fxml");
    }
    public void askIsland(ArrayList<Island> islands) {
        gui.changeStage("PickIsland.fxml");
        island1.setVisible(false);
        island2.setVisible(false);
        island3.setVisible(false);
        island4.setVisible(false);
        island5.setVisible(false);
        island6.setVisible(false);
        island7.setVisible(false);
        island8.setVisible(false);
        island9.setVisible(false);
        island10.setVisible(false);
        island11.setVisible(false);
        island12.setVisible(false);
        for(Island i : islands) {
            double height = 22;
            double width = 22;
            if(i.getIslandID() == gui.getIDFromIslandImage(island1.toString())) {
                double layoutX = island1.getLayoutX();
                double layoutY = island1.getLayoutY();
                double offsetX = 25;
                double offsetY = 15;
                if(i.getMergedIslands().size() > 1) {
                    island1.setFitWidth(155);
                    island1.setFitHeight(145);
                    island1.setImage(new Image("@../../graphics/wooden_pieces/island2.png"));
                }
                for(Student s : gui.getModelView().getGameCopy().getGameBoard().getIslandById(0).getStudents()) {
                    ImageView stud = new ImageView();
                    setStudentsImage(s);
                    stud.setFitHeight(height);
                    stud.setFitWidth(width);
                    stud.setLayoutX(layoutX + offsetX);
                    stud.setLayoutY(layoutY + offsetY);
                    offsetX+= 5;
                    offsetY+= 5;
                }
                island1.setVisible(true);
            } /* COPIARE PER TUTTE LE 12 ISOLE else if(...) */


        }
    }
    public void askCloud() {
        gui.changeStage("PickCloud.fxml");
        cloud1.setVisible(false);
        cloud2.setVisible(false);
        cloud3.setVisible(false);
        cloud4.setVisible(false);
        int offsetX = 25;
        int offsetY = 15;
        for(CloudTile c : gui.getModelView().getGameCopy().getGameBoard().getClouds()) {
            if(c.getID() == 1) {
                cloud1.setVisible(true);
                for(Student s : gui.getModelView().getGameCopy().getGameBoard().getClouds().get(1).getStudents()) {
                    ImageView stud = null;
                    setStudentsImage(s);
                    stud.setLayoutX(cloud1.getLayoutX() + offsetX);
                    stud.setLayoutY(cloud1.getLayoutY() + offsetY);
                    offsetX+=5;
                    offsetY+=5;
                }
            }
            // COPIARE PER IL RESTO DELLE ISOLE
        }
    }

    public void setStudentsImage(Student s) {
        ImageView stud;
        switch(s.getType().toString()) {
            case "BLUE" -> {
                stud = new ImageView("@../../graphics/wooden_pieces/3D/blueStudent3D.png");
            } case "RED" -> {
                stud = new ImageView("@../../graphics/wooden_pieces/3D/redStudent3D.png");
            } case "GREEN" -> {
                stud = new ImageView("@../../graphics/wooden_pieces/3D/greenStudent3D.png");
            } case "PINK" -> {
                stud = new ImageView("@../../graphics/wooden_pieces/3D/pinkStudent3D.png");
            } case "YELLOW" -> {
                stud = new ImageView("@../../graphics/wooden_pieces/3D/yellowStudent3D.png");
            }
        }
    }

}
