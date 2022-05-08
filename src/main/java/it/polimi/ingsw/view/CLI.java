package it.polimi.ingsw.view;


import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.messages.clienttoserver.WizardChoice;
import it.polimi.ingsw.messages.clienttoserver.actions.*;
import it.polimi.ingsw.messages.servertoclient.Answer;
import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.AssistantDeck;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.enumerations.Action;
import it.polimi.ingsw.model.enumerations.Assistants;
import it.polimi.ingsw.model.enumerations.Wizards;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.SchoolBoard;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.player.Table;
import it.polimi.ingsw.model.player.Tower;

import java.beans.PropertyChangeEvent;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Scanner;

import static it.polimi.ingsw.constants.Constants.*;

//CLI riceve UserAction dal Controller, switch(userAction.getPropertyName()) e nei case usa ask...()
//es: controller.send(new PickAssistant(Actions.PICKASSISTANT))
//    cli.receiveCommand(??) ???

public class CLI implements Runnable, ListenerInterface {


    private Scanner in;
    private PrintWriter output; //Panci aveva PrintStream, cambia qualcosa con PrintWriter?
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
    private ActionHandler actionHandler;
    private final PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    public CLI() {
        in = new Scanner(System.in);
        output = new PrintWriter(System.out);
        modelView = new ModelView(this, new VisualBoard());
        virtualClient.addPropertyChangeListener(parser);
        activeGame = true;
        actionHandler = new ActionHandler(this, modelView);
    }

    //scrivere diversi show per ogni parte del modello da mostrare
    public void showBoard() {
        output.println("Here's a summary of your board: ");
        output.println(Constants.ANSI_GREEN + "Green = " + modelView.getGreenStudents(modelView.getCurrentPlayer()) + " - Professor : "
                + modelView.hasGreenProfessor(modelView.getCurrentPlayer()) + ANSI_RESET);
        output.println(Constants.ANSI_RED + "Red = " + modelView.getRedStudents(modelView.getCurrentPlayer()) + " - Professor : "
                + modelView.hasRedProfessor(modelView.getCurrentPlayer()) + ANSI_RESET);
        output.println(ANSI_YELLOW + "Yellow = " + modelView.getYellowStudents(modelView.getCurrentPlayer()) + " - Professor : "
                + modelView.hasYellowProfessor(modelView.getCurrentPlayer()) + ANSI_RESET);
        output.println(ANSI_PURPLE + "Pink = " + modelView.getPinkStudents(modelView.getCurrentPlayer()) + " - Professor : "
                + modelView.hasPinkProfessor(modelView.getCurrentPlayer()) + ANSI_RESET);
        output.println(ANSI_BLUE + "Blue = " + modelView.getBlueStudents(modelView.getCurrentPlayer()) + " - Professor : "
                + modelView.hasRedProfessor(modelView.getCurrentPlayer()) + ANSI_RESET);
    }

    public String printTower(Island isl) {
        if(isl.hasTower()) {
            return "yes";
        }
        return "false";
    }

    public Integer printYellowStudentsOnIsland(Island isl) {
        int y = 0;
        for(int j=0; j < isl.getStudents().size(); j++) {
            if(isl.getStudents().get(j).getType().toString().toUpperCase().equals("YELLOW")) {
                y++;
            }
        }
        return y;
    }
    public Integer printBlueStudentsOnIsland(Island isl) {
        int b = 0;
        for(int j=0; j < isl.getStudents().size(); j++) {
            if(isl.getStudents().get(j).getType().toString().toUpperCase().equals("BLUE")) {
                b++;
            }
        }
        return b;
    }
    public Integer printPinkStudentsOnIsland(Island isl) {
        int p = 0;
        for(int j=0; j < isl.getStudents().size(); j++) {
            if(isl.getStudents().get(j).getType().toString().toUpperCase().equals("PINK")) {
                p++;
            }
        }
        return p;
    }
    public Integer printGreenStudentsOnIsland(Island isl) {
        int g = 0;
        for(int j=0; j < isl.getStudents().size(); j++) {
            if(isl.getStudents().get(j).getType().toString().toUpperCase().equals("GREEN")) {
                g++;
            }
        }
        return g;
    }
    public Integer printRedStudentsOnIsland(Island isl) {
        int r = 0;
        for(int j=0; j < isl.getStudents().size(); j++) {
            if(isl.getStudents().get(j).getType().toString().toUpperCase().equals("RED")) {
                r++;
            }
        }
        return r;
    }

    public void showIslands() {
        output.println(">Here's a little description of the islands in the game board: ");
        for (Island island : modelView.getVisualBoard().getIslandsView()) {
            output.println("Island " + island.getIslandID() + " : " + "\n" + " Students : ");
            for (Student stud : island.getStudents()) {
                output.print(stud.getType().toString() + "\n");
            }
            output.println("\nTower: " + island.getTower().getColor().toString());
        }
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

    public PrintWriter getOutput() {
        return output;
    }

    public void askMoves(AssistantCard card) {
        output.println(">Pick a number of mother nature moves between 1 and "
                + modelView.getCurrentPlayer().getChosenAssistant().getMoves());
        chosenMoves = in.next();
        virtualClient.firePropertyChange("PickMoves", null, chosenMoves);
    }

    public void printPlayerDeck() {
        for (AssistantCard card : modelView.getCurrentPlayer().getAssistantDeck().getDeck()) {
            output.print("(Name: " + card.getName() + "," + "Value: " + card.getValue() + "," + "Moves: " + card.getMoves());
        }
    }

    public void askAssistant(AssistantDeck deck) {
        output.println(">Pick an assistant from your deck by typing its name: ");
        printPlayerDeck();
        chosenAssistant = in.next();
        virtualClient.firePropertyChange("PickAssistant", null, chosenAssistant);
    }

    public void askCloud(ArrayList<CloudTile> clouds) {
        output.println(">Pick a cloud by typing its ID: ");
        chosenCloud = in.next();
        virtualClient.firePropertyChange("PickCloud", null, chosenCloud);
    }

    //chiamato dal controller, quando ritorna lo studente esso viene salvato in una variabile
    //che verrà poi usata per la chosenDestination nella userAction successiva
    public void askStudent(SchoolBoard schoolB) {
        output.println(">Pick a student from your Entrance: ");
        chosenStudent = in.next();
        virtualClient.firePropertyChange("PickStudent", null, chosenStudent);
    }

    //prende come input lo studente scelto nella richiesta precedente
    public void askDestination() {
        output.println(">Pick a destination between your DiningRoom or an island: ");
        chosenDestination = in.next();
        virtualClient.firePropertyChange("PickDestination", null, chosenDestination);
    }


    public void askCharacterCard(CharacterCard[] arr) {
        output.println(">Type the name of the Character Card you want to play: ");
        chosenCharacter = in.next();
        virtualClient.firePropertyChange("PickCharachter", null, chosenCharacter);
    }

    public void chooseExpertMode() {
        String expertModeChoice;
        System.out.println("Sono in chooseExpertMode");
        System.out.print(">");
        expertModeChoice = in.nextLine();
        System.out.println("Ho fatto l'assegnamento: della expert mode " + expertModeChoice);
        clientConnection.sendUserInput(new ExpertModeChoice(expertModeChoice));
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
    public void showGenericMessage(Answer serverAnswer) {
        output.println(serverAnswer);
    }

    public void showError(String message) {
        output.println(ANSI_RED + ">Warning! " + message + ANSI_RESET);
    }

    public void showWinMessage(Player winner) {
        if (GameHandler.getGame().getPlayersNumber() != 4) {
            output.println(">Game over! The winner is " + ANSI_CYAN + winner + ANSI_RESET);
        } else {
            output.println(">Game over! The winner is team " + ANSI_CYAN +
                    winner.getTeammateID() + ANSI_RESET);
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
        }//TODO: CICIO DA GUARDARE
        //listeners.addPropertyChangeListener("action", new ActionParser(connectionSocket, modelView));
    }


    @Override
    public void run() {
        userNicknameSetup();
    }

    //viene chiamato all'interno di propertyChange della CLI, notificata dall'Action Handler
    //TODO da mettere nell'action Handler
    public void makeAction(String serverCommand) {
        switch(serverCommand) {
            case "PICKASSISTANT" -> {
                askAssistant(modelView.getCurrentPlayer().getAssistantDeck());
            }
            case "PICKCLOUD" -> {
                askCloud(modelView.getVisualBoard().getClouds());
            }
            case "PICKDESTINATION" -> {
                askDestination();
            }
            case "PICKSTUDENT" -> {
                askStudent(modelView.getCurrentPlayer().getBoard());
            }
            case "PICKMOVESNUMBER" -> {
                askMoves(modelView.getCurrentPlayer().getChosenAssistant());
            }
            case "PICKCHARACTER" -> {
                askCharacterCard(modelView.getVisualBoard().getCharacters());
            }
            default -> {
                output.println("Action error: no such action");
            }
        }
    }

    public AssistantCard takeCard (AssistantCard card){
        for(AssistantCard c : modelView.getVisualBoard().getDeck()){
            if(card.getName() == c.getName()){
                modelView.getVisualBoard().getDeck().removeCard(card);
                return card;
            }
        }
    }

    //TODO continuare sta merda
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
                askCharacterCard(modelView.getVisualBoard().getCharacters());
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
                if(serverCommand == "PICKSTUDENT") {
                    if(modelView.getDestinationUserAction()!=null) {
                        updateStudentMove(((PickStudent) modelView.getLastUserAction()).getChosenStudent().getType().toString(),
                                ((PickDestination) modelView.getDestinationUserAction()).getDestination().toString());
                    }
                }
                updateModelView(serverCommand);
            }
            default -> System.out.println("Unknown answer from server");
        }
    }
}


