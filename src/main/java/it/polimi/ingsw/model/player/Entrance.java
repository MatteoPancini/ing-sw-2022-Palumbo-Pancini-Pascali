package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.Student;

import it.polimi.ingsw.model.enumerations.PawnType;

import java.lang.reflect.Array;

import java.util.ArrayList;

import it.polimi.ingsw.model.Game;

import it.polimi.ingsw.model.board.GameBoard;

import java.util.Collections;

public class Entrance{
    private ArrayList<Student> students;

    public Entrance (){
        students = new ArrayList<Student>();
    }

    public ArrayList<Student> getStudents() { return students; }

    public void setStudents(ArrayList<Student> newStudents) {
        for(int i = 0; i < newStudents.size(); i++){
            if(newStudents.get(i) != null) students.add(newStudents.get(i));
        }
    }
}