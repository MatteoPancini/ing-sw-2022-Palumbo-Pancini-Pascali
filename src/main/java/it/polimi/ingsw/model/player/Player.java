package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.AssistantDeck;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.board.Student;


import java.util.ArrayList;

public class Player {
    private String nickname;
    private AssistantDeck assistantDeck;
    private SchoolBoard board;
    private boolean isPlaying;
    private boolean isWinner;
    private Player teammate;

    public Player(String name) {
        nickname = name;
        assistantDeck = new AssistantDeck();
        board = new SchoolBoard();
        isPlaying = true;
        isWinner = false;
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

    public boolean isPlaying() {
        return isPlaying;
    };

    public boolean isWinner() {
        return isWinner;
    }

    public AssistantCard pickAssistant() { ... };

    //sceglie un numero compreso tra 1 e card.moves
    public int chooseMoves(AssistantCard card) { ... };

}
