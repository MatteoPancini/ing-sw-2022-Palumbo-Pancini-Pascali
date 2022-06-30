package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.enumerations.CloudSide;

import java.io.Serializable;
import java.util.ArrayList;

public class CloudTile implements Serializable {
    private CloudSide side;
    private int ID;
    private ArrayList<Student> cloudStudents;

    public CloudTile(CloudSide side){
        this.side = side;
        cloudStudents = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public CloudSide getSide() {
        return side;
    }

    public ArrayList<Student> getStudents(){ return cloudStudents; }

    public void setStudents(ArrayList<Student> newStudents){ cloudStudents = newStudents; }

    /**
     * Remove students from a cloud tile
     */
    public void removeStudents() {
        cloudStudents = null;
    }
}