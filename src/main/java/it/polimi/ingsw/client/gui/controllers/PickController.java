package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.clienttoserver.actions.*;
import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.enumerations.PawnType;
import it.polimi.ingsw.model.enumerations.Wizards;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Table;
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
import java.util.Objects;
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
    @FXML ImageView character1;
    @FXML ImageView character2;
    @FXML ImageView character3;
    @FXML Button effect1;
    @FXML Button effect2;
    @FXML Button effect3;

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

    public void askCharacter() {
        gui.changeStage("PickCharacter.fxml");
        character1 = setCharacterImage(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(0));
        character2 = setCharacterImage(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(1));
        character3 = setCharacterImage(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(2));
        character1.setVisible(true);
        character2.setVisible(true);
        character3.setVisible(true);
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


    public void pickID(ActionEvent e) {
        UserAction action = null;
        ImageView img = (ImageView) e.getSource();
        String island = img.getId();
        switch(island) {
            case "island1" -> {
                action = new PickDestination(gui.getModelView().getGameCopy().getGameBoard().getIslandById(1));
            } //copiare aumentando l'id dell'isola
        }
    }

    public void askAssistant() {
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
        double height = 22;
        double width = 22;
        for(Island i : islands) {
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
                    ImageView stud = setStudentsImage(s);
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
                    stud = setStudentsImage(s);
                    stud.setLayoutX(cloud1.getLayoutX() + offsetX);
                    stud.setLayoutY(cloud1.getLayoutY() + offsetY);
                    offsetX+=5;
                    offsetY+=5;
                }
            }
            // COPIARE PER IL RESTO DELLE ISOLE
        }
    }
    public void askStudent() {
        gui.changeStage("PickStudent.fxml");
        redButton.setVisible(false);
        yellowButton.setVisible(false);
        blueButton.setVisible(false);
        greenButton.setVisible(false);
        pinkButton.setVisible(false);

        if(gui.getModelView().isJesterAction()) {
            if(gui.getModelView().getCharacterAction() % 2 == 0) {
                for(CharacterCard c : gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters()) {
                    if(Objects.equals(c.getName().toString(), "JESTER")) {
                        printStudentButtons(c);
                    }
                }
            } else {
                askStudent();
            }
            gui.getModelView().setCharacterAction(gui.getModelView().getCharacterAction() + 1);

        } else if(gui.getModelView().isMinestrelAction()) {
            if(gui.getModelView().getCharacterAction() % 2 == 0) {
                for(CharacterCard c : gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters()) {
                    if(Objects.equals(c.getName().toString(), "MINESTREL")) {
                        printStudentButtons(c);
                    }
                }
            } else {
                askStudent();

            }
            gui.getModelView().setCharacterAction(gui.getModelView().getCharacterAction() + 1);

        } else {
            for(Student s : gui.getModelView().getGameCopy().getCurrentPlayer().getBoard().getEntrance().getStudents()) {
                printStudentButton(s);
            }
        }

    }

    private void printStudentButton(Student s) {
        switch(s.getType().toString()) {
            case "BLUE" -> {
                blueButton.setVisible(true);
            } case "RED" -> {
                ;redButton.setVisible(true);
            } case "GREEN" -> {
                ;greenButton.setVisible(true);
            } case "PINK" -> {
                pinkButton.setVisible(true);
            } case "YELLOW" -> {
                yellowButton.setVisible(true);
            }
        }
    }

    private void printStudentButtons(CharacterCard c) {
        for(Student s : c.getStudents()) {
            printStudentButton(s);
        }
    }

    public void askPawnType() {
        gui.changeStage("PickPawnType.fxml");
    }

    public ImageView setStudentsImage(Student s) {
        ImageView stud = null;
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
        return stud;
    }

    public ImageView setCharacterImage(CharacterCard c) {
        ImageView character = null;
        String effect = null;
        switch(c.getName().toString()) {
            case "HERALD" -> {
                character = new ImageView("@../../graphics/characters/herald.png");
            } case "KNIGHT" -> {
                character = new ImageView("@../../graphics/characters/knight.png");
            } case "CENTAUR" -> {
                character = new ImageView("@../../graphics/characters/centaur.png");
            } case "FARMER" -> {
                character = new ImageView("@../../graphics/characters/farmer.png");
            } case "FUNGARUS" -> {
                character = new ImageView("@../../graphics/characters/fungarus.png");
            } case "JESTER" -> {
                character = new ImageView("@../../graphics/characters/jester.png");
            } case "THIEF" -> {
                character = new ImageView("@../../graphics/characters/thief.png");
            } case "MINESTREL" -> {
                character = new ImageView("@../../graphics/characters/minestrel.png");
            } case "GRANNY_HERBS" -> {
                character = new ImageView("@../../graphics/characters/grannyHerbs.png");
            } case "MAGIC_POSTMAN" -> {
                character = new ImageView("@../../graphics/characters/magicPostman.png");
            } case "SPOILED_PRINCESS" -> {
                character = new ImageView("@../../graphics/characters/spoiledPrincess.png");
            } case "MONK" -> {
                character = new ImageView("@../../graphics/characters/monk.png");
            }
        }
        return character;
    }

    //TODO continuare gli effetti per gli altri characters
    public String setCharacterEffect(CharacterCard c) {
        String effect = null;
        switch(c.getName().toString()) {
            case "HERALD" -> {
                effect = "Choose an island and calculate the majority as if Mother Nature had finished her move there. In this turn Mother Nature will move as usual and on the Island where her movement ends, the majority will normally be calculated.";
            } case "KNIGHT" -> {
                effect = "This turn, you have 2 additional influence points when calculating influence.";
            } case "CENTAUR" -> {
                effect = "When counting the influence on an Island (or a group of Islands), the Towers present are not counted.";
            } case "FARMER" -> {
                effect = "During this turn, you take control of Professors even if you have the same number of Students in your Dining Room as the player currently controlling them.";
            } case "FUNGARUS" -> {
                effect = "Choose a Student color; this turn, that color provides no influence when calculating influence.";
            } case "JESTER" -> {
            } /*case "THIEF" -> {
            } case "MINESTREL" -> {
            } case "GRANNY_HERBS" -> {
            } case "MAGIC_POSTMAN" -> {
            } case "SPOILED_PRINCESS" -> {
            } case "MONK" -> {
            }*/
        }
        return effect;
    }
}
