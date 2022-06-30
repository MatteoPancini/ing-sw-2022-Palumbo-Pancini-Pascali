package it.polimi.ingsw.model.board;
import java.io.Serializable;

public class MotherNature implements Serializable {
    private int position;
    private static MotherNature motherNature = null;

    private MotherNature(){}

    public static MotherNature getMotherNature() {
        if(motherNature == null) {
            motherNature = new MotherNature();
        }
        return motherNature;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        System.out.println("Setting MN in island " + position);
        this.position = position;
    }
}