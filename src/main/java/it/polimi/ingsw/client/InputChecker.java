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
        PickAssistant action = null;
        switch (input.toUpperCase()) {
            case "EAGLE" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.EAGLE)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.EAGLE);
                } else {
                    cli.showError("Error: you chose an assistant card already played by another player!");
                    cli.askAssistant();
                }
            }
            case "DOG" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.DOG)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.DOG);
                } else {
                    cli.showError("Error: you chose an assistant card already played by another player!");
                    cli.askAssistant();
                }
            }
            case "ELEPHANT" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.ELEPHANT)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.ELEPHANT);
                } else {
                    cli.showError("Error: you chose an assistant card already played by another player!");
                    cli.askAssistant();
                }
            }
            case "CAT" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.CAT)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.CAT);
                } else {
                    cli.showError("Error: you chose an assistant card already played by another player!");
                    cli.askAssistant();
                }
            }
            case "CHEETAH" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.CHEETAH)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.CHEETAH);
                } else {
                    cli.showError("Error: you chose an assistant card already played by another player!");
                    cli.askAssistant();
                }
            }
            case "LIZARD" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.LIZARD)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.LIZARD);
                } else {
                    cli.showError("Error: you chose an assistant card already played by another player!");
                    cli.askAssistant();
                }
            }
            case "OCTOPUS" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.OCTOPUS)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.OCTOPUS);
                } else {
                    cli.showError("Error: you chose an assistant card already played by another player!");
                    cli.askAssistant();
                }
            }
            case "OSTRICH" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.OSTRICH)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.OSTRICH);
                } else {
                    cli.showError("Error: you chose an assistant card already played by another player!");
                    cli.askAssistant();
                }
            }
            case "TURTLE" -> {
                if (modelView.getGameCopy().canPlayAssistant(Assistants.TURTLE)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.TURTLE);
                } else {
                    cli.showError("Error: you chose an assistant card already played by another player!");
                    cli.askAssistant();
                }
            }
            case "FOX" -> {
                System.out.println("Entro in foxx");

                if (modelView.getGameCopy().canPlayAssistant(Assistants.FOX)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.FOX);
                } else {
                    cli.showError("Error: you chose an assistant card already played by another player!");
                    cli.askAssistant();
                }
            }
            default -> action = new PickAssistant();
        }
        return action;
    }

    public PickMovesNumber checkMoves(String input) {
        PickMovesNumber action = null;
        int maxMoves;
        System.out.println("My input: " + input);
        if(modelView.isMagicPostmanAction()) {
            maxMoves = modelView.getGameCopy().getCurrentPlayer().getChosenAssistant().getMoves() + 2;
            modelView.setMagicPostmanAction(false);
        } else {
            maxMoves = modelView.getGameCopy().getCurrentPlayer().getChosenAssistant().getMoves();
        }
        try {
            int moves = Integer.parseInt(input);
            System.out.println("Parso " + moves);
            if (moves > 0 && moves <= maxMoves) {
                action = new PickMovesNumber(moves);
            }
        } catch (NumberFormatException e) {
            cli.showError("Error: NumberFormatException. Please insert a number!");
            cli.askMoves(modelView.getGameCopy().getCurrentPlayer().getChosenAssistant());
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
        //System.out.println("Tipo passato: " + type.toString());
        for(int i = 0; i < modelView.getGameCopy().getCurrentPlayer().getBoard().getEntrance().getStudents().size(); i++) {
            //System.out.println("Tipo letto: " + modelView.getGameCopy().getCurrentPlayer().getBoard().getEntrance().getStudents().get(i).getType().toString());

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
                cli.askDestination();
            }
        }
        return action;
    }

    public PickDestination checkIsland(String islandID) {
        PickDestination action = null;
        try {
            int island = Integer.parseInt(islandID);
            if (island > 0 && island < 13) {
                int realIsland = island - 1;
                System.out.println("invio island" + realIsland);
                action = new PickDestination(modelView.getGameCopy().getGameBoard().getIslands().get(realIsland));
            } else {
                cli.showError("Error: wrong island! Choose a number between 1 and 12, according to " +
                        "the remaining islands");
                cli.askIsland(modelView.getGameCopy().getGameBoard().getIslands());
            }
        } catch(NumberFormatException e) {
            cli.showError("Error: NumberFormatException. Please insert a number!");
            cli.askIsland(modelView.getGameCopy().getGameBoard().getIslands());
        }

        return action;
    }

    public PickStudent checkStudent(String studentType) {
        PickStudent action = null;
        if (isStudentInEntrance(studentType)) {
            action = new PickStudent(new Student(toPawnType(studentType)));
        } else {
            cli.askStudent(modelView.getGameCopy().getCurrentPlayer().getBoard());
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
        int cloudID;
        try {
            cloudID = Integer.parseInt(input);
            //ricordare che funziona solo se rimuovo gli studenti dalla nuvola una volta scelta
            //e che le clouds hanno ID che parte da 0 (per combaciare con l'indice dell'arraylist)
            if(modelView.getGameCopy().getGameBoard().getClouds().get(cloudID - 1).getStudents() != null) {
                action = new PickCloud(modelView.getGameCopy().getGameBoard().getClouds().get(cloudID - 1));
            }
            else {
                cli.showError("Error: the cloud has already been taken! Choose another one");
                cli.askCloud(modelView.getGameCopy().getGameBoard().getClouds());
            }
        } catch(NumberFormatException e) {
            cli.showError("Error: NumberFormatException. Please insert a number!");
            cli.askCloud(modelView.getGameCopy().getGameBoard().getClouds());
        }

        return action;

    }

    public PickCharacter checkCharacter(String input) {
        PickCharacter action = null;
        switch(input.toUpperCase()) {
            case "HERALD" -> {
                if(modelView.getGameCopy().getCurrentPlayer().getMyCoins() >= 3) {
                    action = new PickCharacter(Characters.HERALD);
                } else {
                    cli.showError("You don't have enough coins... choose another card or none");
                    cli.askCharacterCard(modelView.getGameCopy().getGameBoard().getPlayableCharacters());
                }

            }
            case "KNIGHT" -> {
                if(modelView.getGameCopy().getCurrentPlayer().getMyCoins() >= 2) {
                    action = new PickCharacter(Characters.KNIGHT);
                } else {
                    cli.showError("You don't have enough coins... choose another card or none");
                    cli.askCharacterCard(modelView.getGameCopy().getGameBoard().getPlayableCharacters());
                }
            }
            case "CENTAUR" -> {
                if(modelView.getGameCopy().getCurrentPlayer().getMyCoins() >= 3) {
                    action = new PickCharacter(Characters.CENTAUR);
                } else {
                    cli.showError("You don't have enough coins... choose another card or none");
                    cli.askCharacterCard(modelView.getGameCopy().getGameBoard().getPlayableCharacters());
                }
            }
            case "FARMER" -> {
                if(modelView.getGameCopy().getCurrentPlayer().getMyCoins() >= 2) {
                    action = new PickCharacter(Characters.FARMER);
                } else {
                    cli.showError("You don't have enough coins... choose another card or none");
                    cli.askCharacterCard(modelView.getGameCopy().getGameBoard().getPlayableCharacters());
                }
            }
            case "FUNGARUS" -> {
                if(modelView.getGameCopy().getCurrentPlayer().getMyCoins() >= 3) {
                    action = new PickCharacter(Characters.FUNGARUS);
                } else {
                    cli.showError("You don't have enough coins... choose another card or none");
                    cli.askCharacterCard(modelView.getGameCopy().getGameBoard().getPlayableCharacters());
                }
            }
            case "JESTER" -> {
                if(modelView.getGameCopy().getCurrentPlayer().getMyCoins() >= 1) {
                    action = new PickCharacter(Characters.JESTER);
                } else {
                    cli.showError("You don't have enough coins... choose another card or none");
                    cli.askCharacterCard(modelView.getGameCopy().getGameBoard().getPlayableCharacters());
                }
            }
            case "THIEF" -> {
                if(modelView.getGameCopy().getCurrentPlayer().getMyCoins() >= 3) {
                    action = new PickCharacter(Characters.THIEF);
                } else {
                    cli.showError("You don't have enough coins... choose another card or none");
                    cli.askCharacterCard(modelView.getGameCopy().getGameBoard().getPlayableCharacters());
                }
            }
            case "MINESTREL" -> {
                if(modelView.getGameCopy().getCurrentPlayer().getMyCoins() >= 1) {
                    action = new PickCharacter(Characters.MINESTREL);
                } else {
                    cli.showError("You don't have enough coins... choose another card or none");
                    cli.askCharacterCard(modelView.getGameCopy().getGameBoard().getPlayableCharacters());
                }
            }
            case "MONK" -> {
                if(modelView.getGameCopy().getCurrentPlayer().getMyCoins() >= 1) {
                    action = new PickCharacter(Characters.MONK);
                } else {
                    cli.showError("You don't have enough coins... choose another card or none");
                    cli.askCharacterCard(modelView.getGameCopy().getGameBoard().getPlayableCharacters());
                }
            }
            case "GRANNY_HERBS" -> {
                if(modelView.getGameCopy().getCurrentPlayer().getMyCoins() >= 2) {
                    action = new PickCharacter(Characters.GRANNY_HERBS);
                } else {
                    cli.showError("You don't have enough coins... choose another card or none");
                    cli.askCharacterCard(modelView.getGameCopy().getGameBoard().getPlayableCharacters());
                }
            }
            case "MAGIC_POSTMAN" -> {
                if(modelView.getGameCopy().getCurrentPlayer().getMyCoins() >= 1) {
                    action = new PickCharacter(Characters.MAGIC_POSTMAN);
                } else {
                    cli.showError("You don't have enough coins... choose another card or none");
                    cli.askCharacterCard(modelView.getGameCopy().getGameBoard().getPlayableCharacters());
                }
            }
            case "SPOILED_PRINCESS" -> {
                if(modelView.getGameCopy().getCurrentPlayer().getMyCoins() >= 2) {
                    action = new PickCharacter(Characters.SPOILED_PRINCESS);
                } else {
                    cli.showError("You don't have enough coins... choose another card or none");
                    cli.askCharacterCard(modelView.getGameCopy().getGameBoard().getPlayableCharacters());
                }
            }
            case "NONE" -> {
                action = new PickCharacter(null);
            }
            default -> action = null;
        }
        return action;
    }

    public void quitGame() {
        clientConnection.sendUserInput(new QuitGame());
        System.err.println("Disconnected from the server.");
        System.exit(0);
    }
}
