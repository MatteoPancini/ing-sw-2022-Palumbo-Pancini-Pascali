package it.polimi.ingsw.messages.clienttoserver;

/**
 * Class PickAssistant is a message used to send the standard or expert mode choice of the user
 */
public class ExpertModeChoice implements Message {
    private String expertChoice;

    /**
     * Constructor of the class
     * @param expertChoice expert/standard choice
     */
    public ExpertModeChoice(String expertChoice) {
        this.expertChoice = expertChoice;
    }

    public String getExpertChoice() {
        return expertChoice;
    }
}
