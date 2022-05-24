package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.enumerations.PawnType;

import java.io.Serializable;
import java.util.ArrayList;

public class DiningRoom implements Serializable {
    private ArrayList<Table> diningRoom = new ArrayList<Table>();

    public DiningRoom() {
        ArrayList<PawnType> pawns = new ArrayList<PawnType>();
        pawns.add(PawnType.GREEN);
        pawns.add(PawnType.RED);
        pawns.add(PawnType.YELLOW);
        pawns.add(PawnType.PINK);
        pawns.add(PawnType.BLUE);

        for (PawnType p : pawns) {
            Table table = new Table(p);
            diningRoom.add(table);
        }
    }

    public ArrayList<Table> getDiningRoom() {
        return diningRoom;
    }

    public void setDiningRoom(Student student) {
        PawnType type = student.getType();
        for(int i=0; i<5; i++) {
            if(this.getDiningRoom().get(i).getColor().equals(type)) {
                this.getDiningRoom().get(i).addStudent(student);
            }
        }
    }

}