
package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.servertoclient.Answer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class ModelView {
    private CLI cli;
    private VisualBoard visualBoard;
    private Player currentPlayer;
    private ArrayList<Player> players;
    private Answer serverAnswer;
    private boolean activeInput;
    private Game game;

    public ModelView(CLI cli, VisualBoard visualBoard)  {
        this.cli = cli;
        this.visualBoard = visualBoard;
    }

    public Answer getServerAnswer() {
        return serverAnswer;
    }

    public CLI getCli() {
        return cli;
    }

    public Game getGame() {
        return game;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public VisualBoard getVisualBoard() {
        return visualBoard;
    }

    public boolean getActiveInput() {
        return activeInput;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void enableInput() {
        activeInput = true;
    }

    public void disableInput() {
        activeInput = false;
    }

    public void setPlayers() {
        for(Player p : game.getPlayers())
            players.add(p);
    }

}

