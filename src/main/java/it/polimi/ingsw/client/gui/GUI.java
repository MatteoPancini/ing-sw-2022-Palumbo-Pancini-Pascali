package it.polimi.ingsw.client.gui;

import com.sun.scenario.effect.Blend;
import it.polimi.ingsw.client.ActionHandler;
import it.polimi.ingsw.client.ClientConnection;
import it.polimi.ingsw.client.ListenerInterface;
import it.polimi.ingsw.client.ModelView;
import it.polimi.ingsw.client.gui.controllers.*;
import it.polimi.ingsw.messages.servertoclient.ExpertModeAnswer;
import it.polimi.ingsw.messages.servertoclient.NumOfPlayerRequest;
import it.polimi.ingsw.messages.servertoclient.WizardAnswer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.enumerations.PawnType;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Table;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.URL;
import java.util.*;



public class GUI extends Application implements ListenerInterface {


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

    public AssistantCard getAssistantFromDeck(Assistants a) {
        for(AssistantCard card : modelView.getGameCopy().getCurrentPlayer().getAssistantDeck().getDeck()) {
            if(a.equals(card.getName())) {
                return card;
            }
        }
        return null;
    }

    public int getIDFromIslandImage(String s) {
        int id = -1;
        switch(s) {
            case "island1" -> {
                id = 1;
            } case "island2" -> {
                id = 2;
            } case "island3" -> {
                id = 3;
            } case "island4" -> {
                id = 4;
            } case "island5" -> {
                id = 5;
            } case "island6" -> {
                id = 6;
            } case "island7" -> {
                id = 7;
            } case "island8" -> {
                id = 8;
            } case "island9" -> {
                id = 9;
            } case "island10" -> {
                id = 10;
            } case "island11" -> {
                id = 11;
            } case "island12" -> {
                id = 12;
            }
        }
        return id;
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
        //stage.getIcons().add(new Image(getClass().getResourceAsStream("/graphics/icons/eriantys.png")));
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
                System.out.println(pathFxml);
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
                controller.askExpertMode(((ExpertModeAnswer) modelView.getServerAnswer()).getMessage());
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
                infoAlert.setTitle("INFO");
                infoAlert.setHeaderText("Information from server");
                infoAlert.setContentText(modelView.getServerAnswer().getMessage().toString());
                infoAlert.show();
            });
                    //showServerMessage(modelView.getServerAnswer());

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
                    Platform.runLater(() -> updateMainScene());
                }

                //this.changeStage();
            }
            /*case "WinMessage" -> {
                assert serverCommand != null;
                showWinMessage();
            }
            case "LoseMessage" -> {
                assert serverCommand != null;
                showLoseMessage(changeEvent.getNewValue().toString());
            }

             */
            default -> System.out.println("Unknown answer from server");
        }
    }
}
