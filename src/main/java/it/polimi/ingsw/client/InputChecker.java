package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.AlreadyPlayedAssistantException;
import it.polimi.ingsw.messages.clienttoserver.actions.*;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.player.Player;

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
                if (modelView.getGameCopy().canPlayAssistant(Assistants.EAGLE)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, new AssistantCard(Assistants.EAGLE));
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "DOG" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.DOG)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, new AssistantCard(Assistants.DOG));
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "ELEPHANT" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.ELEPHANT)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, new AssistantCard(Assistants.ELEPHANT));
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "CAT" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.CAT)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, new AssistantCard(Assistants.CAT));
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "CHEETAH" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.CHEETAH)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, new AssistantCard(Assistants.CHEETAH));
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "LIZARD" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.LIZARD)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, new AssistantCard(Assistants.LIZARD));
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "OCTOPUS" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.OCTOPUS)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, new AssistantCard(Assistants.OCTOPUS));
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "OSTRICH" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.OSTRICH)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, new AssistantCard(Assistants.OSTRICH));
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "TURTLE" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.TURTLE)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, new AssistantCard(Assistants.TURTLE));
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "FOX" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.FOX)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, new AssistantCard(Assistants.FOX));
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
        int maxMoves = modelView.getGameCopy().getGameBoard().getLastAssistantUsed().get(modelView.getGameCopy().getCurrentPlayer().getPlayerID()).getMoves();
        int moves = Integer.parseInt(input);
        if (moves > 0 && moves < maxMoves) {
            action = new PickMovesNumber(moves);
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
                action = new PickDestination(modelView.getGameCopy().getCurrentPlayer().getBoard().getDiningRoom());
            }
            case 'I' -> {
                int island = Integer.parseInt(destination);
                if (island > 0 && island < 13) {
                    action = new PickDestination(modelView.getGameCopy().getGameBoard().getIslands().get(Integer.parseInt(destination)));
                } else {
                    cli.showError("Error: wrong island! Choose a number between 1 and 12, according to " +
                            "the remaining islands");
                }
            }
            default -> {
                cli.showError("Error: type a destination for your student by choosing between 'dining room'" +
                        "or 'island'");
            }
        }
        return action;
    }

    public PickStudent checkStudent(String studentType) {
        PickStudent action = null;
        if (isStudentInEntrance(studentType, modelView.getGameCopy().getCurrentPlayer())) {
            action = new PickStudent(new Student(toPawnType(studentType)));
        }
        return action;
    }

    public PickCloud checkCloud(String input) {
        PickCloud action = null;
        int cloudID = Integer.parseInt(input);
        //ricordare che funziona solo se rimuovo gli studenti dalla nuvola una volta scelta
        //e che le clouds hanno ID che parte da 0 (per combaciare con l'indice dell'arraylist)
        if(!modelView.getGameCopy().getGameBoard().getClouds().get(cloudID).getStudents().isEmpty()) {
            action = new PickCloud(modelView.getGameCopy().getGameBoard().getClouds().get(cloudID));
        }
        else {
            cli.showError("Error: the cloud has already been taken! Choose another one");
        }
        return action;
    }

    /* aspettare implementazione delle character cards
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
    } */

    public void quitGame() {
        clientConnection.sendUserInput(new QuitGame());
        System.err.println("Disconnected from the server.");
        System.exit(0);
    }
}
