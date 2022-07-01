package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.servertoclient.Answer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;

/**
 * Class Model View is used to get informations from the game copy received or the game status
 */
public class ModelView {
    private final CLI cli;
    private final GUI gui;
    private Answer serverAnswer;
    private boolean activeInput = false;
    private Game gameCopy;
    private String playerNickname;
    private boolean pianification = false;
    private boolean action = false;
    private boolean magicPostmanAction = false;
    private boolean grannyHerbsAction;
    private boolean princessAction;
    private int characterAction = 0;
    private boolean monkAction;
    private boolean isFourPlayers;
    private boolean minestrelAction = false;
    private boolean jesterAction = false;


    /**
     * Constructor of model view class with CLI mode
     * @param cli cli instance of the game
     */
    public ModelView(CLI cli)  {
        this.cli = cli;
        this.gui = null;

    }

    /**
     * Constructor of model view class with GUI mode
     * @param gui gui instance of the game
     */
    public ModelView(GUI gui) {
        this.gui = gui;
        this.cli = null;
    }

    public boolean isPrincessAction() {
        return princessAction;
    }

    public void setPrincessAction(boolean princessAction) {
        this.princessAction = princessAction;
    }

    public boolean isGrannyHerbsAction() {
        return grannyHerbsAction;
    }

    public void setGrannyHerbsAction(boolean grannyHerbsAction) {
        this.grannyHerbsAction = grannyHerbsAction;
    }


    public boolean isMonkAction() {
        return monkAction;
    }

    public void setMonkAction(boolean monkAction) {
        this.monkAction = monkAction;
    }


    public void setFourPlayers(boolean fourPlayers) {
        isFourPlayers = fourPlayers;
    }

    public boolean isFourPlayers() {
        return isFourPlayers;
    }

    public int getCharacterAction() {
        return characterAction;
    }

    public void setCharacterAction(int characterAction) {
        this.characterAction = characterAction;
    }

    public void setJesterAction(boolean jesterAction) {
        this.jesterAction = jesterAction;
    }

    public boolean isJesterAction() {
        return jesterAction;
    }

    public void setMinestrelAction(boolean minestrelAction) {
        this.minestrelAction = minestrelAction;
    }

    public boolean isMinestrelAction() {
        return minestrelAction;
    }

    public boolean isMagicPostmanAction() {
        return magicPostmanAction;
    }

    public void setMagicPostmanAction(boolean magicPostmanAction) {
        this.magicPostmanAction = magicPostmanAction;
    }

    public boolean isAction() {
        return action;
    }

    public boolean isPianification() {
        return pianification;
    }

    public void setPianification(boolean pianification) {
        this.pianification = pianification;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public void setServerAnswer(Answer serverAnswer) {
        this.serverAnswer = serverAnswer;
    }

    public void setActivateInput(boolean activateUserInput) {
        this.activeInput = activateUserInput;
    }

    public void setPlayerNickname(String userNickname) {
        this.playerNickname = userNickname;
    }

    public String getPlayerNickname() {
        return playerNickname;
    }


    public Answer getServerAnswer() {
        return serverAnswer;
    }

    public CLI getCli() {
        return cli;
    }

    public Game getGameCopy() {
        return gameCopy;
    }

    public boolean getActiveInput() {
        return activeInput;
    }

    public void setGameCopy(Game game) {
        this.gameCopy = game;
    }

    /**
     * Method hasYellowProfessor returns true if the input player has the yellow Professor
     * @param p player
     * @return yes/no
     */
    public String hasYellowProfessor(Player p) {
        String yellowProfessor;
        if(p.getBoard().getProfessorTable().getProfTable().get(4).hasProfessor()) {
            yellowProfessor = "yes";
        }
        else {
            yellowProfessor = "no";
        }
        return yellowProfessor;
    }
    /**
     * Method hasBlueProfessor returns true if the input player has the blue Professor
     * @param p player
     * @return yes/no
     */
    public String hasBlueProfessor(Player p) {
        String blueProfessor;
        if(p.getBoard().getProfessorTable().getProfTable().get(0).hasProfessor()) {
            blueProfessor = "yes";
        }
        else {
            blueProfessor = "no";
        }
        return blueProfessor;
    }

    /**
     * Method hasGreenProfessor returns true if the input player has the green Professor
     * @param p player
     * @return yes/no
     */
    public String hasGreenProfessor(Player p) {
        String greenProfessor;
        if(p.getBoard().getProfessorTable().getProfTable().get(1).hasProfessor()) {
            greenProfessor = "yes";
        }
        else {
            greenProfessor = "no";
        }
        return greenProfessor;
    }
    /**
     * Method hasPinkProfessor returns true if the input player has the pink Professor
     * @param p player
     * @return yes/no
     */
    public String hasPinkProfessor(Player p) {
        String pinkProfessor;
        if(p.getBoard().getProfessorTable().getProfTable().get(2).hasProfessor()) {
            pinkProfessor = "yes";
        }
        else {
            pinkProfessor = "no";
        }
        return pinkProfessor;
    }
    /**
     * Method hasRedProfessor returns true if the input player has the red Professor
     * @param p player
     * @return yes/no
     */
    public String hasRedProfessor(Player p) {
        String redProfessor;
        if(p.getBoard().getProfessorTable().getProfTable().get(3).hasProfessor()) {
            redProfessor = "yes";
        }
        else {
            redProfessor = "no";
        }
        return redProfessor;
    }

}
