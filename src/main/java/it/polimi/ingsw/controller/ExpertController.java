package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.clienttoserver.actions.Action;
import it.polimi.ingsw.messages.servertoclient.JesterAction;
import it.polimi.ingsw.messages.servertoclient.MagicPostmanAction;
import it.polimi.ingsw.messages.servertoclient.MinestrelAction;
import it.polimi.ingsw.messages.servertoclient.RequestAction;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.enumerations.PawnType;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;


//TODO M: da capire se fare turnController.askMotherNature() subito qui oppure dopo aver fatto l'azione nel turnController
//          magari settando un boolean CharacterEffect
public class ExpertController {
    private final Game game;
    private final GameBoard board;
    private final TurnController turnController;
    private Student studentOne;
    private Student studentTwo;
    private PawnType pawnTypeChosen;
    private Student studentChosen;



    public ExpertController(Game game, GameBoard board, TurnController turnController) {
        this.game = game;
        this.board = board;
        this.turnController = turnController;
    }

    public void setStudentChosen(Student studentChosen) {
        this.studentChosen = studentChosen;
    }

    public void setStudentOne(Student studentOne) {
        this.studentOne = studentOne;
    }

    public void setStudentTwo(Student studentTwo) {
        this.studentTwo = studentTwo;
    }

    public PawnType getPawnTypeChosen() {
        return pawnTypeChosen;
    }

    public void setPawnTypeChosen(PawnType pawnTypeChosen) {
        this.pawnTypeChosen = pawnTypeChosen;
    }


    //CENTAUR
    //------------------------------------------------------------------------------------------------------------------
    private boolean centaurEffect;
    public void setCentaurEffect(boolean centaurEffect) {
        this.centaurEffect = centaurEffect;
    }

    public boolean isCentaurEffect() {
        return centaurEffect;
    }

    public void centaurEffect() {
        centaurEffect = true;
        turnController.askMotherNatureMoves();
    }
    //------------------------------------------------------------------------------------------------------------------




    //JESTER
    //------------------------------------------------------------------------------------------------------------------
    private boolean jesterEffect;
    private int jesterReqNum = 0;

    public int getJesterReqNum() {
        return jesterReqNum;
    }
    public boolean isJesterEffect() {
        return jesterEffect;
    }

    public void setJesterReqNum(int jesterReqNum) {
        this.jesterReqNum = jesterReqNum;
    }

    public void jesterEffect() {
        jesterEffect = true;
        jesterReqNum = 0;
        turnController.getGameHandler().sendSinglePlayer(new JesterAction(), turnController.getCurrentPlayer().getPlayerID());
        turnController.askStudent();
    }

    public void activeJesterEffect() {
        if(studentOne != null || studentTwo != null) {
            turnController.askStudent();
        } else {
            //student 1 = jester
            //student 2 = entrance
            for(CharacterCard c : game.getGameBoard().getPlayableCharacters()) {
                if(c.getName().toString() == "JESTER") {
                    c.removeStudent(studentOne);
                    c.addStudent(studentTwo);
                }
            }
            game.getCurrentPlayer().getBoard().getEntrance().getStudents().remove(studentTwo);
            game.getCurrentPlayer().getBoard().getEntrance().getStudents().add(studentOne);

            studentOne = null;
            studentTwo = null;
        }
        if(minestrelReqNum == 0) {
            turnController.askMotherNatureMoves();
        } else {
            turnController.askStudent();
        }
    }
    //------------------------------------------------------------------------------------------------------------------


    //MONK
    //------------------------------------------------------------------------------------------------------------------
    private boolean monkEffect;
    public boolean isMonkEffect() {
        return monkEffect;
    }
    public void setMonkEffect(boolean monkEffect) {
        this.monkEffect = monkEffect;
    }
    public void monkEffect() {
        monkEffect = true;
        RequestAction studentRequest = new RequestAction(Action.PICK_MONK_STUDENT);
        turnController.getGameHandler().sendSinglePlayer(studentRequest, turnController.getCurrentPlayer().getPlayerID());
    }
    public void activeMonkEffect() {
        RequestAction islandRequest = new RequestAction(Action.PICK_ISLAND);
        turnController.getGameHandler().sendSinglePlayer(islandRequest, turnController.getCurrentPlayer().getPlayerID());
    }

    //------------------------------------------------------------------------------------------------------------------



    //SPOILED_PRINCESS
    //------------------------------------------------------------------------------------------------------------------
    private boolean spoiledPrincessEffect;

    public boolean isSpoiledPrincessEffect() {
        return spoiledPrincessEffect;
    }

    public void spoiledPrincessEffect() {
        spoiledPrincessEffect = true;
        RequestAction princessRequest = new RequestAction(Action.PICK_STUDENT_PRINCESS);
        turnController.getGameHandler().sendSinglePlayer(princessRequest, turnController.getCurrentPlayer().getPlayerID());
    }

    public void activeSpoiledPrincessEffect() {
        game.getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(studentChosen);

        for(CharacterCard c : game.getGameBoard().getPlayableCharacters()) {
            if(c.getName().toString() == "SPOILED_PRINCESS") {
                c.removeStudent(studentChosen);
                c.addStudent(game.getGameBoard().getStudentsBag().get(0));
                break;
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------


    //THIEF
    //------------------------------------------------------------------------------------------------------------------
    public void thiefEffect() {
        thiefEffect = true;

        RequestAction pawnRequest = new RequestAction(Action.PICK_PAWN_TYPE);
        turnController.getGameHandler().sendSinglePlayer(pawnRequest, turnController.getCurrentPlayer().getPlayerID());
    }

    public boolean isThiefEffect() {
        return thiefEffect;
    }
    private boolean thiefEffect;

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
    //------------------------------------------------------------------------------------------------------------------


    //FARMER
    //------------------------------------------------------------------------------------------------------------------
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
    //------------------------------------------------------------------------------------------------------------------


    //MINESTREL
    //------------------------------------------------------------------------------------------------------------------
    private boolean minestrelEffect;
    private int minestrelReqNum;
    public boolean isMinestrelEffect() {
        return minestrelEffect;
    }

    public int getMinestrelReqNum() {
        return minestrelReqNum;
    }

    public void setMinestrelReqNum(int minestrelReqNum) {
        this.minestrelReqNum = minestrelReqNum;
    }
    public void minestrelEffect() {
        minestrelEffect = true;
        minestrelReqNum = 0;
        turnController.getGameHandler().sendSinglePlayer(new MinestrelAction(), turnController.getCurrentPlayer().getPlayerID());
        turnController.askStudent();
    }

    public void activeMinestrelEffect() {
        if(studentOne != null || studentTwo != null) {
            turnController.askStudent();
        } else {
            //student 1 = dining room
            //student 2 = entrance
            game.getCurrentPlayer().getBoard().getDiningRoom().getDiningRoom().get(studentOne.getType().getPawnID()).removeStudent();
            game.getCurrentPlayer().getBoard().getEntrance().getStudents().remove(studentTwo);

            game.getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(studentTwo);
            game.getCurrentPlayer().getBoard().getEntrance().getStudents().add(studentOne);

            studentOne = null;
            studentTwo = null;
        }

        if(minestrelReqNum == 0) {
            turnController.askMotherNatureMoves();
        } else {
            turnController.askStudent();
        }
    }
    //------------------------------------------------------------------------------------------------------------------



    //GRANNY HERBS
    //------------------------------------------------------------------------------------------------------------------
    private boolean grannyHerbsEffect;
    public void setGrannyHerbsEffect(boolean grannyHerbsEffect) {
        this.grannyHerbsEffect = grannyHerbsEffect;
    }
    public boolean isGrannyHerbsEffect() {
        return grannyHerbsEffect;
    }
    public void grannyHerbsEffect() {
        grannyHerbsEffect = true;
        RequestAction islandRequest = new RequestAction(Action.PICK_ISLAND);
        turnController.getGameHandler().sendSinglePlayer(islandRequest, turnController.getCurrentPlayer().getPlayerID());
    }
    public void setGrannyHerbsTile(Island island) {
        for(Island is : turnController.getController().getGame().getGameBoard().getIslands()) {
            if(is.getIslandID() == island.getIslandID()) {
                is.setNoEntry(true);
            }
        }
    }
    //------------------------------------------------------------------------------------------------------------------




    //HERALD
    //------------------------------------------------------------------------------------------------------------------
    public void heraldEffect() {
        RequestAction islandDestination = new RequestAction(Action.PICK_ISLAND);
        turnController.getGameHandler().sendSinglePlayer(islandDestination, turnController.getCurrentPlayer().getPlayerID());


        turnController.askMotherNatureMoves();
    }

    //------------------------------------------------------------------------------------------------------------------




    //KNIGHT
    //------------------------------------------------------------------------------------------------------------------
    public void knightEffect() {
        turnController.getCurrentPlayer().setIslandInfluence(2);
        turnController.askMotherNatureMoves();
    }
    //------------------------------------------------------------------------------------------------------------------


    //FUNGARUS
    //------------------------------------------------------------------------------------------------------------------
    private boolean fungarusEffect;
    public boolean isFungarusEffect() {
        return fungarusEffect;
    }
    public void fungarusEffect() {
        fungarusEffect = true;

        RequestAction islandDestination = new RequestAction(Action.PICK_PAWN_TYPE);
        turnController.getGameHandler().sendSinglePlayer(islandDestination, turnController.getCurrentPlayer().getPlayerID());
    }
    public void setFungarusEffect(boolean fungarusEffect) {
        this.fungarusEffect = fungarusEffect;
    }
    //------------------------------------------------------------------------------------------------------------------



    //MAGICPOSTMAN
    //------------------------------------------------------------------------------------------------------------------
    public void magicPostmanEffect() {
        turnController.getGameHandler().sendSinglePlayer(new MagicPostmanAction(), turnController.getCurrentPlayer().getPlayerID());
        turnController.askMotherNatureMoves();
    }
    //------------------------------------------------------------------------------------------------------------------


}
