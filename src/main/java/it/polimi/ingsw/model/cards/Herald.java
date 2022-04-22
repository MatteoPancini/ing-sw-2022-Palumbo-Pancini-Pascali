package it.polimi.ingsw.model.cards;

public class Herald {
    private static Herald herald = null;


    private Herald(){

    }

    public static Herald getHerald() {
        if(herald == null) {
            herald = new Herald();
        }
        return herald;
    }

}
