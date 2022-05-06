package it.polimi.ingsw.messages.clienttoserver.actions;

public class PickTeam {
    private Action action;
    private int chosenTeam;

    public PickTeam(int team) {
        action = Action.PICK_TEAM;
        chosenTeam = team;
    }

    public Action getAction() {
        return action;
    }

    public int getChosenTeam() {
        return chosenTeam;
    }
}
