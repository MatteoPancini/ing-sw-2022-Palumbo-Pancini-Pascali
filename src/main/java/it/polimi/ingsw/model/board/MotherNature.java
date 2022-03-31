package it.polimi.ingsw.model.board;
import java.util.Random;

public class MotherNature {
    private Island position;
    private static MotherNature motherNature = null;

    private MotherNature(){}

    public static MotherNature getMotherNature() {
        if(motherNature == null) {
            motherNature = new MotherNature();
        }
        return motherNature;
    }

    public Island getPosition() {
        return position;
    }

    public void setPosition(Island position) {
        this.position = position;
    }

}
