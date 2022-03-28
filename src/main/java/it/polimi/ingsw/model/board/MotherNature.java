package it.polimi.ingsw.model.board;
import java.util.Random;

public class MotherNature {
    private int position;
    //implementazione singleton
    private static MotherNature instance = null;
    public static MotherNature getInstance() {
        if(instance == null) {
            instance = new MotherNature();
        }
        return instance;
    }
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


}
