package it.polimi.ingsw.view;


import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.messages.clienttoserver.actions.UserAction;
import it.polimi.ingsw.messages.servertoclient.Answer;
import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.AssistantDeck;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.enumerations.Action;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.SchoolBoard;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.player.Tower;

import java.beans.PropertyChangeEvent;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.util.Scanner;

import static it.polimi.ingsw.constants.Constants.*;

//CLI riceve UserAction dal Controller, switch(userAction.getPropertyName()) e nei case usa ask...()
//es: controller.send(new PickAssistant(Actions.PICKASSISTANT))
//    cli.receiveCommand(??) ???

public class CLI implements Runnable {


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

    /* public void showIslands() {
        output.println(">Here's a little description of the islands in the game board: ") ;
        for(Island island : modelView.getVisualBoard().getIslandsView()) {
            output.println("(ID: " + island.getIslandID() + ", " + "Tower: " + printTower(island) + ", " +
                    "Students: " + "\nYellow = " + printYellowStudentsOnIsland(island).toString() + "\nBlue = "
                    + printBlueStudentsOnIsland(island).toString() + "\nPink = " + printPinkStudentsOnIsland(island).toString()
                    + "\nGreen = " + printGreenStudentsOnIsland(island).toString() +
                    "\nRed = ") + printRedStudentsOnIsland(island).toString());
        }
    } */

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

    public void askStudent(SchoolBoard schoolB) {
        output.println(">Pick a student from your Entrance: ");
        chosenStudent = in.next();
        virtualClient.firePropertyChange("PickStudent", null, chosenStudent);
    }

    public void askDestination() {
        output.println(">Pick a destination between your DiningRoom or an island: ");
        chosenDestination = in.next();
        virtualClient.firePropertyChange("PickDestination", null, chosenDestination);
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
            System.out.println(">Is it right? [yes/no] ");
            System.out.print(">");
            if (in.nextLine().equalsIgnoreCase("yes")) {
                nickCheck = true;
            } else {
                userNickname = null;
            }

            clientConnection = new ClientConnection();
            modelView.setPlayerNickname(userNickname);
        }
    }


    @Override
    public void run() {
        userNicknameSetup();
    }

    //TODO gestire l'invio di messaggi di tipo UserAction dal controller e la ricezione dalla CLI e il relativo utilizzo
    public void executeCommand(Action cmd) {
        switch(cmd.toString().toUpperCase()) {
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
}
