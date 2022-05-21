package it.polimi.ingsw.client.gui.controllers;


import it.polimi.ingsw.client.gui.GUI;
import javafx.fxml.FXML;

import java.awt.*;

/**
 * LoaderController class is the loading screen controller which covers the color, challenger and
 * worker placement phases. By using several calls to main GUI class, it has a 360-degree view on
 * the GUI package and can make modifications based on the actual server request
 */

public class LoadingController implements GUIController {
    public static final String GODS_MENU_FXML = "godsMenu.fxml";

    private GUI gui;
    @FXML private Label displayStatus;





    //public void askPlayerNumber(String )


    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
