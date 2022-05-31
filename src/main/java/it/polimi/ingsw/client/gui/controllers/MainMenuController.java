package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.ClientConnection;
import it.polimi.ingsw.client.Parser;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.awt.*;

public class MainMenuController implements GUIController {

    private GUI gui;
    private static final String MAIN_MENU = "mainMenu.fxml";

    @FXML private TextField username;
    @FXML private TextField ipAddress;
    @FXML private TextField port;
    @FXML private Label confirmationLabel;
    @FXML private ImageView menuBackground;
    @FXML private ImageView cranioLogo;
    @FXML private Label authors;
    @FXML private ImageView eriantys;
    @FXML private Button startButton;
    @FXML private Button quitButton;


    /** Method quit kills the application when the "Quit" button is pressed. */
    public void quit() {
        System.out.println("Thanks for playing! See you next time!");
        System.exit(0);
    }


    public void play() {
        gui.changeStage("setup.fxml");
    }

    public void start() {
        if (username.getText().equals("") || ipAddress.getText().equals("") || port.getText().equals("")) {
            confirmationLabel.setText("Attention: missing parameters!!!");
        } else if (ipAddress.getText().contains(" ")) {
            confirmationLabel.setText("Attention: address must not contain spaces!");
        } else {
            gui.getModelView().setPlayerNickname(username.getText());
            LoadingController loadingController;
            try {
                Constants.setAddress(ipAddress.getText());
                Constants.setPort(Integer.parseInt(port.getText()));
            } catch (NumberFormatException e) {
                confirmationLabel.setText("Error: missing parameters!");
                return;
            }

            try {
                gui.changeStage("loading.fxml");
                loadingController = (LoadingController) gui.getControllerFromName("loading.fxml");
                loadingController.setText("Configuring connection socket...");
                ClientConnection clientConnection = new ClientConnection();
                if(!clientConnection.setupNickname(username.getText(), gui.getModelView(),gui.getActionHandler())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Server not reachable");
                    alert.setContentText(
                            "The entered IP/port doesn't match any active server or the server is not "
                                    + "running. Please try again!");
                    alert.showAndWait();
                    gui.changeStage(MAIN_MENU);
                    return;
                }
                gui.setClientConnection(clientConnection);
                loadingController.setText("SOCKET CONNECTION \nSETUP COMPLETED!");
                loadingController.setText("WAITING FOR PLAYERS");
                gui.getVirtualClient().addPropertyChangeListener("action", new Parser(clientConnection, gui.getModelView()));
            } catch (DuplicateNicknameException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Duplicate nickname");
                alert.setHeaderText("Duplicate nickname!");
                alert.setContentText("This nickname is already in use! Please choose another one.");
                alert.showAndWait();
                gui.changeStage(MAIN_MENU);
            }
        }
    }







    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
