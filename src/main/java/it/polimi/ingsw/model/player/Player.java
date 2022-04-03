package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.AssistantDeck;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.enumerations.Wizards;


import java.util.ArrayList;

public class Player {

    private final String nickname;
    private final int playerID;
    private Wizards wizard;
    private final AssistantDeck assistantDeck;
    private final SchoolBoard board;
    private boolean isPlaying;
    private boolean isWinner;
    private final int teammateID;

    public Player(String nickname, int playerID) {
        this.nickname = nickname;
        assistantDeck = new AssistantDeck();
        board = new SchoolBoard(playerID); //potremmo far corrispondere l'ID della board con il client ID così da avere lo stesso identificativo
        this.playerID = playerID;
        this.wizard = null;
    };

    public String getNickname() {
        return nickname;
    };

    public AssistantDeck getAssistantDeck() {
        return assistantDeck;
    }

    public SchoolBoard getBoard() {
        return board;
    }

    public void setBoard(SchoolBoard board) {
        this.board = board;
    }

    public AssistantCard pickAssistant() { ... };
    public void setWizard(Wizards wiz) {
        if(this.wizard == null) {
            this.wizard = wiz;
        }
    }

    public Wizards getWizard() {
        return wizard;
    }

    //sceglie un numero compreso tra 1 e card.moves
    public int chooseMoves(AssistantCard card) {};

    public void setPlayerID(int playerID) { this.playerID = playerID; }

    public int getPlayerID(){ return playerID; }

    public void setTeammateID(int teammateID) { this.teammateID = teammateID; };

    public int getTeammateID(){ return teammateID; }

}
