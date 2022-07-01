package it.polimi.ingsw.messages.clienttoserver.actions;

/**
 * Class PickMovesNumber is a user action used to send the number of mother nature moves chosen by the user
 */
public class PickMovesNumber implements UserAction {
    private Action action;
    private int moves;

    /**
     * Constructor of the class
     * @param num number of moves chosen
     */
    public PickMovesNumber(int num) {
        this.action = Action.PICK_MOVES_NUMBER;
        this.moves = num;
    }

    public Action getAction() {
        return action;
    }

    public int getMoves() {
        return moves;
    }
}
