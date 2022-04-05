package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.enumerations.*;

public class PianificationHandler {
    private final Game game;
    private final GameBoard board;

    public PianificationHandler(Game game, GameBoard board){
        this.game = game;
        this.board = board;
    }

    public void playAssistantCard() {
        for (int i = 0; i < game.getPlayersNumber(); i++) {
            AssistantCard chosenCard = board.getLastAssistantUsed().get(i).getOwner().pickAssistant();
            board.getLastAssistantUsed().get(i).setState(CardState.PLAYED);
            board.setLastAssistantUsed(i, chosenCard);
            chosenCard.setState(CardState.IN_USE);
        }

        for (int j = 0; j < board.getLastAssistantUsed().size(); j++) {
            boolean flag = false;
            for(int k = 0; k < board.getLastAssistantUsed().size() - 1; k++) {
                if(board.getLastAssistantUsed().get(j).getValue() > board.getLastAssistantUsed().get(j + 1).getValue()) {
                    AssistantCard ac = board.getLastAssistantUsed().get(j);
                    board.setLastAssistantUsed(j, board.getLastAssistantUsed().get(j + 1));
                    board.setLastAssistantUsed(j + 1, ac);
                    flag = true;
                }
            }
            if(!flag) break;
        }
    }

}