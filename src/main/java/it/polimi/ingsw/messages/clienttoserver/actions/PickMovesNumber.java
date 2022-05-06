package it.polimi.ingsw.messages.clienttoserver.actions;

public class PickMovesNumber implements UserAction {
    private Action action;
    private int moves;

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
