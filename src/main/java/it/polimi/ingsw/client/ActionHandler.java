package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.servertoclient.*;

import java.beans.PropertyChangeSupport;

public class ActionHandler {
    private ModelView modelView;
    private CLI cli;

    private final PropertyChangeSupport view = new PropertyChangeSupport(this);


    public ActionHandler(CLI cli, ModelView modelView) {
        this.cli = cli;
        this.modelView = modelView;
        view.addPropertyChangeListener(cli);
    }


    public void answerHandler() {
        Answer answer = modelView.getServerAnswer();
        System.out.println("Analizzo la server answer: " + answer.getMessage());

        if(answer instanceof NumOfPlayerRequest) {
            view.firePropertyChange("InitialGamePhase", null, "RequestPlayerNumber");
        } else if(answer instanceof WizardAnswer) {
            if (((WizardAnswer) answer).getMessage() != null) {
                view.firePropertyChange("InitialGamePhase", null, "RequestWizard");
            } else {
                modelView.setWizardName(((WizardAnswer) answer).getWizard());
                //cli.showServerMessage();
            }
        } else if(answer instanceof ExpertModeAnswer) {
            view.firePropertyChange("InitialGamePhase", null, "ExpertModeAnswer");
        } else if (answer instanceof DynamicAnswer) {
            notifyDynamicAnswer(answer);
        } else if (answer instanceof RequestAction) {
            String actionType = answer.getMessage().toString();
            view.firePropertyChange("ActionPhase", null, actionType);
        } //TODO M: inserisci answer instanceof ActionDoneNotification
        // else if()


    }

    private void notifyDynamicAnswer(Answer answer) {
        view.firePropertyChange("DynamicAnswer", null, answer.getMessage());
        modelView.setActivateInput(((DynamicAnswer) answer).isActivateUserInput());
    }
}
