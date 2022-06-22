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
    @FXML ImageView motherNature;
    @FXML ImageView myBoard;
    @FXML ImageView topBoard;
    @FXML ImageView leftBoard;
    @FXML ImageView rightBoard;
    @FXML Button effect1;
    @FXML Button effect2;
    @FXML Button effect3;
    @FXML ImageView character1Coin;
    @FXML ImageView character2Coin;
    @FXML ImageView character3Coin;
    @FXML Label character1LabelCoin;
    @FXML Label character2LabelCoin;
    @FXML Label character3LabelCoin;
    @FXML ImageView myCoins;
    @FXML Label myCoinsLabel;
    @FXML ImageView character1Coins;
    @FXML ImageView character2Coins;
    @FXML ImageView character3Coins;

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
                //askStudentEntrance(gui.getModelView().getGameCopy().getCurrentPlayer().getBoard().getEntrance());
                updatePickStudents();

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
                    //myCoinsLabel.setText(gui.getModelView().getGameCopy());
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
        Image pic = null;
        for (Player pl : gui.getModelView().getGameCopy().getActivePlayers()) {
            if (pl.getNickname().equals(gui.getModelView().getGameCopy().getCurrentPlayer().getNickname())) {
                for (BoardCell b : pl.getBoard().getProfessorTable().getProfessorTable()) {
                    if (b.hasProfessor()) {
                        if (b.getBoardCellType() == PawnType.BLUE) {
                            pic = new Image("@../../graphics/wooden_pieces/2D/blueProf3D.png");
                            myBlueProfessor.setImage(pic);
                            myBlueProfessor.setVisible(true);
                        } else if (b.getBoardCellType() == PawnType.GREEN) {
                            pic = new Image("@../../graphics/wooden_pieces/2D/greenProf3D.png");
                            myGreenProfessor.setImage(pic);
                            myGreenProfessor.setVisible(true);
                        } else if (b.getBoardCellType() == PawnType.PINK) {
                            pic = new Image("@../../graphics/wooden_pieces/2D/pinkProf3D.png");
                            myPinkProfessor.setImage(pic);
                            myPinkProfessor.setVisible(true);
                        } else if (b.getBoardCellType() == PawnType.RED) {
                            pic = new Image("@../../graphics/wooden_pieces/2D/redProf3D.png");
                            myRedProfessor.setImage(pic);
                            myRedProfessor.setVisible(true);
                        } else if (b.getBoardCellType() == PawnType.YELLOW) {
                            pic = new Image("@../../graphics/wooden_pieces/2D/yellowProf3D.png");
                            myYellowProfessor.setImage(pic);
                            myYellowProfessor.setVisible(true);
                        }
                    }
                }
            } else if (cont == 0) {
                //top Player corresponding to cont 0
                for (BoardCell b : pl.getBoard().getProfessorTable().getProfessorTable()) {
                    if (b.hasProfessor()) {
                        if (b.getBoardCellType() == PawnType.BLUE) {
                            pic = new Image("@../../graphics/wooden_pieces/2D/blueProf3D.png");
                            topBlueProfessor.setImage(pic);
                            topBlueProfessor.setVisible(true);
                        } else if (b.getBoardCellType() == PawnType.GREEN) {
                            pic = new Image("@../../graphics/wooden_pieces/2D/greenProf3D.png");
                            topGreenProfessor.setImage(pic);
                            topGreenProfessor.setVisible(true);
                        } else if (b.getBoardCellType() == PawnType.PINK) {
                            pic = new Image("@../../graphics/wooden_pieces/2D/pinkProf3D.png");
                            topPinkProfessor.setImage(pic);
                            topPinkProfessor.setVisible(true);
                        } else if (b.getBoardCellType() == PawnType.RED) {
                            pic = new Image("@../../graphics/wooden_pieces/2D/redProf3D.png");
                            topRedProfessor.setImage(pic);
                            topRedProfessor.setVisible(true);
                        } else if (b.getBoardCellType() == PawnType.YELLOW) {
                            pic = new Image("@../../graphics/wooden_pieces/2D/yellowProf3D.png");
                            topYellowProfessor.setImage(pic);
                            topYellowProfessor.setVisible(true);
                        }
                    }
                }
                cont++;
            } else if (cont == 1) {
                //left Player corresponding to cont 1
                for (BoardCell b : pl.getBoard().getProfessorTable().getProfessorTable()) {
                    if (b.hasProfessor()) {
                        if (b.getBoardCellType() == PawnType.BLUE) {
                            pic = new Image("@../../graphics/wooden_pieces/2D/blueProf3D.png");
                            leftBlueProfessor.setImage(pic);
                            leftBlueProfessor.setVisible(true);
                        } else if (b.getBoardCellType() == PawnType.GREEN) {
                            pic = new Image("@../../graphics/wooden_pieces/2D/greenProf3D.png");
                            leftGreenProfessor.setImage(pic);
                            leftGreenProfessor.setVisible(true);
                        } else if (b.getBoardCellType() == PawnType.PINK) {
                            pic = new Image("@../../graphics/wooden_pieces/2D/pinkProf3D.png");
                            leftPinkProfessor.setImage(pic);
                            leftPinkProfessor.setVisible(true);
                        } else if (b.getBoardCellType() == PawnType.RED) {
                            pic = new Image("@../../graphics/wooden_pieces/2D/redProf3D.png");
                            leftRedProfessor.setImage(pic);
                            leftRedProfessor.setVisible(true);
                        } else if (b.getBoardCellType() == PawnType.YELLOW) {
                            pic = new Image("@../../graphics/wooden_pieces/2D/yellowProf3D.png");
                            leftYellowProfessor.setImage(pic);
                            leftYellowProfessor.setVisible(true);
                        }
                    }
                }
                cont++;
            } else if (cont == 2) {
                for (BoardCell b : pl.getBoard().getProfessorTable().getProfessorTable()) {
                    if (b.hasProfessor()) {
                        if (b.getBoardCellType() == PawnType.BLUE) {
                            pic = new Image("@../../graphics/wooden_pieces/2D/blueProf3D.png");
                            rightBlueProfessor.setImage(pic);
                            rightBlueProfessor.setVisible(true);
                        } else if (b.getBoardCellType() == PawnType.GREEN) {
                            pic = new Image("@../../graphics/wooden_pieces/2D/greenProf3D.png");
                            rightGreenProfessor.setImage(pic);
                            rightGreenProfessor.setVisible(true);
                        } else if (b.getBoardCellType() == PawnType.PINK) {
                            pic = new Image("@../../graphics/wooden_pieces/2D/pinkProf3D.png");
                            rightPinkProfessor.setImage(pic);
                            rightPinkProfessor.setVisible(true);
                        } else if (b.getBoardCellType() == PawnType.RED) {
                            pic = new Image("@../../graphics/wooden_pieces/2D/redProf3D.png");
                            rightRedProfessor.setImage(pic);
                            rightRedProfessor.setVisible(true);
                        } else if (b.getBoardCellType() == PawnType.YELLOW) {
                            pic = new Image("@../../graphics/wooden_pieces/2D/yellowProf3D.png");
                            rightYellowProfessor.setImage(pic);
                            rightYellowProfessor.setVisible(true);
                        }
                    }
                }
                cont++;
            }
        }
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

    public void updateMotherNature() {
        motherNature.setVisible(false);
        Image img = new Image("@../../graphics/wooden_pieces/mother_nature.png");
        motherNature.setImage(img);
        int motherNaturePosition = gui.getModelView().getGameCopy().getGameBoard().getMotherNature().getPosition();
        switch (motherNaturePosition) {
            case 1 -> {
                motherNature.setLayoutX(289);
                motherNature.setLayoutY(-5);
                motherNature.setVisible(true);
            }
            case 2 -> {
                motherNature.setLayoutX(397);
                motherNature.setLayoutY(21);
                motherNature.setVisible(true);
            }
            case 3 -> {
                motherNature.setLayoutX(501);
                motherNature.setLayoutY(82);
                motherNature.setVisible(true);
            }
            case 4 -> {
                motherNature.setLayoutX(526);
                motherNature.setLayoutY(192);
                motherNature.setVisible(true);
            }
            case 5 -> {
                motherNature.setLayoutX(501);
                motherNature.setLayoutY(284);
                motherNature.setVisible(true);
            }
            case 6 -> {
                motherNature.setLayoutX(414);
                motherNature.setLayoutY(358);
                motherNature.setVisible(true);
            }
            case 7 -> {
                motherNature.setLayoutX(295);
                motherNature.setLayoutY(382);
                motherNature.setVisible(true);
            }

            case 8 -> {
                motherNature.setLayoutX(190);
                motherNature.setLayoutY(358);
                motherNature.setVisible(true);
            }

            case 9 -> {
                motherNature.setLayoutX(104);
                motherNature.setLayoutY(284);
                motherNature.setVisible(true);
            }

            case 10 -> {
                motherNature.setLayoutX(65);
                motherNature.setLayoutY(188);
                motherNature.setVisible(true);
            }

            case 11 -> {
                motherNature.setLayoutX(115);
                motherNature.setLayoutY(87);
                motherNature.setVisible(true);
            }

            case 12 -> {
                motherNature.setLayoutX(184);
                motherNature.setLayoutY(18);
                motherNature.setVisible(true);
            }
        }
    }


    public void updateNoEntryTile() {
        Image img = new Image("@../../graphics/wooden_pieces/deny_island_icon.png");
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
                    case 1 -> {
                        noEntryTile1.setLayoutX(289);
                        noEntryTile1.setLayoutY(31);
                        noEntryTile1.setImage(img);
                        noEntryTile1.setVisible(true);
                    }
                    case 2 -> {
                        noEntryTile2.setLayoutX(397);
                        noEntryTile2.setLayoutY(55);
                        noEntryTile2.setImage(img);
                        noEntryTile2.setVisible(true);
                    }
                    case 3 -> {
                        noEntryTile3.setLayoutX(501);
                        noEntryTile3.setLayoutY(120);
                        noEntryTile3.setImage(img);
                        noEntryTile3.setVisible(true);
                    }
                    case 4 -> {
                        noEntryTile4.setLayoutX(526);
                        noEntryTile4.setLayoutY(226);
                        noEntryTile4.setImage(img);
                        noEntryTile4.setVisible(true);
                    }
                    case 5 -> {
                        noEntryTile5.setLayoutX(501);
                        noEntryTile5.setLayoutY(323);
                        noEntryTile5.setImage(img);
                        noEntryTile5.setVisible(true);
                    }
                    case 6 -> {
                        noEntryTile6.setLayoutX(414);
                        noEntryTile6.setLayoutY(396);
                        noEntryTile6.setImage(img);
                        noEntryTile6.setVisible(true);
                    }
                    case 7 -> {
                        noEntryTile7.setLayoutX(295);
                        noEntryTile7.setLayoutY(417);
                        noEntryTile7.setImage(img);
                        noEntryTile7.setVisible(true);
                    }
                    case 8 -> {
                        noEntryTile8.setLayoutX(190);
                        noEntryTile8.setLayoutY(396);
                        noEntryTile8.setImage(img);
                        noEntryTile8.setVisible(true);
                    }
                    case 9 -> {
                        noEntryTile9.setLayoutX(104);
                        noEntryTile9.setLayoutY(318);
                        noEntryTile9.setImage(img);
                        noEntryTile9.setVisible(true);
                    }
                    case 10 -> {
                        noEntryTile10.setLayoutX(65);
                        noEntryTile10.setLayoutY(224);
                        noEntryTile10.setImage(img);
                        noEntryTile10.setVisible(true);
                    }
                    case 11 -> {
                        noEntryTile11.setLayoutX(115);
                        noEntryTile11.setLayoutY(128);
                        noEntryTile11.setImage(img);
                        noEntryTile11.setVisible(true);
                    }
                    case 12 -> {
                        noEntryTile12.setLayoutX(184);
                        noEntryTile12.setLayoutY(58);
                        noEntryTile12.setImage(img);
                        noEntryTile12.setVisible(true);
                    }
                }
            }
        }
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

    public void pickIsland(ActionEvent e) {
        UserAction action = null;
        ImageView img = (ImageView) e.getSource();
        String island = img.getId();
        switch(island) {
            case "island1Button" -> {
                action = new PickDestination(gui.getModelView().getGameCopy().getGameBoard().getIslands().get(0));
                island1Button.setVisible(false);
            }
            case "island2Button" -> {
                action = new PickDestination(gui.getModelView().getGameCopy().getGameBoard().getIslands().get(1));
                island2Button.setVisible(false);

            }
            case "island3Button" -> {
                action = new PickDestination(gui.getModelView().getGameCopy().getGameBoard().getIslands().get(2));
                island3Button.setVisible(false);

            }
            case "island4Button" -> {
                action = new PickDestination(gui.getModelView().getGameCopy().getGameBoard().getIslands().get(3));
                island4Button.setVisible(false);

            }
            case "island5Button" -> {
                action = new PickDestination(gui.getModelView().getGameCopy().getGameBoard().getIslands().get(4));
                island5Button.setVisible(false);

            }
            case "island6Button" -> {
                action = new PickDestination(gui.getModelView().getGameCopy().getGameBoard().getIslands().get(5));
                island6Button.setVisible(false);

            }
            case "island7Button" -> {
                action = new PickDestination(gui.getModelView().getGameCopy().getGameBoard().getIslands().get(6));
                island7Button.setVisible(false);

            }
            case "island8Button" -> {
                action = new PickDestination(gui.getModelView().getGameCopy().getGameBoard().getIslands().get(7));
                island8Button.setVisible(false);

            }
            case "island9Button" -> {
                action = new PickDestination(gui.getModelView().getGameCopy().getGameBoard().getIslands().get(8));
                island9Button.setVisible(false);

            }
            case "island10Button" -> {
                action = new PickDestination(gui.getModelView().getGameCopy().getGameBoard().getIslands().get(9));
                island10Button.setVisible(false);

            }
            case "island11Button" -> {
                action = new PickDestination(gui.getModelView().getGameCopy().getGameBoard().getIslands().get(10));
                island11Button.setVisible(false);

            }
            case "island12Button" -> {
                action = new PickDestination(gui.getModelView().getGameCopy().getGameBoard().getIslands().get(11));
                island12Button.setVisible(false);

            }
        }

        gui.getClientConnection().sendUserInput(action);
    }

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
        island1Button.setVisible(true);
        island2Button.setVisible(true);
        island3Button.setVisible(true);
        island4Button.setVisible(true);
        island5Button.setVisible(true);
        island6Button.setVisible(true);
        island7Button.setVisible(true);
        island8Button.setVisible(true);
        island9Button.setVisible(true);
        island10Button.setVisible(true);
        island11Button.setVisible(true);
        island12Button.setVisible(true);
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

        Platform.runLater(() -> {
            gui.changeStage("PickAssistant.fxml");
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
        });
    }

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
        for (Island isl : gui.getModelView().getGameCopy().getGameBoard().getIslands()) {
            if (isl.getIslandID() == 1) {
                island1.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(0).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland1.setVisible(true);
                        redLabelIsland1.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowIsland1.setVisible(true);
                        yellowLabelIsland1.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.PINK)) {
                        pinkIsland1.setVisible(true);
                        pinkLabelIsland1.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.BLUE)) {
                        blueIsland1.setVisible(true);
                        blueLabelIsland1.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenIsland1.setVisible(true);
                        greenLabelIsland1.setText(getStudentsNumber(isl, s));
                    }
                }
                if(isl.hasTower()) {
                    towerIsland1.setVisible(true);
                    towerLabelIsland1.setText(getTowersNumber(isl));
                }

            } else if (isl.getIslandID() == 2) {
                island2.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(0).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland2.setVisible(true);
                        redLabelIsland2.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowIsland2.setVisible(true);
                        yellowLabelIsland2.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.PINK)) {
                        pinkIsland2.setVisible(true);
                        pinkLabelIsland2.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.BLUE)) {
                        blueIsland2.setVisible(true);
                        blueLabelIsland2.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenIsland2.setVisible(true);
                        greenLabelIsland2.setText(getStudentsNumber(isl, s));
                    }
                }
                if(isl.hasTower()) {
                    towerIsland2.setVisible(true);
                    towerLabelIsland2.setText(getTowersNumber(isl));
                }

            } else if (isl.getIslandID() == 3) {
                island3.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(0).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland3.setVisible(true);
                        redLabelIsland3.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowIsland3.setVisible(true);
                        yellowLabelIsland3.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.PINK)) {
                        pinkIsland3.setVisible(true);
                        pinkLabelIsland3.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.BLUE)) {
                        blueIsland3.setVisible(true);
                        blueLabelIsland3.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenIsland3.setVisible(true);
                        greenLabelIsland3.setText(getStudentsNumber(isl, s));
                    }
                }
                if(isl.hasTower()) {
                    towerIsland3.setVisible(true);
                    towerLabelIsland3.setText(getTowersNumber(isl));
                }

            } else if (isl.getIslandID() == 4) {
                island4.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(0).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland4.setVisible(true);
                        redLabelIsland4.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowIsland4.setVisible(true);
                        yellowLabelIsland4.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.PINK)) {
                        pinkIsland4.setVisible(true);
                        pinkLabelIsland4.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.BLUE)) {
                        blueIsland4.setVisible(true);
                        blueLabelIsland4.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenIsland4.setVisible(true);
                        greenLabelIsland4.setText(getStudentsNumber(isl, s));
                    }
                }
                if(isl.hasTower()) {
                    towerIsland4.setVisible(true);
                    towerLabelIsland4.setText(getTowersNumber(isl));
                }

            } else if (isl.getIslandID() == 5) {
                island5.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(0).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland5.setVisible(true);
                        redLabelIsland5.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowIsland5.setVisible(true);
                        yellowLabelIsland5.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.PINK)) {
                        pinkIsland5.setVisible(true);
                        pinkLabelIsland5.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.BLUE)) {
                        blueIsland5.setVisible(true);
                        blueLabelIsland5.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenIsland5.setVisible(true);
                        greenLabelIsland5.setText(getStudentsNumber(isl, s));
                    }
                }
                if(isl.hasTower()) {
                    towerIsland5.setVisible(true);
                    towerLabelIsland5.setText(getTowersNumber(isl));
                }

            } else if (isl.getIslandID() == 6) {
                island6.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(0).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland6.setVisible(true);
                        redLabelIsland6.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowIsland6.setVisible(true);
                        yellowLabelIsland6.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.PINK)) {
                        pinkIsland6.setVisible(true);
                        pinkLabelIsland6.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.BLUE)) {
                        blueIsland6.setVisible(true);
                        blueLabelIsland6.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenIsland6.setVisible(true);
                        greenLabelIsland6.setText(getStudentsNumber(isl, s));
                    }
                }
                if(isl.hasTower()) {
                    towerIsland6.setVisible(true);
                    towerLabelIsland6.setText(getTowersNumber(isl));
                }

            } else if (isl.getIslandID() == 7) {
                island7.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(0).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland7.setVisible(true);
                        redLabelIsland7.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowIsland7.setVisible(true);
                        yellowLabelIsland7.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.PINK)) {
                        pinkIsland7.setVisible(true);
                        pinkLabelIsland7.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.BLUE)) {
                        blueIsland7.setVisible(true);
                        blueLabelIsland7.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenIsland7.setVisible(true);
                        greenLabelIsland7.setText(getStudentsNumber(isl, s));
                    }
                }
                if(isl.hasTower()) {
                    towerIsland7.setVisible(true);
                    towerLabelIsland7.setText(getTowersNumber(isl));
                }

            } else if (isl.getIslandID() == 8) {
                island8.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(0).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland8.setVisible(true);
                        redLabelIsland8.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowIsland8.setVisible(true);
                        yellowLabelIsland8.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.PINK)) {
                        pinkIsland8.setVisible(true);
                        pinkLabelIsland8.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.BLUE)) {
                        blueIsland8.setVisible(true);
                        blueLabelIsland8.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenIsland8.setVisible(true);
                        greenLabelIsland8.setText(getStudentsNumber(isl, s));
                    }
                }
                if(isl.hasTower()) {
                    towerIsland8.setVisible(true);
                    towerLabelIsland8.setText(getTowersNumber(isl));
                }

            } else if (isl.getIslandID() == 9) {
                island9.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(0).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland9.setVisible(true);
                        redLabelIsland9.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowIsland9.setVisible(true);
                        yellowLabelIsland9.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.PINK)) {
                        pinkIsland9.setVisible(true);
                        pinkLabelIsland9.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.BLUE)) {
                        blueIsland9.setVisible(true);
                        blueLabelIsland9.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenIsland9.setVisible(true);
                        greenLabelIsland9.setText(getStudentsNumber(isl, s));
                    }
                }
                if(isl.hasTower()) {
                    towerIsland9.setVisible(true);
                    towerLabelIsland9.setText(getTowersNumber(isl));
                }

            } else if (isl.getIslandID() == 10) {
                island10.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(0).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland10.setVisible(true);
                        redLabelIsland10.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowIsland10.setVisible(true);
                        yellowLabelIsland10.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.PINK)) {
                        pinkIsland10.setVisible(true);
                        pinkLabelIsland10.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.BLUE)) {
                        blueIsland10.setVisible(true);
                        blueLabelIsland10.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenIsland10.setVisible(true);
                        greenLabelIsland10.setText(getStudentsNumber(isl, s));
                    }
                }
                if(isl.hasTower()) {
                    towerIsland10.setVisible(true);
                    towerLabelIsland10.setText(getTowersNumber(isl));
                }

            } else if (isl.getIslandID() == 11) {
                island11.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(0).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland11.setVisible(true);
                        redLabelIsland11.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowIsland11.setVisible(true);
                        yellowLabelIsland11.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.PINK)) {
                        pinkIsland11.setVisible(true);
                        pinkLabelIsland11.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.BLUE)) {
                        blueIsland11.setVisible(true);
                        blueLabelIsland11.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenIsland11.setVisible(true);
                        greenLabelIsland11.setText(getStudentsNumber(isl, s));
                    }
                }
                if(isl.hasTower()) {
                    towerIsland11.setVisible(true);
                    towerLabelIsland11.setText(getTowersNumber(isl));
                }

            } else if (isl.getIslandID() == 12) {
                island12.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getIslands().get(0).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redIsland12.setVisible(true);
                        redLabelIsland12.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowIsland12.setVisible(true);
                        yellowLabelIsland12.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.PINK)) {
                        pinkIsland12.setVisible(true);
                        pinkLabelIsland12.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.BLUE)) {
                        blueIsland12.setVisible(true);
                        blueLabelIsland12.setText(getStudentsNumber(isl, s));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenIsland12.setVisible(true);
                        greenLabelIsland12.setText(getStudentsNumber(isl, s));
                    }
                }
                if(isl.hasTower()) {
                    towerIsland12.setVisible(true);
                    towerLabelIsland12.setText(getTowersNumber(isl));
                }

            }
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
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getClouds().get(0).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redCloud1.setVisible(true);
                        redLabelCloud1.setText(getCloudStudentsNumber(c));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenCloud1.setVisible(true);
                        greenLabelCloud1.setText(getCloudStudentsNumber(c));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowCloud1.setVisible(true);
                        yellowLabelCloud1.setText(getCloudStudentsNumber(c));
                    }  else if(s.getType().equals(PawnType.PINK)) {
                        pinkCloud1.setVisible(true);
                        pinkLabelCloud1.setText(getCloudStudentsNumber(c));
                    }  else if(s.getType().equals(PawnType.BLUE)) {
                        blueCloud1.setVisible(true);
                        blueLabelCloud1.setText(getCloudStudentsNumber(c));
                    }
                }
            } else if (c.getID() == 2) {
                cloud2.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getClouds().get(1).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redCloud2.setVisible(true);
                        redLabelCloud2.setText(getCloudStudentsNumber(c));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenCloud2.setVisible(true);
                        greenLabelCloud2.setText(getCloudStudentsNumber(c));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowCloud2.setVisible(true);
                        yellowLabelCloud2.setText(getCloudStudentsNumber(c));
                    }  else if(s.getType().equals(PawnType.PINK)) {
                        pinkCloud2.setVisible(true);
                        pinkLabelCloud2.setText(getCloudStudentsNumber(c));
                    }  else if(s.getType().equals(PawnType.BLUE)) {
                        blueCloud2.setVisible(true);
                        blueLabelCloud2.setText(getCloudStudentsNumber(c));
                    }
                }
            } else if (c.getID() == 3) {
                cloud3.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getClouds().get(2).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redCloud3.setVisible(true);
                        redLabelCloud3.setText(getCloudStudentsNumber(c));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenCloud3.setVisible(true);
                        greenLabelCloud3.setText(getCloudStudentsNumber(c));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowCloud3.setVisible(true);
                        yellowLabelCloud3.setText(getCloudStudentsNumber(c));
                    }  else if(s.getType().equals(PawnType.PINK)) {
                        pinkCloud3.setVisible(true);
                        pinkLabelCloud3.setText(getCloudStudentsNumber(c));
                    }  else if(s.getType().equals(PawnType.BLUE)) {
                        blueCloud3.setVisible(true);
                        blueLabelCloud3.setText(getCloudStudentsNumber(c));
                    }
                }
            } else if (c.getID() == 4) {
                cloud4.setVisible(true);
                for (Student s : gui.getModelView().getGameCopy().getGameBoard().getClouds().get(3).getStudents()) {
                    if(s.getType().equals(PawnType.RED)) {
                        redCloud4.setVisible(true);
                        redLabelCloud4.setText(getCloudStudentsNumber(c));
                    } else if(s.getType().equals(PawnType.GREEN)) {
                        greenCloud4.setVisible(true);
                        greenLabelCloud4.setText(getCloudStudentsNumber(c));
                    } else if(s.getType().equals(PawnType.YELLOW)) {
                        yellowCloud4.setVisible(true);
                        yellowLabelCloud4.setText(getCloudStudentsNumber(c));
                    }  else if(s.getType().equals(PawnType.PINK)) {
                        pinkCloud4.setVisible(true);
                        pinkLabelCloud4.setText(getCloudStudentsNumber(c));
                    }  else if(s.getType().equals(PawnType.BLUE)) {
                        blueCloud4.setVisible(true);
                        blueLabelCloud4.setText(getCloudStudentsNumber(c));
                    }
                }
            }
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
        for(int i=0; i < 7; i++) {
            myTowers.getChildren().get(i).setVisible(false);
            leftTowers.getChildren().get(i).setVisible(false);
            rightTowers.getChildren().get(i).setVisible(false);
            topTowers.getChildren().get(i).setVisible(false);
        }
        int cont = 0;
        Image img = null;
        for (Player p : gui.getModelView().getGameCopy().getActivePlayers()) {
            if(p.equals(gui.getModelView().getGameCopy().getCurrentPlayer())) {
                for(int i=0; i < p.getBoard().getTowerArea().getTowerArea().size(); i++) {
                    img = setTowersImage(p.getBoard().getTowerArea().getTowerArea().get(i));
                    ((ImageView) myTowers.getChildren().get(i)).setImage(img);
                    ((ImageView) myTowers.getChildren().get(i)).setVisible(true);
                }
            } else if(cont==0) {
                for(int i=0; i < p.getBoard().getTowerArea().getTowerArea().size(); i++) {
                    img = setTowersImage(p.getBoard().getTowerArea().getTowerArea().get(i));
                    ((ImageView) topTowers.getChildren().get(i)).setImage(img);
                    ((ImageView) topTowers.getChildren().get(i)).setVisible(true);
                }
                cont++;
            } else if(cont==1) {
                for(int i=0; i < p.getBoard().getTowerArea().getTowerArea().size(); i++) {
                    img = setTowersImage(p.getBoard().getTowerArea().getTowerArea().get(i));
                    ((ImageView) leftTowers.getChildren().get(i)).setImage(img);
                    ((ImageView) leftTowers.getChildren().get(i)).setVisible(true);
                }
                cont++;
            } else if(cont==2) {
                for(int i=0; i < p.getBoard().getTowerArea().getTowerArea().size(); i++) {
                    img = setTowersImage(p.getBoard().getTowerArea().getTowerArea().get(i));
                    ((ImageView) rightTowers.getChildren().get(i)).setImage(img);
                    ((ImageView) rightTowers.getChildren().get(i)).setVisible(true);
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
        myWizard.setVisible(false);
        topWizard.setVisible(false);
        leftWizard.setVisible(false);
        rightWizard.setVisible(false);
        Image img = null;
        for (Player p : gui.getModelView().getGameCopy().getActivePlayers()) {
            img = setWizardImage(p.getWizard());
            if(p.equals(gui.getModelView().getGameCopy().getCurrentPlayer())) {
                ((ImageView) myWizard).setImage(img);
                ((ImageView) myWizard).setVisible(true);
            } else if(cont==0) {
                ((ImageView) topWizard).setImage(img);
                ((ImageView) topWizard).setVisible(true);
                cont++;
            } else if(cont==1) {
                ((ImageView) leftWizard).setImage(img);
                ((ImageView) leftWizard).setVisible(true);
                cont++;
            } else if(cont==2) {
                ((ImageView) rightWizard).setImage(img);
                ((ImageView) rightWizard).setVisible(true);
                cont++;
            }
        }
    }

    public void updateAssistant() {
        int cont = 0;
        myAssistant.setVisible(false);
        leftAssistant.setVisible(false);
        topAssistant.setVisible(false);
        rightAssistant.setVisible(false);
        Image img = null;
        for(Player p : gui.getModelView().getGameCopy().getActivePlayers()) {
            img = setAssistantImage(p.getChosenAssistant());
            if(p.equals(gui.getModelView().getGameCopy().getCurrentPlayer())) {
                ((ImageView) myAssistant).setImage(img);
                ((ImageView) myAssistant).setVisible(true);
            } else if(cont==0) {
                ((ImageView) topAssistant).setImage(img);
                ((ImageView) topAssistant).setVisible(true);
                cont++;
            } else if(cont==1) {
                ((ImageView) leftAssistant).setImage(img);
                ((ImageView) leftAssistant).setVisible(true);
                cont++;
            } else if(cont==2) {
                ((ImageView) rightAssistant).setImage(img);
                ((ImageView) rightAssistant).setVisible(true);
                cont++;
            }
        }
    }

    public void updateEntrances() {
        for(int i=0; i < 9; i++) {
            myEntrance.getChildren().get(i).setVisible(false);
            leftEntrance.getChildren().get(i).setVisible(false);
            topEntrance.getChildren().get(i).setVisible(false);
            rightEntrance.getChildren().get(i).setVisible(false);
        }
        Image pic = null;
        int cont = 0;
        for(Player p : gui.getModelView().getGameCopy().getActivePlayers()) {
            if(p.equals(gui.getModelView().getGameCopy().getCurrentPlayer())) {
                for (Student s : p.getBoard().getEntrance().getStudents()) {
                    for (int i = 0; i < p.getBoard().getEntrance().getStudents().size(); i++) {
                        pic = setStudentsEntrance(s);
                        ((ImageView) myEntrance.getChildren().get(i)).setImage(pic);
                        ((ImageView) myEntrance.getChildren().get(i)).setVisible(true);
                    }
                }
            } else if(cont == 0) {
                for (Student s : p.getBoard().getEntrance().getStudents()) {
                    for (int i = 0; i < p.getBoard().getEntrance().getStudents().size(); i++) {
                        pic = setStudentsEntrance(s);
                        ((ImageView) topEntrance.getChildren().get(i)).setImage(pic);
                        ((ImageView) topEntrance.getChildren().get(i)).setVisible(true);
                    }
                }
                cont++;
            } else if(cont == 1) {
                for (Student s : p.getBoard().getEntrance().getStudents()) {
                    for (int i = 0; i < p.getBoard().getEntrance().getStudents().size(); i++) {
                        pic = setStudentsEntrance(s);
                        ((ImageView) leftEntrance.getChildren().get(i)).setImage(pic);
                        ((ImageView) leftEntrance.getChildren().get(i)).setVisible(true);
                    }
                }
                cont++;
            } else if(cont == 2) {
                for (Student s : p.getBoard().getEntrance().getStudents()) {
                    for (int i = 0; i < p.getBoard().getEntrance().getStudents().size(); i++) {
                        pic = setStudentsEntrance(s);
                        ((ImageView) rightEntrance.getChildren().get(i)).setImage(pic);
                        ((ImageView) rightEntrance.getChildren().get(i)).setVisible(true);
                    }
                }
                cont++;
            }
        }
    }

    /*public void updateTowerAreas() {
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
                        ((ImageView) myTowers.getChildren().get(i)).setVisible(false);
                    }
                }
                cont++;
            }
        }
    }*/

    //creare un parametro che indichi che tipo di action è in corso, passarla come parametro
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
        character1Button.setVisible(false);
        character2Button.setVisible(false);
        character3Button.setVisible(false);
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
        character1Coins.setVisible(true);
        character2Coins.setVisible(true);
        character3Coins.setVisible(true);

        //TODO studenti sulle character cards, coins e rendere visibili i bottoni character1 ecc
        /*for(CharacterCard c : gui.getModelView().getGameCopy().getGameBoard().getPlayableCharacters()) {
            if(c.getName().equals())
        }*/
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

    public void pickCloud1() {
        gui.getClientConnection().sendUserInput(new PickCloud(gui.getModelView().getGameCopy().getGameBoard().getClouds().get(0)));
    }
    public void pickCloud2() {
        gui.getClientConnection().sendUserInput(new PickCloud(gui.getModelView().getGameCopy().getGameBoard().getClouds().get(1)));
    }
    public void pickCloud3() {
        gui.getClientConnection().sendUserInput(new PickCloud(gui.getModelView().getGameCopy().getGameBoard().getClouds().get(2)));
    }
    public void pickCloud4() {
        gui.getClientConnection().sendUserInput(new PickCloud(gui.getModelView().getGameCopy().getGameBoard().getClouds().get(3)));
    }

    public void pickBluePawn() {
        gui.getClientConnection().sendUserInput(new PickPawnType(PawnType.BLUE));
    }
    public void pickRedPawn() {
        gui.getClientConnection().sendUserInput(new PickPawnType(PawnType.RED));
    }
    public void pickYellowPawn() {
        gui.getClientConnection().sendUserInput(new PickPawnType(PawnType.YELLOW));
    }
    public void pickGreenPawn() {
        gui.getClientConnection().sendUserInput(new PickPawnType(PawnType.GREEN));
    }
    public void pickPinkPawn() {
        gui.getClientConnection().sendUserInput(new PickPawnType(PawnType.PINK));
    }
    public void pickBlueStudent() {
        gui.getClientConnection().sendUserInput(new PickStudent(new Student(PawnType.BLUE)));
    }
    public void pickRedStudent() {
        gui.getClientConnection().sendUserInput(new PickStudent(new Student(PawnType.RED)));
    }
    public void pickYellowStudent() {
        gui.getClientConnection().sendUserInput(new PickStudent(new Student(PawnType.YELLOW)));
    }
    public void pickGreenStudent() {
        gui.getClientConnection().sendUserInput(new PickStudent(new Student(PawnType.GREEN)));
    }
    public void pickPinkStudent() {
        gui.getClientConnection().sendUserInput(new PickStudent(new Student(PawnType.PINK)));
    }

    public void quitGame() {
        System.out.println("Thanks for playing! See you next time!");
        System.exit(0);
    }

}
