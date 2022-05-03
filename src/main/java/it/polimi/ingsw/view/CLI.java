
package it.polimi.ingsw.view;


import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.messages.servertoclient.Answer;
import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.AssistantDeck;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.SchoolBoard;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.beans.PropertyChangeSupport;
import java.util.Scanner;

public class CLI implements Runnable {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

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


    public CLI() {
        in = new Scanner(System.in);
        output = new PrintWriter(System.out);
        modelView = new ModelView(this, new VisualBoard());
        virtualClient.addPropertyChangeListener(parser);
    }

    //scrivere diversi show per ogni parte del modello da mostrare
    public void showBoard() {
        output.println("Here's a summary of your board: ");
        output.println(ANSI_GREEN + "Green = " + modelView.getGreenStudents(modelView.getCurrentPlayer()) + " - Professor : "
                + modelView.hasGreenProfessor(modelView.getCurrentPlayer()) + ANSI_RESET);
        output.println(ANSI_RED + "Red = " + modelView.getRedStudents(modelView.getCurrentPlayer()) + " - Professor : "
                + modelView.hasRedProfessor(modelView.getCurrentPlayer()) + ANSI_RESET);
        output.println(ANSI_YELLOW + "Yellow = " + modelView.getYellowStudents(modelView.getCurrentPlayer()) + " - Professor : "
                + modelView.hasYellowProfessor(modelView.getCurrentPlayer()) + ANSI_RESET);
        output.println(ANSI_PURPLE + "Pink = " + modelView.getPinkStudents(modelView.getCurrentPlayer()) + " - Professor : "
                + modelView.hasPinkProfessor(modelView.getCurrentPlayer()) + ANSI_RESET);
        output.println(ANSI_BLUE + "Blue = " + modelView.getBlueStudents(modelView.getCurrentPlayer()) + " - Professor : "
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
        for(AssistantCard card : modelView.getCurrentPlayer().getAssistantDeck().getDeck()) {
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

    public void askStudent(SchoolBoard schoolB) {
        output.println(">Pick a student from your Entrance: ");
        chosenStudent = in.next();
        virtualClient.firePropertyChange("PickStudent", null, chosenStudent);
    }

    public void askStudentDestination() {
        output.println(">Pick a destination between your DiningRoom or an island: ");
        chosenDestination = in.next();
        virtualClient.firePropertyChange("PickDestination", null, chosenDestination);
    }

    public void askTeam() {
        output.println(">Pick your team by typing its id: ");
        chosenTeam = in.next();
        virtualClient.firePropertyChange("PickTeam", null, chosenTeam);
    }

    public void askCharacterCard(CharacterCard[] arr) {
        output.println("Type the name of the Character Card you want to play: ");
        chosenCharacter = in.next();
        virtualClient.firePropertyChange("PickCharachter", null, chosenCharacter);
    }

    public void showGenericMessage(Answer serverAnswer) {
        output.println(serverAnswer);
    }

    public void showError(String message) {
        output.println(ANSI_RED + "Warning! " + message + ANSI_RESET);
    }

    public void showWinMessage(Player winner) {
        if (GameHandler.getGame().getPlayersNumber() != 4) {
            output.println("Game over! The winner is " +  ANSI_CYAN + winner + ANSI_RESET);
        }
        else {
            output.println("Partita terminata! I vincitori sono i giocatori della squadra: " +
                    winner.getTeammateID());
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
            System.out.println(">Is it right? [yes/no] ");
            System.out.print(">");
            if (in.nextLine().equalsIgnoreCase("yes")) {
                nickCheck = true;
            } else {
                userNickname = null;
            }

            clientConnection = new ClientConnection();
            modelView.setPlayerNickname(userNickname); //decidere se dare un int id come parametro (serve per
            //setplsyernickname in quanto opera su un array che necessita get(id)
        }
    }

    @Override
    public void run() {
        userNicknameSetup();
    }
}


 */