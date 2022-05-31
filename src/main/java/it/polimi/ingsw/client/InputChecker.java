package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.exceptions.AlreadyPlayedAssistantException;
import it.polimi.ingsw.messages.clienttoserver.QuitGame;
import it.polimi.ingsw.messages.clienttoserver.actions.*;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.AssistantCard;
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


    /**
     * prende in input i chosen values e ritorna una UserAction da inviare al server
     *
     * @param input  assistant chosen
     * @return
     * @throws AlreadyPlayedAssistantException
     */
    public PickAssistant checkAssistant(String input) throws AlreadyPlayedAssistantException {
        PickAssistant action;
        switch (input.toUpperCase()) {
            case "EAGLE" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.EAGLE)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.EAGLE);
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "DOG" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.DOG)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.DOG);
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "ELEPHANT" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.ELEPHANT)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.ELEPHANT);
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "CAT" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.CAT)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.CAT);
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "CHEETAH" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.CHEETAH)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.CHEETAH);
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "LIZARD" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.LIZARD)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.LIZARD);
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "OCTOPUS" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.OCTOPUS)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.OCTOPUS);
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "OSTRICH" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.OSTRICH)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.OSTRICH);
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "TURTLE" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.TURTLE)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.TURTLE);
                } else {
                    throw new AlreadyPlayedAssistantException();
                }
            }
            case "FOX" -> {
                System.out.println("Entro in foxx");

                if (modelView.getGameCopy().canPlayAssistant(Assistants.FOX)) {
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
        int maxMoves;
        if(modelView.isMagicPostmanAction()) {
            maxMoves = modelView.getGameCopy().getGameBoard().getLastAssistantUsed().get(modelView.getGameCopy().getCurrentPlayer().getPlayerID()).getMoves() + 2;
            modelView.setMagicPostmanAction(false);
        } else {
            maxMoves = modelView.getGameCopy().getGameBoard().getLastAssistantUsed().get(modelView.getGameCopy().getCurrentPlayer().getPlayerID()).getMoves();
        }
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

    public boolean isStudentInEntrance(String input) {
        PawnType type = toPawnType(input);
        System.out.println("Tipo passato: " + type.toString());
        for(int i = 0; i < modelView.getGameCopy().getCurrentPlayer().getBoard().getEntrance().getStudents().size(); i++) {
            System.out.println("Tipo letto: " + modelView.getGameCopy().getCurrentPlayer().getBoard().getEntrance().getStudents().get(i).getType().toString());

            if(modelView.getGameCopy().getCurrentPlayer().getBoard().getEntrance().getStudents().get(i).getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    //se action == null ri-chiedo l'input
    public PickDestination checkDestination(String destination) {
        PickDestination action = null;
        System.out.println(destination.toUpperCase());
        switch(destination.toUpperCase()) {
            case "DININGROOM" -> {
                action = new PickDestination(modelView.getGameCopy().getCurrentPlayer().getBoard().getDiningRoom());
            }
            case "ISLAND" -> {
                cli.askIsland(modelView.getGameCopy().getGameBoard().getIslands());
                return null;
                /*
                int island = Integer.parseInt(destination);
                if (island > 0 && island < 13) {
                    action = new PickDestination(modelView.getGameCopy().getGameBoard().getIslands().get(island - 1));
                } else {
                    cli.showError("Error: wrong island! Choose a number between 1 and 12, according to " +
                            "the remaining islands");
                }

                 */
            }
            default -> {
                cli.showError("Error: type a destination for your student by choosing between 'diningroom'" +
                        "or 'island'");
            }
        }
        return action;
    }

    public PickDestination checkIsland(String islandID) {
        PickDestination action = null;
        int island = Integer.parseInt(islandID);
        if (island > 0 && island < 13) {
            int realIsland = island - 1;
            System.out.println("invio island" + realIsland);
            action = new PickDestination(modelView.getGameCopy().getGameBoard().getIslands().get(realIsland));
        } else {
            cli.showError("Error: wrong island! Choose a number between 1 and 12, according to " +
                    "the remaining islands");
        }

        return action;
    }

    public PickStudent checkStudent(String studentType) {
        PickStudent action = null;
        if (isStudentInEntrance(studentType)) {
            action = new PickStudent(new Student(toPawnType(studentType)));
        }
        return action;
    }

    public PickPawnType checkPawnType(String pawnType) {
        PickPawnType action = null;
        System.out.println("Entro in check");
        if(pawnType.toUpperCase().equalsIgnoreCase("GREEN") || pawnType.toUpperCase().equalsIgnoreCase("RED") || pawnType.toUpperCase().equalsIgnoreCase("YELLOW") || pawnType.toUpperCase().equalsIgnoreCase("PINK")  || pawnType.toUpperCase().equalsIgnoreCase("BLUE")) {
            action = new PickPawnType(toPawnType(pawnType));
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
                action = new PickCharacter(Characters.FUNGARUS);
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
                action = new PickCharacter(Characters.MAGIC_POSTMAN);
            }
            case "SPOILED_PRINCESS" -> {
                action = new PickCharacter(Characters.SPOILED_PRINCESS);
            }
            case "NONE" -> {
                action = new PickCharacter(null);
            }
            default -> action = new PickCharacter();
        }
        return action;
    }

    public void quitGame() {
        clientConnection.sendUserInput(new QuitGame());
        System.err.println("Disconnected from the server.");
        System.exit(0);
    }
}
