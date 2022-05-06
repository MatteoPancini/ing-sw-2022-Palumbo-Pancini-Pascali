/*
package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.servertoclient.Answer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.PawnType;
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
    private int yellow;
    private int blue;
    private int pink;
    private int red;
    private int green;

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
        return yellow;
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
        return yellow;
    }

    //decidere se id parte da 0 o da 1
    public void setPlayerNickname(String playerNickname) {
        currentPlayer.setNickname(playerNickname);
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
}*/
