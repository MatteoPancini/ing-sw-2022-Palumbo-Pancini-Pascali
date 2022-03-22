package it.polimi.ingsw.model;

import it.polimi.ingsw.model.game.MotherNature;
import it.polimi.ingsw.model.player.Player;

public class PlayerTurn {
    private Player currentPlayer;

    //prende gli studenti scelti dal player e li sposta nella dining room
    public void putStudentsToDiningRoom() { ... };

    //prende le nuvole scelte dai players e li inserisce nell'ingresso di ogni player
    public void takeCloud() { ... };

    public void moveMotherNature(int moves_num) { ... };

    //controlla se l'isola è già conquistata e, nel caso, da chi
    public Player islandInfluenceCheck(MotherNature mother){
        int newPosition = MotherNature.getPosition();

        //TODO: finire round
    };

    public boolean checkMerge(MotherNature mother) { ... };

}
