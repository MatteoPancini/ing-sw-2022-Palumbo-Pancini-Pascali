package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.enumerations.CloudSide;

import java.util.ArrayList;

public class CloudTile {
    private CloudSide side;
    private ArrayList<Student> cloudStudents;

    public CloudTile(CloudSide side) {
        this.side = side;
    }

    public ArrayList<Student> getCloudStudents() {
        return cloudStudents;
    }
}
