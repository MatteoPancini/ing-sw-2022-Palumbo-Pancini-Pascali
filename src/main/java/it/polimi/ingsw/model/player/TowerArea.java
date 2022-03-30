package it.polimi.ingsw.model.player;

import java.util.ArrayList;

import it.polimi.ingsw.model.enumerations.TowerColor;

import it.polimi.ingsw.model.Game;

public class TowerArea {
    private Game game;
    private ArrayList<Tower> myTowers;

    public TowerArea(TowerColor color) {
        myTowers = new ArrayList<Tower>;
        int towersNumber;
        if(game.getPlayersNumber() == 3) towersNumber = 6;
        else towersNumber = 8;
        for(int i = 1; i <= towersNumber; i++){
            myTowers.add(new Tower(color));
        }
    }

}
