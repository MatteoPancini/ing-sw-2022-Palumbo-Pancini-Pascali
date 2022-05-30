package it.polimi.ingsw.messages.clienttoserver.actions;

public class PickCharacterActionsNum implements UserAction {
    private int number;
    private Action action;



    public PickCharacterActionsNum(int number) {
        this.action = Action.PICK_CHARACTER_NUM;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
