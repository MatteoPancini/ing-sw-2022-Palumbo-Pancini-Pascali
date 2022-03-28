package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

public class Island {

    private int islandID; //TODO: DA MAPPARE IN FASE DI COSTRUZIONE
    private int tower;
    private ArrayList<Island> mergedIsland;
    private ArrayList<Student> students;
    private boolean noEntry;
    private Player owner;

    public Island(int ID){
        islandID = ID;
        tower = 0;
        mergedIsland = new ArrayList<Island>;
        students = new ArrayList<Student>;
        noEntry = false;
        owner = null;
    }
}
