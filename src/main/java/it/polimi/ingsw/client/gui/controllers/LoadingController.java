package it.polimi.ingsw.client.gui.controllers;


import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.clienttoserver.ExpertModeChoice;
import it.polimi.ingsw.messages.clienttoserver.PlayersNumberChoice;
import it.polimi.ingsw.messages.clienttoserver.WizardChoice;
import it.polimi.ingsw.model.enumerations.Wizards;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;


import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * LoaderController class is the loading screen controller which covers the color, challenger and
 * worker placement phases. By using several calls to main GUI class, it has a 360-degree view on
 * the GUI package and can make modifications based on the actual server request
 */

public class LoadingController implements GUIController {

    private GUI gui;
    @FXML private Label status;



    public void setText(String text) {
        status.setText(text.toUpperCase());
    }

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


    public void askExpertMode(String serverReq) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ExpertMode Setup");
        alert.setHeaderText("Choose your game mode.");
        alert.setContentText(serverReq);

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


    public void askWizard(List<Wizards> wizardsList) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Wizard Setup");
        alert.setHeaderText("Choose your wizard!");
        alert.setContentText("Click one of the wizards below!");
        HashMap<String, ButtonType> buttons = new HashMap<>();
        wizardsList.forEach(n -> buttons.put(n.toString(), new ButtonType(n.toString())));
        alert.getButtonTypes().setAll(buttons.values());
        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent(buttonType -> gui.getClientConnection().sendUserInput(new WizardChoice(Wizards.parseWizardInput(buttonType.getText()))));
    }


    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
