package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.clienttoserver.actions.Action;
import it.polimi.ingsw.messages.servertoclient.RequestAction;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.enumerations.PawnType;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;


//TODO M: da capire se fare turnController subito qui oppure dopo aver fatto l'azione nel turnController
//          magari settando un boolean CharacterEffect
public class ExpertController {
    private final Game game;
    private final GameBoard board;
    private final TurnController turnController;


    public ExpertController(Game game, GameBoard board, TurnController turnController) {
        this.game = game;
        this.board = board;
        this.turnController = turnController;
    }

    public void heraldEffect() {
        RequestAction islandDestination = new RequestAction(Action.PICK_ISLAND);
        turnController.getGameHandler().sendSinglePlayer(islandDestination, turnController.getCurrentPlayer().getPlayerID());


        turnController.askMotherNatureMoves();
    }

    public void knightEffect() {
        turnController.getCurrentPlayer().setIslandInfluence(2);

        turnController.askMotherNatureMoves();

    }

    public void centaurEffect() {
        turnController.setCentaurEffect(true);

        turnController.askMotherNatureMoves();


    }

    public void farmerEffect() {
        ArrayList<PawnType> pawns = new ArrayList<>();
        pawns.add(PawnType.BLUE);
        pawns.add(PawnType.GREEN);
        pawns.add(PawnType.RED);
        pawns.add(PawnType.YELLOW);
        pawns.add(PawnType.PINK);

        for(PawnType type : pawns) {
            for(Player p : game.getActivePlayers()) {
                if(!p.equals(game.getCurrentPlayer())) {
                    if(game.getCurrentPlayer().getBoard().getDiningRoom().getDiningRoom().get(type.getPawnID()).getTable().size() == p.getBoard().getDiningRoom().getDiningRoom().get(type.getPawnID()).getTable().size() && !game.getCurrentPlayer().getBoard().getProfessorTable().getCellByColor(type).hasProfessor()) {
                        game.getCurrentPlayer().getBoard().getProfessorTable().getCellByColor(type).setProfessor(game.getGameBoard().getProfessorByColor(type));
                        p.getBoard().getProfessorTable().getCellByColor(type).resetProfessor();
                    }
                }
            }
        }

    }


    public void fungarusEffect() {
        turnController.setFungarusEffect(true);

        //TODO M -> FAI VEDERE A CICIO PER AGGIUNGERE ACTION
        RequestAction islandDestination = new RequestAction(Action.PICK_PAWN_TYPE);
        turnController.getGameHandler().sendSinglePlayer(islandDestination, turnController.getCurrentPlayer().getPlayerID());

    }

    public void jesterEffect() {

        //TODO M -> CHIEDI A CICIO COME FAR VEDERE GLI STUDENTI IN UNA CARTA


    }

    //TODO M -> finire le actions
    public void thiefEffect() {

    }

    public void minestrelEffect() {

    }

    public void monkEffect() {

    }

    public void grannyHerbsEffect() {

    }

    public void magicPostmanEffect() {

    }

    public void spoiledPrincessEffect() {

    }


}
