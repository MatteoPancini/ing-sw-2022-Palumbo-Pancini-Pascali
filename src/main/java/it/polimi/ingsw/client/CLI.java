package it.polimi.ingsw.client;


import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.messages.clienttoserver.ExpertModeChoice;
import it.polimi.ingsw.messages.clienttoserver.PlayersNumberChoice;
import it.polimi.ingsw.messages.clienttoserver.WizardChoice;
import it.polimi.ingsw.messages.servertoclient.Answer;
import it.polimi.ingsw.messages.servertoclient.WizardAnswer;
import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.AssistantDeck;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.enumerations.Wizards;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.SchoolBoard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLI implements Runnable, ListenerInterface {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private final Scanner in;
    private final PrintStream out;
    private Parser parser;
    private PropertyChangeSupport virtualClient;
    private String chosenWizard;
    private String chosenNickname;
    private String chosenMoves;
    private String chosenAssistant;
    private String chosenCloud;
    private String chosenStudent;
    private String chosenDestination;
    private String chosenTeam;
    private String chosenCharacter;
    private ClientConnection clientConnection;
    private final ModelView modelView;

    private boolean activeGame;
    private final PropertyChangeSupport listeners = new PropertyChangeSupport(this);


    private ActionHandler actionHandler;


    public CLI() {
        in = new Scanner(System.in);
        out = new PrintStream(System.out);
        modelView = new ModelView(this, new VisualBoard());
        virtualClient.addPropertyChangeListener(parser);
        activeGame = true;
        actionHandler = new ActionHandler(this, modelView);
    }

    //scrivere diversi show per ogni parte del modello da mostrare
    public void showBoard() {
        out.println("Here's a summary of your board: ");
        out.println(ANSI_GREEN + "Green = " + modelView.getGreenStudents(modelView.getCurrentPlayer()) + " - Professor : "
                + modelView.hasGreenProfessor(modelView.getCurrentPlayer()) + ANSI_RESET);
        out.println(ANSI_RED + "Red = " + modelView.getRedStudents(modelView.getCurrentPlayer()) + " - Professor : "
                + modelView.hasRedProfessor(modelView.getCurrentPlayer()) + ANSI_RESET);
        out.println(ANSI_YELLOW + "Yellow = " + modelView.getYellowStudents(modelView.getCurrentPlayer()) + " - Professor : "
                + modelView.hasYellowProfessor(modelView.getCurrentPlayer()) + ANSI_RESET);
        out.println(ANSI_PURPLE + "Pink = " + modelView.getPinkStudents(modelView.getCurrentPlayer()) + " - Professor : "
                + modelView.hasPinkProfessor(modelView.getCurrentPlayer()) + ANSI_RESET);
        out.println(ANSI_BLUE + "Blue = " + modelView.getBlueStudents(modelView.getCurrentPlayer()) + " - Professor : "
                + modelView.hasRedProfessor(modelView.getCurrentPlayer()) + ANSI_RESET);
    }

    public Scanner getIn() {
        return in;
    }

    public ModelView getModelView() {
        return modelView;
    }

    public Parser getParser() {
        return parser;
    }

    public PrintStream getOutput() {
        return out;
    }

    public void askMoves(AssistantCard card) {
        out.println(">Pick a number of mother nature moves between 1 and "
                + modelView.getCurrentPlayer().getChosenAssistant().getMoves());
        chosenMoves = in.next();
        virtualClient.firePropertyChange("PickMoves", null, chosenMoves);
    }

    public void printPlayerDeck() {
        for(AssistantCard card : modelView.getCurrentPlayer().getAssistantDeck().getDeck()) {
            out.print("(Name: " + card.getName() + "," + "Value: " + card.getValue() + "," + "Moves: " + card.getMoves());
        }
    }

    public void askAssistant(AssistantDeck deck) {
        out.println(">Pick an assistant from your deck by typing its name: ");
        printPlayerDeck();
        chosenAssistant = in.next();
        virtualClient.firePropertyChange("PickAssistant", null, chosenAssistant);
    }

    public void askCloud(ArrayList<CloudTile> clouds) {
        out.println(">Pick a cloud by typing its ID: ");
        chosenCloud = in.next();
        virtualClient.firePropertyChange("PickCloud", null, chosenCloud);
    }

    public void askStudent(SchoolBoard schoolB) {
        out.println(">Pick a student from your Entrance: ");
        chosenStudent = in.next();
        virtualClient.firePropertyChange("PickStudent", null, chosenStudent);
    }

    public void askStudentDestination() {
        out.println(">Pick a destination between your DiningRoom or an island: ");
        chosenDestination = in.next();
        virtualClient.firePropertyChange("PickDestination", null, chosenDestination);
    }

    public void askTeam() {
        out.println(">Pick your team by typing its id: ");
        chosenTeam = in.next();
        virtualClient.firePropertyChange("PickTeam", null, chosenTeam);
    }

    public void askCharacterCard(CharacterCard[] arr) {
        out.println("Type the name of the Character Card you want to play: ");
        chosenCharacter = in.next();
        virtualClient.firePropertyChange("PickCharachter", null, chosenCharacter);
    }

    public void showGenericMessage(Answer serverAnswer) {
        out.println(serverAnswer);
    }

    public void showError(String message) {
        out.println(ANSI_RED + "Warning! " + message + ANSI_RESET);
    }

    public void showWinMessage(Player winner) {
        if (GameHandler.getGame().getPlayersNumber() != 4) {
            out.println("Game over! The winner is " +  ANSI_CYAN + winner + ANSI_RESET);
        }
        else {
            out.println("Partita terminata! I vincitori sono i giocatori della squadra: " +
                    winner.getIdTeam());
        }
    }


    public void userNicknameSetup() {
        String userNickname = null;
        boolean nickCheck = false;

        while (nickCheck == false) {
            do {
                System.out.println(">Please, insert your nickname: ");
                System.out.print(">");
                userNickname = in.nextLine();
            } while (userNickname == null);
            System.out.println(">Your nickname is: " + userNickname);
            System.out.println(">Is it right? [y/n] ");
            System.out.print(">");
            if (in.nextLine().equalsIgnoreCase("y")) {
                nickCheck = true;
            } else {
                userNickname = null;
            }
        }
        clientConnection = new ClientConnection();

        modelView.setPlayerNickname(userNickname);
        try {
            if (!clientConnection.setupNickname(userNickname, modelView, actionHandler)) {
                System.err.println("The entered IP/port doesn't match any active server or the server is not " + "running. Please try again!");
                System.exit(-1);
            }
            System.out.println("Socket Connection setup completed!");
        } catch (DuplicateNicknameException e) {
            //e.printStackTrace();
            userNicknameSetup();
        }//TODO M: CICIO DA GUARDARE
        listeners.addPropertyChangeListener("action", new Parser(clientConnection, modelView));
    }

    public boolean isActiveGame() {
        return activeGame;
    }

    public void actionsLoop() {
        in.reset();
        String cmd = in.nextLine();
        listeners.firePropertyChange("action", null, cmd);
    }


    public void choosePlayerNumber() {
        System.out.println("Sono in choosePlayerNumber");
        int numOfPlayer;
        while (true) {
            try {
                Scanner inputNum = new Scanner(System.in);
                System.out.print(">");
                numOfPlayer = inputNum.nextInt();
                System.out.println("Ho fatto l'assegnamento del player"); //SPOILER: NON CI ARRIVA!
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid parameter, it must be a numeric value.");
            }

        }
        clientConnection.sendUserInput(new PlayersNumberChoice(numOfPlayer));

    }

    public void chooseWizard(List<Wizards> availableWizards) {

        while (true) {
            System.out.println(">Choose your wizard!");
            System.out.print(">");
            try {
                String wizardTyped = in.nextLine();
                Wizards wizardChosen = Wizards.parseWizardInput(wizardTyped);
                if(availableWizards.contains(wizardChosen)) {
                    clientConnection.sendUserInput(new WizardChoice(wizardChosen));
                    modelView.setGameStarted(true);
                    return;
                } else {
                    System.out.println("Wizard not available!");
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid input! Please provide one of the accepted wizards.");
                System.out.print(">");
            }
        }
    }

    public void chooseExpertMode() {
        String expertModeChoice;
        System.out.println("Sono in chooseExpertMode");
        System.out.print(">");
        expertModeChoice = in.nextLine();
        System.out.println("Ho fatto l'assegnamento: della expert mode " + expertModeChoice);
        clientConnection.sendUserInput(new ExpertModeChoice(expertModeChoice));


    }

    @Override
    public void run() {
        userNicknameSetup();
        while (isActiveGame()) {

            if(modelView.isGameStarted()) {
                actionsLoop();
            }


        }
        in.close();
        out.close();
    }


    public void showServerMessage(Answer serverAnswer) {
        System.out.println(serverAnswer.getMessage());
    }


    public void initialGamePhaseHandler(String serverCommand) {
        //System.out.println("Sono entrato in initialGamePhaseHandler perchè ho letto: " + serverCommand);
        switch(serverCommand) {

            case "RequestPlayerNumber" -> {
                showServerMessage(modelView.getServerAnswer());
                choosePlayerNumber();
            }
            case "ExpertModeAnswer" -> {
                showServerMessage(modelView.getServerAnswer());
                chooseExpertMode();
            }

            case "RequestWizard"-> {
                System.out.println(((WizardAnswer) modelView.getServerAnswer()).getMessage() + "\nRemaining:");
                ((WizardAnswer) modelView.getServerAnswer()).getWizardsLeft().forEach(wizardLeft -> System.out.println(wizardLeft + ", "));
                //System.out.println("\n");
                chooseWizard(((WizardAnswer) modelView.getServerAnswer()).getWizardsLeft());
            }

            default -> out.println("Nothing to do");

        }

    }

    public void actionPhaseHandler(String serverCommand) {
        switch(serverCommand) {

            case "PICKASSISTANT" -> {

            }


        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent changeEvent) {

        String serverCommand = (changeEvent.getNewValue() != null) ? changeEvent.getNewValue().toString() : null;
        //System.out.println("PropertyChange arrivato: " + serverCommand);
        switch(changeEvent.getPropertyName()) {
            case "InitialGamePhase" -> {
                assert serverCommand != null;
                //System.out.println("Sono in property change e ho letto:" + serverCommand);
                initialGamePhaseHandler(serverCommand);
                break;
            }

            case "DynamicAnswer" -> {
                //System.out.println("Sono in propertyChange e ho letto una Dynamic Answer");
                showServerMessage(modelView.getServerAnswer());
            }

            case "ActionPhase" -> {
                assert serverCommand != null;

                makeAction(serverCommand);

            }

            case "EndActionPhase" -> {

                updateModelView(serverCommand);
            }




            default -> System.out.println("Unknown answer from server");
        }
    }


    public static void main(String[] args) {
        System.out.println("Welcome to the magic world of: ERIANTYS\nLet's start your client configuration!");
        Scanner clientInput = new Scanner(System.in);
        System.out.println(">Please, insert the server IP address:");
        System.out.print(">");
        String ipServerAddress = clientInput.nextLine();
        System.out.println(">Please, insert the server port");
        System.out.print(">");
        int serverPort = clientInput.nextInt();
        Constants.setAddress(ipServerAddress);
        Constants.setPort(serverPort);

        CLI cli = new CLI();
        cli.run();


    }


}