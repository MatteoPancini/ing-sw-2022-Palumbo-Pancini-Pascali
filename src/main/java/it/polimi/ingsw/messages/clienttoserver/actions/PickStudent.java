package it.polimi.ingsw.messages.clienttoserver.actions;

import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.enumerations.PawnType;

public class PickStudent implements UserAction {
    private Action action;
    private Student chosenStudent;

    public PickStudent() {
        this.action = Action.PICK_STUDENT;
    }

    public PickStudent(Student stud) {
        this.action = Action.PICK_STUDENT;
        this.chosenStudent = new Student(stud.getType());
    }

    public Action getAction() {
        return action;
    }

    public Student getChosenStudent() {
        return chosenStudent;
    }
}

