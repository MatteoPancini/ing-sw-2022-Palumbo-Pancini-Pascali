package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.enumerations.PawnType;

import java.io.Serializable;
import java.util.ArrayList;

public class DiningRoom implements Serializable {
    private ArrayList<Table> diningRoom = new ArrayList<>();
    private boolean takeCoin;

    public DiningRoom() {
        ArrayList<PawnType> pawns = new ArrayList<>();
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

    public boolean isTakeCoin() {
        return takeCoin;
    }

    public void setTakeCoin(boolean takeCoin) {
        this.takeCoin = takeCoin;
    }

    public ArrayList<Table> getDiningRoom() {
        return diningRoom;
    }

    public void setStudentToDiningRoom(Student student) {
        System.out.println("Setting student");
        PawnType type = student.getType();
        for(int i = 0; i < 5; i++) {
            if(this.getDiningRoom().get(i).getColor().equals(type)) {
                getDiningRoom().get(i).addStudent(student);
                if(getDiningRoom().get(i).getLastPosition() - 1 == 2 || getDiningRoom().get(i).getLastPosition() - 1 == 5 || getDiningRoom().get(i).getLastPosition() - 1 == 8) {
                    takeCoin = true;
                }
                System.out.println(takeCoin);
                return;
            }
        }
    }

}