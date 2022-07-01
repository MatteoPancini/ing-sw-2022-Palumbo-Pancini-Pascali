package it.polimi.ingsw.messages.clienttoserver.actions;

/**
 * Class PickCharacterActionsNum is a user action used to send the character cards' action to do, chosen by the user
 */
public class PickCharacterActionsNum implements UserAction {
    private int number;
    private Action action;


    /**
     * Constructor of the class
     * @param number number of actions chosen
     */
    public PickCharacterActionsNum(int number) {
        this.action = Action.PICK_CHARACTER_NUM;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
