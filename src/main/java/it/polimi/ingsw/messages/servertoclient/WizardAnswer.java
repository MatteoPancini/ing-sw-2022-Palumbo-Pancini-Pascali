package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.model.enumerations.Wizards;

import java.util.ArrayList;
import java.util.List;

public class WizardAnswer implements Answer {
    private String wizardRequest;
    private String wizardMessage;

    private List<Wizards> wizardsLeft = new ArrayList<>();

    public WizardAnswer(String wizardRequest) {
        this.wizardRequest = wizardRequest;
        this.wizardMessage = null;
    }

    public WizardAnswer(String wizardRequest, String wizard) {
        this.wizardRequest = wizardRequest;
        this.wizardMessage = wizard;
    }

    public List<Wizards> getWizardsLeft() {
        return wizardsLeft;
    }

    public void setWizardsLeft(List<Wizards> wizardsLeft) {
        this.wizardsLeft = wizardsLeft;
    }

    public String getWizard() {
        return wizardMessage;
    }

    @Override
    public String getMessage() {
        return wizardRequest;
    }
}
