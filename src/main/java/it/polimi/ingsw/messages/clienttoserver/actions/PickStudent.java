package it.polimi.ingsw.messages.clienttoserver.actions;

import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.enumerations.PawnType;

/**
 * Class PickStudent is a user action used to send the student chosen by the user
 */
public class PickStudent implements UserAction {
    private Action action;
    private Student chosenStudent;

    /**
     * Constructor of the class
     * @param stud chosen student
     */
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

