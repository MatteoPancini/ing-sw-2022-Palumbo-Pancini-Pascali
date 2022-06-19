package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.clienttoserver.actions.*;
import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.player.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainSceneController implements GUIController {

    private GUI gui;
    @FXML
    Group myBlueStudents;
    @FXML
    Group myRedStudents;
    @FXML
    Group myGreenStudents;
    @FXML
    Group myYellowStudents;
    @FXML
    Group myPinkStudents;
    @FXML
    Group leftBlueStudents;
    @FXML
    Group leftRedStudents;
    @FXML
    Group leftGreenStudents;
    @FXML
    Group leftYellowStudents;
    @FXML
    Group leftPinkStudents;
    @FXML
    Group topBlueStudents;
    @FXML
    Group topRedStudents;
    @FXML
    Group topGreenStudents;
    @FXML
    Group topPinkStudents;
    @FXML
    Group topYellowStudents;
    @FXML
    Group rightBlueStudents;
    @FXML
    Group rightRedStudents;
    @FXML
    Group rightGreenStudents;
    @FXML
    Group rightPinkStudents;
    @FXML
    Group rightYellowStudents;
    @FXML Group myTowers;
    @FXML Group leftTowers;
    @FXML Group topTowers;
    @FXML Group rightTowers;
    @FXML Button islandButton;
    @FXML Button diningRoomButton;
    @FXML ImageView myWizard;
    @FXML ImageView leftWizard;
    @FXML ImageView topWizard;
    @FXML ImageView rightWizard;
    @FXML Label descriptionLabel;
    @FXML Button askAssistantButton;
    @FXML ImageView blueStudent;
    @FXML ImageView redStudent;
    @FXML ImageView greenStudent;
    @FXML ImageView pinkStudent;
    @FXML ImageView yellowStudent;
    @FXML
    ImageView island1;
    @FXML
    ImageView island2;
    @FXML
    ImageView island3;
    @FXML
    ImageView island4;
    @FXML
    ImageView island5;
    @FXML
    ImageView island6;
    @FXML
    ImageView island7;
    @FXML
    ImageView island8;
    @FXML
    ImageView island9;
    @FXML
    ImageView island10;
    @FXML
    ImageView island11;
    @FXML
    ImageView island12;
    @FXML
    ImageView cloud1;
    @FXML
    ImageView cloud2;
    @FXML
    ImageView cloud3;
    @FXML
    ImageView cloud4;
    @FXML ImageView redIsland1;
    @FXML Label redLabelIsland1;
    @FXML ImageView towerIsland1;
    @FXML Label towerLabelIsland1;
    @FXML Group myEntrance;
    @FXML Group leftEntrance;
    @FXML Group rightEntrance;
    @FXML Group topEntrance;
    @FXML ImageView myAssistant;
    @FXML ImageView leftAssistant;
    @FXML ImageView topAssistant;
    @FXML ImageView rightAssistant;
    @FXML ImageView red;
    @FXML ImageView pink;
    @FXML ImageView green;
    @FXML ImageView blue;
    @FXML ImageView yellow;
    @FXML Button greenPawn;
    @FXML Button redPawn;
    @FXML Button bluePawn;
    @FXML Button yellowPawn;
    @FXML Button pinkPawn;
    @FXML Button cloud1Button;
    @FXML Button cloud2Button;
    @FXML Button cloud3Button;
    @FXML Button cloud4Button;
    @FXML ImageView character1;
    @FXML ImageView character2;
    @FXML ImageView character3;
    @FXML Button character1Button;
    @FXML Button character2Button;
    @FXML Button character3Button;
    @FXML ImageView assistant2;
    @FXML ImageView redCloud1;
    @FXML Label redLabelCloud1;


    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }



    public void update(String serverCommand) {
        updateDiningRooms();
        updateClouds();
        updateWizard();
        updateTowers();
        updateIslands();
        updateCharacters();
        updateAssistant();
        updateEntrances();
        updateTowerAreas();
        switch (serverCommand) {
            case "PICK_ASSISTANT" -> {
               showAssistantButton();
            }
            case "PICK_CLOUD" -> {
                askCloud();
            }
            case "PICK_DESTINATION" -> {
                askDestination();
            }
            case "PICK_STUDENT" -> {
                askStudentEntrance(gui.getModelView().getGameCopy().getCurrentPlayer().getBoard().getEntrance());
            }
            case "PICK_MOVES_NUMBER" -> {
                if(gui.getModelView().isJesterAction()) {
                    gui.getModelView().setJesterAction(false);
                } else if(gui.getModelView().isMinestrelAction()) {
                    gui.getModelView().setMinestrelAction(false);
                }
                askMoves(gui.getModelView().getGameCopy().getCurrentPlayer().getChosenAssistant());
            }
            case "PICK_PAWN_TYPE" -> {
                askPawnType();
            }
            default -> {
                System.out.println("Update without user actions (server command not in switch cases)");
            }
        }
    }

    //ritorna una lista delle possibili mosse di un assistant card da passare nella choice box
    public List<String> getMovesList(AssistantCard a) {
        List<String> moves = new ArrayList<>();
        for(int i=1; i <= gui.getModelView().getGameCopy().getCurrentPlayer().getChosenAssistant().getMoves(); i++) {
            moves.add(Integer.toString(i));
        }
        return moves;
    }


     public void askMoves(AssistantCard a) {
         ChoiceBox<String> choiceBox = new ChoiceBox<>();
         PickMovesNumber action = null;
         ObservableList<String> availableChoices = FXCollections.observableList(getMovesList(a));
         choiceBox.setItems(availableChoices);
         choiceBox.show();
         String choice = choiceBox.getSelectionModel().getSelectedItem();
         switch(choice) {
             case "1" -> {
                 action = new PickMovesNumber(1);
             } case "2" -> {
                 action = new PickMovesNumber(2);
             } case "3" -> {
                 action = new PickMovesNumber(3);
             } case "4" -> {
                 action = new PickMovesNumber(4);
             } case "5" -> {
                 action = new PickMovesNumber(5);
             } case "6" -> {
                 action = new PickMovesNumber(6);
             } case "7" -> {
                 action = new PickMovesNumber(7);
             } case "8" -> {
                 action = new PickMovesNumber(8);
             } case "9" -> {
                 action = new PickMovesNumber(9);
             } case "10" -> {
                 action = new PickMovesNumber(10);
             }
         }
         gui.getClientConnection().sendUserInput(action);
     }

    /*public void askMoves(AssistantCard a) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Assistant Moves");
        alert.setHeaderText("Choose your moves.");
        //alert.setContentText(serverReq);
        int cont = 1;
        for(int i=1; i <= a.getMoves(); i++) {
            cont+= 1;
        }
        if(gui.getModelView().isMagicPostmanAction()) {
            cont+= 2;
        }
        for(int j=1; j < cont; j++) {
            if(j==1) {
                ButtonType one = new ButtonType("1");
            } else if(j==2) {
                ButtonType two = new ButtonType("2");
            } else if(j==3) {
                ButtonType three = new ButtonType("3");
            } else if(j==4) {
                ButtonType four = new ButtonType("4");
            } else if(j==5) {
                ButtonType five = new ButtonType("5");
            } else if(j==6) {
                ButtonType six = new ButtonType("6");
            }else if(j==7) {
                ButtonType seven = new ButtonType("7");
            } else if(j==8) {
                ButtonType eight = new ButtonType("8");
            } else if(j==9) {
                ButtonType nine = new ButtonType("9");
            } else if(j==10) {
                ButtonType ten = new ButtonType("10");
            }
        }


        Optional<ButtonType> gameModeChoice = alert.showAndWait();
        String choice = null;
        if(gameModeChoice.isPresent() && gameModeChoice.get() == standardMode) {
            choice = "n";
        } else if(gameModeChoice.isPresent() && gameModeChoice.get() == expertMode) {
            choice = "y";
        }
        gui.getClientConnection().sendUserInput(new ExpertModeChoice(choice));
    }*/

    public void pickDiningRoomDestination() {
        PickDestination action = new PickDestination(gui.getModelView().getGameCopy().getCurrentPlayer().getBoard().getDiningRoom());
        diningRoomButton.setVisible(false);
        gui.getClientConnection().sendUserInput(action);
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

    public void askDestination() {
        descriptionLabel.setText("Pick an island or the dining room button to choose the student destination");
        diningRoomButton.setVisible(true);
        island1.setVisible(true);
        island2.setVisible(true);
        island3.setVisible(true);
        island4.setVisible(true);
        island5.setVisible(true);
        island6.setVisible(true);
        island7.setVisible(true);
        island8.setVisible(true);
        island9.setVisible(true);
        island10.setVisible(true);
        island11.setVisible(true);
        island12.setVisible(true);
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
        cloud1Button.setVisible(false);
        cloud2Button.setVisible(false);
        cloud3Button.setVisible(false);
        cloud4Button.setVisible(false);
    }
    public void showAssistantButton() {
        descriptionLabel.setText("Click the pick assistant button to choose your assistant. " +
                "Remember to take a look at the game status to make the best choice!");
        askAssistantButton.setVisible(true);
    }

    public void askAssistant(ActionEvent e) {
        assistant2.setVisible(false);
        Platform.runLater(() -> {
            gui.changeStage("PickAssistant.fxml");
            for(AssistantCard a : gui.getModelView().getGameCopy().getCurrentPlayer().getAssistantDeck().getDeck()) {
                if(a.getName().equals(Assistants.OSTRICH)) {
                    if(gui.getModelView().getGameCopy().canPlayAssistant(a.getName())) {
                        assistant2.setVisible(true);
                        assistant2.setImage(setAssistantImage(a));
                    }
                }
            } //TODO da continuare per ogni assistant
        });
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

    public void updateDiningRooms() {
        Image pic;
        ImageView img;
        int cont = 0; //serve per decidere dove posizionare la board del player nella main scene
        for (Player pl : gui.getModelView().getGameCopy().getActivePlayers()) {
            if (pl.getNickname().equals(gui.getModelView().getGameCopy().getCurrentPlayer().getNickname())) {
                for (Table t : pl.getBoard().getDiningRoom().getDiningRoom()) {
                    for (int i = 0; i < t.getTable().size(); i++) {
                        if (t.getTable().get(i).hasStudent()) {
                            if (t.getTable().get(i).getBoardCellType() == PawnType.BLUE) {
                                pic = new Image("@../../graphics/wooden_pieces/2D/blue_Student.png");
                                img = ((ImageView) myBlueStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getTable().get(i).getBoardCellType() == PawnType.GREEN) {
                                pic = new Image("@../../graphics/wooden_pieces/2D/green_Student.png");
                                img = ((ImageView) myGreenStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getTable().get(i).getBoardCellType() == PawnType.RED) {
                                pic = new Image("@../../graphics/wooden_pieces/2D/red_Student.png");
                                img = ((ImageView) myRedStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getTable().get(i).getBoardCellType() == PawnType.PINK) {
                                pic = new Image("@../../graphics/wooden_pieces/2D/pink_Student.png");
                                img = ((ImageView) myPinkStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getTable().get(i).getBoardCellType() == PawnType.YELLOW) {
                                pic = new Image("@../../graphics/wooden_pieces/2D/yellow_Student.png");
                                img = ((ImageView) myYellowStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            }
                        }
                    }
                }
            } else if (cont == 0){
                //top Player corresponding to cont 0
                for (Table t : pl.getBoard().getDiningRoom().getDiningRoom()) {
                    for (int i = 0; i < t.getTable().size(); i++) {
                        if (t.getTable().get(i).hasStudent()) {
                            if (t.getTable().get(i).getBoardCellType() == PawnType.BLUE) {
                                pic = new Image("@../../graphics/wooden_pieces/2D/blue_Student.png");
                                img = ((ImageView) topBlueStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getTable().get(i).getBoardCellType() == PawnType.GREEN) {
                                pic = new Image("@../../graphics/wooden_pieces/2D/green_Student.png");
                                img = ((ImageView) topGreenStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getTable().get(i).getBoardCellType() == PawnType.RED) {
                                pic = new Image("@../../graphics/wooden_pieces/2D/red_Student.png");
                                img = ((ImageView) topRedStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getTable().get(i).getBoardCellType() == PawnType.PINK) {
                                pic = new Image("@../../graphics/wooden_pieces/2D/pink_Student.png");
                                img = ((ImageView) topPinkStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getTable().get(i).getBoardCellType() == PawnType.YELLOW) {
                                pic = new Image("@../../graphics/wooden_pieces/2D/yellow_Student.png");
                                img = ((ImageView) topYellowStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            }
                        }
                    }
                }
                cont++;
            } else if (cont == 1) {
                //left Player corresponding to cont 1
                for (Table t : pl.getBoard().getDiningRoom().getDiningRoom()) {
                    for (int i = 0; i < t.getTable().size(); i++) {
                        if (t.getTable().get(i).hasStudent()) {
                            if (t.getTable().get(i).getBoardCellType() == PawnType.BLUE) {
                                pic = new Image("@../../graphics/wooden_pieces/2D/blue_Student.png");
                                img = ((ImageView) leftBlueStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getTable().get(i).getBoardCellType() == PawnType.GREEN) {
                                pic = new Image("@../../graphics/wooden_pieces/2D/green_Student.png");
                                img = ((ImageView) leftGreenStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getTable().get(i).getBoardCellType() == PawnType.RED) {
                                pic = new Image("@../../graphics/wooden_pieces/2D/red_Student.png");
                                img = ((ImageView) leftRedStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getTable().get(i).getBoardCellType() == PawnType.PINK) {
                                pic = new Image("@../../graphics/wooden_pieces/2D/pink_Student.png");
                                img = ((ImageView) leftPinkStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getTable().get(i).getBoardCellType() == PawnType.YELLOW) {
                                pic = new Image("@../../graphics/wooden_pieces/2D/yellow_Student.png");
                                img = ((ImageView) leftYellowStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            }
                        }
                    }
                }
                cont++;
            } else if (cont == 2) {
                //right Player corresponding to cont 2
                for (Table t : pl.getBoard().getDiningRoom().getDiningRoom()) {
                    for (int i = 0; i < t.getTable().size(); i++) {
                        if (t.getTable().get(i).hasStudent()) {
                            if (t.getTable().get(i).getBoardCellType() == PawnType.BLUE) {
                                pic = new Image("@../../graphics/wooden_pieces/2D/blue_Student.png");
                                img = ((ImageView) rightBlueStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getTable().get(i).getBoardCellType() == PawnType.GREEN) {
                                pic = new Image("@../../graphics/wooden_pieces/2D/green_Student.png");
                                img = ((ImageView) rightGreenStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getTable().get(i).getBoardCellType() == PawnType.RED) {
                                pic = new Image("@../../graphics/wooden_pieces/2D/red_Student.png");
                                img = ((ImageView) rightRedStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getTable().get(i).getBoardCellType() == PawnType.PINK) {
                                pic = new Image("@../../graphics/wooden_pieces/2D/pink_Student.png");
                                img = ((ImageView) rightPinkStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getTable().get(i).getBoardCellType() == PawnType.YELLOW) {
                                pic = new Image("@../../graphics/wooden_pieces/2D/yellow_Student.png");
                                img = ((ImageView) rightYellowStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            }
                        }
                    }
                }
                cont++;
            }
        }
    }

    public void updateIslands() {
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
        //TODO settare tutti i bottoni a false e settarli a true nella pickIsland
        double height = 22;
        double width = 22;
        for (Island isl : gui.getModelView().getGameCopy().getGameBoard().getIslands()) {
            if (isl.getIslandID() == gui.getIDFromIslandImage(island1.toString())) {
                island1.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(0).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland1.setVisible(true);
                        redLabelIsland1.setText(getStudentsNumber(isl, s));
                    } //TODO continuare per ogni s
                }
                if(isl.hasTower()) {
                    towerIsland1.setVisible(true);
                    towerLabelIsland1.setText(getTowersNumber(isl));
                }

            } //TODO COPIARE PER TUTTE LE 12 ISOLE else if(...)
        }
    }

    public Image setTowersImage(Tower t) {
        Image tower = null;
        switch(t.getColor().toString()) {
            case "BLACK" -> {
                tower = new Image("@../../graphics/wooden_pieces/black_tower.png");
            } case "GREY" -> {
                tower = new Image("@../../graphics/wooden_pieces/grey_tower.png");
            } case "WHITE" -> {
                tower = new Image("@../../graphics/wooden_pieces/white_tower.png");
            }
        }
        return tower;
    }
    public void askIsland() {

    }

    public String getTowersNumber(Island i) {
        int num = 0;
        for(Tower r : i.getMergedTowers()) {
            num+= 1;
        }
        return Integer.toString(num);
    }
    /*
    public ImageView setStudentsImage(Student s) {
        ImageView stud = null;
        switch (s.getType().toString()) {
            case "BLUE" -> {
                stud = new ImageView("@../../graphics/wooden_pieces/3D/blueStudent3D.png");
            }
            case "RED" -> {
                stud = new ImageView("@../../graphics/wooden_pieces/3D/redStudent3D.png");
            }
            case "GREEN" -> {
                stud = new ImageView("@../../graphics/wooden_pieces/3D/greenStudent3D.png");
            }
            case "PINK" -> {
                stud = new ImageView("@../../graphics/wooden_pieces/3D/pinkStudent3D.png");
            }
            case "YELLOW" -> {
                stud = new ImageView("@../../graphics/wooden_pieces/3D/yellowStudent3D.png");
            }
        }
        return stud;
    } */

    public String getStudentsNumber(Island i, Student s) {
        int num = 0;
        for(Student stud : i.getStudents()) {
            if(stud.getType()==s.getType()) {
                num+= 1;
            }
        }
        return Integer.toString(num);
    }

    public void updateClouds() {
        cloud1.setVisible(false);
        cloud2.setVisible(false);
        cloud3.setVisible(false);
        cloud4.setVisible(false);
        for (CloudTile c : gui.getModelView().getGameCopy().getGameBoard().getClouds()) {
            if (c.getID() == 1) {
                cloud1.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getClouds().get(1).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redCloud1.setVisible(true);
                        redLabelCloud1.setText(getCloudStudentsNumber(c));
                    }
                }
            }
            //TODO COPIARE PER IL RESTO DELLE ISOLE
        }
    }

    public void askCloud() {
        cloud1Button.setVisible(false);
        cloud2Button.setVisible(false);
        cloud3Button.setVisible(false);
        cloud4Button.setVisible(false);
        for(CloudTile c : gui.getModelView().getGameCopy().getGameBoard().getClouds()) {
            if(c.getID() == 1) {
                cloud1Button.setVisible(true);
                cloud1Button.setText("Cloud 1");
            } else if(c.getID() == 2) {
                cloud2Button.setVisible(true);
                cloud2Button.setText("Cloud 2");
            } else if(c.getID() == 3) {
                cloud3Button.setVisible(true);
                cloud3Button.setText("Cloud 3");
            } else if(c.getID() == 4) {
                cloud4Button.setVisible(true);
                cloud4Button.setText("Cloud 4");
            }
        }
        updateClouds();
    }


    public String getCloudStudentsNumber(CloudTile cloud) {
        int num = 0;
        for(Student s : cloud.getStudents()) {
            if(s.getType()==s.getType()) {
                num+= 1;
            }
        }
        return Integer.toString(num);
    }

    public void updateTowers() {
        int cont = 0;
        Image img = null;
        for (Player p : gui.getModelView().getGameCopy().getActivePlayers()) {
            if(p.equals(gui.getModelView().getGameCopy().getCurrentPlayer())) {
                for(int i=0; i < p.getBoard().getTowerArea().getTowerArea().size(); i++) {
                    img = setTowersImage(p.getBoard().getTowerArea().getTowerArea().get(i));
                    ((ImageView) myTowers.getChildren().get(i)).setImage(img);
                }
            } else if(cont==0) {
                for(int i=0; i < p.getBoard().getTowerArea().getTowerArea().size(); i++) {
                    img = setTowersImage(p.getBoard().getTowerArea().getTowerArea().get(i));
                    ((ImageView) topTowers.getChildren().get(i)).setImage(img);
                }
                cont++;
            } else if(cont==1) {
                for(int i=0; i < p.getBoard().getTowerArea().getTowerArea().size(); i++) {
                    img = setTowersImage(p.getBoard().getTowerArea().getTowerArea().get(i));
                    ((ImageView) leftTowers.getChildren().get(i)).setImage(img);
                }
                cont++;
            } else if(cont==2) {
                for(int i=0; i < p.getBoard().getTowerArea().getTowerArea().size(); i++) {
                    img = setTowersImage(p.getBoard().getTowerArea().getTowerArea().get(i));
                    ((ImageView) rightTowers.getChildren().get(i)).setImage(img);
                }
                cont++;
            }
        }
    }

    public Image setWizardImage(Wizards w) {
        Image wiz = null;
        switch (w.toString()) {
            case "KING" -> {
                wiz = new Image("@../../graphics/wizards/king_no_bg.png");
            }
            case "WITCH" -> {
                wiz = new Image("@../../graphics/wizards/pixie_no_bg.png");
            }
            case "MONACH" -> {
                wiz = new Image("@../../graphics/wizards/sorcerer_no_bg.png");
            }
            case "FOREST" -> {
                wiz = new Image("@../../graphics/wizards/wizard_no_bg.png");
            }
        }
        return wiz;
    }

    public void updateWizard() {
        int cont = 0;
        Image img = null;
        for (Player p : gui.getModelView().getGameCopy().getActivePlayers()) {
            img = setWizardImage(p.getWizard());
            if(p.equals(gui.getModelView().getGameCopy().getCurrentPlayer())) {
                ((ImageView) myWizard).setImage(img);
            } else if(cont==0) {
                ((ImageView) topWizard).setImage(img);
                cont++;
            } else if(cont==1) {
                ((ImageView) leftWizard).setImage(img);
                cont++;
            } else if(cont==2) {
                ((ImageView) rightWizard).setImage(img);
                cont++;
            }
        }
    }

    public void updateAssistant() {
        int cont = 0;
        Image img = null;
        for(Player p : gui.getModelView().getGameCopy().getActivePlayers()) {
            img = setAssistantImage(p.getChosenAssistant());
            if(p.equals(gui.getModelView().getGameCopy().getCurrentPlayer())) {
                ((ImageView) myAssistant).setImage(img);
            } else if(cont==0) {
                ((ImageView) topAssistant).setImage(img);
                cont++;
            } else if(cont==1) {
                ((ImageView) leftAssistant).setImage(img);
                cont++;
            } else if(cont==2) {
                ((ImageView) rightAssistant).setImage(img);
                cont++;
            }
        }
    }

    public void updateEntrances() {
        Image pic = null;
        int cont = 0;
        for(Player p : gui.getModelView().getGameCopy().getActivePlayers()) {
            if(p.equals(gui.getModelView().getGameCopy().getCurrentPlayer())) {
                for (Student s : p.getBoard().getEntrance().getStudents()) {
                    for (int i = 0; i < p.getBoard().getEntrance().getStudents().size(); i++) {
                        pic = setStudentsEntrance(s);
                        ((ImageView) myEntrance.getChildren().get(i)).setImage(pic);
                    }
                }
            } else if(cont == 0) {
                for (Student s : p.getBoard().getEntrance().getStudents()) {
                    for (int i = 0; i < p.getBoard().getEntrance().getStudents().size(); i++) {
                        pic = setStudentsEntrance(s);
                        ((ImageView) topEntrance.getChildren().get(i)).setImage(pic);
                    }
                }
                cont++;
            } else if(cont == 1) {
                for (Student s : p.getBoard().getEntrance().getStudents()) {
                    for (int i = 0; i < p.getBoard().getEntrance().getStudents().size(); i++) {
                        pic = setStudentsEntrance(s);
                        ((ImageView) leftEntrance.getChildren().get(i)).setImage(pic);
                    }
                }
                cont++;
            } else if(cont == 2) {
                for (Student s : p.getBoard().getEntrance().getStudents()) {
                    for (int i = 0; i < p.getBoard().getEntrance().getStudents().size(); i++) {
                        pic = setStudentsEntrance(s);
                        ((ImageView) rightEntrance.getChildren().get(i)).setImage(pic);
                    }
                }
                cont++;
            }
        }
    }

    public void updateTowerAreas() {
        Image pic = null;
        int cont = 0;
        for(Player p : gui.getModelView().getGameCopy().getActivePlayers()) {
            if (p.equals(gui.getModelView().getGameCopy().getCurrentPlayer())) {
                if(p.getBoard().getTowerArea().getTowerArea().get(0).getColor().equals(TowerColor.GREY)) {
                    pic = new Image("@../../graphics/wooden_pieces/grey_tower.png");
                } else if(p.getBoard().getTowerArea().getTowerArea().get(0).getColor().equals(TowerColor.BLACK)) {
                    pic = new Image("@../../graphics/wooden_pieces/black_tower.png");
                } else if(p.getBoard().getTowerArea().getTowerArea().get(0).getColor().equals(TowerColor.WHITE)) {
                    pic = new Image("@../../graphics/wooden_pieces/white_tower.png");
                }
                for(Tower t : p.getBoard().getTowerArea().getTowerArea()) {
                    for(int i=0; i < p.getBoard().getTowerArea().getTowerArea().size(); i++) {
                        ((ImageView) myTowers.getChildren().get(i)).setImage(pic);
                    }
                }
                cont++;
             } else if(cont == 0) {
                if(p.getBoard().getTowerArea().getTowerArea().get(0).getColor().equals(TowerColor.GREY)) {
                    pic = new Image("@../../graphics/wooden_pieces/grey_tower.png");
                } else if(p.getBoard().getTowerArea().getTowerArea().get(0).getColor().equals(TowerColor.BLACK)) {
                    pic = new Image("@../../graphics/wooden_pieces/black_tower.png");
                } else if(p.getBoard().getTowerArea().getTowerArea().get(0).getColor().equals(TowerColor.WHITE)) {
                    pic = new Image("@../../graphics/wooden_pieces/white_tower.png");
                }
                for(Tower t : p.getBoard().getTowerArea().getTowerArea()) {
                    for(int i=0; i < p.getBoard().getTowerArea().getTowerArea().size(); i++) {
                        ((ImageView) myTowers.getChildren().get(i)).setImage(pic);
                    }
                }
                cont++;
            } else if(cont == 1) {
                if(p.getBoard().getTowerArea().getTowerArea().get(0).getColor().equals(TowerColor.GREY)) {
                    pic = new Image("@../../graphics/wooden_pieces/grey_tower.png");
                } else if(p.getBoard().getTowerArea().getTowerArea().get(0).getColor().equals(TowerColor.BLACK)) {
                    pic = new Image("@../../graphics/wooden_pieces/black_tower.png");
                } else if(p.getBoard().getTowerArea().getTowerArea().get(0).getColor().equals(TowerColor.WHITE)) {
                    pic = new Image("@../../graphics/wooden_pieces/white_tower.png");
                }
                for(Tower t : p.getBoard().getTowerArea().getTowerArea()) {
                    for(int i=0; i < p.getBoard().getTowerArea().getTowerArea().size(); i++) {
                        ((ImageView) myTowers.getChildren().get(i)).setImage(pic);
                    }
                }
                cont++;
            }
        }
    }

    /* creare un parametro che indichi che tipo di action è in corso, passarla come parametro
    public void updatePickStudents(UserAction action) {
        green.setVisible(false);
        yellow.setVisible(false);
        pink.setVisible(false);
        blue.setVisible(false);
        red.setVisible(false);
        if(action instanceof PickStudent) {
            if(gui.getModelView().isJesterAction()) {
                if(gui.getModelView().getCharacterAction() % 2 == 0) {
                    for(CharacterCard c : gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters()) {
                        if(c.getName() == Characters.JESTER) {
                            askStudentCard(c);
                            break;
                        }
                    }
                } else {
                    askStudentEntrance(gui.getModelView().getGameCopy().getCurrentPlayer().getBoard().getEntrance());
                }
                gui.getModelView().setCharacterAction(gui.getModelView().getCharacterAction() + 1);

            } else if(gui.getModelView().isMinestrelAction()) {
                if(gui.getModelView().getCharacterAction() % 2 == 0) {
                    askStudent(gui.getModelView().getGameCopy().getCurrentPlayer().getBoard().getDiningRoom());
                }
                gui.getModelView().setCharacterAction(gui.getModelView().getCharacterAction() + 1);

            } else if(gui.getModelView().isPrincessAction()) {
                for(CharacterCard c : gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters()) {
                    if(c.getName() == Characters.SPOILED_PRINCESS) {
                        askStudentCard(c);
                        gui.getModelView().setPrincessAction(false);
                        break;
                    }
                }
            } else if(gui.getModelView().isMonkAction()) {
                for(CharacterCard c : gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters()) {
                    if(c.getName() == Characters.MONK) {
                        askStudentCard(c);
                        break;
                    }
                }
            } else {
                askStudentEntrance(gui.getModelView().getGameCopy().getCurrentPlayer().getBoard().getEntrance());
            }
        }
    }*/

    public void askStudentEntrance(Entrance e) {
        descriptionLabel.setText("Pick a student from your entrance");
        for(Student s : e.getStudents()) {
            if(s.getType().equals(PawnType.RED)) {
                red.setVisible(true);
                redStudent.setVisible(true);
            } else if(s.getType().equals(PawnType.GREEN)) {
                green.setVisible(true);
                greenStudent.setVisible(true);
            } else if(s.getType().equals(PawnType.BLUE)) {
                blue.setVisible(true);
                blueStudent.setVisible(true);
            } else if(s.getType().equals(PawnType.YELLOW)) {
                yellow.setVisible(true);
                yellowStudent.setVisible(true);
            } else if(s.getType().equals(PawnType.PINK)) {
                pink.setVisible(true);
                pinkStudent.setVisible(true);
            }
        }
    }

    public void askStudentCard(CharacterCard c) {
        descriptionLabel.setText("Pick a student from the " + c.getName());
        for(Student s : c.getStudents()) {
            if(s.getType().equals(PawnType.RED)) {
                red.setVisible(true);
            } else if(s.getType().equals(PawnType.GREEN)) {
                green.setVisible(true);
            } else if(s.getType().equals(PawnType.BLUE)) {
                blue.setVisible(true);
            } else if(s.getType().equals(PawnType.YELLOW)) {
                yellow.setVisible(true);
            } else if(s.getType().equals(PawnType.PINK)) {
                pink.setVisible(true);
            }
        }
    }

    public void askStudent(DiningRoom dr) {
        descriptionLabel.setText("Pick a student from your dining room");
        for(Table t : gui.getModelView().getGameCopy().getCurrentPlayer().getBoard().getDiningRoom().getDiningRoom()) {
            if(t.getTable().get(0).hasStudent()) {
                if(t.getTable().get(0).getBoardCellType() == PawnType.BLUE) {
                    blue.setVisible(true);
                    blueStudent.setVisible(true);
                }
                else if(t.getTable().get(0).getBoardCellType() == PawnType.GREEN) {
                    green.setVisible(true);
                    greenStudent.setVisible(true);
                }
                else if(t.getTable().get(0).getBoardCellType() == PawnType.RED) {
                    red.setVisible(true);
                    redStudent.setVisible(true);
                }
                else if(t.getTable().get(0).getBoardCellType() == PawnType.PINK) {
                    pink.setVisible(true);
                    pinkStudent.setVisible(true);
                }
                else if(t.getTable().get(0).getBoardCellType() == PawnType.YELLOW) {
                    yellow.setVisible(true);
                    yellowStudent.setVisible(true);
                }
            }
        }
    }

    public void askPawnType() {
        descriptionLabel.setText("Pick a pawn type based on the effect of the character you chose");
        green.setVisible(true);
        blue.setVisible(true);
        yellow.setVisible(true);
        red.setVisible(true);
        pink.setVisible(true);
        greenPawn.setVisible(true);
        redPawn.setVisible(true);
        yellowPawn.setVisible(true);
        bluePawn.setVisible(true);
        pinkPawn.setVisible(true);
    }

    public void updateCharacters() {
        character1.setVisible(true);
        character2.setVisible(true);
        character3.setVisible(true);
        character1Button.setVisible(true);
        character2Button.setVisible(true);
        character3Button.setVisible(true);
        character1.setImage(getCharacterImage(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(0)));
        character2.setImage(getCharacterImage(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(0)));
        character3.setImage(getCharacterImage(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(0)));
    }

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

    public Image getCharacterImage(CharacterCard c) {
        Image pic = null;
        switch(c.getName().toString()) {
            case "HERALD" -> {
                pic = new Image("@../../graphics/characters/herald.png");
            } case "KNIGHT" -> {
                pic = new Image("@../../graphics/characters/knight.png");
            } case "CENTAUR" -> {
                pic = new Image("@../../graphics/characters/centaur.png");
            } case "FARMER" -> {
                pic = new Image("@../../graphics/characters/farmer.png");
            } case "FUNGARUS" -> {
                pic = new Image("@../../graphics/characters/fungarus.png");
            } case "JESTER" -> {
                pic = new Image("@../../graphics/characters/jester.png");
            } case "THIEF" -> {
                pic = new Image("@../../graphics/characters/thief.png");
            } case "MINESTREL" -> {
                pic = new Image("@../../graphics/characters/minestrel.png");
            } case "GRANNY_HERBS" -> {
                pic = new Image("@../../graphics/characters/grannyHerbs.png");
            } case "MAGIC_POSTMAN" -> {
                pic = new Image("@../../graphics/characters/magicPostman.png");
            } case "SPOILED_PRINCESS" -> {
                pic = new Image("@../../graphics/characters/spoiledPrincess.png");
            } case "MONK" -> {
                pic = new Image("@../../graphics/characters/monk.png");
            }
        }
        return pic;
    }

    public Image setStudentsEntrance(Student s) {
        Image stud = null;
        switch(s.getType().toString()) {
            case "BLUE" -> {
                stud = new Image("@../../graphics/wooden_pieces/3D/blueStudent3D.png");
            } case "RED" -> {
                stud = new Image("@../../graphics/wooden_pieces/3D/redStudent3D.png");
            } case "GREEN" -> {
                stud = new Image("@../../graphics/wooden_pieces/3D/greenStudent3D.png");
            } case "PINK" -> {
                stud = new Image("@../../graphics/wooden_pieces/3D/pinkStudent3D.png");
            } case "YELLOW" -> {
                stud = new Image("@../../graphics/wooden_pieces/3D/yellowStudent3D.png");
            }
        }
        return stud;
    }

    public Image setAssistantImage(AssistantCard a) {
        Image img = null;
        switch (a.getName().toString()) {
            case "EAGLE" -> {
                img = new Image("@../../graphics/assistants/Assistente (4)");
            }
            case "DOG" -> {
                img = new Image("@../../graphics/assistants/Assistente (8)");
            }
            case "ELEPHANT" -> {
                img = new Image("@../../graphics/assistants/Assistente (9)");
            }
            case "CAT" -> {
                img = new Image("@../../graphics/assistants/Assistente (3)");
            }
            case "CHEETAH" -> {
                img = new Image("@../../graphics/assistants/Assistente (1)");
            }
            case "LIZARD" -> {
                img = new Image("@../../graphics/assistants/Assistente (6)");
            }
            case "OCTOPUS" -> {
                img = new Image("@../../graphics/assistants/Assistente (7)");
            }
            case "OSTRICH" -> {
                img = new Image("@../../graphics/assistants/Assistente (2)");
            }
            case "TURTLE" -> {
                img = new Image("@../../graphics/assistants/Assistente (10)");
            }
            case "FOX" -> {
                img = new Image("@../../graphics/assistants/Assistente (5)");
            }
        }
        return img;
    }


}
