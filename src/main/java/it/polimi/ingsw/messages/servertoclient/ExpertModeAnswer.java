package it.polimi.ingsw.messages.servertoclient;

public class ExpertModeAnswer implements Answer {
    private final String expertModeAnswer;

    public ExpertModeAnswer(String expertModeAnswer) {
        this.expertModeAnswer = expertModeAnswer;
    }

    @Override
    public String getMessage() {
        return expertModeAnswer;
    }
}