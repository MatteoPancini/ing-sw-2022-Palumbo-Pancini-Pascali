package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.*;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.messages.clienttoserver.ExpertModeChoice;
import it.polimi.ingsw.messages.clienttoserver.PlayersNumberChoice;
import it.polimi.ingsw.messages.clienttoserver.WizardChoice;
import it.polimi.ingsw.messages.clienttoserver.actions.PickCharacterActionsNum;
import it.polimi.ingsw.messages.servertoclient.Answer;
import it.polimi.ingsw.messages.servertoclient.WizardAnswer;
import it.polimi.ingsw.messages.servertoclient.errors.ServerError;
import it.polimi.ingsw.messages.servertoclient.errors.ServerErrorTypes;
import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.AssistantDeck;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.enumerations.PawnType;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.enumerations.Wizards;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.SchoolBoard;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.player.Table;
import it.polimi.ingsw.model.player.Tower;

import java.beans.PropertyChangeEvent;
import java.io.PrintStream;
import java.util.ArrayList;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Scanner;

import static it.polimi.ingsw.constants.Constants.*;

public class CLI implements Runnable, ListenerInterface {
    private final Scanner in;
    private static PrintStream out;
    private String chosenCharacter;
    //private String chosenTeam;
    private ClientConnection clientConnection;
    private final ModelView modelView;
    private boolean activeGame;
    private final ActionHandler actionHandler;
    private final PropertyChangeSupport virtualClient = new PropertyChangeSupport(this);
    private boolean firstTurn = true;
    private String whiteP = "";
    private String greyP = "";
    private String blackP = "";

    public CLI() {
        in = new Scanner(System.in);
        out = new PrintStream(System.out);
        modelView = new ModelView(this);
        activeGame = true;
        actionHandler = new ActionHandler(this, modelView);
    }

    public String printTowers(Island isl) {
        String towers = "";
        if(isl.hasTower()) {
            for (Tower t : isl.getMergedTowers()) {
                if (t.getColor().equals(TowerColor.WHITE)) {
                    towers = towers + "○ ";
                } else if (t.getColor().equals(TowerColor.BLACK)) {
                    towers = towers + ANSI_WHITE + "█ " + ANSI_RESET;
                } else if (t.getColor().equals(TowerColor.GREY)) {
                    towers = towers + ANSI_WHITE + "▲ " + ANSI_RESET;
                }
            }
        }
        return towers;
    }

    public void setActiveGame(boolean activeGame) {
        this.activeGame = activeGame;
    }

    public Scanner getIn() {
        return in;
    }

    public PrintStream getOutput() {
        return out;
    }

    public Integer printYellowStudentsOnIsland(Island isl) {
        int y = 0;
        for(int j=0; j < isl.getStudents().size(); j++) {
            if(isl.getStudents().get(j).getType().toString().toUpperCase().equals("YELLOW")) {
                y++;
            }
        }
        return y;
    }
    public Integer printBlueStudentsOnIsland(Island isl) {
        int b = 0;
        for(int j=0; j < isl.getStudents().size(); j++) {
            if(isl.getStudents().get(j).getType().toString().toUpperCase().equals("BLUE")) {
                b++;
            }
        }
        return b;
    }
    public Integer printPinkStudentsOnIsland(Island isl) {
        int p = 0;
        for(int j=0; j < isl.getStudents().size(); j++) {
            if(isl.getStudents().get(j).getType().toString().toUpperCase().equals("PINK")) {
                p++;
            }
        }
        return p;
    }
    public Integer printGreenStudentsOnIsland(Island isl) {
        int g = 0;
        for(int j=0; j < isl.getStudents().size(); j++) {
            if(isl.getStudents().get(j).getType().toString().toUpperCase().equals("GREEN")) {
                g++;
            }
        }
        return g;
    }
    public Integer printRedStudentsOnIsland(Island isl) {
        int r = 0;
        for(int j=0; j < isl.getStudents().size(); j++) {
            if(isl.getStudents().get(j).getType().toString().toUpperCase().equals("RED")) {
                r++;
            }
        }
        return r;
    }

    public void showAvailableCharacters() {
        if (modelView.getGameCopy().isExpertMode()) {
            System.out.println(">The available characters for this match are: ");
            showCharactersDescription();
        }
    }

    /*public void showCharactersDescription() {
        for(CharacterCard c : CharacterDeck.getPlayableCards()) {
            out.print(ANSI_CYAN + c.getName() + " : " + c.getEffect() + ANSI_RESET);
        }
    }*/

    public void showCharactersDescription() {
        //CLITable st = new CLITable();
        //st.setShowVerticalLines(true);
        //st.addRow("Effect: ");
        //st.addRow("Cost: ");
        for(int i = 0; i < modelView.getGameCopy().getGameBoard().getPlayableCharacters().size(); i++) {
            CLITable st = new CLITable();
            st.setShowVerticalLines(true);
            st.setHeaders(modelView.getGameCopy().getGameBoard().getPlayableCharacters().get(i).getName().toString());
            st.addRow("Effect: " + modelView.getGameCopy().getGameBoard().getPlayableCharacters().get(i).getEffect());
            st.addRow("Cost: " + Integer.toString(modelView.getGameCopy().getGameBoard().getPlayableCharacters().get(i).getInitialCost()));
            st.print();
        }


        /*
        for(CharacterCard c : modelView.getGameCopy().getGameBoard().getPlayableCharacters()) {
            st.setHeader(c.getName().toString());
            st.addRow(c.getEffect());
            st.addRow(Integer.toString(c.getInitialCost()));
            st.print();
        }

         */
    }

    //scrivere diversi show per ogni parte del modello da mostrare
    public void showBoard() {
        System.out.println("Here's a summary of your board: ");
        System.out.println(Constants.ANSI_GREEN + "Green = " + modelView.getGreenStudents(modelView.getGameCopy().getCurrentPlayer()) + " - Professor : "
                + modelView.hasGreenProfessor(modelView.getGameCopy().getCurrentPlayer()) + ANSI_RESET);
        System.out.println(Constants.ANSI_RED + "Red = " + modelView.getRedStudents(modelView.getGameCopy().getCurrentPlayer()) + " - Professor : "
                + modelView.hasRedProfessor(modelView.getGameCopy().getCurrentPlayer()) + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "Yellow = " + modelView.getYellowStudents(modelView.getGameCopy().getCurrentPlayer()) + " - Professor : "
                + modelView.hasYellowProfessor(modelView.getGameCopy().getCurrentPlayer()) + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "Pink = " + modelView.getPinkStudents(modelView.getGameCopy().getCurrentPlayer()) + " - Professor : "
                + modelView.hasPinkProfessor(modelView.getGameCopy().getCurrentPlayer()) + ANSI_RESET);
        System.out.println(ANSI_BLUE + "Blue = " + modelView.getBlueStudents(modelView.getGameCopy().getCurrentPlayer()) + " - Professor : "
                + modelView.hasRedProfessor(modelView.getGameCopy().getCurrentPlayer()) + ANSI_RESET);
    }

    /*public void showIslands() {
        System.out.println(">Here's a little description of the islands in the game board: ");
        for (Island island : modelView.getGameCopy().getGameBoard().getIslands()) {
            if(island.getMergedIslands().size() > 1) {
                System.out.println("Island " + island.getIslandID() + ":" + "(merged with ");
                for (int i = 0; i < island.getMergedIslands().size(); i++) {
                    System.out.print(island.getMergedIslands().get(i).getIslandID() + " ");
                }
                System.out.print(")\n");
                System.out.println("Students: ");
                for (Student stud : island.getStudents()) {
                    System.out.print(stud.getType().toString() + "\n");
                }
                for (int i = 0; i < island.getMergedIslands().size(); i++)
                {
                    System.out.println("\nTowers: " + island.getMergedTowers() + " "  + island.getTower().getColor().toString());
                }
            } else if(island.getMergedIslands().size() == 1) {
                System.out.println("Island " + island.getIslandID() + ":" + "\n" + "Students : ");
                for (Student stud : island.getStudents()) {
                    System.out.print(stud.getType().toString() + "\n");
                }
                System.out.println("\nTower: " + island.getTower().getColor().toString());
            }
        }
    }*/

    public String studentsOnIsland(Island i) {
        String[] stud = new String[100];
        StringBuilder s = new StringBuilder();
        boolean y = false;
        boolean b = false;
        boolean p = false;
        boolean g = false;
        boolean r = false;

        for (int t=0; t < i.getStudents().size(); t++) {
            if(i.getStudents().get(t).getType() == PawnType.YELLOW && !y) {
                stud[t] = ANSI_YELLOW + "• [" + printYellowStudentsOnIsland(i) + "]" + ANSI_RESET;
                y = true;
            }
            else if(i.getStudents().get(t).getType() == PawnType.BLUE && !b) {
                stud[t] = ANSI_BLUE + "• [" + printBlueStudentsOnIsland(i) + "]" + ANSI_RESET;
                b = true;
            }
            else if(i.getStudents().get(t).getType() == PawnType.PINK && !p) {
                stud[t] = ANSI_PURPLE + "• [" + printPinkStudentsOnIsland(i) + "]" + ANSI_RESET;
                p = true;
            }
            else if(i.getStudents().get(t).getType() == PawnType.GREEN && !g) {
                stud[t] = ANSI_GREEN + "• [" + printGreenStudentsOnIsland(i) + "]" + ANSI_RESET;
                g = true;
            }
            else if(i.getStudents().get(t).getType() == PawnType.RED && !r) {
                stud[t] = ANSI_RED + "• [" + printRedStudentsOnIsland(i) + "]" + ANSI_RESET;
                r = true;
            }
            //stud[t] = printColor(i.getStudents().get(t).getType()) + i.getStudents().get(t).getType() + ANSI_RESET ;
        }
        for(int j=0; j < stud.length; j++) {
            if (stud[j] != null) {
                if(j==0) {

                    s = new StringBuilder(stud[0].toString());
                }
                else {
                    s.append(", ").append(stud[j]);
                }
            }
        }
        return s.toString();
    }

    public String isMerged(Island island) {
        String[] merged = new String[12];
        StringBuilder s = new StringBuilder();
        for(int j=0; j < modelView.getGameCopy().getGameBoard().getIslands().size(); j++) {
            if (modelView.getGameCopy().getGameBoard().getIslands().get(j).equals(island)) {
                if (modelView.getGameCopy().getGameBoard().getIslands().get(j).getMergedIslands().size() > 1) {
                    for (int i = 0; i < modelView.getGameCopy().getGameBoard().getIslands().get(j).getMergedIslands().size(); i++) {
                        merged[i] = Integer.toString(modelView.getGameCopy().getGameBoard().getIslands().get(j).getMergedIslands().get(i).getIslandID());
                    }
                    for(int i=0; i < merged.length; i++) {
                        if (merged[i] != null) {
                            if(i==0) {
                                s = new StringBuilder(merged[0].toString());
                            }
                            else {
                                s.append(", ").append(merged[i].toString());
                            }
                        }
                    }
                }
            }
        }
        return s.toString();
    }

    public void showIslandsTable() {
        //System.out.println(">Here's a little description of the islands in the game board. [Towers' legend: ▲ = Grey , █ = Black , ○ = White]");
        System.out.println(">Here's a little description of the islands in the game board. [Towers' legend: ▲ = Grey \"" + greyP + "\", █ = Black \"" + blackP + "\", ○ = White \"" + whiteP + "\"]");

        CLITable st = new CLITable();
        //st.setShowVerticalLines(true);
        //st.setHeaders("Island ID", "Merged Islands", "Students", "Towers");
        st.setHeaders("Island ID", "Merged Islands", "Students & Towers");

        for(int i = 0; i < modelView.getGameCopy().getGameBoard().getIslands().size(); i++) {
            //st.addRow(Integer.toString(modelView.getGameCopy().getGameBoard().getIslands().get(i).getIslandID()), isMerged(modelView.getGameCopy().getGameBoard().getIslands().get(i)), studentsOnIsland(modelView.getGameCopy().getGameBoard().getIslands().get(i)), printTowers(modelView.getGameCopy().getGameBoard().getIslands().get(i)));
            if(modelView.getGameCopy().getGameBoard().getIslands().get(i).getNoEntry()) {
                st.addRow(Integer.toString(modelView.getGameCopy().getGameBoard().getIslands().get(i).getIslandID()) + ANSI_RED + " X" + ANSI_RESET, isMerged(modelView.getGameCopy().getGameBoard().getIslands().get(i)), studentsOnIsland(modelView.getGameCopy().getGameBoard().getIslands().get(i)) + "          " + printTowers(modelView.getGameCopy().getGameBoard().getIslands().get(i)));
            } else {
                st.addRow(Integer.toString(modelView.getGameCopy().getGameBoard().getIslands().get(i).getIslandID()), isMerged(modelView.getGameCopy().getGameBoard().getIslands().get(i)), studentsOnIsland(modelView.getGameCopy().getGameBoard().getIslands().get(i)) + "          " + printTowers(modelView.getGameCopy().getGameBoard().getIslands().get(i)));

            }

        }
        /*
        for(Island island : modelView.getGameCopy().getGameBoard().getIslands()) {
            st.addRow(Integer.toString(island.getIslandID()), isMerged(island).toString(), studentsOnIsland(island).toString(), printTowers(island));
        }

         */
        showMotherNature();
        st.print();
    }

    /*public void showLastAssistantsUsed() {
        out.println(">These are all the assistants used in this turn: ");
        for(AssistantCard ass : modelView.getGameCopy().getGameBoard().getLastAssistantUsed()) {
            out.println("Name: " + ass.getName() + "Value: " + ass.getValue() + "Maximum moves: " + ass.getMoves());
        }
    }*/
    /*
    public Player[] getPlayersByAssistantUsed() {

        Player[] players = new Player[4];
        for(int i=0; i < modelView.getGameCopy().getActivePlayers().size() ; i++) {
            players[i] = modelView.getGameCopy().getGameBoard().getLastAssistantUsed().get(i).getOwner();
        }


        ArrayList<Player> players = new ArrayList<>();
        for()
        return players;
    }

    public String[] getAssistantUsedByOwner() {
        String[] assistants = new String[4];
        for(int i=0; i < modelView.getGameCopy().getActivePlayers().size() ; i++) {
            assistants[i] = modelView.getGameCopy().getActivePlayers().get(i).getChosenAssistant().getName().toString();
        }
        return assistants;
    }

    /* old version
        public void showLastAssistantsUsed() {
        System.out.println(">These are all the assistants used in this turn: ");
        CLITable st = new CLITable();
        Player[] nicknames = getPlayersByAssistantUsed();
        String[] assistants = getAssistantUsedByOwner();
        st.setHeaders(nicknames[0].getNickname(), nicknames[1].getNickname(), nicknames[2].getNickname(), nicknames[3].getNickname());
        st.addRow(nicknames[0].getChosenAssistant().getName().toString(), nicknames[1].getChosenAssistant().getName().toString(),
                nicknames[2].getChosenAssistant().getName().toString(), nicknames[3].getChosenAssistant().getName().toString());
        st.print();
    } */

    public void showLastAssistantsUsed() {
        System.out.println(">These are all the assistants used in this turn: ");
        CLITable st = new CLITable();
        st.setShowVerticalLines(true);
        /*
        Player[] nicknames = getPlayersByAssistantUsed();
        String[] assistants = getAssistantUsedByOwner();

         */
        ArrayList<String> nicknames = new ArrayList<>();
        ArrayList<String> assistants = new ArrayList<>();
        for(AssistantCard a : modelView.getGameCopy().getGameBoard().getLastAssistantUsed()) {
            nicknames.add(a.getOwner().getNickname());
            assistants.add(a.getName().toString());
        }

        switch (modelView.getGameCopy().getGameBoard().getLastAssistantUsed().size()) {
            case 1 -> {
                st.setHeaders(nicknames.get(0));
                st.addRow(assistants.get(0));
            }

            case 2 -> {
                st.setHeaders(nicknames.get(0), nicknames.get(1));
                st.addRow(assistants.get(0), assistants.get(1));
            }

            case 3 -> {
                st.setHeaders(nicknames.get(0), nicknames.get(1), nicknames.get(2));
                st.addRow(assistants.get(0), assistants.get(1), assistants.get(2));
            }
        }

        st.print();
    }

    public String printStudentsOnCloud(int ID) {
        StringBuilder str = new StringBuilder();
        for(Student s : modelView.getGameCopy().getGameBoard().getClouds().get(ID - 1).getStudents()) {
            //System.out.print("-" + s.getType());
            str.append(printColor(s.getType())).append("•").append(ANSI_RESET);
        }
        return str.toString();
    }

    public void showClouds() {
        System.out.println(">Clouds status of this turn: ");
        CLITable st = new CLITable();
        st.setHeaders("Cloud ID", "Students");
        for(int i = 0; i < modelView.getGameCopy().getGameBoard().getClouds().size(); i++) {
            if(modelView.getGameCopy().getGameBoard().getClouds().get(i).getStudents() != null) {
                st.addRow(Integer.toString(modelView.getGameCopy().getGameBoard().getClouds().get(i).getID()), printStudentsOnCloud(modelView.getGameCopy().getGameBoard().getClouds().get(i).getID()));
                /*
                for(Student s : modelView.getGameCopy().getGameBoard().getClouds().get(i).getStudents()) {
                    System.out.println(modelView.getGameCopy().getGameBoard().getClouds().get(i).getID() + ", " + s.getType().toString());
                }
                 */
                //st.addRow(Integer.toString(c.getID()), printStudentsOnCloud(c.getID()));
            }
        }
        st.print();
    }

    public String printColor(PawnType p){
        String color = ANSI_RESET;
        if(p==PawnType.RED) {
            color = ANSI_RED;
        }
        else if(p==PawnType.BLUE) {
            color = ANSI_BLUE;
        }
        else if(p==PawnType.GREEN) {
            color = ANSI_GREEN;
        }
        else if(p==PawnType.YELLOW) {
            color = ANSI_YELLOW;
        }
        else if(p==PawnType.PINK) {
            color = ANSI_PURPLE;
        }
        return color;
    }

    public void showEntrance() {
        System.out.println(">Here's a summary of the students in your entrance: ");
        //System.out.println(modelView.getGameCopy().getCurrentPlayer().getBoard().getEntrance().getStudents().size());
        for(Student s : modelView.getGameCopy().getCurrentPlayer().getBoard().getEntrance().getStudents()) {
            System.out.print("•" + printColor(s.getType()) + s.getType() + ANSI_RESET);
        }
        System.out.println("\n");
    }

    public void showCoins() {
        if (modelView.getGameCopy().isExpertMode()) {
            System.out.println(">You have " + ANSI_YELLOW + modelView.getGameCopy().getCurrentPlayer().getMyCoins() + " coins left." + ANSI_RESET);
        }
    }

    public int[] getPlayerDiningRoom(String name) {
        int r=0, p=0, g=0, y=0, b=0;
        int[] students = new int[11];
        for(Player pl : modelView.getGameCopy().getActivePlayers()) {
            if(pl.getNickname().equals(name)) {
                for(Table t : pl.getBoard().getDiningRoom().getDiningRoom()) {
                    for(int i=0; i < t.getTable().size(); i++) {
                        if(t.getTable().get(i).hasStudent()) {
                            if(t.getTable().get(i).getBoardCellType() == PawnType.BLUE) {
                                b++;
                            }
                            else if(t.getTable().get(i).getBoardCellType() == PawnType.GREEN) {
                                g++;
                            }
                            else if(t.getTable().get(i).getBoardCellType() == PawnType.RED) {
                                r++;
                            }
                            else if(t.getTable().get(i).getBoardCellType() == PawnType.PINK) {
                                p++;
                            }
                            else if(t.getTable().get(i).getBoardCellType() == PawnType.YELLOW) {
                                y++;
                            }
                        }
                    }
                }
                students[0] = b;
                students[1] = g;
                students[2] = r;
                students[3] = p;
                students[4] = y;
                }
            }
        return students;
    }

   /* implementazione senza la tabella, segue quella con
        public void showDiningRooms() {
        out.println(">Take a look at the other players' dining rooms!");
        for (Player p : modelView.getGameCopy().getActivePlayers()) {
            int[] students = getPlayerDiningRoom(p.getPlayerID());
            out.println("Player " + p.getNickname() + "has: ");
            out.print(ANSI_BLUE + students[0] + "blue students\n" + ANSI_RESET);
            out.print(ANSI_GREEN + students[1] + "green students\n" + ANSI_RESET);
            out.print(ANSI_RED + students[2] + "red students\n" + ANSI_RESET);
            out.print(ANSI_PURPLE + students[3] + "pink students\n" + ANSI_RESET);
            out.print(ANSI_YELLOW + students[4] + "yellow students\n" + ANSI_RESET);
        }
    }*/

    public void showOtherDiningRooms() {
        System.out.println(">Take a look at the other players' dining rooms!\n");

        for (int i=0; i<modelView.getGameCopy().getActivePlayers().size(); i++) {
            showDiningRoom(modelView.getGameCopy().getActivePlayers().get(i));
        }
        /*CLITable st = new CLITable();
        //st.setShowVerticalLines(true);
        ArrayList<Player> players = modelView.getGameCopy().getActivePlayers();
        if(players.size()==4) {
            st.setHeaders(players.get(0).getNickname(), players.get(1).getNickname(), players.get(2).getNickname(), players.get(3).getNickname());
        } else if(players.size()==3) {
            st.setHeaders(players.get(0).getNickname(), players.get(1).getNickname(), players.get(2).getNickname());
        }
        else if(players.size()==2) {
            st.setHeaders(players.get(0).getNickname(), players.get(1).getNickname());
        }
        for (Player p : modelView.getGameCopy().getActivePlayers()) {
            st.addRow(ANSI_BLUE + "Blue: ", Integer.toString(getPlayerDiningRoom(p.getPlayerID())[0]) + ANSI_RESET);
            st.addRow(ANSI_GREEN + "Green: ", Integer.toString(getPlayerDiningRoom(p.getPlayerID())[1]) + ANSI_RESET);
            st.addRow(ANSI_RED + "Red: ", Integer.toString(getPlayerDiningRoom(p.getPlayerID())[2]) + ANSI_RESET);
            st.addRow(ANSI_PURPLE + "Pink: ", Integer.toString(getPlayerDiningRoom(p.getPlayerID())[3]) + ANSI_RESET);
            st.addRow(ANSI_YELLOW + "Yellow: ", Integer.toString(getPlayerDiningRoom(p.getPlayerID())[4]) + ANSI_RESET);
            st.print();
        }*/
    }

    public void showDiningRoom(Player p) {
        CLITable st = new CLITable();

        if(modelView.isFourPlayers()) {
            st.setHeaders(p.getNickname().toString() + " team " + p.getIdTeam());
        } else {
            st.setHeaders(p.getNickname().toString());
        }

        st.addRow(ANSI_BLUE + "• [" + Integer.toString(getPlayerDiningRoom(p.getNickname())[0]) + "]" + " - Professor : " + modelView.hasBlueProfessor(p) + ANSI_RESET);
        st.addRow(ANSI_GREEN + "• [" + Integer.toString(getPlayerDiningRoom(p.getNickname())[1]) + "]" + " - Professor : " + modelView.hasGreenProfessor(p) + ANSI_RESET);
        st.addRow(ANSI_RED + "• [" + Integer.toString(getPlayerDiningRoom(p.getNickname())[2]) + "]" + " - Professor : " + modelView.hasRedProfessor(p) + ANSI_RESET);
        st.addRow(ANSI_PURPLE + "• [" + Integer.toString(getPlayerDiningRoom(p.getNickname())[3]) + "]" + " - Professor : " + modelView.hasPinkProfessor(p) + ANSI_RESET);
        st.addRow(ANSI_YELLOW + "• [" + Integer.toString(getPlayerDiningRoom(p.getNickname())[4]) + "]" + " - Professor : " + modelView.hasYellowProfessor(p) + ANSI_RESET);
        st.print();
    }

    public void showPawnType() {
        ArrayList<PawnType> pawns = new ArrayList<>();
        pawns.add(PawnType.BLUE);
        pawns.add(PawnType.GREEN);
        pawns.add(PawnType.RED);
        pawns.add(PawnType.YELLOW);
        pawns.add(PawnType.PINK);

        for(PawnType type : pawns) {
            System.out.println(printColor(type) + type.toString() + ANSI_RESET);
        }
        System.out.println("\n");
    }

    public void askMoves(AssistantCard card) {
        showMotherNature();
        if(modelView.isMagicPostmanAction()) {
            System.out.println(">Pick a number of mother nature moves between 1 and "
                    + (modelView.getGameCopy().getCurrentPlayer().getChosenAssistant().getMoves() + 2));
        } else {
            System.out.println(">Pick a number of mother nature moves between 1 and "
                    + modelView.getGameCopy().getCurrentPlayer().getChosenAssistant().getMoves());
        }

        //private Parser parser;
        //private String chosenWizard;
        //private String chosenNickname;
        //in.reset();
        System.out.print(">");
        Scanner input = new Scanner(System.in);
        String chosenMoves = input.nextLine();
        if(chosenMoves.toUpperCase().equalsIgnoreCase("QUIT")) {
            virtualClient.firePropertyChange("Quit", null, "Quit");
        } else {
            virtualClient.firePropertyChange("PickMovesNumber", null, chosenMoves);
        }

    }

    public void printPlayerDeck() {
        System.out.println(">Take a look at your deck before choosing: ");
        for (AssistantCard card : modelView.getGameCopy().getCurrentPlayer().getAssistantDeck().getDeck()) {
            System.out.println("-Name: " + card.getName() + "," + "Value: " + card.getValue() + "," + "Moves: " + card.getMoves());
        }
    }

    //alla fine del turno rimuovere le lastAssistantsUsed
    public void askAssistant() {
        if(modelView.getGameCopy().getGameBoard().getLastAssistantUsed().size() > 0) {
            showLastAssistantsUsed();
        }
        System.out.println(">Pick an assistant from your deck by typing its name ");
        if(modelView.getGameCopy().getGameBoard().getLastAssistantUsed().size()>=1) {
            System.out.println(">Remember: you can't play an assistant already played by another player!");
        }
        printPlayerDeck();
        System.out.print(">");
        Scanner input = new Scanner(System.in);
        String chosenAssistant = input.nextLine();
        if(chosenAssistant.equalsIgnoreCase("QUIT")) {
            virtualClient.firePropertyChange("Quit", null, "Quit");
        }
        virtualClient.firePropertyChange("PickAssistant", null, chosenAssistant);
    }

    public void askCloud(ArrayList<CloudTile> clouds) {
        System.out.println(">Pick a cloud by typing its ID: ");
        showClouds();
        System.out.print(">");
        Scanner input = new Scanner(System.in);
        String chosenCloud = input.nextLine();
        if(chosenCloud.toUpperCase().equalsIgnoreCase("QUIT")) {
            virtualClient.firePropertyChange("Quit", null, "Quit");
        } else {
            virtualClient.firePropertyChange("PickCloud", null, chosenCloud);

        }
    }

    //chiamato dal controller, quando ritorna lo studente esso viene salvato in una variabile
    //che verrà poi usata per la chosenDestination nella userAction successiva
    public void askStudent(SchoolBoard schoolB) {
        System.out.println(">Pick a student from your Entrance by typing its color: ");
        System.out.println("[RED, BLUE, YELLOW, GREEN, PINK]");
        showEntrance();
        System.out.print(">");
        Scanner input = new Scanner(System.in);
        String chosenStudent = input.nextLine();
        if(chosenStudent.toUpperCase().equalsIgnoreCase("QUIT")) {
            virtualClient.firePropertyChange("Quit", null, "Quit");
        } else {
            virtualClient.firePropertyChange("PickStudent", null, chosenStudent);
        }
    }

    //prende come input lo studente scelto nella richiesta precedente
    public void askDestination() {
        System.out.println(">Pick a destination between your DiningRoom or an island [DiningRoom / island]:");
        System.out.print(">");
        Scanner input = new Scanner(System.in);
        String chosenDestination = input.nextLine();
        if(chosenDestination.toUpperCase().equalsIgnoreCase("QUIT")) {
            virtualClient.firePropertyChange("Quit", null, "Quit");
        } else {
            virtualClient.firePropertyChange("PickDestination", null, chosenDestination);
        }
    }

    public void askCharacterCard(ArrayList<CharacterCard> cards) {
        if (modelView.getGameCopy().isExpertMode()) {
            showCoins();
            System.out.println(">Type the name of the character card you want to play [\"NONE\" if you don't want to play one]: ");
            showCharactersDescription();
            System.out.print(">");
            Scanner input = new Scanner(System.in);
            String chosenCharacter = input.nextLine();
            if(chosenCharacter.equalsIgnoreCase("QUIT")) {
                virtualClient.firePropertyChange("Quit", null, "Quit");
            } else {
                virtualClient.firePropertyChange("PickCharacter", null, chosenCharacter);
            }
        }
    }

    public void askIsland(ArrayList<Island> islands) {
        if(modelView.isGrannyHerbsAction()) {
            System.out.println("GrannyHerbs action phase... put a tile into an island!");
        }
        if(modelView.isMonkAction()) {
            System.out.println("Monk action phase... choose an island where in which you want to put monk's student!");

        }
        System.out.println(">Choose an island by typing its ID: ");
        showIslandsTable();
        System.out.print(">");
        Scanner input = new Scanner(System.in);
        String chosenIsland = input.nextLine();
        if(chosenIsland.equalsIgnoreCase("QUIT")) {
            virtualClient.firePropertyChange("Quit", null, "Quit");
        } else {
            virtualClient.firePropertyChange("PickIsland", null, chosenIsland);
        }

        if(modelView.isGrannyHerbsAction()) {
            modelView.setGrannyHerbsAction(false);
        }
        if(modelView.isMonkAction()) {
            modelView.setMonkAction(false);
        }
    }

    public void askPawnType() {
        if(modelView.isMinestrelAction()) {
            System.out.println(">Choose a pawn type of a student from your diningroom: ");
            showDiningRoom(modelView.getGameCopy().getCurrentPlayer());
        }
        System.out.println(">Choose a pawn type: ");
        showPawnType();
        System.out.print(">");
        Scanner input = new Scanner(System.in);
        String chosenPawnType = input.nextLine();
        System.out.println("Typed " + chosenPawnType);
        if(chosenPawnType.equalsIgnoreCase("QUIT")) {
            virtualClient.firePropertyChange("Quit", null, "Quit");
        } else {
            if(modelView.isMinestrelAction()) {
                virtualClient.firePropertyChange("PickStudent", null, chosenPawnType);
            } else {
                virtualClient.firePropertyChange("PickPawnType", null, chosenPawnType);
            }

        }
    }

    public void askStudentMonk(CharacterCard monk) {
        System.out.println(">Choose a student from monk's students: ");
        showCharacterStudents(monk);

    }

    public void showCharacterStudent(CharacterCard card) {
        for(Student s : card.getStudents()) {
            System.out.print("•" + printColor(s.getType()) + s.getType() + ANSI_RESET);
        }
    }


    public void askStudentJester(CharacterCard jester) {
        System.out.println(">Choose a student from jester's students: ");
        showCharacterStudents(jester);

    }

    private void showCharacterStudents(CharacterCard card) {
        showCharacterStudent(card);
        System.out.println("\n");
        System.out.print(">");
        Scanner input = new Scanner(System.in);
        String jesterStudent = input.nextLine();
        System.out.println("Typed " + jesterStudent);
        if(jesterStudent.equalsIgnoreCase("QUIT")) {
            virtualClient.firePropertyChange("Quit", null, "Quit");
        } else {
            virtualClient.firePropertyChange("PickStudent", null, jesterStudent);
        }
    }


    public void askCharacterActionsNumber() {
        System.out.println("How many students from dining room to entrance do you want to change? [1,2 for Minestrel, 1,2,3, for Jester]");
        int moves;
        while(true) {
            try {
                System.out.print(">");
                moves = in.nextInt();
                if((moves == 1 || moves == 2) && modelView.isMinestrelAction()) break;
                else if((moves == 1 || moves == 2 || moves == 3) && modelView.isJesterAction()) break;
                else {
                    System.out.println("Choose [1,2] for Minestrel, [1,2,3] for Jester");
                }
            } catch(NumberFormatException e) {
                showError("Error: NumberFormatException. Please insert a number!");
                askCharacterActionsNumber();
            }

        }
        clientConnection.sendUserInput(new PickCharacterActionsNum(moves));
    }
    public void askStudentPrincess(CharacterCard princess) {
        System.out.println(">Choose a student from princess's students: ");
        showCharacterStudent(princess);
        System.out.println("\n");
        System.out.print(">");
        Scanner input = new Scanner(System.in);
        String princessStudent = input.nextLine();
        if(princessStudent.equalsIgnoreCase("QUIT")) {
            virtualClient.firePropertyChange("Quit", null, "Quit");
        } else {
            virtualClient.firePropertyChange("PickStudent", null, princessStudent);
        }
    }

    public void showMotherNature() {
        System.out.println(">Now Mother Nature is on island " + modelView.getGameCopy().getGameBoard().getMotherNature().getPosition());
    }

    public void noWinnerGame() {
        System.exit(0);
    }

    public void chooseExpertMode() {
        String expertModeChoice;
        //System.out.println("Sono in chooseExpertMode");
        System.out.print(">");
        expertModeChoice = in.nextLine();
        //System.out.println("Ho fatto l'assegnamento: della expert mode " + expertModeChoice);
        clientConnection.sendUserInput(new ExpertModeChoice(expertModeChoice));
    }

    public void chooseWizard(List<Wizards> availableWizards) {
        while (true) {
            System.out.println(">Choose your wizard!");
            try {
                System.out.print(">");
                String wizardTyped = in.nextLine();
                Wizards wizardChosen = Wizards.parseWizardInput(wizardTyped);
                if(availableWizards.contains(wizardChosen)) {
                    clientConnection.sendUserInput(new WizardChoice(wizardChosen));
                    modelView.setGameStarted(true);
                    return;
                } else {
                    System.out.println("Wizard not available!");
                    System.out.println(">Choose your wizard!");
                }
            } catch (IllegalArgumentException e) {
                System.err.println(ANSI_RED + "Invalid input! Please provide one of the accepted wizards."+ ANSI_RESET);
                System.out.print(">");
            }
        }
    }

    public void choosePlayerNumber() {
        //System.out.println("Sono in choosePlayerNumber");
        int numOfPlayer;
        while (true) {
            try {
                Scanner inputNum = new Scanner(System.in);
                System.out.print(">");
                numOfPlayer = inputNum.nextInt();
                //out.println("Ho fatto l'assegnamento del player"); //SPOILER: NON CI ARRIVA!
                /*
                if(numOfPlayer == 4) {
                    modelView.setFourPlayers(true);
                }

                 */

                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid parameter, it must be a numeric value.");
                choosePlayerNumber();
            }
        }
        clientConnection.sendUserInput(new PlayersNumberChoice(numOfPlayer));
    }

    public void showServerMessage(Answer serverAnswer) {
        System.out.println(serverAnswer.getMessage());
    }

    public void showError(String message) {
        System.out.println(ANSI_RED + ">Warning! " + message + ANSI_RESET);
    }

    public void showWinMessage() {
        System.out.println(">Game over!" + ANSI_RED + "You are the winner!" + ANSI_RESET);
    }

    public void showLoseMessage(String winnerNickname) {
        System.out.println(">Game Over! You lost :(");
        if(modelView.isFourPlayers()) {
            ArrayList<String> winners = new ArrayList<>();
            int winnerTeam = -1;
            for(Player p : modelView.getGameCopy().getPlayers()) {
                if(p.getNickname() == winnerNickname) {
                    winnerTeam = p.getIdTeam();
                }
            }

            for(Player p : modelView.getGameCopy().getPlayers()) {
                if(p.getIdTeam() == winnerTeam) {
                    winners.add(p.getNickname());
                }
            }

            System.out.println("The winner are " + ANSI_RED + winners.get(0) + " & " + winners.get(1) + ANSI_RESET + " from team " + winnerTeam);
        } else {
            System.out.println("The winner is " + ANSI_RED + winnerNickname + ANSI_RESET);
        }

    }

    public void userNicknameSetup() {
        //System.out.println("Entro in usernameSetup");

        String userNickname = null;
        boolean nickCheck = false;

        while (!nickCheck) {
            do {
                System.out.println(">Please, insert your nickname: ");
                System.out.print(">");
                userNickname = in.nextLine();
            } while (userNickname == null);
            System.out.println(">Your nickname is: " + userNickname);
            System.out.println(">Is it right? [y/n] ");
            System.out.print(">");
            if (in.nextLine().equalsIgnoreCase("y")) {
                nickCheck = true;
            } else {
                userNickname = null;
            }
        }
        clientConnection = new ClientConnection();
        modelView.setPlayerNickname(userNickname);
        try {
            if (!clientConnection.setupNickname(userNickname, modelView, actionHandler)) {
                System.err.println("The entered IP/port doesn't match any active server or the server is not " + "running. Please try again!");
                System.exit(-1);
            }
            System.out.println("Socket Connection setup completed!");
        } catch (DuplicateNicknameException e) {
            //e.printStackTrace();
            userNicknameSetup();
        }
        virtualClient.addPropertyChangeListener(new Parser(clientConnection, modelView));
    }

    /*
    public void noActionsLoop() {
        in.reset();
        String cmd = in.nextLine();
        //showError("It's not your turn :(");
        virtualClient.firePropertyChange("noAction", null, cmd);
    }*/

    @Override
    public void run() {
        //System.out.println("Entro in run");

        userNicknameSetup();
        /*
        while (isActiveGame()) {
            //TODO: (maybe) permettere di scrivere sempre
            if(modelView.isGameStarted()) {
                if (!modelView.isStartPlaying()) {
                    System.out.println("Entro dentro all'action");
                    noActionsLoop();
                }
            }
        }

         */
        while(true) {
            if(!activeGame) {
                break;
            }
        }
        //in.close();
        out.close();
    }

    public void endGameMessage() {
        System.out.println("Thanks to have played Eriantys!");
        System.out.println("Application will now close...");
        System.exit(0);
    }

    public void initialGamePhaseHandler(String serverCommand) {
        //System.out.println("Sono entrato in initialGamePhaseHandler perchè ho letto: " + serverCommand);
        switch(serverCommand) {

            case "RequestPlayerNumber" -> {
                showServerMessage(modelView.getServerAnswer());
                choosePlayerNumber();
            }
            case "ExpertModeAnswer" -> {
                showServerMessage(modelView.getServerAnswer());
                chooseExpertMode();
            }
            case "RequestWizard"-> {
                out.println(((WizardAnswer) modelView.getServerAnswer()).getMessage() + "\nRemaining:");
                ((WizardAnswer) modelView.getServerAnswer()).getWizardsLeft().forEach(wizardLeft -> out.println(wizardLeft + ", "));
                //System.out.println("\n");
                chooseWizard(((WizardAnswer) modelView.getServerAnswer()).getWizardsLeft());
            }
            default -> out.println("Nothing to do");
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the magic world of:");
        System.out.println(" .----------------.  .----------------.  .----------------.  .----------------.  .-----------------. .----------------.  .----------------.  .----------------. \n" +
                "| .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |\n" +
                "| |  _________   | || |  _______     | || |     _____    | || |      __      | || | ____  _____  | || |  _________   | || |  ____  ____  | || |    _______   | |\n" +
                "| | |_   ___  |  | || | |_   __ \\    | || |    |_   _|   | || |     /  \\     | || ||_   \\|_   _| | || | |  _   _  |  | || | |_  _||_  _| | || |   /  ___  |  | |\n" +
                "| |   | |_  \\_|  | || |   | |__) |   | || |      | |     | || |    / /\\ \\    | || |  |   \\ | |   | || | |_/ | | \\_|  | || |   \\ \\  / /   | || |  |  (__ \\_|  | |\n" +
                "| |   |  _|  _   | || |   |  __ /    | || |      | |     | || |   / ____ \\   | || |  | |\\ \\| |   | || |     | |      | || |    \\ \\/ /    | || |   '.___`-.   | |\n" +
                "| |  _| |___/ |  | || |  _| |  \\ \\_  | || |     _| |_    | || | _/ /    \\ \\_ | || | _| |_\\   |_  | || |    _| |_     | || |    _|  |_    | || |  |`\\____) |  | |\n" +
                "| | |_________|  | || | |____| |___| | || |    |_____|   | || ||____|  |____|| || ||_____|\\____| | || |   |_____|    | || |   |______|   | || |  |_______.'  | |\n" +
                "| |              | || |              | || |              | || |              | || |              | || |              | || |              | || |              | |\n" +
                "| '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |\n" +
                " '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------' ");
        System.out.println("Let's start your client configuration!\n");
        Scanner clientInput = new Scanner(System.in);
        System.out.println(">Please, insert the server IP address:");
        System.out.print(">");
        String ipServerAddress = clientInput.nextLine();
        System.out.println(">Please, insert the server port");
        System.out.print(">");
        int serverPort = clientInput.nextInt();
        Constants.setAddress(ipServerAddress);
        Constants.setPort(serverPort);
        CLI cli = new CLI();

        cli.run();
        //System.out.println("Arrivo dopo qui");

    }

    public void showCharacters(AssistantDeck deck) {
        System.out.println(">Take a look at your deck before choosing: ");
        System.out.println(deck.getDeck().size());

        for(int i = 0; i < 10; i++) {
            System.out.println("(Name: " + String.valueOf(deck.getDeck().get(i).getName()) + ", " + "Value: " + deck.getDeck().get(i).getValue() + ", " + "Moves: " + deck.getDeck().get(i).getMoves());
        }
    }

    public void showServerError() {
        if(((ServerError) modelView.getServerAnswer()).getError() == ServerErrorTypes.FULLGAMESERVER) {
            showError("Server is full... please try again later!");
            System.exit(-1);
        }
    }

    public void attributeTowers() {
        for(Player p : modelView.getGameCopy().getActivePlayers()) {
            if(p.getBoard().getTowerArea().getTowerArea().get(0).getColor() == TowerColor.BLACK) {
                blackP = p.getNickname();

            } else if (p.getBoard().getTowerArea().getTowerArea().get(0).getColor() == TowerColor.GREY) {
                greyP = p.getNickname();


            } else if(p.getBoard().getTowerArea().getTowerArea().get(0).getColor() == TowerColor.WHITE) {
                whiteP = p.getNickname();
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent changeEvent) {
        String serverCommand = (changeEvent.getNewValue() != null) ? changeEvent.getNewValue().toString() : null;
        //System.out.println("PropertyChange arrivato: " + serverCommand);
        switch(changeEvent.getPropertyName()) {
            case "InitialGamePhase" -> {
                assert serverCommand != null;
                //System.out.println("Sono in property change e ho letto:" + serverCommand);
                initialGamePhaseHandler(serverCommand);
            }
            case "DynamicAnswer" -> //System.out.println("Sono in propertyChange e ho letto una Dynamic Answer");
                    showServerMessage(modelView.getServerAnswer());
            case "ActionPhase" -> {
                assert serverCommand != null;
                actionHandler.makeAction(serverCommand);
            }
            case "UpdateModelView" -> {
                assert serverCommand != null;

                System.out.println("Current player is" + modelView.getGameCopy().getCurrentPlayer().getNickname());
                if(modelView.isAction()) {
                    if(firstTurn) {
                        attributeTowers();
                        firstTurn = false;
                    }
                    showIslandsTable();
                    showClouds();
                    //showMotherNature();
                    showAvailableCharacters();
                    showCoins();
                    showOtherDiningRooms();
                }
                if(modelView.isPianification()) {
                    showAvailableCharacters();

                }
            }

            case "WinMessage" -> {
                assert serverCommand != null;
                setActiveGame(false);
                showWinMessage();
                endGameMessage();
            }
            case "LoseMessage" -> {
                assert serverCommand != null;
                setActiveGame(false);
                showLoseMessage(changeEvent.getNewValue().toString());
                endGameMessage();

            }
            default -> System.out.println("Unknown answer from server");
        }
    }
}