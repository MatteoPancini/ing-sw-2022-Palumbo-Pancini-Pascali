package it.polimi.ingsw.model.cards;


import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.enumerations.Characters;

import java.util.ArrayList;

public class CharacterCard {
    private final Characters name;
    private int cost;
    private final String effect;
    private ArrayList<Student> students = new ArrayList<Student>();

    public CharacterCard(Characters name, String effect, int cost){
        this.name = name;
        this.cost = cost;
        this.effect = effect;
    }

    public CharacterCard() {
        name = null;
        effect = null;
    }

    public void incrementPrice(){
        cost++;
    }

    public Characters getName() {
        return name;
    }

    public String getEffect() { return effect; }

    public ArrayList<Student> getStudents() { return students; }

    public void setStudents(Student stud){
        if(students == null) students = new ArrayList<Student>();
        students.add(stud);
    }

}

