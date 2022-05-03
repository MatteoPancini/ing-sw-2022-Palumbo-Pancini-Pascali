package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.model.enumerations.Wizards;

import java.util.ArrayList;
import java.util.List;

public class WizardAnswer implements Answer {
    private String wizardRequest;

    private List<Wizards> wizardsLeft = new ArrayList<>();

    public WizardAnswer(String wizardRequest) {
        this.wizardRequest = wizardRequest;
    }

    public List<Wizards> getWizardsLeft() {
        return wizardsLeft;
    }

    public void setWizardsLeft(List<Wizards> wizardsLeft) {
        this.wizardsLeft = wizardsLeft;
    }
}
