package it.polimi.ingsw.messages.clienttoserver;

public class ExpertModeChoice implements Message {
    private String expertChoice;

    public ExpertModeChoice(String expertChoice) {
        this.expertChoice = expertChoice;
    }

    public String getExpertChoice() {
        return expertChoice;
    }
}
