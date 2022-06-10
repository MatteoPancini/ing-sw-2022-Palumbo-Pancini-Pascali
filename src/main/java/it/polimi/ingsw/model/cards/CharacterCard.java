package it.polimi.ingsw.model.cards;


import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.enumerations.Characters;

import java.io.Serializable;
import java.util.ArrayList;

public class CharacterCard implements Serializable {
    private final Characters name;
    private int initialCost;
    private final String effect;
    private ArrayList<Student> students = new ArrayList<Student>();
    private int resetCost = initialCost;


    public CharacterCard(Characters name, String effect, int initialCost) {
        this.name = name;
        this.initialCost = initialCost;
        this.effect = effect;
    }

    public void incrementPrice(){
        initialCost++;
    }

    public Characters getName() {
        return name;
    }

    public void resetCost() {
        System.out.print("Reset cost to " + resetCost);
        initialCost = resetCost;
    }

    public void setResetCost(int resetCost) {
        this.resetCost = resetCost;
    }

    public String getEffect() { return effect; }

    public ArrayList<Student> getStudents() { return students; }

    public void setStudents(Student stud) {
        if(students == null) students = new ArrayList<Student>();
        students.add(stud);
    }

    public int getResetCost() {
        return resetCost;
    }

    public int getInitialCost() { return initialCost; }

    public void removeStudent(Student stud) {
        students.remove(stud);
    }

    public void addStudent(Student stud) {
        students.add(stud);
    }


}