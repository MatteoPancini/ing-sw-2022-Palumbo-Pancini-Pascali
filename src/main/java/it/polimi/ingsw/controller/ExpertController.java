package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.clienttoserver.actions.Action;
import it.polimi.ingsw.messages.servertoclient.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.GameBoard;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.enumerations.Characters;
import it.polimi.ingsw.model.enumerations.PawnType;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;


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


    public Student getStudentChosen() {
        return studentChosen;
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
    }

    public void activeJesterEffect() {
        if(studentOne == null || studentTwo == null) {
            turnController.getGameHandler().sendSinglePlayer(new GameCopy(turnController.getController().getGame()), turnController.getController().getGame().getCurrentPlayer().getPlayerID());
            turnController.askStudent();
        } else {
            //student 1 = jester
            //student 2 = entrance
            System.out.println("Stud 1: " + studentOne.getType());
            System.out.println("Stud 2: " + studentTwo.getType());

            for(CharacterCard c : game.getGameBoard().getPlayableCharacters()) {
                if(c.getName() == Characters.JESTER) {
                    c.removeStudent(studentOne);
                    c.addStudent(studentTwo);
                }
            }
            game.getCurrentPlayer().getBoard().getEntrance().removeStudent(studentTwo);
            game.getCurrentPlayer().getBoard().getEntrance().setStudents(studentOne);

            studentOne = null;
            studentTwo = null;
            if(jesterReqNum == 0) {
                jesterEffect = false;
                turnController.askMotherNatureMoves();
            } else {
                turnController.getGameHandler().sendSinglePlayer(new GameCopy(turnController.getController().getGame()), turnController.getController().getGame().getCurrentPlayer().getPlayerID());
                turnController.askStudent();
            }
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
        turnController.getGameHandler().sendSinglePlayer(new MonkAction(), turnController.getCurrentPlayer().getPlayerID());
        RequestAction studentRequest = new RequestAction(Action.PICK_STUDENT);
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
        turnController.getGameHandler().sendSinglePlayer(new PrincessAction(), turnController.getCurrentPlayer().getPlayerID() );
        RequestAction princessRequest = new RequestAction(Action.PICK_STUDENT);
        turnController.getGameHandler().sendSinglePlayer(princessRequest, turnController.getCurrentPlayer().getPlayerID());
    }

    public void activeSpoiledPrincessEffect() {
        game.getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(studentChosen);

        for(CharacterCard c : game.getGameBoard().getPlayableCharacters()) {
            if(c.getName() ==  Characters.SPOILED_PRINCESS) {
                c.removeStudent(studentChosen);
                c.addStudent(game.getGameBoard().getStudentsBag().get(0));
                turnController.setStudentToMove(studentChosen);
                turnController.checkProfessorInfluence();
                game.getGameBoard().removeStudents(0);
                spoiledPrincessEffect = false;
                break;
            }
        }
        turnController.askMotherNatureMoves();
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
                if(p.getBoard().getDiningRoom().getDiningRoom().get(pawnTypeChosen.getPawnID()).getTableStudentsNum() > 0) {
                    //System.out.println(pawnTypeChosen.getPawnID());
                    turnController.getController().getGame().getGameBoard().getStudentsBag().add(new Student(pawnTypeChosen));
                    p.getBoard().getDiningRoom().getDiningRoom().get(pawnTypeChosen.getPawnID()).removeStudent();
                }
                if(p.getBoard().getDiningRoom().getDiningRoom().get(pawnTypeChosen.getPawnID()).getTableStudentsNum() == 0 && turnController.getController().getGame().getGameBoard().getProfessorByColor(pawnTypeChosen).getOwner() == p) {
                    turnController.getController().getGame().getGameBoard().getProfessorByColor(pawnTypeChosen).setOwner(null);
                    p.getBoard().getProfessorTable().getCellByColor(pawnTypeChosen).resetProfessor();
                    break;
                }

            }
        }

        turnController.askMotherNatureMoves();
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
            if(game.getGameBoard().getProfessorByColor(type).getOwner() != null) {
                for(Player p : game.getActivePlayers()) {
                    if(!p.equals(game.getCurrentPlayer())) {
                        if(game.getCurrentPlayer().getBoard().getDiningRoom().getDiningRoom().get(type.getPawnID()).getTableStudentsNum() == p.getBoard().getDiningRoom().getDiningRoom().get(type.getPawnID()).getTableStudentsNum() && !game.getCurrentPlayer().getBoard().getProfessorTable().getCellByColor(type).hasProfessor()) {
                            game.getCurrentPlayer().getBoard().getProfessorTable().getCellByColor(type).setProfessor(game.getGameBoard().getProfessorByColor(type));
                            p.getBoard().getProfessorTable().getCellByColor(type).resetProfessor();
                            game.getGameBoard().getProfessorByColor(type).setOwner(game.getCurrentPlayer());
                        }
                    }
                }
            }

        }

        turnController.askMotherNatureMoves();

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
    }

    public void activeMinestrelEffect() {
        if(studentOne == null || studentTwo == null) {
            turnController.getGameHandler().sendSinglePlayer(new GameCopy(turnController.getController().getGame()), turnController.getController().getGame().getCurrentPlayer().getPlayerID());
            turnController.askStudent();
        } else {
            //student 1 = dining room
            //student 2 = entrance
            System.out.println("Entro nell'active minestrel");
            game.getCurrentPlayer().getBoard().getDiningRoom().getDiningRoom().get(studentOne.getType().getPawnID()).removeStudent();
            game.getCurrentPlayer().getBoard().getEntrance().removeStudent(studentTwo);

            game.getCurrentPlayer().getBoard().getDiningRoom().setStudentToDiningRoom(studentTwo);
            game.getCurrentPlayer().getBoard().getEntrance().setStudents(studentOne);

            studentOne = null;
            studentTwo = null;

            if(minestrelReqNum == 0) {
                minestrelEffect = false;
                turnController.askMotherNatureMoves();
            } else {
                turnController.getGameHandler().sendSinglePlayer(new GameCopy(turnController.getController().getGame()), turnController.getController().getGame().getCurrentPlayer().getPlayerID());
                turnController.askStudent();
            }
        }


    }
    //------------------------------------------------------------------------------------------------------------------



    //GRANNY HERBS
    //------------------------------------------------------------------------------------------------------------------
    private boolean grannyHerbsEffect;
    public boolean isGrannyHerbsEffect() {
        return grannyHerbsEffect;
    }
    public void grannyHerbsEffect() {
        grannyHerbsEffect = true;
        turnController.getGameHandler().sendSinglePlayer(new GrannyHerbsAction(), turnController.getCurrentPlayer().getPlayerID());
    }
    public void setGrannyHerbsTile(Island island) {
        for(Island is : turnController.getController().getGame().getGameBoard().getIslands()) {
            if(is.getIslandID() == island.getIslandID()) {
                System.out.println("Setting granny " + is.getIslandID());
                is.setNoEntry(true);
                break;
            }
        }
        grannyHerbsEffect = false;
        turnController.askMotherNatureMoves();
    }
    //------------------------------------------------------------------------------------------------------------------




    //HERALD
    //------------------------------------------------------------------------------------------------------------------
    private boolean heraldEffect;

    public void heraldEffect() {
        heraldEffect = true;
        RequestAction islandDestination = new RequestAction(Action.PICK_ISLAND);
        turnController.getGameHandler().sendSinglePlayer(islandDestination, turnController.getCurrentPlayer().getPlayerID());
    }

    public boolean isHeraldEffect() {
        return heraldEffect;
    }

    public void setHeraldEffect(boolean heraldEffect) {
        this.heraldEffect = heraldEffect;
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

        RequestAction pawnTypeReq = new RequestAction(Action.PICK_PAWN_TYPE);
        turnController.getGameHandler().sendSinglePlayer(pawnTypeReq, turnController.getCurrentPlayer().getPlayerID());
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
