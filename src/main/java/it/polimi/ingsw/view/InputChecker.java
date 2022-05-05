/*package it.polimi.ingsw.view;

import it.polimi.ingsw.exceptions.AlreadyPlayedAssistantException;
import it.polimi.ingsw.messages.clienttoserver.actions.*;
import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.AssistantCard;
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
        return client;
    }

    public PickAssistant checkAssistant(String input) throws AlreadyPlayedAssistantException {
        PickAssistant action;
        switch (input.toUpperCase()) {
            case "EAGLE" -> {
                if (modelView.getGame().canPlayAssistant(Assistants.EAGLE)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.EAGLE);
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "DOG" -> {
                if (modelView.getGame().canPlayAssistant(Assistants.DOG)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.DOG);
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "ELEPHANT" -> {
                if (modelView.getGame().canPlayAssistant(Assistants.ELEPHANT)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.ELEPHANT);
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "CAT" -> {
                if (modelView.getGame().canPlayAssistant(Assistants.CAT)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.CAT);
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "CHEETAH" -> {
                if (modelView.getGame().canPlayAssistant(Assistants.CHEETAH)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.CHEETAH);
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "LIZARD" -> {
                if (modelView.getGame().canPlayAssistant(Assistants.LIZARD)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.LIZARD);
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "OCTOPUS" -> {
                if (modelView.getGame().canPlayAssistant(Assistants.OCTOPUS)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.OCTOPUS);
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "OSTRICH" -> {
                if (modelView.getGame().canPlayAssistant(Assistants.OSTRICH)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.OSTRICH);
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "TURTLE" -> {
                if (modelView.getGame().canPlayAssistant(Assistants.TURTLE)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.TURTLE);
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "FOX" -> {
                if (modelView.getGame().canPlayAssistant(Assistants.FOX)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.FOX);
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
        int maxMoves = modelView.getCurrentPlayer().getChosenAssistant().getMoves();
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
    public PickStudentMove checkStudentMove(String student, String destination) {
        PickStudentMove action = null;
        if(isStudentInEntrance(student, modelView.getCurrentPlayer())) {
            if(Integer.parseInt(destination) > 0 && Integer.parseInt(destination) < 13)
            action = new PickStudentMove(student, destination);
            else {
                cli.getOutput().println("Error: wrong island! Choose a number between 1 and 12");
            }
        }
        else {
            cli.getOutput().println("Error: you don't own that student! Try again");
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
                action = new PickCharacter(Characters.HERALD);
            }
            case "KNIGHT" -> {
                action = new PickCharacter(Characters.KNIGHT);
            }
            case "CENTAUR" -> {
                action = new PickCharacter(Characters.CENTAUR);
            }
            case "FARMER" -> {
                action = new PickCharacter(Characters.FARMER);
            }
            case "FUNGARUS" -> {

            }
            case "JESTER" -> {
                action = new PickCharacter(Characters.JESTER);
            }
            case "THIEF" -> {
                action = new PickCharacter(Characters.THIEF);
            }
            case "MINESTREL" -> {
                action = new PickCharacter(Characters.MINESTREL);
            }
            case "MONK" -> {
                action = new PickCharacter(Characters.MONK);
            }
            case "GRANNY_HERBS" -> {
                action = new PickCharacter(Characters.GRANNY_HERBS);
            }
            case "MAGIC_POSTMAN" -> {
                action = new PickCharacter(Characters. MAGIC_POSTMAN);
            }
            case "SPOILED_PRINCESS" -> {
                action = new PickCharacter(Characters.SPOILED_PRINCESS);
            }
            default -> action = new PickCharacter();
        }
        return action;
    }
}
*/