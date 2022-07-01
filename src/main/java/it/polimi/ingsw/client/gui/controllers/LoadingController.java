package it.polimi.ingsw.client.gui.controllers;


import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.clienttoserver.ExpertModeChoice;
import it.polimi.ingsw.messages.clienttoserver.PlayersNumberChoice;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import java.util.Optional;

/**
 * Class LoaderController is the controller that handles the initial set up of the game, by displaying the request
 * of players number and standard/expert mode
 */
public class LoadingController implements GUIController {

    private GUI gui;
    @FXML private Label status;

    public void setText(String text) {
        status.setText(text.toUpperCase());
    }

    /**
     * Method askPlayerNumber displays to the user the request of players number by showing an alert,
     * then it sends it to server
     * @param serverReq request from server
     */
    public void askPlayerNumber(String serverReq) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Lobby capacity");
        alert.setHeaderText("Choose the number of players.");
        alert.setContentText(serverReq);

        ButtonType twoPlayers = new ButtonType("2");
        ButtonType threePlayers = new ButtonType("3");
        ButtonType fourPlayers = new ButtonType("4");

        alert.getButtonTypes().setAll(twoPlayers, threePlayers, fourPlayers);
        Optional<ButtonType> numOfPlayersChoice = alert.showAndWait();
        int playersNum = 0;
        if (numOfPlayersChoice.isPresent() && numOfPlayersChoice.get() == twoPlayers) {
            playersNum = 2;
        } else if (numOfPlayersChoice.isPresent() && numOfPlayersChoice.get() == threePlayers) {
            playersNum = 3;
        } else if(numOfPlayersChoice.isPresent() && numOfPlayersChoice.get() == fourPlayers) {
            playersNum = 4;
        }
        gui.getClientConnection().sendUserInput(new PlayersNumberChoice(playersNum));
    }


    /**
     * Method askExpertMode displays to the user the request of the game mode (standard or expert) by showing an alert,
     * then it sends it to server
     */
    public void askExpertMode() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ExpertMode Setup");
        alert.setHeaderText("Choose your game mode");
        alert.setContentText("Choose your game mode!");

        ButtonType standardMode = new ButtonType("STANDARD MODE");
        ButtonType expertMode = new ButtonType("EXPERT MODE");

        alert.getButtonTypes().setAll(standardMode, expertMode);
        Optional<ButtonType> gameModeChoice = alert.showAndWait();
        String choice = null;
        if(gameModeChoice.isPresent() && gameModeChoice.get() == standardMode) {
            choice = "n";
        } else if(gameModeChoice.isPresent() && gameModeChoice.get() == expertMode) {
            choice = "y";
        }
        gui.getClientConnection().sendUserInput(new ExpertModeChoice(choice));
    }

    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
