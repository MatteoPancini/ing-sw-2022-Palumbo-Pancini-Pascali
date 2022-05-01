package it.polimi.ingsw.view;


import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.AssistantDeck;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.SchoolBoard;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.util.Scanner;

public class CLI {
    private Scanner scanner;
    private PrintWriter output;
    private ModelView modelView;
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


    public CLI() {
        scanner = new Scanner(System.in);
        output = new PrintWriter(System.out);
        modelView = new ModelView(this, new VisualBoard());
        virtualClient.addPropertyChangeListener(parser);
    }

    //scrivere diversi show per ogni parte del modello da mostrare
    public void showModel(GameBoard modelCopy) {
        outputStream.println(???);
    }

    public Scanner getScanner() {
        return scanner;
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
        output.println(">Pick a number of mother nature moves between 1 and"
                + modelView.getCurrentPlayer().getChosenAssistant().getMoves());
        chosenMoves = scanner.next();
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
        chosenAssistant = scanner.next();
        virtualClient.firePropertyChange("PickAssistant", null, chosenAssistant);
    }

    public void askCloud(ArrayList<CloudTile> clouds) {
        output.println("Scegliere una nuvola: ");
        chosenCloud = scanner.next();
        setChanged();
        notifyObservers();
    }

    public void askStudent(SchoolBoard schoolB) {
        outputStream.println("Scegliere lo studente: ");
        chosenStudent = scanner.next();
        setChanged();
        notifyObservers();
    }

    public void askStudentDestination() {
        outputStream.println("Scegliere una destinazione tra DiningRoom o Cloud: ");
        chosenDestination = scanner.next();
        setChanged();
        notifyObservers();
    }

    public void askTeam() {
        outputStream.println("Scegliere una squadra: ");
        chosenTeam = scanner.next();
        setChanged();
        notifyObservers();
    }

    public void askCharacterCard(CharacterCard[] arr) {
        outputStream.println("Scegliere una delle 3 carte personaggio da giocare: ");
        chosenCharacter = scanner.next();
        setChanged();
        notifyObservers();
    }

    public void showGenericMessage(Answer serverAnswer) {
        outputStream.println(serverAnswer);
    }

    public void showError(String message) {
        outputStream.println("Attenzione! Errore: " + message);
    }

    public void showWinMessage(Player winner) {
        if (GameHandler.getGame().getPlayersNumber() != 4) {
            output.println("Partita terminata! Il vincitore è: " + winner);
        }
        else {
            output.println("Partita terminata! I vincitori sono i giocatori della squadra: " +
                    winner.getTeammateID());
        }
    }

}


 */