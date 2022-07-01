package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.messages.clienttoserver.QuitGame;
import it.polimi.ingsw.messages.clienttoserver.actions.*;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.enumerations.*;
import it.polimi.ingsw.model.player.Table;

/**
 * Class that checks if the user inputs are correct and, if so, it returns the corresponding user action to the parser
 */
public class InputChecker {
    private final ClientConnection clientConnection;
    private final ModelView modelView;
    private final CLI cli;

    /**
     * Constructor that generates a new instance of the class only for the CLI
     * @param modelView model view instance
     * @param cli cli instance of the game
     * @param connection connection of the game
     */
    public InputChecker(ModelView modelView, CLI cli, ClientConnection connection) {
        this.modelView = modelView;
        this.cli = cli;
        this.clientConnection = connection;
    }


    /**
     * Method checkAssistant checks if the chosen assistant by the player exists or can be played,
     * then it returns the user action to the parser
     * @param input  assistant chosen
     * @return user action PickAssistant
     */
    public PickAssistant checkAssistant(String input) {
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

                if (modelView.getGameCopy().canPlayAssistant(Assistants.FOX)) {
                    action = new PickAssistant(Action.PICK_ASSISTANT, Assistants.FOX);
                } else {
                    cli.showError("Error: you chose an assistant card already played by another player!");
                    cli.askAssistant();
                }
            }

            case "QUIT" -> quitGame();
            default -> {
                cli.showError("Error: you chose an assistant card already played by another player!");
                cli.askAssistant();
            }
        }
        return action;
    }

    /**
     * Method checkMoves checks if the user input for number of mother nature moves is correct,
     * and if so it sends it to server
     * @param input user input
     * @return user action PickMovesNumber
     */
    public PickMovesNumber checkMoves(String input) {
        PickMovesNumber action = null;
        int maxMoves;
        if(modelView.isMagicPostmanAction()) {
            maxMoves = (modelView.getGameCopy().getCurrentPlayer().getChosenAssistant().getMoves() + 2);
        } else {
            maxMoves = modelView.getGameCopy().getCurrentPlayer().getChosenAssistant().getMoves();
        }
        try {
            int moves = Integer.parseInt(input);
            if (moves > 0 && moves <= maxMoves) {
                action = new PickMovesNumber(moves);
                if(modelView.isMagicPostmanAction()) {
                    modelView.setMagicPostmanAction(false);
                }
            } else {
                cli.askMoves();
            }
        } catch (NumberFormatException e) {
            cli.showError("Error: NumberFormatException. Please insert a number!");
            cli.askMoves();
        }

        return action;
    }

    /**
     * Method toPawnType turns an input into a pawn type by reading its initial letter
     * @param input user input
     * @return PawnType enum
     */
    public PawnType toPawnType(String input) {
        PawnType type = null;
        try {
            char firstLetter = input.charAt(0);
            switch(Character.toUpperCase(firstLetter)) {
                case 'Y' -> type = PawnType.YELLOW;
                case 'B' -> type = PawnType.BLUE;
                case 'P' -> type = PawnType.PINK;
                case 'R' -> type = PawnType.RED;
                case 'G' -> type = PawnType.GREEN;
            }
        } catch(StringIndexOutOfBoundsException e) {
            return null;
        }

        return type;
    }

    /**
     * Method isStudentInEntrance returns true if the input student is in the current player's entrance
     * @param input student type
     * @return boolean value
     */
    public boolean isStudentInEntrance(String input) {
        PawnType type = toPawnType(input);
        if(type == null) return false;
        for(int i = 0; i < modelView.getGameCopy().getCurrentPlayer().getBoard().getEntrance().getStudents().size(); i++) {
            if(modelView.getGameCopy().getCurrentPlayer().getBoard().getEntrance().getStudents().get(i).getType().equals(type)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Method isStudentInJester returns true if the input student is on the character card Jester
     * @param input student type
     * @return boolean value
     */
    public boolean isStudentInJester(String input) {
        PawnType type = toPawnType(input);
        if(type == null) return false;
        for(CharacterCard c : modelView.getGameCopy().getGameBoard().getPlayableCharacters()) {
            if(c.getName() == Characters.JESTER) {
                for(Student s : c.getStudents()) {
                    if(s.getType().equals(type)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Method isStudentInPrincess returns true if the input student is on the character card Spoiled Princess
     * @param input student type
     * @return boolean value
     */
    public boolean isStudentInPrincess(String input) {
        PawnType type = toPawnType(input);
        if(type == null) return false;
        for(CharacterCard c : modelView.getGameCopy().getGameBoard().getPlayableCharacters()) {
            if(c.getName() == Characters.SPOILED_PRINCESS) {
                for(Student s : c.getStudents()) {
                    if(s.getType().equals(type)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Method isStudentInMonk returns true if the input student is on the character card Monk
     * @param input student type
     * @return boolean value
     */
    public boolean isStudentInMonk(String input) {
        PawnType type = toPawnType(input);
        if(type == null) return false;
        for(CharacterCard c : modelView.getGameCopy().getGameBoard().getPlayableCharacters()) {
            if(c.getName() == Characters.MONK) {
                for(Student s : c.getStudents()) {
                    if(s.getType().equals(type)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * Method checkStudentInDiningRoom returns true if the input student is in the current player's dining room
     * @param input student type
     * @return boolean value
     */
    public boolean checkStudentInDiningRoom(String input) {
        PawnType type = toPawnType(input);
        if(type == null) return false;
        for(Table t : modelView.getGameCopy().getCurrentPlayer().getBoard().getDiningRoom().getDiningRoom()) {
            if(t.getTableStudentsNum() > 0) {
                if(t.getDiningTable().get(0).getBoardCellType() == type) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Method checkDestination checks if the destination received from the user is dining room or an island,
     * then it sends it to server
     * @param destination input
     * @return user action PickDestination
     */
    public PickDestination checkDestination(String destination) {
        PickDestination action = null;
        switch(destination.toUpperCase()) {
            case "DININGROOM" -> action = new PickDestination(modelView.getGameCopy().getCurrentPlayer().getBoard().getDiningRoom());
            case "ISLAND" -> {
                cli.askIsland();
                return null;
            }
            case "QUIT" -> quitGame();
            default -> {
                cli.showError("Error: type a destination for your student by choosing between 'diningroom'" +
                        "or 'island'");
                cli.askDestination();
            }
        }
        return action;
    }

    /**
     * Method checkIsland checks if the island ID typed by the user is am valid island,
     * then it sends it to server
     * @param islandID input
     * @return user action PickDestination
     */
    public PickDestination checkIsland(String islandID) {
        PickDestination action = null;
        try {
            int island = Integer.parseInt(islandID);
            if (island > 0 && island < 13) {
                for(int i=0; i<modelView.getGameCopy().getGameBoard().getIslands().size(); i++) {
                    if(island == modelView.getGameCopy().getGameBoard().getIslands().get(i).getIslandID()) {
                        action = new PickDestination(modelView.getGameCopy().getGameBoard().getIslands().get(i));
                        break;
                    }
                }
                if(action == null) {
                    cli.showError("Error: merged island! Choose a valid number ID");
                    cli.askIsland();
                }

            } else {
                cli.showError("Error: wrong island! Choose a number between 1 and 12, according to " +
                        "the remaining islands");
                cli.askIsland();
            }
        } catch(NumberFormatException e) {
            cli.showError("Error: NumberFormatException. Please insert a number!");
            cli.askIsland();
        }

        return action;
    }

    /**
     * Method checkStudent checks if the student received from the user is a valid student in entrance or on character cards,
     * then it sends it to server
     * @param studentType input
     * @return user action PickStudent
     */
    public PickStudent checkStudent(String studentType) {
        PickStudent action = null;
        if(modelView.isJesterAction()) {
            if(modelView.getCharacterAction() % 2 == 0) {
                if(isStudentInJester(studentType)) {
                    PawnType type = toPawnType(studentType);
                    if(type == null) {
                        for(CharacterCard c : modelView.getGameCopy().getGameBoard().getPlayableCharacters()) {
                            if(c.getName() == Characters.JESTER) {
                                cli.showError("PawnType error! Try again!");
                                cli.askStudentJester(c);
                                break;
                            }
                        }
                    } else {
                        action = new PickStudent(new Student(type));
                    }
                } else {
                    cli.showError("No such student in Jester! Please enter a valid student!");
                    for (CharacterCard c : modelView.getGameCopy().getGameBoard().getPlayableCharacters()) {
                        if (c.getName() == Characters.JESTER) {
                            cli.askStudentJester(c);
                            break;
                        }
                    }
                }
            } else {
                if (isStudentInEntrance(studentType)) {
                    PawnType type = toPawnType(studentType);
                    if(type == null) {
                        cli.askStudent();
                    } else {
                        action = new PickStudent(new Student(type));
                    }
                } else {
                    cli.showError("No such student in your entrance! Please enter a valid student!");
                    cli.askStudent();
                }
            }
        } else if(modelView.isMinestrelAction()) {
            if(modelView.getCharacterAction() % 2 == 0) {
                if(checkStudentInDiningRoom(studentType)) {
                    action = new PickStudent(new Student(toPawnType(studentType)));
                } else {
                    cli.showError("Invalid pawn type! Please insert a valid pawn type");
                    cli.askPawnType();
                }
            } else {
                if (isStudentInEntrance(studentType)) {
                    PawnType type = toPawnType(studentType);
                    if(type == null) {
                        cli.askStudent();
                    } else {
                        action = new PickStudent(new Student(type));
                    }
                }
            }
        } else if(modelView.isPrincessAction()) {
            if(isStudentInPrincess(studentType)) {
                PawnType type = toPawnType(studentType);
                if(type == null) {
                    for(CharacterCard c : modelView.getGameCopy().getGameBoard().getPlayableCharacters()) {
                        if(c.getName() == Characters.SPOILED_PRINCESS) {
                            cli.showError("PawnType error! Try again!");
                            cli.askStudentPrincess(c);
                            break;
                        }
                    }
                } else {
                    action = new PickStudent(new Student(type));
                }
            } else {
                cli.showError("No such student in Jester! Please enter a valid student!");
                for (CharacterCard c : modelView.getGameCopy().getGameBoard().getPlayableCharacters()) {
                    if (c.getName() == Characters.JESTER) {
                        cli.askStudentJester(c);
                        break;
                    }
                }
            }

        } else if(modelView.isMonkAction()) {
            if(isStudentInMonk(studentType)) {
                PawnType type = toPawnType(studentType);
                if(type == null) {
                    for(CharacterCard c : modelView.getGameCopy().getGameBoard().getPlayableCharacters()) {
                        if(c.getName() == Characters.MONK) {
                            cli.showError("PawnType error! Try again!");
                            cli.askStudentMonk(c);
                            break;
                        }
                    }
                } else {
                    action = new PickStudent(new Student(type));
                }
            }  else {
                cli.showError("No such student in monk! Please enter a valid student!");
                for(CharacterCard c : modelView.getGameCopy().getGameBoard().getPlayableCharacters()) {
                    if(c.getName() == Characters.MONK) {
                        cli.askStudentMonk(c);
                        break;
                    }
                }
            }
        } else {
            if (isStudentInEntrance(studentType)) {
                PawnType type = toPawnType(studentType);
                if(type == null) {
                    cli.askStudent();
                } else {
                    action = new PickStudent(new Student(type));
                }
            } else {
                cli.showError("No such student in your entrance! Please enter a valid student!");
                cli.askStudent();
            }
        }
        return action;

    }

    /**
     * Method checkPawnType checks if the student typed by the user is a valid one, it's used for character cards
     * @param pawnType input
     * @return user action PickPawnType
     */
    public PickPawnType checkPawnType(String pawnType) {
        PickPawnType action = null;
        if(pawnType.equalsIgnoreCase("GREEN") || pawnType.equalsIgnoreCase("RED") || pawnType.equalsIgnoreCase("YELLOW") || pawnType.equalsIgnoreCase("PINK")  || pawnType.equalsIgnoreCase("BLUE")) {
            action = new PickPawnType(toPawnType(pawnType));
        } else {
            cli.showError("Invalid pawn type! Please insert a valid pawn type");
            cli.askPawnType();
        }
        return action;
    }

    /**
     * Method checkCloud checks if the cloud typed by the user is a valid one,
     * then it sends it to server
     * @param input cloud ID
     * @return user action PickCloud
     */
    public PickCloud checkCloud(String input) {
        PickCloud action = null;
        int cloudID;
        try {
            cloudID = Integer.parseInt(input);
            if(modelView.getGameCopy().getGameBoard().getClouds().get(cloudID - 1).getStudents() != null) {
                action = new PickCloud(modelView.getGameCopy().getGameBoard().getClouds().get(cloudID - 1));
            }
            else {
                cli.showError("Error: the cloud has already been taken! Choose another one");
                cli.askCloud();
            }
        } catch(NumberFormatException e) {
            cli.showError("Error: NumberFormatException. Please insert a number!");
            cli.askCloud();
        }

        return action;

    }

    /**
     * Method checkCharacter checks if the character typed by the user is a valid one,
     * then it sends it to server
     * @param input character name
     * @return user action PickCharacter
     */
    public PickCharacter checkCharacter(String input) {
        PickCharacter action = null;
        switch(input.toUpperCase()) {
            case "HERALD" -> {
                if(modelView.getGameCopy().getCurrentPlayer().getMyCoins() >= 3) {
                    action = new PickCharacter(Characters.HERALD);
                } else {
                    cli.showError("You don't have enough coins... choose another card or none");
                    cli.askCharacterCard();
                }

            }
            case "KNIGHT" -> {
                if(modelView.getGameCopy().getCurrentPlayer().getMyCoins() >= 2) {
                    action = new PickCharacter(Characters.KNIGHT);
                } else {
                    cli.showError("You don't have enough coins... choose another card or none");
                    cli.askCharacterCard();
                }
            }
            case "CENTAUR" -> {
                if(modelView.getGameCopy().getCurrentPlayer().getMyCoins() >= 3) {
                    action = new PickCharacter(Characters.CENTAUR);
                } else {
                    cli.showError("You don't have enough coins... choose another card or none");
                    cli.askCharacterCard();
                }
            }
            case "FARMER" -> {
                if(modelView.getGameCopy().getCurrentPlayer().getMyCoins() >= 2) {
                    action = new PickCharacter(Characters.FARMER);
                } else {
                    cli.showError("You don't have enough coins... choose another card or none");
                    cli.askCharacterCard();
                }
            }
            case "FUNGARUS" -> {
                if(modelView.getGameCopy().getCurrentPlayer().getMyCoins() >= 3) {
                    action = new PickCharacter(Characters.FUNGARUS);
                } else {
                    cli.showError("You don't have enough coins... choose another card or none");
                    cli.askCharacterCard();
                }
            }
            case "JESTER" -> {
                if(modelView.getGameCopy().getCurrentPlayer().getMyCoins() >= 1) {
                    action = new PickCharacter(Characters.JESTER);
                } else {
                    cli.showError("You don't have enough coins... choose another card or none");
                    cli.askCharacterCard();
                }
            }
            case "THIEF" -> {
                if(modelView.getGameCopy().getCurrentPlayer().getMyCoins() >= 3) {
                    action = new PickCharacter(Characters.THIEF);
                } else {
                    cli.showError("You don't have enough coins... choose another card or none");
                    cli.askCharacterCard();
                }
            }
            case "MINESTREL" -> {
                if(modelView.getGameCopy().getCurrentPlayer().getMyCoins() >= 1) {
                    action = new PickCharacter(Characters.MINESTREL);
                } else {
                    cli.showError("You don't have enough coins... choose another card or none");
                    cli.askCharacterCard();
                }
            }
            case "MONK" -> {
                if(modelView.getGameCopy().getCurrentPlayer().getMyCoins() >= 1) {
                    action = new PickCharacter(Characters.MONK);
                } else {
                    cli.showError("You don't have enough coins... choose another card or none");
                    cli.askCharacterCard();
                }
            }
            case "GRANNY_HERBS" -> {
                if(modelView.getGameCopy().getCurrentPlayer().getMyCoins() >= 2) {
                    action = new PickCharacter(Characters.GRANNY_HERBS);
                } else {
                    cli.showError("You don't have enough coins... choose another card or none");
                    cli.askCharacterCard();
                }
            }
            case "MAGIC_POSTMAN" -> {
                if(modelView.getGameCopy().getCurrentPlayer().getMyCoins() >= 1) {
                    action = new PickCharacter(Characters.MAGIC_POSTMAN);
                } else {
                    cli.showError("You don't have enough coins... choose another card or none");
                    cli.askCharacterCard();
                }
            }
            case "SPOILED_PRINCESS" -> {
                if(modelView.getGameCopy().getCurrentPlayer().getMyCoins() >= 2) {
                    action = new PickCharacter(Characters.SPOILED_PRINCESS);
                } else {
                    cli.showError("You don't have enough coins... choose another card or none");
                    cli.askCharacterCard();
                }
            }
            case "NONE" -> action = new PickCharacter(null);

            case "QUIT" -> quitGame();
            default -> action = null;
        }
        return action;
    }

    /**
     * Method quitGame disconnects the game from the server
     */
    public void quitGame() {
        clientConnection.sendUserInput(new QuitGame());
        System.err.println("Disconnected from the server.");
        System.exit(0);
    }
}
