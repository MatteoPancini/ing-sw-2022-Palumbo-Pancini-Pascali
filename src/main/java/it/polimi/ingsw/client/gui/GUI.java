package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ActionHandler;
import it.polimi.ingsw.client.ClientConnection;
import it.polimi.ingsw.client.ListenerInterface;
import it.polimi.ingsw.client.ModelView;
import it.polimi.ingsw.client.gui.controllers.*;
import it.polimi.ingsw.messages.servertoclient.ExpertModeAnswer;
import it.polimi.ingsw.messages.servertoclient.NumOfPlayerRequest;
import it.polimi.ingsw.messages.servertoclient.WizardAnswer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.*;



public class GUI extends Application implements ListenerInterface {

    @FXML Label descriptionLabel;

    private final PropertyChangeSupport virtualClient = new PropertyChangeSupport(this);
    private final ModelView modelView;
    private final ActionHandler actionHandler;
    private ClientConnection clientConnection;
    private MainSceneController mainSceneController;

    private boolean activeGame;
    private Stage stage;
    private Scene currentScene;
    private HashMap<String, Scene> nameMapScene = new HashMap<>();
    private final HashMap<String, GUIController> nameMapController = new HashMap<>();

    private boolean firstSetupScene;

    public boolean isActiveGame() {
        return activeGame;
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public HashMap<String, Scene> getNameMapScene() {
        return nameMapScene;
    }

    public HashMap<String, GUIController> getNameMapController() {
        return nameMapController;
    }

    public Alert getInfoAlert() {
        return infoAlert;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    private Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);

    private MediaPlayer mediaPlayer;

    private static final String MAIN_MENU = "mainMenu.fxml";
    private static final String SETUP = "setup.fxml";
    private static final String LOADING_PAGE = "loading.fxml";
    private static final String WIZARD_MENU = "wizardMenu.fxml";
    private static final String MAIN_SCENE = "finalBoardScene.fxml";
    private static final String PICK_ASSISTANT = "PickAssistant.fxml";



    public GUI() {
        this.modelView = new ModelView(this);
        actionHandler = new ActionHandler(this, modelView);
        activeGame = true;
        firstSetupScene = true;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public ModelView getModelView() {
        return modelView;
    }


    public PropertyChangeSupport getVirtualClient() {
        return virtualClient;
    }

    public void run() {
        stage.setTitle("Eriantys");
        stage.setScene(currentScene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/graphics/eriantys_banner.png")));
        stage.show();

        //resizing

        ResizeController resize = new ResizeController((Pane) currentScene.lookup("#mainPane"));
        currentScene.widthProperty().addListener(resize.getWidthListener());
        currentScene.heightProperty().addListener(resize.getHeightListener());



        //musica

        /*
        Media pick = new Media(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("media/Epic_Battle_Speech.mp3")).toExternalForm());
        mediaPlayer = new MediaPlayer(pick);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(25);
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
        });

         */

    }

    public void changeStage(String newScene) {
        currentScene = nameMapScene.get(newScene);
        stage.setScene(currentScene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            showQuitGame();
        });
        ResizeController resize = new ResizeController((Pane) currentScene.lookup("#mainPane"));
        currentScene.widthProperty().addListener(resize.getWidthListener());
        currentScene.heightProperty().addListener(resize.getHeightListener());
    }

    @Override
    public void start(Stage stage) {
        setupGui();
        this.stage = stage;
        //Load dei font
        run();
    }


    public void setupGui() {
        List<String> fxmlList = new ArrayList<>(Arrays.asList(MAIN_MENU, SETUP, LOADING_PAGE, WIZARD_MENU, PICK_ASSISTANT, MAIN_SCENE));
        try {
            for(String pathFxml : fxmlList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + pathFxml));
                nameMapScene.put(pathFxml, new Scene(loader.load()));
                GUIController controller = loader.getController();
                controller.setGui(this);
                nameMapController.put(pathFxml, controller);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentScene = nameMapScene.get(MAIN_MENU);
    }

    public GUIController getControllerFromName(String name) {
        return nameMapController.get(name);
    }

    public ActionHandler getActionHandler() {
        return actionHandler;
    }

    public ClientConnection getClientConnection() {
        return clientConnection;
    }

    public void setClientConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    public void initialGamePhaseHandler(String serverCommand) {
        //System.out.println("Sono entrato in initialGamePhaseHandler perchè ho letto: " + serverCommand);
        switch(serverCommand) {

            case "RequestPlayerNumber" -> Platform.runLater(() -> {
                LoadingController controller = (LoadingController) getControllerFromName(LOADING_PAGE);
                controller.askPlayerNumber(((NumOfPlayerRequest) modelView.getServerAnswer()).getMessage());
            });

            case "ExpertModeAnswer" -> Platform.runLater(() -> {
                LoadingController controller = (LoadingController) getControllerFromName(LOADING_PAGE);
                controller.askExpertMode();
            }) ;
            case "RequestWizard" -> Platform.runLater(() -> {
                WizardMenuController controller = (WizardMenuController) getControllerFromName(WIZARD_MENU);
                controller.askWizard(((WizardAnswer) modelView.getServerAnswer()).getWizardsLeft());
            });

            default -> System.out.println("Nothing to do");

        }
    }



    public void updateMainScene() {
        MainSceneController controller = (MainSceneController) getControllerFromName(MAIN_SCENE);
        controller.update("STANDARD_UPDATE");
        /*
        getControllerFromName(MAIN_SCENE).
        mainSceneController.updateIslands();
        mainSceneController.updateTowers();
        mainSceneController.updateWizard();
        mainSceneController.updateClouds();
        mainSceneController.updateDiningRooms();
         */
    }



    @Override
    public void propertyChange(PropertyChangeEvent changeEvent) {
        String serverCommand = (changeEvent.getNewValue() != null) ? changeEvent.getNewValue().toString() : null;
        //System.out.println("PropertyChange arrivato: " + serverCommand);
        switch(changeEvent.getPropertyName()) {
            case "InitialGamePhase" -> {
                assert serverCommand != null;
                //System.out.println("Sono in property change e ho letto:" + serverCommand);
                initialGamePhaseHandler(serverCommand);
            }

            case "DynamicAnswer" -> Platform.runLater(() -> {
                if(modelView.isPianification() || modelView.isAction()) {
                    MainSceneController controller = (MainSceneController) getControllerFromName(MAIN_SCENE);
                    controller.updateDescription(modelView.getServerAnswer().getMessage().toString());
                } else {
                    infoAlert.setTitle("INFO");
                    infoAlert.setHeaderText("Information from server");
                    infoAlert.setContentText(modelView.getServerAnswer().getMessage().toString());
                    infoAlert.show();
                }
            });
            case "ActionPhase" -> {
                assert serverCommand != null;
                actionHandler.makeAction(serverCommand);
            }
            case "UpdateModelView" -> {
                assert serverCommand != null;
                modelView.setGameCopy((Game) changeEvent.getNewValue());
                if(firstSetupScene) {
                    Platform.runLater(() -> {
                        changeStage(MAIN_SCENE);
                        updateMainScene();
                    });
                    firstSetupScene = false;
                } else {
                    Platform.runLater(this::updateMainScene);
                }

                //this.changeStage();
            }
            case "WinMessage" -> {
                assert serverCommand != null;
                showWinGame();
            }
            case "LoseMessage" -> {
                assert serverCommand != null;
                showLoseGame(changeEvent.getNewValue().toString());
            }

            default -> System.out.println("Unknown answer from server");
        }
    }

    public void showQuitGame() {
        infoAlert.setTitle("Quit Game");
        infoAlert.setContentText("Game successfully quitted!");
    }

    public void showEndGameNoWinner() {
        infoAlert.setTitle("Game Over");
        infoAlert.setContentText("The game is over without a winner! Thanks to have played Eriantys!");
    }

    public void showWinGame() {
        infoAlert.setTitle("Game Over");
        infoAlert.setContentText("Congratulations: you are the winner! Thanks to have played Eriantys!");
    }

    public void showLoseGame(String winnerNickname) {
        infoAlert.setTitle("Game Over");
        if(modelView.isFourPlayers()) {
            ArrayList<String> winners = new ArrayList<>();
            int winnerTeam = -1;
            for(Player p : modelView.getGameCopy().getPlayers()) {
                if(p.getNickname().equals(winnerNickname)) {
                    winnerTeam = p.getIdTeam();
                }
            }

            for(Player p : modelView.getGameCopy().getPlayers()) {
                if(p.getIdTeam() == winnerTeam) {
                    winners.add(p.getNickname());
                }
            }
            infoAlert.setContentText("You lost: the winner are " + winners.get(0) + " & " + winners.get(1) + " from team " + winnerTeam + "! Thanks to have played Eriantys!");
        } else {
            infoAlert.setContentText("You lost: the winner is " + winnerNickname + "! Thanks to have played Eriantys!");
        }
}
}
