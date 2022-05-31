package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.Student;

import it.polimi.ingsw.model.enumerations.PawnType;

import java.io.Serializable;
import java.lang.reflect.Array;

import java.util.ArrayList;

import it.polimi.ingsw.model.Game;

import it.polimi.ingsw.model.board.GameBoard;

import java.util.Collections;

public class Entrance implements Serializable {
    private ArrayList<Student> students;

    public Entrance (){
        students = new ArrayList<Student>();
    }

    public ArrayList<Student> getStudents() { return students; }

    public void setStudents(Student newStudent) { students.add(newStudent); }

    public void removeStudent(Student studentToRemove) {
        for(int i = 0; i < students.size(); i++) {
            if(students.get(i).getType() == studentToRemove.getType()) {
                students.remove(i);
                break;
            }
        }
        System.out.println("Rimuovo studente -> " + students.size());

    }

}