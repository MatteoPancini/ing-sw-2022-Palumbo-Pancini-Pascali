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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * Class MainSceneController handles the main board scene by updating it each time
 * a game copy of the Game is received from the server
 */
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
    @FXML Button diningRoomButton;
    @FXML ImageView myWizard;
    @FXML ImageView leftWizard;
    @FXML ImageView topWizard;
    @FXML ImageView rightWizard;
    @FXML Label descriptionLabel;
    @FXML Button askAssistantButton;
    @FXML Button blueStudent;
    @FXML Button redStudent;
    @FXML Button greenStudent;
    @FXML Button pinkStudent;
    @FXML Button yellowStudent;

    @FXML Button noCharacterButton;
    @FXML ImageView character1Coin;
    @FXML Label character1LabelCoin;
    @FXML ImageView character2Coin;
    @FXML Label character2LabelCoin;
    @FXML ImageView character3Coin;
    @FXML Label character3LabelCoin;
    @FXML ImageView greenCharacter1;
    @FXML ImageView blueCharacter1;
    @FXML ImageView pinkCharacter1;
    @FXML ImageView redCharacter1;
    @FXML ImageView yellowCharacter1;
    @FXML ImageView greenCharacter2;
    @FXML ImageView blueCharacter2;
    @FXML ImageView pinkCharacter2;
    @FXML ImageView redCharacter2;
    @FXML ImageView yellowCharacter2;
    @FXML ImageView greenCharacter3;
    @FXML ImageView blueCharacter3;
    @FXML ImageView pinkCharacter3;
    @FXML ImageView redCharacter3;
    @FXML ImageView yellowCharacter3;


    @FXML ChoiceBox<String> pickMovesBox;

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
    @FXML ImageView yellowIsland1;
    @FXML Label yellowLabelIsland1;
    @FXML ImageView blueIsland1;
    @FXML Label blueLabelIsland1;
    @FXML ImageView pinkIsland1;
    @FXML Label pinkLabelIsland1;
    @FXML ImageView greenIsland1;
    @FXML Label greenLabelIsland1;
    @FXML ImageView towerIsland1;
    @FXML Label towerLabelIsland1;
    @FXML ImageView towerIsland2;
    @FXML Label towerLabelIsland2;
    @FXML ImageView towerIsland3;
    @FXML Label towerLabelIsland3;
    @FXML ImageView towerIsland4;
    @FXML Label towerLabelIsland4;
    @FXML ImageView towerIsland5;
    @FXML Label towerLabelIsland5;
    @FXML ImageView towerIsland6;
    @FXML Label towerLabelIsland6;
    @FXML ImageView towerIsland7;
    @FXML Label towerLabelIsland7;
    @FXML ImageView towerIsland8;
    @FXML Label towerLabelIsland8;
    @FXML ImageView towerIsland9;
    @FXML Label towerLabelIsland9;
    @FXML ImageView towerIsland10;
    @FXML Label towerLabelIsland10;
    @FXML ImageView towerIsland11;
    @FXML Label towerLabelIsland11;
    @FXML ImageView towerIsland12;
    @FXML Label towerLabelIsland12;


    @FXML ImageView redIsland2;
    @FXML Label redLabelIsland2;
    @FXML ImageView yellowIsland2;
    @FXML Label yellowLabelIsland2;
    @FXML ImageView blueIsland2;
    @FXML Label blueLabelIsland2;
    @FXML ImageView pinkIsland2;
    @FXML Label pinkLabelIsland2;
    @FXML ImageView greenIsland2;
    @FXML Label greenLabelIsland2;

    @FXML ImageView redIsland3;
    @FXML Label redLabelIsland3;
    @FXML ImageView yellowIsland3;
    @FXML Label yellowLabelIsland3;
    @FXML ImageView blueIsland3;
    @FXML Label blueLabelIsland3;
    @FXML ImageView pinkIsland3;
    @FXML Label pinkLabelIsland3;
    @FXML ImageView greenIsland3;
    @FXML Label greenLabelIsland3;

    @FXML ImageView redIsland4;
    @FXML Label redLabelIsland4;
    @FXML ImageView yellowIsland4;
    @FXML Label yellowLabelIsland4;
    @FXML ImageView blueIsland4;
    @FXML Label blueLabelIsland4;
    @FXML ImageView pinkIsland4;
    @FXML Label pinkLabelIsland4;
    @FXML ImageView greenIsland4;
    @FXML Label greenLabelIsland4;

    @FXML ImageView redIsland5;
    @FXML Label redLabelIsland5;
    @FXML ImageView yellowIsland5;
    @FXML Label yellowLabelIsland5;
    @FXML ImageView blueIsland5;
    @FXML Label blueLabelIsland5;
    @FXML ImageView pinkIsland5;
    @FXML Label pinkLabelIsland5;
    @FXML ImageView greenIsland5;
    @FXML Label greenLabelIsland5;

    @FXML ChoiceBox<String> pickCharacterActionNumberBox;

    @FXML ImageView redIsland6;
    @FXML Label redLabelIsland6;
    @FXML ImageView yellowIsland6;
    @FXML Label yellowLabelIsland6;
    @FXML ImageView blueIsland6;
    @FXML Label blueLabelIsland6;
    @FXML ImageView pinkIsland6;
    @FXML Label pinkLabelIsland6;
    @FXML ImageView greenIsland6;
    @FXML Label greenLabelIsland6;

    @FXML ImageView redIsland7;
    @FXML Label redLabelIsland7;
    @FXML ImageView yellowIsland7;
    @FXML Label yellowLabelIsland7;
    @FXML ImageView blueIsland7;
    @FXML Label blueLabelIsland7;
    @FXML ImageView pinkIsland7;
    @FXML Label pinkLabelIsland7;
    @FXML ImageView greenIsland7;
    @FXML Label greenLabelIsland7;

    @FXML ImageView redIsland8;
    @FXML Label redLabelIsland8;
    @FXML ImageView yellowIsland8;
    @FXML Label yellowLabelIsland8;
    @FXML ImageView blueIsland8;
    @FXML Label blueLabelIsland8;
    @FXML ImageView pinkIsland8;
    @FXML Label pinkLabelIsland8;
    @FXML ImageView greenIsland8;
    @FXML Label greenLabelIsland8;

    @FXML ImageView redIsland9;
    @FXML Label redLabelIsland9;
    @FXML ImageView yellowIsland9;
    @FXML Label yellowLabelIsland9;
    @FXML ImageView blueIsland9;
    @FXML Label blueLabelIsland9;
    @FXML ImageView pinkIsland9;
    @FXML Label pinkLabelIsland9;
    @FXML ImageView greenIsland9;
    @FXML Label greenLabelIsland9;

    @FXML ImageView redIsland10;
    @FXML Label redLabelIsland10;
    @FXML ImageView yellowIsland10;
    @FXML Label yellowLabelIsland10;
    @FXML ImageView blueIsland10;
    @FXML Label blueLabelIsland10;
    @FXML ImageView pinkIsland10;
    @FXML Label pinkLabelIsland10;
    @FXML ImageView greenIsland10;
    @FXML Label greenLabelIsland10;

    @FXML ImageView redIsland11;
    @FXML Label redLabelIsland11;
    @FXML ImageView yellowIsland11;
    @FXML Label yellowLabelIsland11;
    @FXML ImageView blueIsland11;
    @FXML Label blueLabelIsland11;
    @FXML ImageView pinkIsland11;
    @FXML Label pinkLabelIsland11;
    @FXML ImageView greenIsland11;
    @FXML Label greenLabelIsland11;

    @FXML ImageView motherNature1;
    @FXML ImageView motherNature2;
    @FXML ImageView motherNature3;
    @FXML ImageView motherNature4;
    @FXML ImageView motherNature5;
    @FXML ImageView motherNature6;
    @FXML ImageView motherNature7;
    @FXML ImageView motherNature8;
    @FXML ImageView motherNature9;
    @FXML ImageView motherNature10;
    @FXML ImageView motherNature11;
    @FXML ImageView motherNature12;


    @FXML ImageView redIsland12;
    @FXML Label redLabelIsland12;
    @FXML ImageView yellowIsland12;
    @FXML Label yellowLabelIsland12;
    @FXML ImageView blueIsland12;
    @FXML Label blueLabelIsland12;
    @FXML ImageView pinkIsland12;
    @FXML Label pinkLabelIsland12;
    @FXML ImageView greenIsland12;
    @FXML Label greenLabelIsland12;

    @FXML ImageView noEntryTile1;
    @FXML ImageView noEntryTile2;
    @FXML ImageView noEntryTile3;
    @FXML ImageView noEntryTile4;
    @FXML ImageView noEntryTile5;
    @FXML ImageView noEntryTile6;
    @FXML ImageView noEntryTile7;
    @FXML ImageView noEntryTile8;
    @FXML ImageView noEntryTile9;
    @FXML ImageView noEntryTile10;
    @FXML ImageView noEntryTile11;
    @FXML ImageView noEntryTile12;

    @FXML Label yellowLabelCharacter1;
    @FXML Label blueLabelCharacter1;
    @FXML Label pinkLabelCharacter1;
    @FXML Label redLabelCharacter1;
    @FXML Label greenLabelCharacter1;
    @FXML Label yellowLabelCharacter2;
    @FXML Label blueLabelCharacter2;
    @FXML Label pinkLabelCharacter2;
    @FXML Label redLabelCharacter2;
    @FXML Label greenLabelCharacter2;
    @FXML Label yellowLabelCharacter3;
    @FXML Label blueLabelCharacter3;
    @FXML Label pinkLabelCharacter3;
    @FXML Label redLabelCharacter3;
    @FXML Label greenLabelCharacter3;


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

    @FXML ImageView redCloud1;
    @FXML Label redLabelCloud1;
    @FXML ImageView greenCloud1;
    @FXML Label greenLabelCloud1;
    @FXML ImageView yellowCloud1;
    @FXML Label yellowLabelCloud1;
    @FXML ImageView pinkCloud1;
    @FXML Label pinkLabelCloud1;
    @FXML ImageView blueCloud1;
    @FXML Label blueLabelCloud1;

    @FXML ImageView redCloud2;
    @FXML Label redLabelCloud2;
    @FXML ImageView greenCloud2;
    @FXML Label greenLabelCloud2;
    @FXML ImageView yellowCloud2;
    @FXML Label yellowLabelCloud2;
    @FXML ImageView pinkCloud2;
    @FXML Label pinkLabelCloud2;
    @FXML ImageView blueCloud2;
    @FXML Label blueLabelCloud2;

    @FXML ImageView redCloud3;
    @FXML Label redLabelCloud3;
    @FXML ImageView greenCloud3;
    @FXML Label greenLabelCloud3;
    @FXML ImageView yellowCloud3;
    @FXML Label yellowLabelCloud3;
    @FXML ImageView pinkCloud3;
    @FXML Label pinkLabelCloud3;
    @FXML ImageView blueCloud3;
    @FXML Label blueLabelCloud3;

    @FXML ImageView redCloud4;
    @FXML Label redLabelCloud4;
    @FXML ImageView greenCloud4;
    @FXML Label greenLabelCloud4;
    @FXML ImageView yellowCloud4;
    @FXML Label yellowLabelCloud4;
    @FXML ImageView pinkCloud4;
    @FXML Label pinkLabelCloud4;
    @FXML ImageView blueCloud4;
    @FXML Label blueLabelCloud4;

    @FXML Button island1Button;
    @FXML Button island2Button;
    @FXML Button island3Button;
    @FXML Button island4Button;
    @FXML Button island5Button;
    @FXML Button island6Button;
    @FXML Button island7Button;
    @FXML Button island8Button;
    @FXML Button island9Button;
    @FXML Button island10Button;
    @FXML Button island11Button;
    @FXML Button island12Button;
    @FXML ImageView myBlueProfessor;
    @FXML ImageView myRedProfessor;
    @FXML ImageView myGreenProfessor;
    @FXML ImageView myPinkProfessor;
    @FXML ImageView myYellowProfessor;
    @FXML ImageView topBlueProfessor;
    @FXML ImageView topRedProfessor;
    @FXML ImageView topGreenProfessor;
    @FXML ImageView topPinkProfessor;
    @FXML ImageView topYellowProfessor;
    @FXML ImageView rightBlueProfessor;
    @FXML ImageView rightRedProfessor;
    @FXML ImageView rightGreenProfessor;
    @FXML ImageView rightPinkProfessor;
    @FXML ImageView rightYellowProfessor;
    @FXML ImageView leftBlueProfessor;
    @FXML ImageView leftRedProfessor;
    @FXML ImageView leftGreenProfessor;
    @FXML ImageView leftPinkProfessor;
    @FXML ImageView leftYellowProfessor;
    @FXML ImageView myBoard;
    @FXML ImageView topBoard;
    @FXML ImageView leftBoard;
    @FXML ImageView rightBoard;
    @FXML Button effect1;
    @FXML Button effect2;
    @FXML Button effect3;

    @FXML ImageView myCoins;
    @FXML Label myCoinsLabel;
    private final Image blackTowerImage = new Image("/graphics/wooden_pieces/black_tower.png");
    private final Image greyTowerImage = new Image("/graphics/wooden_pieces/grey_tower.png");
    private final Image whiteTowerImage = new Image("/graphics/wooden_pieces/white_tower.png");

    private final Image assistant1Image = new Image("/graphics/assistants/Assistente (1).png");
    private final Image assistant2Image = new Image("/graphics/assistants/Assistente (2).png");
    private final Image assistant3Image = new Image("/graphics/assistants/Assistente (3).png");
    private final Image assistant4Image = new Image("/graphics/assistants/Assistente (4).png");
    private final Image assistant5Image = new Image("/graphics/assistants/Assistente (5).png");
    private final Image assistant6Image = new Image("/graphics/assistants/Assistente (6).png");
    private final Image assistant7Image = new Image("/graphics/assistants/Assistente (7).png");
    private final Image assistant8Image = new Image("/graphics/assistants/Assistente (8).png");
    private final Image assistant9Image = new Image("/graphics/assistants/Assistente (9).png");
    private final Image assistant10Image = new Image("/graphics/assistants/Assistente (10).png");

    private final Image kingImage = new Image("/graphics/wizards/king_no_bg.png");
    private final Image witchImage = new Image("/graphics/wizards/pixie_no_bg.png");
    private final Image monachImage = new Image("/graphics/wizards/wizard_no_bg.png");
    private final Image forestImage = new Image("/graphics/wizards/sorcerer_no_bg.png");

    private final Image redStudentImage = new Image("/graphics/wooden_pieces/3D/redStudent3D.png");
    private final Image blueStudentImage = new Image("/graphics/wooden_pieces/3D/blueStudent3D.png");
    private final Image greenStudentImage = new Image("/graphics/wooden_pieces/3D/greenStudent3D.png");
    private final Image yellowStudentImage = new Image("/graphics/wooden_pieces/3D/yellowStudent3D.png");
    private final Image pinkStudentImage = new Image("/graphics/wooden_pieces/3D/pinkStudent3D.png");
    private final Image redProfImage = new Image("/graphics/wooden_pieces/3D/redProf3D.png");
    private final Image blueProfImage = new Image("/graphics/wooden_pieces/3D/blueProf3D.png");
    private final Image yellowProfImage = new Image("/graphics/wooden_pieces/3D/yellowProf3D.png");
    private final Image pinkProfImage = new Image("/graphics/wooden_pieces/3D/pinkProf3D.png");
    private final Image greenProfImage = new Image("/graphics/wooden_pieces/3D/greenProf3D.png");

    private final Image centarusImage = new Image("/graphics/characters/centarus.png");
    private final Image farmerImage = new Image("/graphics/characters/farmer.png");
    private final Image fungarusImage = new Image("/graphics/characters/fungarus.png");
    private final Image grannyHerbsImage = new Image("/graphics/characters/grannyHerbs.png");
    private final Image heraldImage = new Image("/graphics/characters/herald.png");
    private final Image jesterImage = new Image("/graphics/characters/jester.png");
    private final Image knightImage = new Image("/graphics/characters/knight.png");
    private final Image magicPostmanImage = new Image("/graphics/characters/magicPostman.png");
    private final Image minestrelImage = new Image("/graphics/characters/minestrel.png");
    private final Image monkImage = new Image("/graphics/characters/monk.png");
    private final Image spoiledPrincessImage = new Image("/graphics/characters/spoiledPrincess.png");
    private final Image thiefImage = new Image("/graphics/characters/thief.png");

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /**
     * Method update is called every time a game copy is received, it updates each part of the main scene (students on boards, clouds, assistants,
     * characters, islands...)
     * @param serverCommand command server indicating if there is a user action in progress in order to show pick buttons
     */
    public void update(String serverCommand) {
        updateDiningRooms();
        updateClouds();
        updateWizard();
        updateTowers();
        updateIslands();
        if(gui.getModelView().getGameCopy().isExpertMode()) {
            updateCharacters();
        } else {
            character1.setVisible(false);
            character2.setVisible(false);
            character3.setVisible(false);
            character1Button.setVisible(false);
            character2Button.setVisible(false);
            character3Button.setVisible(false);
            effect1.setVisible(false);
            effect2.setVisible(false);
            effect3.setVisible(false);
            greenLabelCharacter1.setVisible(false);
            blueLabelCharacter1.setVisible(false);
            pinkLabelCharacter1.setVisible(false);
            redLabelCharacter1.setVisible(false);
            yellowLabelCharacter1.setVisible(false);
            greenLabelCharacter2.setVisible(false);
            blueLabelCharacter2.setVisible(false);
            pinkLabelCharacter2.setVisible(false);
            redLabelCharacter2.setVisible(false);
            yellowLabelCharacter2.setVisible(false);
            greenLabelCharacter3.setVisible(false);
            blueLabelCharacter3.setVisible(false);
            pinkLabelCharacter3.setVisible(false);
            redLabelCharacter3.setVisible(false);
            yellowLabelCharacter3.setVisible(false);
            noCharacterButton.setVisible(false);
            noCharacterButton.setVisible(false);
            character1Coin.setVisible(false);
            character1LabelCoin.setVisible(false);
            character2Coin.setVisible(false);
            character2LabelCoin.setVisible(false);
            character3Coin.setVisible(false);
            character3LabelCoin.setVisible(false);
            greenCharacter1.setVisible(false);
            blueCharacter1.setVisible(false);
            pinkCharacter1.setVisible(false);
            redCharacter1.setVisible(false);
            yellowCharacter1.setVisible(false);
            greenCharacter2.setVisible(false);
            blueCharacter2.setVisible(false);
            pinkCharacter2.setVisible(false);
            redCharacter2.setVisible(false);
            yellowCharacter2.setVisible(false);
            greenCharacter3.setVisible(false);
            blueCharacter3.setVisible(false);
            pinkCharacter3.setVisible(false);
            redCharacter3.setVisible(false);
            yellowCharacter3.setVisible(false);
        }
        updateAssistant();
        updateEntrances();
        updateTowers();
        updateMotherNature();
        updateNoEntryTile();
        updateProfessors();
        updateBoards();

        blueStudent.setVisible(false);
        greenStudent.setVisible(false);
        pinkStudent.setVisible(false);
        yellowStudent.setVisible(false);
        redStudent.setVisible(false);

        blue.setVisible(false);
        green.setVisible(false);
        pink.setVisible(false);
        yellow.setVisible(false);
        red.setVisible(false);

        bluePawn.setVisible(false);
        greenPawn.setVisible(false);
        pinkPawn.setVisible(false);
        yellowPawn.setVisible(false);
        redPawn.setVisible(false);

        cloud1Button.setVisible(false);
        cloud2Button.setVisible(false);
        cloud3Button.setVisible(false);
        cloud4Button.setVisible(false);
        diningRoomButton.setVisible(false);
        askAssistantButton.setVisible(false);
        pickMovesBox.setVisible(false);
        pickCharacterActionNumberBox.setVisible(false);
        descriptionLabel.setText("");

        switch (serverCommand) {
            case "PICK_ASSISTANT" -> showAssistantButton();
            case "PICK_CLOUD" -> askCloud();
            case "PICK_DESTINATION" -> askDestination();
            case "PICK_STUDENT" -> updatePickStudents();
            case "PICK_MOVES_NUMBER" -> {
                if(gui.getModelView().isJesterAction()) {
                    gui.getModelView().setJesterAction(false);
                } else if(gui.getModelView().isMinestrelAction()) {
                    gui.getModelView().setMinestrelAction(false);
                }
                askMoves(gui.getModelView().getGameCopy().getCurrentPlayer().getChosenAssistant());
            }

            case "PICK_CHARACTER" -> askCharacter();
            case "PICK_PAWN_TYPE" -> askPawnType();

            case "PICK_ISLAND" -> askGrannyHerbsIsland();

            case "PICK_CHARACTER_NUMBER" -> askCharacterActionNumber();
            default -> {
                descriptionLabel.setVisible(false);
                System.out.println("Update without user actions (server command not in switch cases)");
            }
        }
    }

    /**
     * Method askCharacterActionNumber asks the number of Jester/Minestrel actions the user wants to play
     */
    public void askCharacterActionNumber() {
        descriptionLabel.setText("Choose the number of actions you want to play");
        descriptionLabel.setVisible(true);
        pickCharacterActionNumberBox.setVisible(true);
        pickCharacterActionNumberBox.getItems().clear();
        if(gui.getModelView().isJesterAction()) {
            for(int i = 1; i <= 3; i++) {
                pickCharacterActionNumberBox.getItems().add(String.valueOf(i));
            }
        } else if(gui.getModelView().isMinestrelAction()){
            for(int i = 1; i <= 2; i++) {
                pickCharacterActionNumberBox.getItems().add(String.valueOf(i));
            }
        }
        pickCharacterActionNumberBox.setOnAction(this::pickCharacterMoves);
    }

    /**
     * Method pickCharacterMoves handles the selection of character moves from the choice box
     * @param e selection event
     */
    public void pickCharacterMoves(ActionEvent e) {
        try {
            int characterMoves = Integer.parseInt(pickCharacterActionNumberBox.getValue());
            gui.getClientConnection().sendUserInput(new PickCharacterActionsNum(characterMoves));
        } catch(NumberFormatException e1) {
            askCharacterActionNumber();
        }
    }

    /**
     * Method updateBoards is used to update all the boards in the main scene,
     * consisting of dining room, professor tables, tower area and entrance
     */
    public void updateBoards() {
        myBoard.setVisible(false);
        leftBoard.setVisible(false);
        rightBoard.setVisible(false);
        topBoard.setVisible(false);
        myCoins.setVisible(false);
        myCoinsLabel.setVisible(false);
        int cont = 0;
        for(Player p : gui.getModelView().getGameCopy().getActivePlayers()) {
            if(p.getNickname().equals(gui.getModelView().getGameCopy().getCurrentPlayer().getNickname())) {
                myBoard.setVisible(true);
                if(gui.getModelView().getGameCopy().isExpertMode()) {
                    myCoinsLabel.setVisible(true);
                    myCoinsLabel.setText(String.valueOf(getCoinsByPlayer(p)));
                    myCoins.setVisible(true);
                }
            } else if(cont==0) {
                topBoard.setVisible(true);
                cont++;
            } else if(cont==1) {
                leftBoard.setVisible(true);
                cont++;
            } else if(cont==2) {
                rightBoard.setVisible(true);
                cont++;
            }
        }
    }

    /**
     * Method getCoinsByPlayer returns the number of coins owned by a player
     * @param p player
     * @return coins number
     */
    public int getCoinsByPlayer(Player p) {
        int coins = 0;
        for(Player pl : gui.getModelView().getGameCopy().getActivePlayers()) {
            if(p.equals(pl)) {
                coins = pl.getMyCoins();
            }
        }
        return coins;
    }

    /**
     * Method updateProfessors is used to update the professors' tables in the different dining rooms
     */
        public void updateProfessors() {
            myBlueProfessor.setVisible(false);
            myYellowProfessor.setVisible(false);
            myPinkProfessor.setVisible(false);
            myGreenProfessor.setVisible(false);
            myRedProfessor.setVisible(false);
            rightBlueProfessor.setVisible(false);
            rightYellowProfessor.setVisible(false);
            rightPinkProfessor.setVisible(false);
            rightGreenProfessor.setVisible(false);
            rightRedProfessor.setVisible(false);
            leftBlueProfessor.setVisible(false);
            leftYellowProfessor.setVisible(false);
            leftPinkProfessor.setVisible(false);
            leftGreenProfessor.setVisible(false);
            leftRedProfessor.setVisible(false);
            topBlueProfessor.setVisible(false);
            topYellowProfessor.setVisible(false);
            topPinkProfessor.setVisible(false);
            topGreenProfessor.setVisible(false);
            topRedProfessor.setVisible(false);
            int cont = 0;
            Image pic;
            for (Player pl : gui.getModelView().getGameCopy().getActivePlayers()) {
                if (pl.getNickname().equals(gui.getModelView().getPlayerNickname())) {
                    for (BoardCell b : pl.getBoard().getProfessorTable().getProfTable()) {
                        if (b.hasProfessor()) {
                            if (b.getBoardCellType() == PawnType.BLUE) {
                                pic = blueProfImage;
                                myBlueProfessor.setImage(pic);
                                myBlueProfessor.setVisible(true);
                            } else if (b.getBoardCellType() == PawnType.GREEN) {
                                pic = greenProfImage;
                                myGreenProfessor.setImage(pic);
                                myGreenProfessor.setVisible(true);
                            } else if (b.getBoardCellType() == PawnType.PINK) {
                                pic = pinkProfImage;
                                myPinkProfessor.setImage(pic);
                                myPinkProfessor.setVisible(true);
                            } else if (b.getBoardCellType() == PawnType.RED) {
                                pic = redProfImage;
                                myRedProfessor.setImage(pic);
                                myRedProfessor.setVisible(true);
                            } else if (b.getBoardCellType() == PawnType.YELLOW) {
                                pic = yellowProfImage;
                                myYellowProfessor.setImage(pic);
                                myYellowProfessor.setVisible(true);
                            }
                        }
                    }
                } else if (cont == 0) {
                    //top Player corresponding to cont 0
                    for (BoardCell b : pl.getBoard().getProfessorTable().getProfTable()) {
                        if (b.hasProfessor()) {
                            if (b.getBoardCellType() == PawnType.BLUE) {
                                pic = blueProfImage;
                                topBlueProfessor.setImage(pic);
                                topBlueProfessor.setVisible(true);
                            } else if (b.getBoardCellType() == PawnType.GREEN) {
                                pic = greenProfImage;
                                topGreenProfessor.setImage(pic);
                                topGreenProfessor.setVisible(true);
                            } else if (b.getBoardCellType() == PawnType.PINK) {
                                pic = pinkProfImage;
                                topPinkProfessor.setImage(pic);
                                topPinkProfessor.setVisible(true);
                            } else if (b.getBoardCellType() == PawnType.RED) {
                                pic = redProfImage;
                                topRedProfessor.setImage(pic);
                                topRedProfessor.setVisible(true);
                            } else if (b.getBoardCellType() == PawnType.YELLOW) {
                                pic = yellowProfImage;
                                topYellowProfessor.setImage(pic);
                                topYellowProfessor.setVisible(true);
                            }
                        }
                    }
                    cont++;
                } else if (cont == 1) {
                    //left Player corresponding to cont 1
                    for (BoardCell b : pl.getBoard().getProfessorTable().getProfTable()) {
                        if (b.hasProfessor()) {
                            if (b.getBoardCellType() == PawnType.BLUE) {
                                pic = blueProfImage;
                                leftBlueProfessor.setImage(pic);
                                leftBlueProfessor.setVisible(true);
                            } else if (b.getBoardCellType() == PawnType.GREEN) {
                                pic = greenProfImage;
                                leftGreenProfessor.setImage(pic);
                                leftGreenProfessor.setVisible(true);
                            } else if (b.getBoardCellType() == PawnType.PINK) {
                                pic = pinkProfImage;
                                leftPinkProfessor.setImage(pic);
                                leftPinkProfessor.setVisible(true);
                            } else if (b.getBoardCellType() == PawnType.RED) {
                                pic = redProfImage;
                                leftRedProfessor.setImage(pic);
                                leftRedProfessor.setVisible(true);
                            } else if (b.getBoardCellType() == PawnType.YELLOW) {
                                pic = yellowProfImage;
                                leftYellowProfessor.setImage(pic);
                                leftYellowProfessor.setVisible(true);
                            }
                        }
                    }
                    cont++;
                } else if (cont == 2) {
                    for (BoardCell b : pl.getBoard().getProfessorTable().getProfTable()) {
                        if (b.hasProfessor()) {
                            if (b.getBoardCellType() == PawnType.BLUE) {
                                pic = blueProfImage;
                                rightBlueProfessor.setImage(pic);
                                rightBlueProfessor.setVisible(true);
                            } else if (b.getBoardCellType() == PawnType.GREEN) {
                                pic = greenProfImage;
                                rightGreenProfessor.setImage(pic);
                                rightGreenProfessor.setVisible(true);
                            } else if (b.getBoardCellType() == PawnType.PINK) {
                                pic = pinkProfImage;
                                rightPinkProfessor.setImage(pic);
                                rightPinkProfessor.setVisible(true);
                            } else if (b.getBoardCellType() == PawnType.RED) {
                                pic = redProfImage;
                                rightRedProfessor.setImage(pic);
                                rightRedProfessor.setVisible(true);
                            } else if (b.getBoardCellType() == PawnType.YELLOW) {
                                pic = yellowProfImage;
                                rightYellowProfessor.setImage(pic);
                                rightYellowProfessor.setVisible(true);
                            }
                        }
                    }
                    cont++;
                }
            }
        }

    /**
     * Method askMoves shows the choice box from which is possible to select the number of mother nature moves,
     * according to the assistant chosen in the current turn
     * @param a assistant chosen
     */
    public void askMoves(AssistantCard a) {
        descriptionLabel.setText("Choose mother nature's moves number from the box");
        descriptionLabel.setVisible(true);
        pickMovesBox.setVisible(true);
        pickMovesBox.getItems().clear();
        for(int i = 1; i <= a.getMoves(); i++) {
            pickMovesBox.getItems().add(String.valueOf(i));
        }
        if(gui.getModelView().isMagicPostmanAction()) {
            pickMovesBox.getItems().add(String.valueOf(a.getMoves() + 1));
            pickMovesBox.getItems().add(String.valueOf(a.getMoves() + 2));
        }
        pickMovesBox.setOnAction(this::pickMoves);
    }

    /**
     * Method pickMoves handles the selection event of a number of moves from the choice box
     * Then it sends it to server
     * @param e selection event
     */
     public void pickMoves(ActionEvent e) {
         int moves = Integer.parseInt(pickMovesBox.getValue());
         gui.getClientConnection().sendUserInput(new PickMovesNumber(moves));
     }

    /**
     * Method updateMotherNature updates mother nature position on islands
     */
     public void updateMotherNature() {
        motherNature1.setVisible(false);
        motherNature2.setVisible(false);
        motherNature3.setVisible(false);
        motherNature4.setVisible(false);
        motherNature5.setVisible(false);
        motherNature6.setVisible(false);
        motherNature7.setVisible(false);
        motherNature8.setVisible(false);
        motherNature9.setVisible(false);
        motherNature10.setVisible(false);
        motherNature11.setVisible(false);
        motherNature12.setVisible(false);
        int motherNaturePosition = gui.getModelView().getGameCopy().getGameBoard().getMotherNature().getPosition();
        switch (motherNaturePosition) {
            case 1 -> motherNature1.setVisible(true);
            case 2 -> motherNature2.setVisible(true);
            case 3 -> motherNature3.setVisible(true);
            case 4 -> motherNature4.setVisible(true);
            case 5 -> motherNature5.setVisible(true);
            case 6 -> motherNature6.setVisible(true);
            case 7 -> motherNature7.setVisible(true);
            case 8 -> motherNature8.setVisible(true);
            case 9 -> motherNature9.setVisible(true);
            case 10 -> motherNature10.setVisible(true);
            case 11 -> motherNature11.setVisible(true);
            case 12 -> motherNature12.setVisible(true);
        }
    }

    /**
     * Method askCharacter asks the user if they want to play a character by showing the corresponding buttons
     */
    public void askCharacter() {
        descriptionLabel.setText("Pick a character or play \"No Character\"");
        noCharacterButton.setVisible(true);
        if(gui.getModelView().getGameCopy().getCurrentPlayer().getMyCoins() >= gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(0).getInitialCost()) {
            character1Button.setVisible(true);
        }
        if(gui.getModelView().getGameCopy().getCurrentPlayer().getMyCoins() >= gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(1).getInitialCost()) {
            character2Button.setVisible(true);
        }
        if(gui.getModelView().getGameCopy().getCurrentPlayer().getMyCoins() >= gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(2).getInitialCost()) {
            character3Button.setVisible(true);
        }
    }


    /**
     * Method updateNoEntryTile updates the no entry tiles on islands if they have been played
     */
    public void updateNoEntryTile() {
        noEntryTile1.setVisible(false);
        noEntryTile2.setVisible(false);
        noEntryTile3.setVisible(false);
        noEntryTile4.setVisible(false);
        noEntryTile5.setVisible(false);
        noEntryTile6.setVisible(false);
        noEntryTile7.setVisible(false);
        noEntryTile8.setVisible(false);
        noEntryTile9.setVisible(false);
        noEntryTile10.setVisible(false);
        noEntryTile11.setVisible(false);
        noEntryTile12.setVisible(false);
        for (Island i : gui.getModelView().getGameCopy().getGameBoard().getIslands()) {
            if (i.getNoEntry()) {
                switch (i.getIslandID()) {
                    case 1 -> noEntryTile1.setVisible(true);
                    case 2 -> noEntryTile2.setVisible(true);
                    case 3 -> noEntryTile3.setVisible(true);
                    case 4 -> noEntryTile4.setVisible(true);
                    case 5 -> noEntryTile5.setVisible(true);
                    case 6 -> noEntryTile6.setVisible(true);
                    case 7 -> noEntryTile7.setVisible(true);
                    case 8 -> noEntryTile8.setVisible(true);
                    case 9 -> noEntryTile9.setVisible(true);
                    case 10 -> noEntryTile10.setVisible(true);
                    case 11 -> noEntryTile11.setVisible(true);
                    case 12 -> noEntryTile12.setVisible(true);
                }
            }
        }
    }

    /**
     * Method pickIsland1 handles the selection event of island1
     */
    public void pickIsland1() {
        for(Island i : gui.getModelView().getGameCopy().getGameBoard().getIslands()) {
            if(i.getIslandID() == 1) {
                gui.getClientConnection().sendUserInput(new PickDestination(i));
                break;
            }
        }
    }
    /**
     * Method pickIsland2 handles the selection event of island2
     */
    public void pickIsland2() {
        for(Island i : gui.getModelView().getGameCopy().getGameBoard().getIslands()) {
            if(i.getIslandID() == 2) {
                gui.getClientConnection().sendUserInput(new PickDestination(i));
                break;
            }
        }
    }
    /**
     * Method pickIsland3 handles the selection event of island3
     */
    public void pickIsland3() {
        for(Island i : gui.getModelView().getGameCopy().getGameBoard().getIslands()) {
            if(i.getIslandID() == 3) {
                gui.getClientConnection().sendUserInput(new PickDestination(i));
                break;
            }
        }
    }
    /**
     * Method pickIsland4 handles the selection event of island4
     */
    public void pickIsland4() {
        for(Island i : gui.getModelView().getGameCopy().getGameBoard().getIslands()) {
            if(i.getIslandID() == 4) {
                gui.getClientConnection().sendUserInput(new PickDestination(i));
                break;
            }
        }
    }
    /**
     * Method pickIsland5 handles the selection event of island5
     */
    public void pickIsland5() {
        for(Island i : gui.getModelView().getGameCopy().getGameBoard().getIslands()) {
            if(i.getIslandID() == 5) {
                gui.getClientConnection().sendUserInput(new PickDestination(i));
                break;
            }
        }
    }
    /**
     * Method pickIsland6 handles the selection event of island6
     */
    public void pickIsland6() {
        for(Island i : gui.getModelView().getGameCopy().getGameBoard().getIslands()) {
            if(i.getIslandID() == 6) {
                gui.getClientConnection().sendUserInput(new PickDestination(i));
                break;
            }
        }
    }
    /**
     * Method pickIsland7 handles the selection event of island7
     */
    public void pickIsland7() {
        for(Island i : gui.getModelView().getGameCopy().getGameBoard().getIslands()) {
            if(i.getIslandID() == 7) {
                gui.getClientConnection().sendUserInput(new PickDestination(i));
                break;
            }
        }
    }
    /**
     * Method pickIsland8 handles the selection event of island8
     */
    public void pickIsland8() {
        for(Island i : gui.getModelView().getGameCopy().getGameBoard().getIslands()) {
            if(i.getIslandID() == 8) {
                gui.getClientConnection().sendUserInput(new PickDestination(i));
                break;
            }
        }
    }
    /**
     * Method pickIsland9 handles the selection event of island9
     */
    public void pickIsland9() {
        for(Island i : gui.getModelView().getGameCopy().getGameBoard().getIslands()) {
            if(i.getIslandID() == 9) {
                gui.getClientConnection().sendUserInput(new PickDestination(i));
                break;
            }
        }
    }
    /**
     * Method pickIsland10 handles the selection event of island10
     */
    public void pickIsland10() {
        for(Island i : gui.getModelView().getGameCopy().getGameBoard().getIslands()) {
            if(i.getIslandID() == 10) {
                gui.getClientConnection().sendUserInput(new PickDestination(i));
                break;
            }
        }
    }
    /**
     * Method pickIsland11 handles the selection event of island11
     */
    public void pickIsland11() {
        for(Island i : gui.getModelView().getGameCopy().getGameBoard().getIslands()) {
            if(i.getIslandID() == 11) {
                gui.getClientConnection().sendUserInput(new PickDestination(i));
                break;
            }
        }
    }
    /**
     * Method pickIsland12 handles the selection event of island12
     */
    public void pickIsland12() {
        for(Island i : gui.getModelView().getGameCopy().getGameBoard().getIslands()) {
            if(i.getIslandID() == 12) {
                gui.getClientConnection().sendUserInput(new PickDestination(i));
                break;
            }
        }
    }

    /**
     * Method pickDiningRoomDestination handles the selection of dining room as destination
     * Then it sends to the server
     */
    public void pickDiningRoomDestination() {
        PickDestination action = new PickDestination(gui.getModelView().getGameCopy().getCurrentPlayer().getBoard().getDiningRoom());
        diningRoomButton.setVisible(false);
        gui.getClientConnection().sendUserInput(action);
    }

    /**
     * Method showEffect1 shows an info alert with the description of character 1's effect
     */
    public void showEffect1() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Characters Effect");
        alert.setHeaderText(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(0).getName() + "'s effect");
        alert.setContentText(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(0).getEffect());
        alert.show();
    }
    /**
     * Method showEffect2 shows an info alert with the description of character 2's effect
     */
    public void showEffect2() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Characters Effect");
        alert.setHeaderText(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(1).getName() + "'s effect");
        alert.setContentText(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(1).getEffect());
        alert.show();

    }
    /**
     * Method showEffect3 shows an info alert with the description of character 3's effect
     */
    public void showEffect3() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Characters Effect");
        alert.setHeaderText(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(2).getName() + "'s effect");
        alert.setContentText(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(2).getEffect());
        alert.show();
    }

    /**
     * Method askDestination asks the user the destination for the picked student,
     * making him choose between dining room or an island
     */
    public void askDestination() {
        descriptionLabel.setText("Pick an island or the dining room button to choose the student destination");
        descriptionLabel.setVisible(true);
        diningRoomButton.setVisible(true);
        for(Island i : gui.getModelView().getGameCopy().getGameBoard().getIslands()) {
            if(i.getIslandID() == 1) island1Button.setVisible(true);
            else if(i.getIslandID() == 2) island2Button.setVisible(true);
            else if(i.getIslandID() == 3) island3Button.setVisible(true);
            else if(i.getIslandID() == 4) island4Button.setVisible(true);
            else if(i.getIslandID() == 5) island5Button.setVisible(true);
            else if(i.getIslandID() == 6) island6Button.setVisible(true);
            else if(i.getIslandID() == 7) island7Button.setVisible(true);
            else if(i.getIslandID() == 8) island8Button.setVisible(true);
            else if(i.getIslandID() == 9) island9Button.setVisible(true);
            else if(i.getIslandID() == 10) island10Button.setVisible(true);
            else if(i.getIslandID() == 11) island11Button.setVisible(true);
            else if(i.getIslandID() == 12) island12Button.setVisible(true);
        }
    }

    /**
     * Method askGrannyHerbsIsland asks the user an island where to put the no entry tile got from the Granny Herbs
     */
    public void askGrannyHerbsIsland() {
        descriptionLabel.setText("Pick an island where you want to put a NO_ENTRY tile!");
        descriptionLabel.setVisible(true);
        for(Island i : gui.getModelView().getGameCopy().getGameBoard().getIslands()) {
            if(i.getIslandID() == 1) island1Button.setVisible(true);
            else if(i.getIslandID() == 2) island2Button.setVisible(true);
            else if(i.getIslandID() == 3) island3Button.setVisible(true);
            else if(i.getIslandID() == 4) island4Button.setVisible(true);
            else if(i.getIslandID() == 5) island5Button.setVisible(true);
            else if(i.getIslandID() == 6) island6Button.setVisible(true);
            else if(i.getIslandID() == 7) island7Button.setVisible(true);
            else if(i.getIslandID() == 8) island8Button.setVisible(true);
            else if(i.getIslandID() == 9) island9Button.setVisible(true);
            else if(i.getIslandID() == 10) island10Button.setVisible(true);
            else if(i.getIslandID() == 11) island11Button.setVisible(true);
            else if(i.getIslandID() == 12) island12Button.setVisible(true);
        }
    }

    /**
     * Method showAssistantButton shows the pick assistant button and the description telling the user what to do
     */
    public void showAssistantButton() {
        descriptionLabel.setText("Click the pick assistant button to choose your assistant. ");
        descriptionLabel.setVisible(true);
        askAssistantButton.setVisible(true);
    }

    /**
     * Method askAssistant shows the stage for the assistant choice by running a new thread
     */
    public void askAssistant() {
        Platform.runLater(() -> {
            PickAssistantController controller = (PickAssistantController) gui.getControllerFromName("PickAssistant.fxml");
            controller.askAssistant();
        });

    }

    /**
     * Method updateDiningRooms updates all the dining rooms by showing the students on them
     */
    public void updateDiningRooms() {
        for(int j=0; j < 10; j++) {
            myBlueStudents.getChildren().get(j).setVisible(false);
            myRedStudents.getChildren().get(j).setVisible(false);
            myYellowStudents.getChildren().get(j).setVisible(false);
            myGreenStudents.getChildren().get(j).setVisible(false);
            myPinkStudents.getChildren().get(j).setVisible(false);
            topBlueStudents.getChildren().get(j).setVisible(false);
            topRedStudents.getChildren().get(j).setVisible(false);
            topYellowStudents.getChildren().get(j).setVisible(false);
            topGreenStudents.getChildren().get(j).setVisible(false);
            topPinkStudents.getChildren().get(j).setVisible(false);
            rightBlueStudents.getChildren().get(j).setVisible(false);
            rightRedStudents.getChildren().get(j).setVisible(false);
            rightYellowStudents.getChildren().get(j).setVisible(false);
            rightGreenStudents.getChildren().get(j).setVisible(false);
            rightPinkStudents.getChildren().get(j).setVisible(false);
            leftBlueStudents.getChildren().get(j).setVisible(false);
            leftRedStudents.getChildren().get(j).setVisible(false);
            leftYellowStudents.getChildren().get(j).setVisible(false);
            leftGreenStudents.getChildren().get(j).setVisible(false);
            leftPinkStudents.getChildren().get(j).setVisible(false);
        }
        Image pic;
        ImageView img;
        int cont = 0;
        for (Player pl : gui.getModelView().getGameCopy().getActivePlayers()) {
            if (pl.getNickname().equals(gui.getModelView().getPlayerNickname())) {
                for (Table t : pl.getBoard().getDiningRoom().getDiningRoom()) {
                    for (int i = 0; i < t.getDiningTable().size(); i++) {
                        if (t.getDiningTable().get(i).hasStudent()) {
                            if (t.getDiningTable().get(i).getBoardCellType() == PawnType.BLUE) {
                                pic = blueStudentImage;
                                img = ((ImageView) myBlueStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getDiningTable().get(i).getBoardCellType() == PawnType.GREEN) {
                                pic = greenStudentImage;
                                img = ((ImageView) myGreenStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getDiningTable().get(i).getBoardCellType() == PawnType.RED) {
                                pic = redStudentImage;
                                img = ((ImageView) myRedStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getDiningTable().get(i).getBoardCellType() == PawnType.PINK) {
                                pic = pinkStudentImage;
                                img = ((ImageView) myPinkStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getDiningTable().get(i).getBoardCellType() == PawnType.YELLOW) {
                                pic = yellowStudentImage;
                                img = ((ImageView) myYellowStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            }
                        }
                    }
                }
            } else if (cont == 0){
                for (Table t : pl.getBoard().getDiningRoom().getDiningRoom()) {
                    for (int i = 0; i < t.getDiningTable().size(); i++) {
                        if (t.getDiningTable().get(i).hasStudent()) {
                            if (t.getDiningTable().get(i).getBoardCellType() == PawnType.BLUE) {
                                pic = blueStudentImage;
                                img = ((ImageView) topBlueStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getDiningTable().get(i).getBoardCellType() == PawnType.GREEN) {
                                pic = greenStudentImage;
                                img = ((ImageView) topGreenStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getDiningTable().get(i).getBoardCellType() == PawnType.RED) {
                                pic = redStudentImage;
                                img = ((ImageView) topRedStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getDiningTable().get(i).getBoardCellType() == PawnType.PINK) {
                                pic = pinkStudentImage;
                                img = ((ImageView) topPinkStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getDiningTable().get(i).getBoardCellType() == PawnType.YELLOW) {
                                pic = yellowStudentImage;
                                img = ((ImageView) topYellowStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            }
                        }
                    }
                }
                cont++;
            } else if (cont == 1) {
                for (Table t : pl.getBoard().getDiningRoom().getDiningRoom()) {
                    for (int i = 0; i < t.getDiningTable().size(); i++) {
                        if (t.getDiningTable().get(i).hasStudent()) {
                            if (t.getDiningTable().get(i).getBoardCellType() == PawnType.BLUE) {
                                pic = blueStudentImage;
                                img = ((ImageView) leftBlueStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getDiningTable().get(i).getBoardCellType() == PawnType.GREEN) {
                                pic = greenStudentImage;
                                img = ((ImageView) leftGreenStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getDiningTable().get(i).getBoardCellType() == PawnType.RED) {
                                pic = redStudentImage;
                                img = ((ImageView) leftRedStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getDiningTable().get(i).getBoardCellType() == PawnType.PINK) {
                                pic = pinkStudentImage;
                                img = ((ImageView) leftPinkStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getDiningTable().get(i).getBoardCellType() == PawnType.YELLOW) {
                                pic = yellowStudentImage;
                                img = ((ImageView) leftYellowStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            }
                        }
                    }
                }
                cont++;
            } else if (cont == 2) {
                for (Table t : pl.getBoard().getDiningRoom().getDiningRoom()) {
                    for (int i = 0; i < t.getDiningTable().size(); i++) {
                        if (t.getDiningTable().get(i).hasStudent()) {
                            if (t.getDiningTable().get(i).getBoardCellType() == PawnType.BLUE) {
                                pic = blueStudentImage;
                                img = ((ImageView) rightBlueStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getDiningTable().get(i).getBoardCellType() == PawnType.GREEN) {
                                pic = greenStudentImage;
                                img = ((ImageView) rightGreenStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getDiningTable().get(i).getBoardCellType() == PawnType.RED) {
                                pic = redStudentImage;
                                img = ((ImageView) rightRedStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getDiningTable().get(i).getBoardCellType() == PawnType.PINK) {
                                pic = pinkStudentImage;
                                img = ((ImageView) rightPinkStudents.getChildren().get(i));
                                img.setImage(pic);
                                img.setVisible(true);
                            } else if (t.getDiningTable().get(i).getBoardCellType() == PawnType.YELLOW) {
                                pic = yellowStudentImage;
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

    /**
     * Method updateIslands updates the islands status of the game by showing the students and towers on them
     */
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
        island1Button.setVisible(false);
        island2Button.setVisible(false);
        island3Button.setVisible(false);
        island4Button.setVisible(false);
        island5Button.setVisible(false);
        island6Button.setVisible(false);
        island7Button.setVisible(false);
        island8Button.setVisible(false);
        island9Button.setVisible(false);
        island10Button.setVisible(false);
        island11Button.setVisible(false);
        island12Button.setVisible(false);
        towerIsland1.setVisible(false);
        towerLabelIsland1.setVisible(false);
        towerIsland2.setVisible(false);
        towerLabelIsland2.setVisible(false);
        towerIsland3.setVisible(false);
        towerLabelIsland3.setVisible(false);
        towerIsland4.setVisible(false);
        towerLabelIsland4.setVisible(false);
        towerIsland5.setVisible(false);
        towerLabelIsland5.setVisible(false);
        towerIsland6.setVisible(false);
        towerLabelIsland6.setVisible(false);
        towerIsland7.setVisible(false);
        towerLabelIsland7.setVisible(false);
        towerIsland8.setVisible(false);
        towerLabelIsland8.setVisible(false);
        towerIsland9.setVisible(false);
        towerLabelIsland9.setVisible(false);
        towerIsland10.setVisible(false);
        towerLabelIsland10.setVisible(false);
        towerIsland11.setVisible(false);
        towerLabelIsland11.setVisible(false);
        towerIsland12.setVisible(false);
        towerLabelIsland12.setVisible(false);

        redIsland1.setVisible(false);
        redLabelIsland1.setVisible(false);
        yellowIsland1.setVisible(false);
        yellowLabelIsland1.setVisible(false);
        greenIsland1.setVisible(false);
        greenLabelIsland1.setVisible(false);
        blueIsland1.setVisible(false);
        blueLabelIsland1.setVisible(false);
        pinkIsland1.setVisible(false);
        pinkLabelIsland1.setVisible(false);

        redIsland2.setVisible(false);
        redLabelIsland2.setVisible(false);
        yellowIsland2.setVisible(false);
        yellowLabelIsland2.setVisible(false);
        greenIsland2.setVisible(false);
        greenLabelIsland2.setVisible(false);
        blueIsland2.setVisible(false);
        blueLabelIsland2.setVisible(false);
        pinkIsland2.setVisible(false);
        pinkLabelIsland2.setVisible(false);

        redIsland3.setVisible(false);
        redLabelIsland3.setVisible(false);
        yellowIsland3.setVisible(false);
        yellowLabelIsland3.setVisible(false);
        greenIsland3.setVisible(false);
        greenLabelIsland3.setVisible(false);
        blueIsland3.setVisible(false);
        blueLabelIsland3.setVisible(false);
        pinkIsland3.setVisible(false);
        pinkLabelIsland3.setVisible(false);

        redIsland4.setVisible(false);
        redLabelIsland4.setVisible(false);
        yellowIsland4.setVisible(false);
        yellowLabelIsland4.setVisible(false);
        greenIsland4.setVisible(false);
        greenLabelIsland4.setVisible(false);
        blueIsland4.setVisible(false);
        blueLabelIsland4.setVisible(false);
        pinkIsland4.setVisible(false);
        pinkLabelIsland4.setVisible(false);

        redIsland5.setVisible(false);
        redLabelIsland5.setVisible(false);
        yellowIsland5.setVisible(false);
        yellowLabelIsland5.setVisible(false);
        greenIsland5.setVisible(false);
        greenLabelIsland5.setVisible(false);
        blueIsland5.setVisible(false);
        blueLabelIsland5.setVisible(false);
        pinkIsland5.setVisible(false);
        pinkLabelIsland5.setVisible(false);

        redIsland6.setVisible(false);
        redLabelIsland6.setVisible(false);
        yellowIsland6.setVisible(false);
        yellowLabelIsland6.setVisible(false);
        greenIsland6.setVisible(false);
        greenLabelIsland6.setVisible(false);
        blueIsland6.setVisible(false);
        blueLabelIsland6.setVisible(false);
        pinkIsland6.setVisible(false);
        pinkLabelIsland6.setVisible(false);

        redIsland7.setVisible(false);
        redLabelIsland7.setVisible(false);
        yellowIsland7.setVisible(false);
        yellowLabelIsland7.setVisible(false);
        greenIsland7.setVisible(false);
        greenLabelIsland7.setVisible(false);
        blueIsland7.setVisible(false);
        blueLabelIsland7.setVisible(false);
        pinkIsland7.setVisible(false);
        pinkLabelIsland7.setVisible(false);

        redIsland8.setVisible(false);
        redLabelIsland8.setVisible(false);
        yellowIsland8.setVisible(false);
        yellowLabelIsland8.setVisible(false);
        greenIsland8.setVisible(false);
        greenLabelIsland8.setVisible(false);
        blueIsland8.setVisible(false);
        blueLabelIsland8.setVisible(false);
        pinkIsland8.setVisible(false);
        pinkLabelIsland8.setVisible(false);

        redIsland9.setVisible(false);
        redLabelIsland9.setVisible(false);
        yellowIsland9.setVisible(false);
        yellowLabelIsland9.setVisible(false);
        greenIsland9.setVisible(false);
        greenLabelIsland9.setVisible(false);
        blueIsland9.setVisible(false);
        blueLabelIsland9.setVisible(false);
        pinkIsland9.setVisible(false);
        pinkLabelIsland9.setVisible(false);

        redIsland10.setVisible(false);
        redLabelIsland10.setVisible(false);
        yellowIsland10.setVisible(false);
        yellowLabelIsland10.setVisible(false);
        greenIsland10.setVisible(false);
        greenLabelIsland10.setVisible(false);
        blueIsland10.setVisible(false);
        blueLabelIsland10.setVisible(false);
        pinkIsland10.setVisible(false);
        pinkLabelIsland10.setVisible(false);

        redIsland11.setVisible(false);
        redLabelIsland11.setVisible(false);
        yellowIsland11.setVisible(false);
        yellowLabelIsland11.setVisible(false);
        greenIsland11.setVisible(false);
        greenLabelIsland11.setVisible(false);
        blueIsland11.setVisible(false);
        blueLabelIsland11.setVisible(false);
        pinkIsland11.setVisible(false);
        pinkLabelIsland11.setVisible(false);

        redIsland12.setVisible(false);
        redLabelIsland12.setVisible(false);
        yellowIsland12.setVisible(false);
        yellowLabelIsland12.setVisible(false);
        greenIsland12.setVisible(false);
        greenLabelIsland12.setVisible(false);
        blueIsland12.setVisible(false);
        blueLabelIsland12.setVisible(false);
        pinkIsland12.setVisible(false);
        pinkLabelIsland12.setVisible(false);


        int cont = 0;
        for (Island isl : gui.getModelView().getGameCopy().getGameBoard().getIslands()) {
            if (isl.getIslandID() == 1) {
                island1.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(cont).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland1.setVisible(true);
                        redLabelIsland1.setVisible(true);
                        redLabelIsland1.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowIsland1.setVisible(true);
                        yellowLabelIsland1.setVisible(true);
                        yellowLabelIsland1.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.PINK)) {
                        pinkIsland1.setVisible(true);
                        pinkLabelIsland1.setVisible(true);
                        pinkLabelIsland1.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.BLUE)) {
                        blueIsland1.setVisible(true);
                        blueLabelIsland1.setVisible(true);
                        blueLabelIsland1.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenIsland1.setVisible(true);
                        greenLabelIsland1.setVisible(true);
                        greenLabelIsland1.setText(getStudentsNumber(isl, s));
                    }
                }
                if(isl.hasTower()) {
                    towerIsland1.setVisible(true);
                    towerIsland1.setImage(setTowersImage(isl.getTower()));
                    towerLabelIsland1.setText(getTowersNumber(isl));
                    towerLabelIsland1.setVisible(true);
                }
                cont++;
            } else if (isl.getIslandID() == 2) {
                island2.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(cont).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland2.setVisible(true);
                        redLabelIsland2.setVisible(true);
                        redLabelIsland2.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowIsland2.setVisible(true);
                        yellowLabelIsland2.setVisible(true);
                        yellowLabelIsland2.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.PINK)) {
                        pinkIsland2.setVisible(true);
                        pinkLabelIsland2.setVisible(true);
                        pinkLabelIsland2.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.BLUE)) {
                        blueIsland2.setVisible(true);
                        blueLabelIsland2.setVisible(true);
                        blueLabelIsland2.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenIsland2.setVisible(true);
                        greenLabelIsland2.setVisible(true);
                        greenLabelIsland2.setText(getStudentsNumber(isl, s));
                    }
                }
                if(isl.hasTower()) {
                    towerIsland2.setVisible(true);
                    towerIsland2.setImage(setTowersImage(isl.getTower()));
                    towerLabelIsland2.setText(getTowersNumber(isl));
                    towerLabelIsland2.setVisible(true);
                }

                cont++;
            } else if (isl.getIslandID() == 3) {
                island3.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(cont).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland3.setVisible(true);
                        redLabelIsland3.setVisible(true);
                        redLabelIsland3.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowIsland3.setVisible(true);
                        yellowLabelIsland3.setVisible(true);
                        yellowLabelIsland3.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.PINK)) {
                        pinkIsland3.setVisible(true);
                        pinkLabelIsland3.setVisible(true);
                        pinkLabelIsland3.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.BLUE)) {
                        blueIsland3.setVisible(true);
                        blueLabelIsland3.setVisible(true);
                        blueLabelIsland3.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenIsland3.setVisible(true);
                        greenLabelIsland3.setVisible(true);
                        greenLabelIsland3.setText(getStudentsNumber(isl, s));
                    }
                }
                if(isl.hasTower()) {
                    towerIsland3.setVisible(true);
                    towerIsland3.setImage(setTowersImage(isl.getTower()));
                    towerLabelIsland3.setText(getTowersNumber(isl));
                    towerLabelIsland3.setVisible(true);
                }
                cont++;

            } else if (isl.getIslandID() == 4) {
                island4.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(cont).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland4.setVisible(true);
                        redLabelIsland4.setVisible(true);
                        redLabelIsland4.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowIsland4.setVisible(true);
                        yellowLabelIsland4.setVisible(true);
                        yellowLabelIsland4.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.PINK)) {
                        pinkIsland4.setVisible(true);
                        pinkLabelIsland4.setVisible(true);
                        pinkLabelIsland4.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.BLUE)) {
                        blueIsland4.setVisible(true);
                        blueLabelIsland4.setVisible(true);
                        blueLabelIsland4.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenIsland4.setVisible(true);
                        greenLabelIsland4.setVisible(true);
                        greenLabelIsland4.setText(getStudentsNumber(isl, s));
                    }
                }
                if(isl.hasTower()) {
                    towerIsland4.setVisible(true);
                    towerIsland4.setImage(setTowersImage(isl.getTower()));
                    towerLabelIsland4.setText(getTowersNumber(isl));
                    towerLabelIsland4.setVisible(true);
                }
                cont++;

            } else if (isl.getIslandID() == 5) {
                island5.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(cont).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland5.setVisible(true);
                        redLabelIsland5.setVisible(true);
                        redLabelIsland5.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowIsland5.setVisible(true);
                        yellowLabelIsland5.setVisible(true);
                        yellowLabelIsland5.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.PINK)) {
                        pinkIsland5.setVisible(true);
                        pinkLabelIsland5.setVisible(true);
                        pinkLabelIsland5.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.BLUE)) {
                        blueIsland5.setVisible(true);
                        blueLabelIsland5.setVisible(true);
                        blueLabelIsland5.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenIsland5.setVisible(true);
                        greenLabelIsland5.setVisible(true);
                        greenLabelIsland5.setText(getStudentsNumber(isl, s));
                    }
                }
                if(isl.hasTower()) {
                    towerIsland5.setVisible(true);
                    towerIsland5.setImage(setTowersImage(isl.getTower()));
                    towerLabelIsland5.setText(getTowersNumber(isl));
                    towerLabelIsland5.setVisible(true);
                }
                cont++;

            } else if (isl.getIslandID() == 6) {
                island6.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(cont).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland6.setVisible(true);
                        redLabelIsland6.setVisible(true);
                        redLabelIsland6.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowIsland6.setVisible(true);
                        yellowLabelIsland6.setVisible(true);
                        yellowLabelIsland6.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.PINK)) {
                        pinkIsland6.setVisible(true);
                        pinkLabelIsland6.setVisible(true);
                        pinkLabelIsland6.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.BLUE)) {
                        blueIsland6.setVisible(true);
                        blueLabelIsland6.setVisible(true);
                        blueLabelIsland6.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenIsland6.setVisible(true);
                        greenLabelIsland6.setVisible(true);
                        greenLabelIsland6.setText(getStudentsNumber(isl, s));
                    }
                }
                if(isl.hasTower()) {
                    towerIsland6.setVisible(true);
                    towerIsland6.setImage(setTowersImage(isl.getTower()));
                    towerLabelIsland6.setText(getTowersNumber(isl));
                    towerLabelIsland6.setVisible(true);
                }
                cont++;

            } else if (isl.getIslandID() == 7) {
                island7.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(cont).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland7.setVisible(true);
                        redLabelIsland7.setVisible(true);
                        redLabelIsland7.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowIsland7.setVisible(true);
                        yellowLabelIsland7.setVisible(true);
                        yellowLabelIsland7.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.PINK)) {
                        pinkIsland7.setVisible(true);
                        pinkLabelIsland7.setVisible(true);
                        pinkLabelIsland7.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.BLUE)) {
                        blueIsland7.setVisible(true);
                        blueLabelIsland7.setVisible(true);
                        blueLabelIsland7.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenIsland7.setVisible(true);
                        greenLabelIsland7.setVisible(true);
                        greenLabelIsland7.setText(getStudentsNumber(isl, s));
                    }
                }
                if(isl.hasTower()) {
                    towerIsland7.setVisible(true);
                    towerIsland7.setImage(setTowersImage(isl.getTower()));
                    towerLabelIsland7.setText(getTowersNumber(isl));
                    towerLabelIsland7.setVisible(true);
                }
                cont++;

            } else if (isl.getIslandID() == 8) {
                island8.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(cont).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland8.setVisible(true);
                        redLabelIsland8.setVisible(true);
                        redLabelIsland8.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowIsland8.setVisible(true);
                        yellowLabelIsland8.setVisible(true);
                        yellowLabelIsland8.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.PINK)) {
                        pinkIsland8.setVisible(true);
                        pinkLabelIsland8.setVisible(true);
                        pinkLabelIsland8.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.BLUE)) {
                        blueIsland8.setVisible(true);
                        blueLabelIsland8.setVisible(true);
                        blueLabelIsland8.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenIsland8.setVisible(true);
                        greenLabelIsland8.setVisible(true);
                        greenLabelIsland8.setText(getStudentsNumber(isl, s));
                    }
                }
                if(isl.hasTower()) {
                    towerIsland8.setVisible(true);
                    towerIsland8.setImage(setTowersImage(isl.getTower()));
                    towerLabelIsland8.setText(getTowersNumber(isl));
                    towerLabelIsland8.setVisible(true);
                }
                cont++;

            } else if (isl.getIslandID() == 9) {
                island9.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(cont).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland9.setVisible(true);
                        redLabelIsland9.setVisible(true);
                        redLabelIsland9.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowIsland9.setVisible(true);
                        yellowLabelIsland9.setVisible(true);
                        yellowLabelIsland9.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.PINK)) {
                        pinkIsland9.setVisible(true);
                        pinkLabelIsland9.setVisible(true);
                        pinkLabelIsland9.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.BLUE)) {
                        blueIsland9.setVisible(true);
                        blueLabelIsland9.setVisible(true);
                        blueLabelIsland9.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenIsland9.setVisible(true);
                        greenLabelIsland9.setVisible(true);
                        greenLabelIsland9.setText(getStudentsNumber(isl, s));
                    }
                }
                if(isl.hasTower()) {
                    towerIsland9.setVisible(true);
                    towerIsland9.setImage(setTowersImage(isl.getTower()));
                    towerLabelIsland9.setText(getTowersNumber(isl));
                    towerLabelIsland9.setVisible(true);
                }
                cont++;

            } else if (isl.getIslandID() == 10) {
                island10.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(cont).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland10.setVisible(true);
                        redLabelIsland10.setVisible(true);
                        redLabelIsland10.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowIsland10.setVisible(true);
                        yellowLabelIsland10.setVisible(true);
                        yellowLabelIsland10.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.PINK)) {
                        pinkIsland10.setVisible(true);
                        pinkLabelIsland10.setVisible(true);
                        pinkLabelIsland10.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.BLUE)) {
                        blueIsland10.setVisible(true);
                        blueLabelIsland10.setVisible(true);
                        blueLabelIsland10.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenIsland10.setVisible(true);
                        greenLabelIsland10.setVisible(true);
                        greenLabelIsland10.setText(getStudentsNumber(isl, s));
                    }
                }
                if(isl.hasTower()) {
                    towerIsland10.setVisible(true);
                    towerIsland10.setImage(setTowersImage(isl.getTower()));
                    towerLabelIsland10.setText(getTowersNumber(isl));
                    towerLabelIsland10.setVisible(true);
                }
                cont++;

            } else if (isl.getIslandID() == 11) {
                island11.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(cont).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland11.setVisible(true);
                        redLabelIsland11.setVisible(true);
                        redLabelIsland11.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowIsland11.setVisible(true);
                        yellowLabelIsland11.setVisible(true);
                        yellowLabelIsland11.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.PINK)) {
                        pinkIsland11.setVisible(true);
                        pinkLabelIsland11.setVisible(true);
                        pinkLabelIsland11.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.BLUE)) {
                        blueIsland11.setVisible(true);
                        blueLabelIsland11.setVisible(true);
                        blueLabelIsland11.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenIsland11.setVisible(true);
                        greenLabelIsland11.setVisible(true);
                        greenLabelIsland11.setText(getStudentsNumber(isl, s));
                    }
                }
                if(isl.hasTower()) {
                    towerIsland11.setVisible(true);
                    towerIsland11.setImage(setTowersImage(isl.getTower()));
                    towerLabelIsland11.setText(getTowersNumber(isl));
                    towerLabelIsland11.setVisible(true);
                }
                cont++;

            } else if (isl.getIslandID() == 12) {
                island12.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(cont).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland12.setVisible(true);
                        redLabelIsland12.setVisible(true);
                        redLabelIsland12.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowIsland12.setVisible(true);
                        yellowLabelIsland12.setVisible(true);
                        yellowLabelIsland12.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.PINK)) {
                        pinkIsland12.setVisible(true);
                        pinkLabelIsland12.setVisible(true);
                        pinkLabelIsland12.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.BLUE)) {
                        blueIsland12.setVisible(true);
                        blueLabelIsland12.setVisible(true);
                        blueLabelIsland12.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenIsland12.setVisible(true);
                        greenLabelIsland12.setVisible(true);
                        greenLabelIsland12.setText(getStudentsNumber(isl, s));
                    }
                }
                if(isl.hasTower()) {
                    towerIsland12.setVisible(true);
                    towerIsland12.setImage(setTowersImage(isl.getTower()));
                    towerLabelIsland12.setText(getTowersNumber(isl));
                    towerLabelIsland12.setVisible(true);
                }
                cont++;

            }
        }
    }

    /**
     * Method setTowersImage sets the image to the corresponding tower
     * @param t tower
     * @return image for tower
     */
    public Image setTowersImage(Tower t) {
        Image tower = null;
        switch(t.getColor().toString()) {
            case "BLACK" -> tower = blackTowerImage;
            case "GREY" -> tower = greyTowerImage;
            case "WHITE" -> tower = whiteTowerImage;
        }
        return tower;
    }

    /**
     * Method updateDescription updates the description that gives the user information on the game and the actions to do
     * @param s description string
     */
    public void updateDescription(String s) {
        descriptionLabel.setVisible(true);
        descriptionLabel.setText(s);
    }

    /**
     * Method getTowersNumber returns the towers number on an island in order to set the label on the given island
     * @param i island
     * @return number of towers
     */
    public String getTowersNumber(Island i) {
        int num = i.getMergedTowers().size();
        return Integer.toString(num);
    }

    /**
     * Method getStudentsNumber returns the students number per type on an island,
     * in order to set the students labels on the given island
     * @param i island
     * @param s student
     * @return number of students of a type
     */
    public String getStudentsNumber(Island i, Student s) {
        int num = 0;
        for(Student stud : i.getStudents()) {
            if(stud.getType()==s.getType()) {
                num+= 1;
            }
        }
        return String.valueOf(num);
    }

    /**
     * Method updateClouds updates the clouds' status of the game by showing the students on them
     */
    public void updateClouds() {
        cloud1.setVisible(false);
        cloud2.setVisible(false);
        cloud3.setVisible(false);
        cloud4.setVisible(false);
        redCloud1.setVisible(false);
        redLabelCloud1.setVisible(false);
        greenCloud1.setVisible(false);
        greenLabelCloud1.setVisible(false);
        pinkCloud1.setVisible(false);
        pinkLabelCloud1.setVisible(false);
        yellowCloud1.setVisible(false);
        yellowLabelCloud1.setVisible(false);
        blueCloud1.setVisible(false);
        blueLabelCloud1.setVisible(false);

        redCloud2.setVisible(false);
        redLabelCloud2.setVisible(false);
        greenCloud2.setVisible(false);
        greenLabelCloud2.setVisible(false);
        pinkCloud2.setVisible(false);
        pinkLabelCloud2.setVisible(false);
        yellowCloud2.setVisible(false);
        yellowLabelCloud2.setVisible(false);
        blueCloud2.setVisible(false);
        blueLabelCloud2.setVisible(false);

        redCloud3.setVisible(false);
        redLabelCloud3.setVisible(false);
        greenCloud3.setVisible(false);
        greenLabelCloud3.setVisible(false);
        pinkCloud3.setVisible(false);
        pinkLabelCloud3.setVisible(false);
        yellowCloud3.setVisible(false);
        yellowLabelCloud3.setVisible(false);
        blueCloud3.setVisible(false);
        blueLabelCloud3.setVisible(false);

        redCloud4.setVisible(false);
        redLabelCloud4.setVisible(false);
        greenCloud4.setVisible(false);
        greenLabelCloud4.setVisible(false);
        pinkCloud4.setVisible(false);
        pinkLabelCloud4.setVisible(false);
        yellowCloud4.setVisible(false);
        yellowLabelCloud4.setVisible(false);
        blueCloud4.setVisible(false);
        blueLabelCloud4.setVisible(false);


        for (CloudTile c : gui.getModelView().getGameCopy().getGameBoard().getClouds()) {
            if (c.getID() == 1) {
                cloud1.setVisible(true);
                if(gui.getModelView().getGameCopy().getGameBoard().getClouds().get(0).getStudents() != null) {
                    for (Student s : gui.getModelView().getGameCopy().getGameBoard().getClouds().get(0).getStudents()) {
                        if(s.getType().equals(PawnType.RED)) {
                            redCloud1.setVisible(true);
                            redLabelCloud1.setVisible(true);
                            redLabelCloud1.setText(getCloudStudentsNumber(c, s));
                        } else if(s.getType().equals(PawnType.GREEN)) {
                            greenCloud1.setVisible(true);
                            greenLabelCloud1.setVisible(true);
                            greenLabelCloud1.setText(getCloudStudentsNumber(c, s));
                        } else if(s.getType().equals(PawnType.YELLOW)) {
                            yellowCloud1.setVisible(true);
                            yellowLabelCloud1.setVisible(true);
                            yellowLabelCloud1.setText(getCloudStudentsNumber(c, s));
                        }  else if(s.getType().equals(PawnType.PINK)) {
                            pinkCloud1.setVisible(true);
                            pinkLabelCloud1.setVisible(true);
                            pinkLabelCloud1.setText(getCloudStudentsNumber(c, s));
                        }  else if(s.getType().equals(PawnType.BLUE)) {
                            blueCloud1.setVisible(true);
                            blueLabelCloud1.setVisible(true);
                            blueLabelCloud1.setText(getCloudStudentsNumber(c, s));
                        }
                    }
                }
            } else if (c.getID() == 2) {
                cloud2.setVisible(true);
                if(gui.getModelView().getGameCopy().getGameBoard().getClouds().get(1).getStudents() != null) {
                    for (Student s : gui.getModelView().getGameCopy().getGameBoard().getClouds().get(1).getStudents()) {
                        if(s.getType().equals(PawnType.RED)) {
                            redCloud2.setVisible(true);
                            redLabelCloud2.setVisible(true);
                            redLabelCloud2.setText(getCloudStudentsNumber(c, s));
                        } else if(s.getType().equals(PawnType.GREEN)) {
                            greenCloud2.setVisible(true);
                            greenLabelCloud2.setVisible(true);
                            greenLabelCloud2.setText(getCloudStudentsNumber(c, s));
                        } else if(s.getType().equals(PawnType.YELLOW)) {
                            yellowCloud2.setVisible(true);
                            yellowLabelCloud2.setVisible(true);
                            yellowLabelCloud2.setText(getCloudStudentsNumber(c, s));
                        }  else if(s.getType().equals(PawnType.PINK)) {
                            pinkCloud2.setVisible(true);
                            pinkLabelCloud2.setVisible(true);
                            pinkLabelCloud2.setText(getCloudStudentsNumber(c, s));
                        }  else if(s.getType().equals(PawnType.BLUE)) {
                            blueCloud2.setVisible(true);
                            blueLabelCloud2.setVisible(true);
                            blueLabelCloud2.setText(getCloudStudentsNumber(c, s));
                        }
                    }
                }
            } else if (c.getID() == 3) {
                cloud3.setVisible(true);
                if(gui.getModelView().getGameCopy().getGameBoard().getClouds().get(2).getStudents() != null) {
                    for (Student s : gui.getModelView().getGameCopy().getGameBoard().getClouds().get(2).getStudents()) {
                        if(s.getType().equals(PawnType.RED)) {
                            redCloud3.setVisible(true);
                            redLabelCloud3.setVisible(true);
                            redLabelCloud3.setText(getCloudStudentsNumber(c, s));
                        } else if(s.getType().equals(PawnType.GREEN)) {
                            greenCloud3.setVisible(true);
                            greenLabelCloud3.setVisible(true);
                            greenLabelCloud3.setText(getCloudStudentsNumber(c, s));
                        } else if(s.getType().equals(PawnType.YELLOW)) {
                            yellowCloud3.setVisible(true);
                            yellowLabelCloud3.setVisible(true);
                            yellowLabelCloud3.setText(getCloudStudentsNumber(c, s));
                        }  else if(s.getType().equals(PawnType.PINK)) {
                            pinkCloud3.setVisible(true);
                            pinkLabelCloud3.setVisible(true);
                            pinkLabelCloud3.setText(getCloudStudentsNumber(c, s));
                        }  else if(s.getType().equals(PawnType.BLUE)) {
                            blueCloud3.setVisible(true);
                            blueLabelCloud3.setVisible(true);
                            blueLabelCloud3.setText(getCloudStudentsNumber(c, s));
                        }
                    }
                }
            } else if (c.getID() == 4) {
                cloud4.setVisible(true);
                if(gui.getModelView().getGameCopy().getGameBoard().getClouds().get(3).getStudents() != null) {
                    for (Student s : gui.getModelView().getGameCopy().getGameBoard().getClouds().get(3).getStudents()) {
                        if(s.getType().equals(PawnType.RED)) {
                            redCloud4.setVisible(true);
                            redLabelCloud4.setVisible(true);
                            redLabelCloud4.setText(getCloudStudentsNumber(c, s));
                        } else if(s.getType().equals(PawnType.GREEN)) {
                            greenCloud4.setVisible(true);
                            greenLabelCloud4.setVisible(true);
                            greenLabelCloud4.setText(getCloudStudentsNumber(c, s));
                        } else if(s.getType().equals(PawnType.YELLOW)) {
                            yellowCloud4.setVisible(true);
                            yellowLabelCloud4.setVisible(true);
                            yellowLabelCloud4.setText(getCloudStudentsNumber(c, s));
                        }  else if(s.getType().equals(PawnType.PINK)) {
                            pinkCloud4.setVisible(true);
                            pinkLabelCloud4.setVisible(true);
                            pinkLabelCloud4.setText(getCloudStudentsNumber(c, s));
                        }  else if(s.getType().equals(PawnType.BLUE)) {
                            blueCloud4.setVisible(true);
                            blueLabelCloud4.setVisible(true);
                            blueLabelCloud4.setText(getCloudStudentsNumber(c, s));
                        }
                    }
                }
            }
        }
    }

    /**
     * Method askCloud asks the user to choose a cloud between the remaining ones
     */
    public void askCloud() {
        cloud1Button.setVisible(false);
        cloud2Button.setVisible(false);
        cloud3Button.setVisible(false);
        cloud4Button.setVisible(false);
        greenLabelCloud1.setVisible(false);
        redLabelCloud1.setVisible(false);
        yellowLabelCloud1.setVisible(false);
        pinkLabelCloud1.setVisible(false);
        blueLabelCloud1.setVisible(false);
        greenLabelCloud2.setVisible(false);
        redLabelCloud2.setVisible(false);
        yellowLabelCloud2.setVisible(false);
        pinkLabelCloud2.setVisible(false);
        blueLabelCloud2.setVisible(false);
        greenLabelCloud3.setVisible(false);
        redLabelCloud3.setVisible(false);
        yellowLabelCloud3.setVisible(false);
        pinkLabelCloud3.setVisible(false);
        blueLabelCloud3.setVisible(false);
        greenLabelCloud4.setVisible(false);
        redLabelCloud4.setVisible(false);
        yellowLabelCloud4.setVisible(false);
        pinkLabelCloud4.setVisible(false);
        blueLabelCloud4.setVisible(false);
        descriptionLabel.setText("Pick a cloud");
        for(CloudTile c : gui.getModelView().getGameCopy().getGameBoard().getClouds()) {
            if(c.getID() == 1) {
                if(c.getStudents() != null) {
                    cloud1Button.setVisible(true);
                    cloud1Button.setText("Cloud 1");
                }
            } else if(c.getID() == 2) {
                if(c.getStudents() != null) {
                    cloud2Button.setVisible(true);
                    cloud2Button.setText("Cloud 2");
                }
            } else if(c.getID() == 3) {
                if(c.getStudents() != null) {
                    cloud3Button.setVisible(true);
                    cloud3Button.setText("Cloud 2");
                }
            } else if(c.getID() == 4) {
                if(c.getStudents() != null) {
                    cloud4Button.setVisible(true);
                    cloud4Button.setText("Cloud 4");
                }
            }
        }
        updateClouds();
    }


    /**
     * Method getCloudStudentsNumber returns the number of students on a cloud, in order to set the labels
     * @param cloud cloud tile
     * @param stud student
     * @return number of students on cloud
     */
    public String getCloudStudentsNumber(CloudTile cloud, Student stud) {
        int num = 0;
        for(Student s : cloud.getStudents()) {
            if(s.getType() == stud.getType()) {
                num+= 1;
            }
        }
        return Integer.toString(num);
    }

    /**
     * Method updateTowers updates the towers on the tower areas by showing them
     */
    public void updateTowers() {
        for(int i=0; i < 8; i++) {
            myTowers.getChildren().get(i).setVisible(false);
            leftTowers.getChildren().get(i).setVisible(false);
            rightTowers.getChildren().get(i).setVisible(false);
            topTowers.getChildren().get(i).setVisible(false);
        }
        int cont = 0;
        Image img;
        for (Player p : gui.getModelView().getGameCopy().getActivePlayers()) {
            if(p.getNickname().equals(gui.getModelView().getPlayerNickname())) {
                for(int i=0; i < p.getBoard().getTowerArea().getTowerArea().size(); i++) {
                    img = setTowersImage(p.getBoard().getTowerArea().getTowerArea().get(i));
                    ((ImageView) myTowers.getChildren().get(i)).setImage(img);
                    myTowers.getChildren().get(i).setVisible(true);
                }
            } else if(cont==0) {
                for(int i=0; i < p.getBoard().getTowerArea().getTowerArea().size(); i++) {
                    img = setTowersImage(p.getBoard().getTowerArea().getTowerArea().get(i));
                    ((ImageView) topTowers.getChildren().get(i)).setImage(img);
                    topTowers.getChildren().get(i).setVisible(true);
                }
                cont++;
            } else if(cont==1) {
                for(int i=0; i < p.getBoard().getTowerArea().getTowerArea().size(); i++) {
                    img = setTowersImage(p.getBoard().getTowerArea().getTowerArea().get(i));
                    ((ImageView) leftTowers.getChildren().get(i)).setImage(img);
                    leftTowers.getChildren().get(i).setVisible(true);
                }
                cont++;
            } else if(cont==2) {
                for(int i=0; i < p.getBoard().getTowerArea().getTowerArea().size(); i++) {
                    img = setTowersImage(p.getBoard().getTowerArea().getTowerArea().get(i));
                    ((ImageView) rightTowers.getChildren().get(i)).setImage(img);
                    rightTowers.getChildren().get(i).setVisible(true);
                }
                cont++;
            }
        }
    }

    /**
     * Method setWizardImage sets the image corresponding to the given wizard
     * @param w wizard
     * @return image for wizard
     */
    public Image setWizardImage(Wizards w) {
        Image wiz = null;
        switch (w.toString()) {
            case "KING" -> wiz = kingImage;
            case "WITCH" -> wiz = witchImage;
            case "MONACH" -> wiz = monachImage;
            case "FOREST" -> wiz = forestImage;
        }
        return wiz;
    }

    /**
     * Method updateWizard updates the wizards in the main scene,
     * assigning the wizard to the player who has chosen it
     */
    public void updateWizard() {
        int cont = 0;
        myWizard.setVisible(false);
        topWizard.setVisible(false);
        leftWizard.setVisible(false);
        rightWizard.setVisible(false);
        Image img;
        for (Player p : gui.getModelView().getGameCopy().getActivePlayers()) {
            img = setWizardImage(p.getWizard());
            if(p.getNickname().equals(gui.getModelView().getPlayerNickname())) {
                myWizard.setImage(img);
                myWizard.setVisible(true);
            } else if(cont==0) {
                topWizard.setImage(img);
                topWizard.setVisible(true);
                cont++;
            } else if(cont==1) {
                leftWizard.setImage(img);
                leftWizard.setVisible(true);
                cont++;
            } else if(cont==2) {
                rightWizard.setImage(img);
                rightWizard.setVisible(true);
                cont++;
            }
        }
    }

    /**
     * Method updateAssistant updates the main board scene by showing the assistants chosen by the players
     */
    public void updateAssistant() {
        int cont = 0;
        myAssistant.setVisible(false);
        leftAssistant.setVisible(false);
        topAssistant.setVisible(false);
        rightAssistant.setVisible(false);
        Image img;
        for(Player p : gui.getModelView().getGameCopy().getActivePlayers()) {
            if(p.getChosenAssistant() != null) {
                img = setAssistantImage(p.getChosenAssistant());
                if(p.getNickname().equals(gui.getModelView().getPlayerNickname())) {
                    myAssistant.setImage(img);
                    myAssistant.setVisible(true);
                } else if(cont==0) {
                    topAssistant.setImage(img);
                    topAssistant.setVisible(true);
                    cont++;
                } else if(cont==1) {
                    leftAssistant.setImage(img);
                    leftAssistant.setVisible(true);
                    cont++;
                } else if(cont==2) {
                    rightAssistant.setImage(img);
                    rightAssistant.setVisible(true);
                    cont++;
                }
            }

        }
    }

    /**
     * Method updateEntrances updates all the entrances in the main scene by showing the students in them
     */
    public void updateEntrances() {
        for(int i=0; i < 9; i++) {
            myEntrance.getChildren().get(i).setVisible(false);
            leftEntrance.getChildren().get(i).setVisible(false);
            topEntrance.getChildren().get(i).setVisible(false);
            rightEntrance.getChildren().get(i).setVisible(false);
        }
        Image pic;
        int cont = 0;
        for(Player p : gui.getModelView().getGameCopy().getActivePlayers()) {
            if(p.getNickname().equals(gui.getModelView().getPlayerNickname())) {
                for (int i = 0; i < p.getBoard().getEntrance().getStudents().size(); i++) {
                    pic = setStudentsEntrance(p.getBoard().getEntrance().getStudents().get(i));
                    ((ImageView) myEntrance.getChildren().get(i)).setImage(pic);
                    myEntrance.getChildren().get(i).setVisible(true);
                }
            } else if(cont == 0) {
                for (int i = 0; i < p.getBoard().getEntrance().getStudents().size(); i++) {
                    pic = setStudentsEntrance(p.getBoard().getEntrance().getStudents().get(i));
                    ((ImageView) topEntrance.getChildren().get(i)).setImage(pic);
                    topEntrance.getChildren().get(i).setVisible(true);
                }
                cont++;
            } else if(cont == 1) {
                for (int i = 0; i < p.getBoard().getEntrance().getStudents().size(); i++) {
                    pic = setStudentsEntrance(p.getBoard().getEntrance().getStudents().get(i));
                    ((ImageView) leftEntrance.getChildren().get(i)).setImage(pic);
                    leftEntrance.getChildren().get(i).setVisible(true);
                }
                cont++;
            } else if(cont == 2) {
                for (int i = 0; i < p.getBoard().getEntrance().getStudents().size(); i++) {
                    pic = setStudentsEntrance(p.getBoard().getEntrance().getStudents().get(i));
                    ((ImageView) rightEntrance.getChildren().get(i)).setImage(pic);
                    rightEntrance.getChildren().get(i).setVisible(true);
                }
                cont++;
            }
        }
    }

    /**
     * Method updatePickStudents updates the students to pick if there is a pick user action
     */
    public void updatePickStudents() {
        green.setVisible(false);
        yellow.setVisible(false);
        pink.setVisible(false);
        blue.setVisible(false);
        red.setVisible(false);
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
                askStudentDiningRoom();
            } else {
                askStudentEntrance(gui.getModelView().getGameCopy().getCurrentPlayer().getBoard().getEntrance());
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

    /**
     * Method askStudentEntrance asks the user to pick a student from their entrance by showing buttons and students type
     * @param e entrance
     */
    public void askStudentEntrance(Entrance e) {
        descriptionLabel.setText("Pick a student from your entrance");
        descriptionLabel.setVisible(true);
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

    /**
     * Method askStudentCard asks the user to pick a student from a character card
     * @param c character card with students on
     */
    public void askStudentCard(CharacterCard c) {
        descriptionLabel.setText("Pick a student from the " + c.getName());
        descriptionLabel.setVisible(true);
        for(Student s : c.getStudents()) {
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

    /**
     * Method askStudentDiningRoom asks the user to pick a student form their dining room
     */
    public void askStudentDiningRoom() {
        descriptionLabel.setText("Pick a student from your dining room");
        descriptionLabel.setVisible(true);
        for(Table t : gui.getModelView().getGameCopy().getCurrentPlayer().getBoard().getDiningRoom().getDiningRoom()) {
            if(t.getDiningTable().get(0).hasStudent()) {
                if(t.getDiningTable().get(0).getBoardCellType() == PawnType.BLUE) {
                    blue.setVisible(true);
                    blueStudent.setVisible(true);
                }
                else if(t.getDiningTable().get(0).getBoardCellType() == PawnType.GREEN) {
                    green.setVisible(true);
                    greenStudent.setVisible(true);
                }
                else if(t.getDiningTable().get(0).getBoardCellType() == PawnType.RED) {
                    red.setVisible(true);
                    redStudent.setVisible(true);
                }
                else if(t.getDiningTable().get(0).getBoardCellType() == PawnType.PINK) {
                    pink.setVisible(true);
                    pinkStudent.setVisible(true);
                }
                else if(t.getDiningTable().get(0).getBoardCellType() == PawnType.YELLOW) {
                    yellow.setVisible(true);
                    yellowStudent.setVisible(true);
                }
            }
        }
    }

    /**
     * Method askPawnType asks the user to pick a student according to the character card's effect
     */
    public void askPawnType() {
        descriptionLabel.setText("Pick a pawn type based on the effect of the character you chose");
        descriptionLabel.setVisible(true);
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

    /**
     * Method updateCharacters updates the character cards in the main scene according
     * to the playable characters in the current game
     */
    public void updateCharacters() {
        character1.setVisible(true);
        character2.setVisible(true);
        character3.setVisible(true);
        character1Button.setVisible(false);
        character2Button.setVisible(false);
        character3Button.setVisible(false);
        noCharacterButton.setVisible(false);
        effect1.setVisible(true);
        effect2.setVisible(true);
        effect3.setVisible(true);
        character1.setImage(getCharacterImage(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(0)));
        character2.setImage(getCharacterImage(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(1)));
        character3.setImage(getCharacterImage(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(2)));
        greenLabelCharacter1.setVisible(false);
        blueLabelCharacter1.setVisible(false);
        pinkLabelCharacter1.setVisible(false);
        redLabelCharacter1.setVisible(false);
        yellowLabelCharacter1.setVisible(false);
        greenLabelCharacter2.setVisible(false);
        blueLabelCharacter2.setVisible(false);
        pinkLabelCharacter2.setVisible(false);
        redLabelCharacter2.setVisible(false);
        yellowLabelCharacter2.setVisible(false);
        greenLabelCharacter3.setVisible(false);
        blueLabelCharacter3.setVisible(false);
        pinkLabelCharacter3.setVisible(false);
        redLabelCharacter3.setVisible(false);
        yellowLabelCharacter3.setVisible(false);
        greenCharacter1.setVisible(false);
        blueCharacter1.setVisible(false);
        pinkCharacter1.setVisible(false);
        redCharacter1.setVisible(false);
        yellowCharacter1.setVisible(false);
        greenCharacter2.setVisible(false);
        blueCharacter2.setVisible(false);
        pinkCharacter2.setVisible(false);
        redCharacter2.setVisible(false);
        yellowCharacter2.setVisible(false);
        greenCharacter3.setVisible(false);
        blueCharacter3.setVisible(false);
        pinkCharacter3.setVisible(false);
        redCharacter3.setVisible(false);
        yellowCharacter3.setVisible(false);
        character1Coin.setVisible(false);
        character1LabelCoin.setVisible(false);
        character2Coin.setVisible(false);
        character2LabelCoin.setVisible(false);
        character3Coin.setVisible(false);
        character3LabelCoin.setVisible(false);
        if(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(0).differentialCost() != 0) {
            character1Coin.setVisible(true);
            character1LabelCoin.setVisible(true);
            character1LabelCoin.setText(String.valueOf(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(0).differentialCost()));
        }
        if(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(1).differentialCost() != 0) {
            character2Coin.setVisible(true);
            character2LabelCoin.setVisible(true);
            character2LabelCoin.setText(String.valueOf(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(1).differentialCost()));
        }
        if(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(2).differentialCost() != 0) {
            character3Coin.setVisible(true);
            character3LabelCoin.setVisible(true);
            character3LabelCoin.setText(String.valueOf(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(2).differentialCost()));
        }
        if(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(0).getName().equals(Characters.JESTER)
                || gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(0).getName().equals(Characters.MONK)
                || gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(0).getName().equals(Characters.SPOILED_PRINCESS)) {
            for(Student s : gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(0).getStudents()) {
                if(s.getType().equals(PawnType.RED)) {
                    redCharacter1.setVisible(true);
                    redLabelCharacter1.setVisible(true);
                    redLabelCharacter1.setText(getCharacterStudentsNumber(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(0), s));
                } else if(s.getType().equals(PawnType.GREEN)) {
                    greenCharacter1.setVisible(true);
                    greenLabelCharacter1.setVisible(true);
                    greenLabelCharacter1.setText(getCharacterStudentsNumber(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(0), s));
                } else if(s.getType().equals(PawnType.PINK)) {
                    pinkCharacter1.setVisible(true);
                    pinkLabelCharacter1.setVisible(true);
                    pinkLabelCharacter1.setText(getCharacterStudentsNumber(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(0), s));
                } else if(s.getType().equals(PawnType.BLUE)) {
                    blueCharacter1.setVisible(true);
                    blueLabelCharacter1.setVisible(true);
                    blueLabelCharacter1.setText(getCharacterStudentsNumber(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(0), s));
                } else if(s.getType().equals(PawnType.YELLOW)) {
                    yellowCharacter1.setVisible(true);
                    yellowLabelCharacter1.setVisible(true);
                    yellowLabelCharacter1.setText(getCharacterStudentsNumber(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(0), s));
                }
            }
        } if(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(1).getName().equals(Characters.JESTER)
                || gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(1).getName().equals(Characters.MONK)
                || gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(1).getName().equals(Characters.SPOILED_PRINCESS)) {
            for(Student s : gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(1).getStudents()) {
                if(s.getType().equals(PawnType.RED)) {
                    redCharacter2.setVisible(true);
                    redLabelCharacter2.setVisible(true);
                    redLabelCharacter2.setText(getCharacterStudentsNumber(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(1), s));
                } else if(s.getType().equals(PawnType.GREEN)) {
                    greenCharacter2.setVisible(true);
                    greenLabelCharacter2.setVisible(true);
                    greenLabelCharacter2.setText(getCharacterStudentsNumber(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(1), s));
                } else if(s.getType().equals(PawnType.PINK)) {
                    pinkCharacter2.setVisible(true);
                    pinkLabelCharacter2.setVisible(true);
                    pinkLabelCharacter2.setText(getCharacterStudentsNumber(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(1), s));
                } else if(s.getType().equals(PawnType.BLUE)) {
                    blueCharacter2.setVisible(true);
                    blueLabelCharacter2.setVisible(true);
                    blueLabelCharacter2.setText(getCharacterStudentsNumber(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(1), s));
                } else if(s.getType().equals(PawnType.YELLOW)) {
                    yellowCharacter2.setVisible(true);
                    yellowLabelCharacter2.setVisible(true);
                    yellowLabelCharacter2.setText(getCharacterStudentsNumber(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(1), s));
                }
            }
        } if(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(2).getName().equals(Characters.JESTER)
                || gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(2).getName().equals(Characters.MONK)
                || gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(2).getName().equals(Characters.SPOILED_PRINCESS)) {
            for(Student s : gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(2).getStudents()) {
                if(s.getType().equals(PawnType.RED)) {
                    redCharacter3.setVisible(true);
                    redLabelCharacter3.setVisible(true);
                    redLabelCharacter3.setText(getCharacterStudentsNumber(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(2), s));
                } else if(s.getType().equals(PawnType.GREEN)) {
                    greenCharacter3.setVisible(true);
                    greenLabelCharacter3.setVisible(true);
                    greenLabelCharacter3.setText(getCharacterStudentsNumber(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(2), s));
                } else if(s.getType().equals(PawnType.PINK)) {
                    pinkCharacter3.setVisible(true);
                    pinkLabelCharacter3.setVisible(true);
                    pinkLabelCharacter3.setText(getCharacterStudentsNumber(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(2), s));
                } else if(s.getType().equals(PawnType.BLUE)) {
                    blueCharacter3.setVisible(true);
                    blueLabelCharacter3.setVisible(true);
                    blueLabelCharacter3.setText(getCharacterStudentsNumber(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(2), s));
                } else if(s.getType().equals(PawnType.YELLOW)) {
                    yellowCharacter3.setVisible(true);
                    yellowLabelCharacter3.setVisible(true);
                    yellowLabelCharacter3.setText(getCharacterStudentsNumber(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(2), s));
                }
            }
        }
    }

    /**
     * Method getCharacterStudentsNumber returns the students number on a character card
     * @param card character card
     * @param stud student type
     * @return students number
     */
    public String getCharacterStudentsNumber(CharacterCard card, Student stud) {
        int num = 0;
        for(Student s : card.getStudents()) {
            if(s.getType().equals(stud.getType())) {
                num++;
            }
        }
        return String.valueOf(num);
    }


    /**
     * Method playCharacter1 handles the click event on character1
     */
    public void playCharacter1() {
        gui.getClientConnection().sendUserInput(new PickCharacter(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(0).getName()));
    }
    /**
     * Method playCharacter2 handles the click event on character2
     */
    public void playCharacter2() {
        gui.getClientConnection().sendUserInput(new PickCharacter(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(1).getName()));
    }
    /**
     * Method playCharacter3 handles the click event on character3
     */
    public void playCharacter3() {
        gui.getClientConnection().sendUserInput(new PickCharacter(gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters().get(2).getName()));
    }
    /**
     * Method playNoCharacter handles the click event on no character button
     */
    public void playNoCharacter() {
        gui.getClientConnection().sendUserInput(new PickCharacter(null));
    }


    /**
     * Method getCharacterImage sets the image corresponding to the given character
     * @param c character card
     * @return image for character
     */
    public Image getCharacterImage(CharacterCard c) {
        Image pic = null;
        switch(c.getName().toString()) {
            case "HERALD" -> pic = heraldImage;
            case "KNIGHT" -> pic = knightImage;
            case "CENTAUR" -> pic = centarusImage;
            case "FARMER" -> pic = farmerImage;
            case "FUNGARUS" -> pic = fungarusImage;
            case "JESTER" -> pic = jesterImage;
            case "THIEF" -> pic = thiefImage;
            case "MINESTREL" -> pic = minestrelImage;
            case "GRANNY_HERBS" -> pic = grannyHerbsImage;
            case "MAGIC_POSTMAN" -> pic = magicPostmanImage;
            case "SPOILED_PRINCESS" -> pic = spoiledPrincessImage;
            case "MONK" -> pic = monkImage;
        }
        return pic;
    }

    /**
     * Method setStudentsEntrance sets the image corresponding to the given student in the entrance
     * @param s student
     * @return image for student
     */
    public Image setStudentsEntrance(Student s) {
        Image stud = null;
        switch(s.getType().toString()) {
            case "BLUE" -> stud = blueStudentImage;
            case "RED" -> stud = redStudentImage;
            case "GREEN" -> stud = greenStudentImage;
            case "PINK" -> stud = pinkStudentImage;
            case "YELLOW" -> stud = yellowStudentImage;
        }
        return stud;
    }

    /**
     * Method setAssistantImage sets the image corresponding to the given assistant card
     * @param a assistant card
     * @return image for assistant
     */
    public Image setAssistantImage(AssistantCard a) {
        Image img = null;
        switch (a.getName().toString()) {
            case "EAGLE" -> img = assistant4Image;
            case "DOG" -> img = assistant8Image;
            case "ELEPHANT" -> img = assistant9Image;
            case "CAT" -> img = assistant3Image;
            case "CHEETAH" -> img = assistant1Image;
            case "LIZARD" -> img = assistant6Image;
            case "OCTOPUS" -> img = assistant7Image;
            case "OSTRICH" -> img = assistant2Image;
            case "TURTLE" -> img = assistant10Image;
            case "FOX" -> img = assistant5Image;
        }
        return img;
    }

    /**
     * Method pickCloud1 handles the click event on the cloud 1 and sends it to server
     */
    public void pickCloud1() {
        gui.getClientConnection().sendUserInput(new PickCloud(gui.getModelView().getGameCopy().getGameBoard().getClouds().get(0)));
    }
    /**
     * Method pickCloud2 handles the click event on the cloud 2 and sends it to server
     */
    public void pickCloud2() {
        gui.getClientConnection().sendUserInput(new PickCloud(gui.getModelView().getGameCopy().getGameBoard().getClouds().get(1)));
    }
    /**
     * Method pickCloud3 handles the click event on the cloud 3 and sends it to server
     */
    public void pickCloud3() {
        gui.getClientConnection().sendUserInput(new PickCloud(gui.getModelView().getGameCopy().getGameBoard().getClouds().get(2)));
    }
    /**
     * Method pickCloud4 handles the click event on the cloud 4 and sends it to server
     */
    public void pickCloud4() {
        gui.getClientConnection().sendUserInput(new PickCloud(gui.getModelView().getGameCopy().getGameBoard().getClouds().get(3)));
    }

    /**
     * Method pickBluePawn handles the click event on the blue pawn and sends it to server
     */
    public void pickBluePawn() {
        gui.getClientConnection().sendUserInput(new PickPawnType(PawnType.BLUE));
    }
    /**
     * Method pickRedPawn handles the click event on the red pawn and sends it to server
     */
    public void pickRedPawn() {
        gui.getClientConnection().sendUserInput(new PickPawnType(PawnType.RED));
    }
    /**
     * Method pickYellowPawn handles the click event on the yellow pawn and sends it to server
     */
    public void pickYellowPawn() {
        gui.getClientConnection().sendUserInput(new PickPawnType(PawnType.YELLOW));
    }
    /**
     * Method pickGreenPawn handles the click event on the green pawn and sends it to server
     */
    public void pickGreenPawn() {
        gui.getClientConnection().sendUserInput(new PickPawnType(PawnType.GREEN));
    }
    /**
     * Method pickPinkPawn handles the click event on the pink pawn and sends it to server
     */
    public void pickPinkPawn() {
        gui.getClientConnection().sendUserInput(new PickPawnType(PawnType.PINK));
    }
    /**
     * Method pickBlueStudent handles the click event on the blue student and sends it to server
     */
    public void pickBlueStudent() {
        gui.getClientConnection().sendUserInput(new PickStudent(new Student(PawnType.BLUE)));
    }
    /**
     * Method pickRedStudent handles the click event on the red student and sends it to server
     */
    public void pickRedStudent() {
        gui.getClientConnection().sendUserInput(new PickStudent(new Student(PawnType.RED)));
    }
    /**
     * Method pickYellowStudent handles the click event on the yellow student and sends it to server
     */
    public void pickYellowStudent() {
        gui.getClientConnection().sendUserInput(new PickStudent(new Student(PawnType.YELLOW)));
    }
    /**
     * Method pickGreenStudent handles the click event on the green student and sends it to server
     */
    public void pickGreenStudent() {
        gui.getClientConnection().sendUserInput(new PickStudent(new Student(PawnType.GREEN)));
    }
    /**
     * Method pickPinkStudent handles the click event on the pink student and sends it to server
     */
    public void pickPinkStudent() {
        gui.getClientConnection().sendUserInput(new PickStudent(new Student(PawnType.PINK)));
    }

    /**
     * Method quitGame quits the game when the quit button is pressed
     */
    public void quitGame() {
        System.out.println("Thanks for playing! See you next time!");
        System.exit(0);
    }

}
