package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.clienttoserver.actions.*;
import it.polimi.ingsw.messages.servertoclient.*;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.player.Table;

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
        }
        else if (answer instanceof DynamicAnswer) {
            notifyDynamicAnswer(answer);
        }


    }

    private void notifyDynamicAnswer(Answer answer) {
        view.firePropertyChange("DynamicAnswer", null, answer.getMessage());
        modelView.setActivateInput(((DynamicAnswer) answer).isActivateUserInput());
    }

    //viene chiamato all'interno di propertyChange della CLI, notificata dall'Action Handler
    public void makeAction(String serverCommand) {
        switch(serverCommand) {
            case "PICKASSISTANT" -> {
                cli.askAssistant(modelView.getCurrentPlayer().getAssistantDeck());
            }
            case "PICKCLOUD" -> {
                cli.askCloud(modelView.getVisualBoard().getClouds());
            }
            case "PICKDESTINATION" -> {
                cli.askDestination();
            }
            case "PICKSTUDENT" -> {
                cli.askStudent(modelView.getCurrentPlayer().getBoard());
            }
            case "PICKMOVESNUMBER" -> {
                cli.askMoves(modelView.getCurrentPlayer().getChosenAssistant());
            }
            case "PICKCHARACTER" -> {
                cli.askCharacterCard(modelView.getVisualBoard().getCharacters());
            }
            default -> {
                cli.getOutput().println("Error: no such action");
            }
        }
    }

    public void updateStudentMove(String student, String dest) {
        if(((PickDestination) modelView.getDestinationUserAction()).getChosenIsland()==-1) {
            for(Table t : modelView.getVisualBoard().getDiningRoom().getDiningRoom()) {
                if(t.getColor().equals(((PickStudent) modelView.getLastUserAction()).getChosenStudent())) {
                    t.addStudent(((PickStudent) modelView.getLastUserAction()).getChosenStudent());
                }
            }
        }
        else if(((PickDestination) modelView.getDestinationUserAction()).getDiningRoom() == null) {
            modelView.getVisualBoard().getIslandsView().get(((PickDestination) modelView.getLastUserAction()).getChosenIsland())
                    .getStudents().add(((PickStudent) modelView.getLastUserAction()).getChosenStudent());
        }
        modelView.setDestinationUserAction(null);
    }
    //TODO il server deve inviare un messaggio ogni volta che cambia il model, oppure trovare un modo
    //per aggiornare la model view nei casi in cui non vengono mandate user action (es: riempire entrance dal sacchetto)
    public void updateModelView(String actionName) {
        switch(actionName) {
            case "PICKASSISTANT" -> {
                modelView.getVisualBoard().
                        setPlayedCard(takeCard(((PickAssistant) modelView.getLastUserAction()).getChosenAssistant()), modelView.getCurrentPlayer());
            }
            case "PICKCLOUD" -> {
                for(Student s : modelView.getVisualBoard().getClouds().get(((PickCloud) modelView.getLastUserAction()).getChosenCloud()).getStudents()) {
                    modelView.getCurrentPlayer().getBoard().getEntrance().getStudents().add(s);
                    modelView.getVisualBoard().getClouds().get(((PickCloud) modelView.getLastUserAction()).getChosenCloud());
                }
            }
            case "PICKMOVESNUMBER" -> {
                modelView.getVisualBoard().setMotherNature(((PickMovesNumber) modelView.getLastUserAction()).getMoves()
                        + modelView.getVisualBoard().getMotherNature().getPosition());
            }
            case "PICKCHARACTER" -> {
                modelView.getVisualBoard().getCharacters().add(((PickCharacter) modelView.getLastUserAction()).getChosenCharacter());
            }
        }
    }

    public AssistantCard takeCard (AssistantCard card){
        for(AssistantCard c : modelView.getVisualBoard().getLastAssistantUsed()){
            if(card.getName() == c.getName()){
                modelView.getVisualBoard().getLastAssistantUsed().remove(card);
                return card;
            }
        }
        return null;
    }
}