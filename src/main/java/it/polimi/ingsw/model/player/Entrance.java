package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.Student;

import it.polimi.ingsw.model.enumerations.PawnType;

import java.lang.reflect.Array;

import java.util.ArrayList;

import it.polimi.ingsw.model.Game;

import it.polimi.ingsw.model.board.GameBoard;

import java.util.Collections

public class Entrance{
    private final Game game;
    private final GameBoard board;
    private ArrayList<Student> students;

    public Entrance (Game game, SchoolBoard board){
        this.game = game;
        this.board = board;
        students = new ArrayList<Student>;
    }

    public ArrayList<Student> getStudents() { return students; }
}