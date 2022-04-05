package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.board.cards.AssistantCard;
import it.polimi.ingsw.model.board.cards.AssistantDeck;
import it.polimi.ingsw.model.board.cards.CharacterCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.SchoolBoard;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

public class View extends Observable {
    private Scanner scanner;
    private PrintWriter outputStream;
    private String chosenWizard;
    private String chosenNickname;
    private String chosenMoves;
    private String chosenAssistant;
    private String chosenCloud;
    private String chosenStudent;
    private String chosenDestination;
    private String chosenTeam;
    private String chosenCharacter;

    //stampa a schermo la GameBoard (tutte? la propria?)
    public void showModel(GameBoard modelCopy) {
        outputStream.println(???);
    }

    public void update(Observable o, String input) {
        //azioni da eseguire quando il model notifica un suo cambiamento
        showModel(GameHandler.getGameBoardCopy());
    }

    public void askNickname() {
        outputStream.println("Inserire un nickname: ");
        chosenNickname = scanner.next();
        setChanged();
        notifyObservers(chosenNickname);
    }

    public void askWizard() {
        outputStream.println("Scegliere un mago: ");
        chosenWizard = scanner.next();
        setChanged();
        notifyObservers();
    }

    public void askMoves(AssistantCard card) {
        outputStream.println("Scegliere un numero di mosse: ");
        chosenMoves= scanner.next();
        setChanged();
        notifyObservers();
    }

    public void askAssistant(AssistantDeck deck) {
        outputStream.println("Scegliere un assistente dal deck: ");
        chosenAssistant = scanner.next();
        setChanged();
        notifyObservers();
    }

    public void askCloud(ArrayList<CloudTile> clouds) {
        outputStream.println("Scegliere un assistente dal deck: ");
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

    public void showGenericMessage(String message) {
        outputStream.println(message);
    }

    public void showError(String message) {
        outputStream.println("Attenzione! Errore: " + message);
    }

    public void showWinMessage(Player winner) {
        if (GameHandler.getGame().getPlayersNumber() != 4) {
            outputStream.println("Partita terminata! Il vincitore è: " + winner);
        }
        else {
            outputStream.println("Partita terminata! I vincitori sono i giocatori della squadra: " +
                    winner.getTeammateID());
        }
    }

}
