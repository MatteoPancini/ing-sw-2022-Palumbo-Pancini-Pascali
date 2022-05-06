package it.polimi.ingsw.view;

import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Tower;

import java.util.ArrayList;

public class VisualBoard {
    private int yellow;
    private int pink;
    private int red;
    private int blue;
    private int green;
    private boolean yellowProfessor;
    private boolean pinkProfessor;
    private boolean redProfessor;
    private boolean blueProfessor;
    private boolean greenProfessor;
    private ArrayList<Island> islandsView;
    private ArrayList<Student> entrance;
    private AssistantCard[] playedCards;
    private ArrayList<CloudTile> clouds;
    private CharacterCard[] characters;

    public VisualBoard() {
        yellow = 0;
        pink = 0;
        red = 0;
        blue = 0;
        green = 0;
        yellowProfessor = false;
        pinkProfessor = false;
        redProfessor = false;
        blueProfessor = false;
        greenProfessor = false;
        islandsView = new ArrayList<Island>();
    }

    public int getBlue() {
        return blue;
    }

    public int getGreen() {
        return green;
    }

    public int getPink() {
        return pink;
    }

    public int getRed() {
        return red;
    }

    public boolean isYellowProfessor() {
        return yellowProfessor;
    }

    public void setYellow(int yellow) {
        this.yellow = yellow;
    }

    public void setPink(int pink) {
        this.pink = pink;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public boolean isPinkProfessor() {
        return pinkProfessor;
    }

    public boolean isRedProfessor() {
        return redProfessor;
    }

    public boolean isBlueProfessor() {
        return blueProfessor;
    }

    public boolean isGreenProfessor() {
        return greenProfessor;
    }

    public void setIslandsView(ArrayList<Island> islandsView) {
        this.islandsView = islandsView;
    }


    public void setClouds(ArrayList<CloudTile> clouds) {
        this.clouds = clouds;
    }

    public int getYellow() {
        return yellow;
    }

    public CharacterCard[] getCharacters() {
        return characters;
    }

    public void setCharacters(CharacterCard[] characters) {
        this.characters = characters;
    }

    public ArrayList<CloudTile> getClouds() {
        return clouds;
    }

    public AssistantCard[] getPlayedCards() {
        return playedCards;
    }
    public AssistantCard getPlayedCard(Player player) {
        return this.getPlayedCards()[player.getPlayerID()];
    }

    public void setPlayedCard(AssistantCard card, Player player) {
        this.playedCards[player.getPlayerID()] = card;
    }

    public ArrayList<Island> getIslandsView() {
        return islandsView;
    }

    public ArrayList<Student> getEntrance() {
        return entrance;
    }

    public void setBlue() {
        this.blue = blue++;
    }
    public void setPink() {
        this.pink = pink++;
    }
    public void setYellow() {
        this.yellow = yellow++;
    }
    public void setGreen() {
        this.green = green++;
    }
    public void setRed() {
        this.red = red++;
    }

    public void setYellowProfessor(boolean prof) {
        this.yellowProfessor = true;
    }
    public void setBlueProfessor(boolean prof) {
        this.blueProfessor = true;
    }
    public void setPinkProfessor(boolean prof) {
        this.pinkProfessor = true;
    }
    public void setRedProfessor(boolean prof) {
        this.redProfessor = true;
    }
    public void setGreenProfessor(boolean prof) {
        this.greenProfessor = true;
    }

    public void setTower(Island island, Tower tower) {
        this.islandsView.get(island.getIslandID()).setTower(tower);
    }

    public void setPlayedCards(Assistants card, Player currPlayer) {
        this.playedCards[currPlayer.getPlayerID()] = card;
    }

    public void setEntrance(ArrayList<Student> students) {
        for(Student stud : students) {
            entrance.add(stud);
        }
    }
}
