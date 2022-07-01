package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ActionHandler;
import it.polimi.ingsw.client.ClientConnection;
import it.polimi.ingsw.client.ListenerInterface;
import it.polimi.ingsw.client.ModelView;
import it.polimi.ingsw.client.gui.controllers.*;
import it.polimi.ingsw.messages.servertoclient.NumOfPlayerRequest;
import it.polimi.ingsw.messages.servertoclient.WizardAnswer;
import it.polimi.ingsw.messages.servertoclient.errors.ServerError;
import it.polimi.ingsw.messages.servertoclient.errors.ServerErrorTypes;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.*;


/**
 * Class GUI manages the game if the user decides to play through graphic interface
 */
public class GUI extends Application implements ListenerInterface {

    private final PropertyChangeSupport virtualClient = new PropertyChangeSupport(this);
    private final ModelView modelView;
    private final ActionHandler actionHandler;
    private ClientConnection clientConnection;

    private Stage stage;
    private Scene currentScene;
    private final HashMap<String, Scene> nameMapScene = new HashMap<>();
    private final HashMap<String, GUIController> nameMapController = new HashMap<>();

    private boolean firstSetupScene;

    public Alert getInfoAlert() {
        return infoAlert;
    }

    private final Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);

    private static final String MAIN_MENU = "mainMenu.fxml";
    private static final String SETUP = "setup.fxml";
    private static final String LOADING_PAGE = "loading.fxml";
    private static final String WIZARD_MENU = "wizardMenu.fxml";
    private static final String MAIN_SCENE = "finalBoardScene.fxml";
    private static final String PICK_ASSISTANT = "PickAssistant.fxml";


    /**
     * Constructor GUI
     */
    public GUI() {
        this.modelView = new ModelView(this);
        actionHandler = new ActionHandler(this, modelView);
        firstSetupScene = true;
    }

    /**
     * Method main launches gui
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public ModelView getModelView() {
        return modelView;
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


    public PropertyChangeSupport getVirtualClient() {
        return virtualClient;
    }

    /**
     * Method run runs the gui with a new stage and resizes it
     */
    public void run() {
        stage.setTitle("Eriantys");
        stage.setScene(currentScene);
        stage.getIcons().add(new Image("/graphics/eriantys_banner.png"));
        stage.show();

        ResizeController resize = new ResizeController((Pane) currentScene.lookup("#mainPane"));
        currentScene.widthProperty().addListener(resize.getWidthListener());
        currentScene.heightProperty().addListener(resize.getHeightListener());
    }

    /**
     * Method changeStage is used to change the stage to a new fxml file
     * @param newScene fxml file
     */
    public void changeStage(String newScene) {
        currentScene = nameMapScene.get(newScene);
        stage.setScene(currentScene);
        stage.show();

        if(newScene.equals(MAIN_SCENE)) {
            stage.setResizable(false);
        } else {
            ResizeController resize = new ResizeController((Pane) currentScene.lookup("#mainPane"));
            currentScene.widthProperty().addListener(resize.getWidthListener());
            currentScene.heightProperty().addListener(resize.getHeightListener());
        }
    }

    /**
     * Method start starts the gui by setting it up and running it
     * @param stage main stage
     */
    @Override
    public void start(Stage stage) {
        setupGui();
        this.stage = stage;
        run();
    }


    /**
     * Method setupGui sets the gui up by loading the multiple fxml files
     */
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

    /**
     * Method initialGamePhaseHandler handles the initial settings of the gui,
     * such as number of players, standard or expert mode, wizard choice...
     * @param serverCommand command from server
     */
    public void initialGamePhaseHandler(String serverCommand) {
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


    /**
     * Method updateMainScene is run every time a game copy is received from the server
     */
    public void updateMainScene() {
        MainSceneController controller = (MainSceneController) getControllerFromName(MAIN_SCENE);
        if(modelView.isJesterAction()) {
            controller.update("PICK_CHARACTER_NUMBER");

        } else if(modelView.isMinestrelAction()) {
            controller.update("PICK_CHARACTER_NUMBER");

        } else if(modelView.isGrannyHerbsAction()) {
            controller.update("PICK_ISLAND");

        } else {
            controller.update("STANDARD_UPDATE");

        }
    }

    /**
     * Method propertyChange listens ActionHandler class and handles the actions to do
     * @param changeEvent event
     */
    @Override
    public void propertyChange(PropertyChangeEvent changeEvent) {
        String serverCommand = (changeEvent.getNewValue() != null) ? changeEvent.getNewValue().toString() : null;
        switch(changeEvent.getPropertyName()) {
            case "InitialGamePhase" -> {
                assert serverCommand != null;
                initialGamePhaseHandler(serverCommand);
            }

            case "DynamicAnswer" -> Platform.runLater(() -> {
                if(!pianificationString(changeEvent.getNewValue().toString())) {
                    if(modelView.isPianification() || modelView.isAction()) {
                        MainSceneController controller = (MainSceneController) getControllerFromName(MAIN_SCENE);
                        controller.updateDescription(modelView.getServerAnswer().getMessage().toString());
                    } else {
                        infoAlert.setTitle("INFO");
                        infoAlert.setHeaderText("Information from server");
                        infoAlert.setContentText(modelView.getServerAnswer().getMessage().toString());
                        infoAlert.show();
                    }
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


    /**
     * Method pianificationString compares the dynamic answer received with the pianification phase string
     * @param message dynamic answer message
     * @return true if equals
     */
    public boolean pianificationString(String message) {
        return message.equals(" ___  _   _   _  _  _  ___  _   __   _  ___  _   _   _  _   ___  _ _   _   __  ___ \n" +
                "| o \\| | / \\ | \\| || || __|| | / _| / \\|_ _|| | / \\ | \\| | | o \\| U | / \\ / _|| __|\n" +
                "|  _/| || o || \\\\ || || _| | |( (_ | o || | | |( o )| \\\\ | |  _/|   || o |\\_ \\| _| \n" +
                "|_|  |_||_n_||_|\\_||_||_|  |_| \\__||_n_||_| |_| \\_/ |_|\\_| |_|  |_n_||_n_||__/|___|\n" +
                "                                                                                   ");
    }

    /**
     * Method showEndGameNoWinner shows an info alert warning that the game is over without a winner
     */
    public void showEndGameNoWinner() {
        infoAlert.setTitle("Game Over");
        infoAlert.setContentText("The game is over without a winner! Thanks to have played Eriantys!");
        infoAlert.showAndWait();
        quitGame();

    }

    /**
     * Method showWinGame shows an info alert warning that the user is the winner of the game
     */
    public void showWinGame() {
        infoAlert.setTitle("Game Over");
        infoAlert.setContentText("Congratulations: you are the winner! Thanks for playing Eriantys!");
        infoAlert.showAndWait();
        quitGame();
    }

    /**
     * Method quitGame quits the game when the quit button is pressed
     */
    public void quitGame() {
        System.out.println("Thanks for playing! See you next time!");
        System.exit(0);
    }

    /**
     * Method showLoseGame shows an info alert warning that the game is over and the user has lost
     */
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
        infoAlert.showAndWait();
        quitGame();
    }

    /**
     * Method showServerError shows a server error by setting an info alert
     */
    public void showServerError() {
        infoAlert.setTitle("Server Error");
        if(((ServerError) modelView.getServerAnswer()).getError() == ServerErrorTypes.FULLGAMESERVER) {
            infoAlert.setContentText("Server's lobby is full... please try again in a few minutes!");
            System.exit(-1);
        } else if(((ServerError) modelView.getServerAnswer()).getError() == ServerErrorTypes.SERVEROUT) {
            infoAlert.setContentText("Server is out... please try again later!");
            System.exit(-1);
        }

    }
}
