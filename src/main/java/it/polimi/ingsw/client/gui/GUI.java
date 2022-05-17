package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ActionHandler;
import it.polimi.ingsw.client.ListenerInterface;
import it.polimi.ingsw.client.ModelView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GUI extends Application implements ListenerInterface {

    private final PropertyChangeSupport listeners = new PropertyChangeSupport(this);
    private final ModelView modelView;
    private final ActionHandler actionHandler;
    private boolean activeGame;
    private Stage stage;
    private Scene currentScene;




    public GUI() {
        this.modelView = new ModelView(this);
        actionHandler = new ActionHandler(this, modelView);
        activeGame = true;
    }

    public static void main(String[] args) {
        launch(args);
    }


    public void run() {
        stage.setTitle("Eriantys");
        stage.setScene(currentScene);


    }

    @Override
    public void start(Stage stage) {
        setupGui();
    }


    public void setupGui() {
        List<String> fxmlList = new ArrayList<>(Arrays.asList());
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
