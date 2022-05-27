package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.clienttoserver.actions.Action;
import it.polimi.ingsw.messages.servertoclient.RequestAction;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.enumerations.PawnType;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;


//TODO M: da capire se fare turnController subito qui oppure dopo aver fatto l'azione nel turnController
//          magari settando un boolean CharacterEffect
public class ExpertController {
    private final Game game;
    private final GameBoard board;
    private final TurnController turnController;

    private boolean centaurEffect;

    private boolean fungarusEffect;

    private boolean grannyHerbsEffect;

    private PawnType pawnTypeChosen;

    public void setCentaurEffect(boolean centaurEffect) {
        this.centaurEffect = centaurEffect;
    }

    public void setFungarusEffect(boolean fungarusEffect) {
        this.fungarusEffect = fungarusEffect;
    }

    public void setGrannyHerbsEffect(boolean grannyHerbsEffect) {
        this.grannyHerbsEffect = grannyHerbsEffect;
    }

    public boolean isGrannyHerbsEffect() {
        return grannyHerbsEffect;
    }

    public boolean isCentaurEffect() {
        return centaurEffect;
    }

    public boolean isFungarusEffect() {
        return fungarusEffect;
    }

    public PawnType getPawnTypeChosen() {
        return pawnTypeChosen;
    }

    public void setPawnTypeChosen(PawnType pawnTypeChosen) {
        this.pawnTypeChosen = pawnTypeChosen;
    }


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
        centaurEffect = true;

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
        fungarusEffect = true;

        RequestAction islandDestination = new RequestAction(Action.PICK_PAWN_TYPE);
        turnController.getGameHandler().sendSinglePlayer(islandDestination, turnController.getCurrentPlayer().getPlayerID());

    }

    //TODO M -> JESTER
    public void jesterEffect() {


    }

    public void thiefEffect() {
        RequestAction pawnRequest = new RequestAction(Action.PICK_PAWN_TYPE);
        turnController.getGameHandler().sendSinglePlayer(pawnRequest, turnController.getCurrentPlayer().getPlayerID());


    }


    //TODO M -> MINESTREL
    public void minestrelEffect() {




    }

    public void monkEffect() {
        RequestAction studentRequest = new RequestAction(Action.PICK_MONK_STUDENT);
        turnController.getGameHandler().sendSinglePlayer(studentRequest, turnController.getCurrentPlayer().getPlayerID());

    }

    public void grannyHerbsEffect() {

        grannyHerbsEffect = true;
        RequestAction studentRequest = new RequestAction(Action.PICK_MONK_STUDENT);
        turnController.getGameHandler().sendSinglePlayer(studentRequest, turnController.getCurrentPlayer().getPlayerID());


    }

    public void setGrannyHerbsTile(Island island) {
        for(Island is : turnController.getController().getGame().getGameBoard().getIslands()) {
            if(is.getIslandID() == island.getIslandID()) {
                is.setNoEntry(true);
            }
        }


    }

    //TODO M -> MAGICPOSTMAN

    public void magicPostmanEffect() {

    }

    //TODO M -> SPOILEDPRINCESS
    public void spoiledPrincessEffect() {

    }


    public void activeThiefEffect() {
        for(Player p : turnController.getController().getGame().getActivePlayers()) {
            for(int i = 0; i < 3; i++) {
                if(p.getBoard().getDiningRoom().getDiningRoom().get(pawnTypeChosen.getPawnID()).getTable().size() > 0) {
                    turnController.getController().getGame().getGameBoard().getStudentsBag().add(new Student(pawnTypeChosen));
                    p.getBoard().getDiningRoom().getDiningRoom().get(pawnTypeChosen.getPawnID()).removeStudent();
                }
            }

        }
    }


}
