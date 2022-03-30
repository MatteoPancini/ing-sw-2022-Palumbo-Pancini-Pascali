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

    public Entrance(){
        Collections.shuffle(board.getStudentsBag());
        int studentsNumber;
        if(game.getPlayersNumber() == 3) studentsNumber = 9;
        else studentsNumber = 7;
        students = new ArrayList<Student>;
        for(int i = 1; i <= studentsNumber; i++){
            students.add(board.getStudentsBag().get(0));
            board.removeStudents(0);
        }
    }

    public ArrayList<Student> getStudents(){ return students; }
}













