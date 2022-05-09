package it.polimi.ingsw.client;

import it.polimi.ingsw.client.ClientConnection;
import it.polimi.ingsw.client.ModelView;
import it.polimi.ingsw.exceptions.AlreadyPlayedAssistantException;
import it.polimi.ingsw.messages.clienttoserver.actions.*;
import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.player.Player;

import java.util.Locale;

public class InputChecker {
    private ClientConnection clientConnection;
    private ModelView modelView;
    private CLI cli;

    public InputChecker(ModelView modelView, CLI cli, ClientConnection connection) {
        this.modelView = modelView;
        this.cli = cli;
        this.clientConnection = connection;
    }

    public ModelView getModelView() {
        return modelView;
    }

    public ClientConnection getConnectionSocket() {
        return clientConnection;
    }

    //prende in input i chosen values e ritorna una UserAction da inviare al server
    public PickAssistant checkAssistant(String input) throws AlreadyPlayedAssistantException {
        PickAssistant action;
        switch (input.toUpperCase()) {
            case "EAGLE" -> {
                if (modelView.getGame().canPlayAssistant(Assistants.EAGLE)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, new AssistantCard(Assistants.EAGLE));
                    modelView.getVisualBoard().
                            setPlayedCard(new AssistantCard(Assistants.EAGLE, 4, 2), modelView.getCurrentPlayer());
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "DOG" -> {
                if (modelView.getGame().canPlayAssistant(Assistants.DOG)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, new AssistantCard(Assistants.DOG));
                    modelView.getVisualBoard().
                            setPlayedCard(new AssistantCard(Assistants.DOG, 8, 4), modelView.getCurrentPlayer());
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "ELEPHANT" -> {
                if (modelView.getGame().canPlayAssistant(Assistants.ELEPHANT)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, new AssistantCard(Assistants.ELEPHANT));
                    modelView.getVisualBoard().
                            setPlayedCard(new AssistantCard(Assistants.ELEPHANT, 9, 5), modelView.getCurrentPlayer());
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "CAT" -> {
                if (modelView.getGame().canPlayAssistant(Assistants.CAT)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, new AssistantCard(Assistants.CAT));
                    modelView.getVisualBoard().
                            setPlayedCard(new AssistantCard(Assistants.CAT, 3, 2), modelView.getCurrentPlayer());
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "CHEETAH" -> {
                if (modelView.getGame().canPlayAssistant(Assistants.CHEETAH)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, new AssistantCard(Assistants.CHEETAH));
                    modelView.getVisualBoard().
                            setPlayedCard(new AssistantCard(Assistants.CHEETAH, 1, 1), modelView.getCurrentPlayer());
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "LIZARD" -> {
                if (modelView.getGame().canPlayAssistant(Assistants.LIZARD)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, new AssistantCard(Assistants.LIZARD));
                    modelView.getVisualBoard().
                            setPlayedCard(new AssistantCard(Assistants.LIZARD, 6, 3), modelView.getCurrentPlayer());
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "OCTOPUS" -> {
                if (modelView.getGame().canPlayAssistant(Assistants.OCTOPUS)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, new AssistantCard(Assistants.OCTOPUS));
                    modelView.getVisualBoard().
                            setPlayedCard(new AssistantCard(Assistants.OCTOPUS, 7, 4), modelView.getCurrentPlayer());
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "OSTRICH" -> {
                if (modelView.getGame().canPlayAssistant(Assistants.OSTRICH)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, new AssistantCard(Assistants.OSTRICH));
                    modelView.getVisualBoard().
                            setPlayedCard(new AssistantCard(Assistants.OSTRICH, 2, 1), modelView.getCurrentPlayer());
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "TURTLE" -> {
                if (modelView.getGame().canPlayAssistant(Assistants.TURTLE)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, new AssistantCard(Assistants.TURTLE));
                    modelView.getVisualBoard().
                            setPlayedCard(new AssistantCard(Assistants.TURTLE, 10, 5), modelView.getCurrentPlayer());
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "FOX" -> {
                if (modelView.getGame().canPlayAssistant(Assistants.FOX)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, new AssistantCard(Assistants.FOX));
                    modelView.getVisualBoard().
                            setPlayedCard(new AssistantCard(Assistants.FOX, 5, 3), modelView.getCurrentPlayer());
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            default -> action = new PickAssistant();
        }
        return action;
    }

    public PickMovesNumber checkMoves(String input) {
        PickMovesNumber action = null;
        int maxMoves = modelView.getVisualBoard().getPlayedCard(modelView.getCurrentPlayer()).getMoves();
        int moves = Integer.parseInt(input);
        if (moves > 0 && moves < maxMoves) {
            action = new PickMovesNumber(moves);
            /* modelView.getVisualBoard().setMotherNature(getModelView().getVisualBoard().getMotherNature().getPosition()
                    + moves);
            modelView.getVisualBoard().getIslandsView().get(getModelView().getVisualBoard().getMotherNature().getPosition()).
                    setMotherNature(true); */
        }
        return action;
    }

    //trasforma un input in un pawntype (es: 'y' -> 'YELLOW')
    public PawnType toPawnType(String input) {
        PawnType type = null;
        char firstLetter = input.charAt(0);
        switch(Character.toUpperCase(firstLetter)) {
            case 'Y' -> type = PawnType.YELLOW;
            case 'B' -> type = PawnType.BLUE;
            case 'P' -> type = PawnType.PINK;
            case 'R' -> type = PawnType.RED;
            case 'G' -> type = PawnType.GREEN;
        }
        return type;
    }

    public boolean isStudentInEntrance(String input, Player player) {
        PawnType type = toPawnType(input);
        for(Student s : player.getBoard().getEntrance().getStudents()) {
            if(s.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    //se action == null ri-chiedo l'input
    public PickDestination checkDestination(String destination) {
        PickDestination action = null;
        switch(destination.charAt(0)) {
            case 'D' -> {
                action = new PickDestination(modelView.getVisualBoard().getDiningRoom());
            }
            case 'I' -> {
                int island = Integer.parseInt(destination);
                if (island > 0 && island < 13) {
                    action = new PickDestination(Integer.parseInt(destination));
                } else {
                    cli.getOutput().println("Error: wrong island! Choose a number between 1 and 12, according to " +
                            "the remaining islands");
                }
            }
            default -> {
                cli.getOutput().println("Error: type a destination for your student by choosing between 'dining room'" +
                        "or 'island' ");
            }
        }
        return action;
    }

    public PickStudent checkStudent(String studentType) {
        PickStudent action = null;
        if (isStudentInEntrance(studentType, modelView.getCurrentPlayer())) {
            action = new PickStudent(new Student(toPawnType(studentType)));
        }
        return action;
    }

    public PickCloud checkCloud(String input) {
        PickCloud action = null;
        int cloudID = Integer.parseInt(input);
        //ricordare che funziona solo se rimuovo gli studenti dalla nuvola una volta scelta
        //e che le clouds hanno ID che parte da 0 (per combaciare con l'indice dell'arraylist)
        if(!modelView.getGame().getGameBoard().getClouds().get(cloudID).getStudents().isEmpty()) {
            action = new PickCloud(cloudID);
        }
        else {
            cli.getOutput().println("Error: the cloud has already been taken! Choose another one");
        }
        return action;
    }

    public PickCharacter checkCharacter(String input) {
        PickCharacter action = null;
        switch(input.toUpperCase()) {
            case "HERALD" -> {
                action = new PickCharacter(new Herald());
            }
            case "KNIGHT" -> {
                action = new PickCharacter(new Knight());
            }
            case "CENTAUR" -> {
                action = new PickCharacter(new Centaur());
            }
            case "FARMER" -> {
                action = new PickCharacter(new Farmer());
            }
            case "FUNGARUS" -> {
                action = new PickCharacter(new Fungarus());
            }
            case "JESTER" -> {
                action = new PickCharacter(new Jester());
            }
            case "THIEF" -> {
                action = new PickCharacter(new Thief());
            }
            case "MINESTREL" -> {
                action = new PickCharacter(new Minestrel());
            }
            case "MONK" -> {
                action = new PickCharacter(new Monk());
            }
            case "GRANNY_HERBS" -> {
                action = new PickCharacter(new GrannyHerbs());
            }
            case "MAGIC_POSTMAN" -> {
                action = new PickCharacter(new MagicPostman());
            }
            case "SPOILED_PRINCESS" -> {
                action = new PickCharacter(new SpoiledPrincess());
            }
            default -> action = new PickCharacter();
        }
        return action;
    }
}
