package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.AssistantDeck;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.enumerations.Wizards;


import java.util.ArrayList;

public class Player {
    private Wizards wizard;
    private String nickname;
    private AssistantDeck assistantDeck;
    private SchoolBoard board;
    private boolean isPlaying;
    private boolean isWinner;
    private Player teammate;

    public Player(String nickname) {
        this.nickname = nickname;
        assistantDeck = new AssistantDeck();
        board = new SchoolBoard();
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

    //sceglie un numero compreso tra 1 e card.moves
    public int chooseMoves(AssistantCard card) { ... };

}
