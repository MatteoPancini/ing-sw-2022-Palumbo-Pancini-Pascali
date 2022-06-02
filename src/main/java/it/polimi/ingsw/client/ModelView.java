package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.clienttoserver.actions.UserAction;
import it.polimi.ingsw.messages.servertoclient.Answer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.PawnType;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class ModelView {
    private CLI cli;
    private GUI gui;
    private Answer serverAnswer;
    private boolean activeInput = false;
    private Game gameCopy;
    private String playerNickname;
    private boolean startPlaying = false;
    private String wizardName;
    private boolean gameStarted;
    private boolean pianification = false;
    private boolean action = false;
    private boolean magicPostmanAction = false;
    private boolean grannyHerbsAction;

    public boolean isGrannyHerbsAction() {
        return grannyHerbsAction;
    }

    public void setGrannyHerbsAction(boolean grannyHerbsAction) {
        this.grannyHerbsAction = grannyHerbsAction;
    }

    private int characterAction = 0;



    public int getCharacterAction() {
        return characterAction;
    }

    public void setCharacterAction(int characterAction) {
        this.characterAction = characterAction;
    }

    private boolean minestrelAction = false;

    private boolean jesterAction = false;

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

    public boolean isActiveInput() {
        return activeInput;
    }

    public void setStartPlaying(boolean startPlaying) {
        this.startPlaying = startPlaying;
    }

    public boolean isStartPlaying() {
        return startPlaying;
    }

    public String getWizardName() {
        return wizardName;
    }

    public void setWizardName(String wizardName) {
        this.wizardName = wizardName;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public void setCli(CLI cli) {
        this.cli = cli;
    }

    public ModelView(CLI cli)  {
        this.cli = cli;
        this.gui = null;

    }

    public ModelView(GUI gui) {
        this.gui = gui;
        this.cli = null;
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
        System.out.println("Setto nuova gameCopy");
        this.gameCopy = game;
    }

    public int getYellowStudents(Player p) {
        int yellow = 0;
        for(int i=0; i<=10; i++) {
            if(p.getBoard().getDiningRoom().getDiningRoom().get(i).getTable().get(i).getBoardCellType()
                    .equals(PawnType.YELLOW)) {
                yellow++;
            }
        }
        return yellow;
    }

    public int getBlueStudents(Player p) {
        int blue = 0;
        for(int i=0; i<=10; i++) {
            if(p.getBoard().getDiningRoom().getDiningRoom().get(i).getTable().get(i).getBoardCellType().equals(PawnType.BLUE)) {
                blue++;
            }
        }
        return blue;
    }

    public int getRedStudents(Player p) {
        int red = 0;
        for(int i=0; i<=10; i++) {
            if(p.getBoard().getDiningRoom().getDiningRoom().get(i).getTable().get(i).getBoardCellType().equals(PawnType.RED)) {
                red++;
            }
        }
        return red;
    }

    public int getPinkStudents(Player p) {
        int pink = 0;
        for(int i=0; i<=10; i++) {
            if(p.getBoard().getDiningRoom().getDiningRoom().get(i).getTable().get(i).getBoardCellType().equals(PawnType.PINK)) {
                pink++;
            }
        }
        return pink;
    }

    public int getGreenStudents(Player p) {
        int green = 0;
        for(int i=0; i<=10; i++) {
            if(p.getBoard().getDiningRoom().getDiningRoom().get(i).getTable().get(i).getBoardCellType().equals(PawnType.GREEN)) {
                green++;
            }
        }
        return green;
    }

    public String hasYellowProfessor(Player p) {
        String yellowProfessor;
        if(p.getBoard().getProfessorTable().getProfessorTable().get(4).hasProfessor()) {
            yellowProfessor = "yes";
        }
        else {
            yellowProfessor = "no";
        }
        return yellowProfessor;
    }

    public String hasBlueProfessor(Player p) {
        String blueProfessor;
        if(p.getBoard().getProfessorTable().getProfessorTable().get(0).hasProfessor()) {
            blueProfessor = "yes";
        }
        else {
            blueProfessor = "no";
        }
        return blueProfessor;
    }

    public String hasGreenProfessor(Player p) {
        String greenProfessor;
        if(p.getBoard().getProfessorTable().getProfessorTable().get(1).hasProfessor()) {
            greenProfessor = "yes";
        }
        else {
            greenProfessor = "no";
        }
        return greenProfessor;
    }

    public String hasPinkProfessor(Player p) {
        String pinkProfessor;
        if(p.getBoard().getProfessorTable().getProfessorTable().get(2).hasProfessor()) {
            pinkProfessor = "yes";
        }
        else {
            pinkProfessor = "no";
        }
        return pinkProfessor;
    }

    public String hasRedProfessor(Player p) {
        String redProfessor;
        if(p.getBoard().getProfessorTable().getProfessorTable().get(3).hasProfessor()) {
            redProfessor = "yes";
        }
        else {
            redProfessor = "no";
        }
        return redProfessor;
    }

    public void setActivateInput(boolean activateUserInput) {
        this.activeInput = activateUserInput;
    }

    public void setPlayerNickname(String userNickname) {
        this.playerNickname = userNickname;
    }
}
