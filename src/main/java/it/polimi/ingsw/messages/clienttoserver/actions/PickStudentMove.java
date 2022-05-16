package it.polimi.ingsw.messages.clienttoserver.actions;

public class PickStudentMove implements UserAction {
    private Action action;
    private String chosenStudent;
    private String chosenDestination;

    public PickStudentMove() {
        this.action = Action.PICK_STUDENT;
    }

    public PickStudentMove(String student, String destination) {
        this.action = Action.PICK_STUDENT;
        this.chosenStudent = student;
        this.chosenDestination = destination;
    }

    public Action getAction() {
        return action;
    }

    public String getChosenDestination() {
        return chosenDestination;
    }

    public String getChosenStudent() {
        return chosenStudent;
    }
}

