package it.polimi.ingsw.client.gui;

import com.sun.scenario.effect.Blend;
import it.polimi.ingsw.client.ActionHandler;
import it.polimi.ingsw.client.ListenerInterface;
import it.polimi.ingsw.client.ModelView;
import it.polimi.ingsw.client.gui.controllers.GUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.*;



public class GUI extends Application implements ListenerInterface {


    private final PropertyChangeSupport listeners = new PropertyChangeSupport(this);
    private final ModelView modelView;
    private final ActionHandler actionHandler;
    private boolean activeGame;
    private Stage stage;
    private Scene currentScene;
    private HashMap<String, Scene> nameMapScene = new HashMap<>();
    private final HashMap<String, GUIController> nameMapController = new HashMap<>();


    private MediaPlayer mediaPlayer;

    private static final String MAIN_MENU = "mainMenu.fxml";
    private static final String SETUP = "setup.fxml";
    private static final String LOADING_PAGE = "loadingPage.fxml";
    private static final String WIZARD_MENU = "wizardMenu.fxml";
    private static final String MAIN_SCENE = "mainScene.fxml";




    public GUI() {
        this.modelView = new ModelView(this);
        actionHandler = new ActionHandler(this, modelView);
        activeGame = true;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public ModelView getModelView() {
        return modelView;
    }



    public void run() {
        stage.setTitle("Eriantys");
        stage.setScene(currentScene);
        //stage.getIcons().add(new Image(getClass().getResourceAsStream("/graphics/icons/eriantys.png")));
        stage.show();

        //resizing
        /*
        ResizeHandler resize = new ResizeHandler((Pane) currentScene.lookup("#mainPane"));
        currentScene.widthProperty().addListener(resize.getWidthListener());
        currentScene.heightProperty().addListener(resize.getHeightListener());


         */
        //musica
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

    }


    public void changeState(String newFXMLScene) {
        currentScene = nameMapScene.get(newFXMLScene);
        stage.setScene(currentScene);

        //resizing
        //ResizeHandler resize = new ResizeHandler((Pane) currentScene.lookup("#mainPane"));
        //currentScene.widthProperty().addListener(resize.getWidthListener());
        //currentScene.heightProperty().addListener(resize.getHeightListener());
    }


    @Override
    public void start(Stage stage) {
        setupGui();
        this.stage = stage;
        //Load dei font
        run();
    }


    public void setupGui() {
        List<String> fxmlList = new ArrayList<>(Arrays.asList(MAIN_MENU, SETUP, LOADING_PAGE, WIZARD_MENU, MAIN_SCENE));
        try {
            for(String pathFxml : fxmlList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + pathFxml));
                nameMapScene.put(pathFxml, new Scene(loader.load()));
                GUIController controller = loader.getController();
                nameMapController.put(pathFxml, controller);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        currentScene = nameMapScene.get(MAIN_MENU);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
