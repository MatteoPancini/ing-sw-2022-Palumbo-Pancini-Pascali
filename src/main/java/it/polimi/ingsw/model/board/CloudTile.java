package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.enumerations.CloudSide;

import java.util.ArrayList

public class CloudTile {
    private CloudSide side;

    private ArrayList<Student> cloudStudents;

    public CloudTile(CloudSide side){
        this.side = side;
        cloudStudents = new ArrayList<Student>;
    }

    public ArrayList<Student> getStudents(){ return cloudStudents; }

    public void setStudents(ArrayList<Student> newStudents){ cloudStudents = newStudents; }
}
