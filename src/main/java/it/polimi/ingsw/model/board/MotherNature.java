package it.polimi.ingsw.model.board;
import java.io.Serializable;

/**
 * Class used to implement mother nature
 */
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

    public void setPosition(int position) { this.position = position; }
}