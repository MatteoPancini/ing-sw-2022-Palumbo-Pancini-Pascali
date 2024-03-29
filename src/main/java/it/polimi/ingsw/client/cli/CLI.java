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
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.enumerations.PawnType;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.enumerations.Wizards;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.player.Table;
import it.polimi.ingsw.model.player.Tower;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Scanner;

import static it.polimi.ingsw.constants.Constants.*;

/**
 * Class CLI manages the game if the user decides to play through command line
 */
public class CLI implements ListenerInterface {
    private final Scanner in;
    private ClientConnection clientConnection;
    private final ModelView modelView;
    private final ActionHandler actionHandler;
    private final PropertyChangeSupport virtualClient = new PropertyChangeSupport(this);
    private boolean firstTurn = true;
    private String whiteP = "";
    private String greyP = "";
    private String blackP = "";

    /**
     * CLI constructor
     */
    public CLI() {
        in = new Scanner(System.in);
        modelView = new ModelView(this);
        actionHandler = new ActionHandler(this, modelView);
    }

    /**
     * Method printTowers prints the different towers on the islands
     * @param isl island
     * @return towers on island
     */
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

    public Scanner getIn() {
        return in;
    }

    /**
     * Method printYellowStudentsOnIsland prints the number of yellow students on a given island
     * @param isl island
     * @return students number
     */
    public Integer printYellowStudentsOnIsland(Island isl) {
        int y = 0;
        for(int j=0; j < isl.getStudents().size(); j++) {
            if(isl.getStudents().get(j).getType().toString().equalsIgnoreCase("YELLOW")) {
                y++;
            }
        }
        return y;
    }
    /**
     * Method printBlueStudentsOnIsland prints the number of blue students on a given island
     * @param isl island
     * @return students number
     */
    public Integer printBlueStudentsOnIsland(Island isl) {
        int b = 0;
        for(int j=0; j < isl.getStudents().size(); j++) {
            if(isl.getStudents().get(j).getType().toString().equalsIgnoreCase("BLUE")) {
                b++;
            }
        }
        return b;
    }
    /**
     * Method printPinkStudentsOnIsland prints the number of pink students on a given island
     * @param isl island
     * @return students number
     */
    public Integer printPinkStudentsOnIsland(Island isl) {
        int p = 0;
        for(int j=0; j < isl.getStudents().size(); j++) {
            if(isl.getStudents().get(j).getType().toString().equalsIgnoreCase("PINK")) {
                p++;
            }
        }
        return p;
    }
    /**
     * Method printGreenStudentsOnIsland prints the number of green students on a given island
     * @param isl island
     * @return students number
     */
    public Integer printGreenStudentsOnIsland(Island isl) {
        int g = 0;
        for(int j=0; j < isl.getStudents().size(); j++) {
            if(isl.getStudents().get(j).getType().toString().equalsIgnoreCase("GREEN")) {
                g++;
            }
        }
        return g;
    }
    /**
     * Method printRedStudentsOnIsland prints the number of red students on a given island
     * @param isl island
     * @return students number
     */
    public Integer printRedStudentsOnIsland(Island isl) {
        int r = 0;
        for(int j=0; j < isl.getStudents().size(); j++) {
            if(isl.getStudents().get(j).getType().toString().equalsIgnoreCase("RED")) {
                r++;
            }
        }
        return r;
    }

    /**
     * Method showAvailableCharacters shows the playabale character cards to the user
     */
    public void showAvailableCharacters() {
        if (modelView.getGameCopy().isExpertMode()) {
            System.out.println(">The available characters for this match are: ");
            showCharactersDescription();
        }
    }

    /**
     * Method showCharacterDescription shows the character cards' effect to the user when he can play them
     */
    public void showCharactersDescription() {
        for(int i = 0; i < modelView.getGameCopy().getGameBoard().getPlayableCharacters().size(); i++) {
            CLITable st = new CLITable();
            st.setShowVerticalLines(true);
            st.setHeaders(modelView.getGameCopy().getGameBoard().getPlayableCharacters().get(i).getName().toString());
            st.addRow("Effect: " + modelView.getGameCopy().getGameBoard().getPlayableCharacters().get(i).getEffect());
            st.addRow("Cost: " + modelView.getGameCopy().getGameBoard().getPlayableCharacters().get(i).getInitialCost());
            st.print();
        }
    }
    /**
     * Method studentsOnIsland shows the students on a given island
     * @param i island
     * @return number and type of students
     */
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
        }
        for(int j=0; j < stud.length; j++) {
            if (stud[j] != null) {
                if(j==0) {

                    s = new StringBuilder(stud[0]);
                }
                else {
                    s.append(", ").append(stud[j]);
                }
            }
        }
        return s.toString();
    }

    /**
     * Method isMerged shows merged islands
     * @param island a given island
     * @return visualization string
     */
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
                                s = new StringBuilder(merged[0]);
                            }
                            else {
                                s.append(", ").append(merged[i]);
                            }
                        }
                    }
                }
            }
        }
        return s.toString();
    }

    /**
     * Method showIslandsTable shows the islands status using a table
     */
    public void showIslandsTable() {
        System.out.println(">Here's a little description of the islands in the game board. [Towers' legend: ▲ = Grey \"" + greyP + "\", █ = Black \"" + blackP + "\", ○ = White \"" + whiteP + "\"]");

        CLITable st = new CLITable();
        st.setHeaders("Island ID", "Merged Islands", "Students & Towers");

        for(int i = 0; i < modelView.getGameCopy().getGameBoard().getIslands().size(); i++) {
            if(modelView.getGameCopy().getGameBoard().getIslands().get(i).getNoEntry()) {
                st.addRow(Integer.toString(modelView.getGameCopy().getGameBoard().getIslands().get(i).getIslandID()), isMerged(modelView.getGameCopy().getGameBoard().getIslands().get(i)), ANSI_RED + "X " + ANSI_RESET + studentsOnIsland(modelView.getGameCopy().getGameBoard().getIslands().get(i)) + "          " + printTowers(modelView.getGameCopy().getGameBoard().getIslands().get(i)));
            } else {
                st.addRow(Integer.toString(modelView.getGameCopy().getGameBoard().getIslands().get(i).getIslandID()), isMerged(modelView.getGameCopy().getGameBoard().getIslands().get(i)), studentsOnIsland(modelView.getGameCopy().getGameBoard().getIslands().get(i)) + "          " + printTowers(modelView.getGameCopy().getGameBoard().getIslands().get(i)));

            }

        }
        showMotherNature();
        st.print();
    }

    /**
     * Method showLastAssistantUsed shows the deck of all the assistants used in the current turn
     */
    public void showLastAssistantsUsed() {
        System.out.println(">These are all the assistants used in this turn: ");
        CLITable st = new CLITable();
        st.setShowVerticalLines(true);

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

    /**
     * Method printStudentsOnCloud shows the students on a given cloud
     * @param ID cloud id
     * @return visualization string
     */
    public String printStudentsOnCloud(int ID) {
        StringBuilder str = new StringBuilder();
        for(Student s : modelView.getGameCopy().getGameBoard().getClouds().get(ID - 1).getStudents()) {
            str.append(printColor(s.getType())).append("•").append(ANSI_RESET);
        }
        return str.toString();
    }

    /**
     * Method showCLouds shows the clouds status to the user
     */
    public void showClouds() {
        System.out.println(">Clouds status of this turn: ");
        CLITable st = new CLITable();
        st.setHeaders("Cloud ID", "Students");
        for(int i = 0; i < modelView.getGameCopy().getGameBoard().getClouds().size(); i++) {
            if(modelView.getGameCopy().getGameBoard().getClouds().get(i).getStudents() != null) {
                st.addRow(Integer.toString(modelView.getGameCopy().getGameBoard().getClouds().get(i).getID()), printStudentsOnCloud(modelView.getGameCopy().getGameBoard().getClouds().get(i).getID()));
            }
        }
        st.print();
    }

    /**
     * Method printColor is used to color the command line with the student color
     * @param p student pawn type
     * @return ansi color corresponding to the given pawn type
     */
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

    /**
     * Method showEntrance shows the player's entrance
     */
    public void showEntrance() {
        System.out.println(">Here's a summary of the students in your entrance: ");
        for(Student s : modelView.getGameCopy().getCurrentPlayer().getBoard().getEntrance().getStudents()) {
            System.out.print("•" + printColor(s.getType()) + s.getType() + ANSI_RESET);
        }
        System.out.println("\n");
    }

    /**
     * Method showCoins shows the user's available coins if playing expert mode
     */
    public void showCoins() {
        if (modelView.getGameCopy().isExpertMode()) {
            System.out.println(">You have " + ANSI_YELLOW + modelView.getGameCopy().getCurrentPlayer().getMyCoins() + " coins left." + ANSI_RESET);
        }
    }

    /**
     * Method getPlayerDiningRoom returns the students present in a player's dining room
     * @param name player nickname
     * @return number of students per type
     */
    public int[] getPlayerDiningRoom(String name) {
        int r=0, p=0, g=0, y=0, b=0;
        int[] students = new int[11];
        for(Player pl : modelView.getGameCopy().getActivePlayers()) {
            if(pl.getNickname().equals(name)) {
                for(Table t : pl.getBoard().getDiningRoom().getDiningRoom()) {
                    for(int i = 0; i < t.getDiningTable().size(); i++) {
                        if(t.getDiningTable().get(i).hasStudent()) {
                            if(t.getDiningTable().get(i).getBoardCellType() == PawnType.BLUE) {
                                b++;
                            }
                            else if(t.getDiningTable().get(i).getBoardCellType() == PawnType.GREEN) {
                                g++;
                            }
                            else if(t.getDiningTable().get(i).getBoardCellType() == PawnType.RED) {
                                r++;
                            }
                            else if(t.getDiningTable().get(i).getBoardCellType() == PawnType.PINK) {
                                p++;
                            }
                            else if(t.getDiningTable().get(i).getBoardCellType() == PawnType.YELLOW) {
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

    /**
     * Method showOtherDiningRooms shows the other players' dining rooms
     */
    public void showOtherDiningRooms() {
        System.out.println(">Take a look at the other players' dining rooms!\n");

        for (int i=0; i<modelView.getGameCopy().getActivePlayers().size(); i++) {
            showDiningRoom(modelView.getGameCopy().getActivePlayers().get(i));
        }
    }

    /**
     * Method showDiningRoom prints a single player's dining room
     * @param p player
     */
    public void showDiningRoom(Player p) {
        CLITable st = new CLITable();

        if(modelView.isFourPlayers()) {
            st.setHeaders(p.getNickname() + " team " + p.getIdTeam());
        } else {
            st.setHeaders(p.getNickname());
        }

        st.addRow(ANSI_BLUE + "• [" + getPlayerDiningRoom(p.getNickname())[0] + "]" + " - Professor : " + modelView.hasBlueProfessor(p) + ANSI_RESET);
        st.addRow(ANSI_GREEN + "• [" + getPlayerDiningRoom(p.getNickname())[1] + "]" + " - Professor : " + modelView.hasGreenProfessor(p) + ANSI_RESET);
        st.addRow(ANSI_RED + "• [" + getPlayerDiningRoom(p.getNickname())[2] + "]" + " - Professor : " + modelView.hasRedProfessor(p) + ANSI_RESET);
        st.addRow(ANSI_PURPLE + "• [" + getPlayerDiningRoom(p.getNickname())[3] + "]" + " - Professor : " + modelView.hasPinkProfessor(p) + ANSI_RESET);
        st.addRow(ANSI_YELLOW + "• [" + getPlayerDiningRoom(p.getNickname())[4] + "]" + " - Professor : " + modelView.hasYellowProfessor(p) + ANSI_RESET);
        st.print();
    }

    /**
     * Method showPawnType prints the pawns types
     */
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

    /** Method askMoves prints the moves request arriving from the server
     */
    public void askMoves() {
        showIslandsTable();
        if(modelView.isMagicPostmanAction()) {
            System.out.println(">Pick a number of mother nature moves between 1 and "
                    + (modelView.getGameCopy().getCurrentPlayer().getChosenAssistant().getMoves() + 2));
        } else {
            System.out.println(">Pick a number of mother nature moves between 1 and "
                    + modelView.getGameCopy().getCurrentPlayer().getChosenAssistant().getMoves());
        }

        System.out.print(">");
        Scanner input = new Scanner(System.in);
        String chosenMoves = input.nextLine();
        if(chosenMoves.equalsIgnoreCase("QUIT")) {
            virtualClient.firePropertyChange("Quit", null, "Quit");
        } else {
            virtualClient.firePropertyChange("PickMovesNumber", null, chosenMoves);
        }

    }

    /**
     * Method printPlayerDeck prints the deck of the assistant cards left
     */
    public void printPlayerDeck() {
        System.out.println(">Take a look at your deck before choosing: ");
        for (AssistantCard card : modelView.getGameCopy().getCurrentPlayer().getAssistantDeck().getDeck()) {
            System.out.println("-Name: " + card.getName() + "," + "Value: " + card.getValue() + "," + "Moves: " + card.getMoves());
        }
    }

    /**
     * Method askAssistant prints the assistant request arriving from the server
     */
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

    /**
     * Method askCloud prints the cloud request arriving from the server
     */
    public void askCloud() {
        System.out.println(">Pick a cloud by typing its ID: ");
        showClouds();
        System.out.print(">");
        Scanner input = new Scanner(System.in);
        String chosenCloud = input.nextLine();
        if(chosenCloud.equalsIgnoreCase("QUIT")) {
            virtualClient.firePropertyChange("Quit", null, "Quit");
        } else {
            virtualClient.firePropertyChange("PickCloud", null, chosenCloud);

        }
    }

    /**
     * Method askStudent prints the student request from the entrance arriving from the server
     */
    public void askStudent() {
        System.out.println(">Pick a student from your Entrance by typing its color: ");
        System.out.println("[RED, BLUE, YELLOW, GREEN, PINK]");
        showEntrance();
        System.out.print(">");
        Scanner input = new Scanner(System.in);
        String chosenStudent = input.nextLine();
        if(chosenStudent.equalsIgnoreCase("QUIT")) {
            virtualClient.firePropertyChange("Quit", null, "Quit");
        } else {
            virtualClient.firePropertyChange("PickStudent", null, chosenStudent);
        }
    }

    /**
     * Method askDestination prints the destination request arriving from the server (island or dining room)
     */
    public void askDestination() {
        System.out.println(">Pick a destination between your DiningRoom or an island [DiningRoom / island]:");
        System.out.print(">");
        Scanner input = new Scanner(System.in);
        String chosenDestination = input.nextLine();
        if(chosenDestination.equalsIgnoreCase("QUIT")) {
            virtualClient.firePropertyChange("Quit", null, "Quit");
        } else {
            virtualClient.firePropertyChange("PickDestination", null, chosenDestination);
        }
    }

    /**
     * Method askCharacterCard prints the character card request arriving from the server
     */
    public void askCharacterCard() {
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

    /**
     * Method askIsland prints the island request arriving from the server
     */
    public void askIsland() {
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

    /**
     * Method askPawnType prints the student request arriving from the server, it is used to handle character cards with students on
     */
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

    /**
     * Method askStudentMonk is used to ask a student on the Monk card
     * @param monk character monk
     */
    public void askStudentMonk(CharacterCard monk) {
        System.out.println(">Choose a student from monk's students: ");
        askCharacterStudents(monk);

    }

    /**
     * Method showCharacterStudent shows the students on a character card if it has them
     * @param card character card
     */
    public void showCharacterStudent(CharacterCard card) {
        for(Student s : card.getStudents()) {
            System.out.print("•" + printColor(s.getType()) + s.getType() + ANSI_RESET);
        }
    }


    /**
     * Method askStudentJester is used to ask a student on the Jester card
     * @param jester character jester
     */
    public void askStudentJester(CharacterCard jester) {
        System.out.println(">Choose a student from jester's students: ");
        askCharacterStudents(jester);
    }

    /**
     * Method askCharacterStudents asks the character card and notifies the action parser through listeners
     * @param card character card
     */
    private void askCharacterStudents(CharacterCard card) {
        showCharacterStudent(card);
        System.out.println("\n");
        System.out.print(">");
        Scanner input = new Scanner(System.in);
        String jesterStudent = input.nextLine();
        if(jesterStudent.equalsIgnoreCase("QUIT")) {
            virtualClient.firePropertyChange("Quit", null, "Quit");
        } else {
            virtualClient.firePropertyChange("PickStudent", null, jesterStudent);
        }
    }


    /**
     * Method askCharacterActionsNumber asks the number of students the player wants to exchange
     */
    public void askCharacterActionsNumber() {
        System.out.println("How many students from dining room to entrance do you want to exchange? [1,2 for Minestrel, 1,2,3, for Jester]");
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

    /**
     * Method askStudentPrincess asks to choose a student from the character Spoiled Princess
     * @param princess princess card
     */
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

    /**
     * Method showMotherNature prints the mother nature's position
     */
    public void showMotherNature() {
        System.out.println(">Now Mother Nature is on island " + modelView.getGameCopy().getGameBoard().getMotherNature().getPosition());
    }


    /**
     * Method chooseExpertMode reads the player's expert mode choice and sends it to the server
     */
    public void chooseExpertMode() {
        String expertModeChoice;
        System.out.print(">");
        expertModeChoice = in.nextLine();
        clientConnection.sendUserInput(new ExpertModeChoice(expertModeChoice));
    }

    /**
     * Method chooseWizard asks the player to choose a wizard and then it sends it to the server
     * @param availableWizards available wizards (not already chosen by the other players)
     */
    public void chooseWizard(List<Wizards> availableWizards) {
        while (true) {
            System.out.println(">Choose your wizard!");
            try {
                System.out.print(">");
                String wizardTyped = in.nextLine();
                Wizards wizardChosen = Wizards.parseWizardInput(wizardTyped);
                if(availableWizards.contains(wizardChosen)) {
                    clientConnection.sendUserInput(new WizardChoice(wizardChosen));
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

    /**
     * Method choosePlayerNumber reads the player's choice of players number
     */
    public void choosePlayerNumber() {
        int numOfPlayer;
        while (true) {
            try {
                Scanner inputNum = new Scanner(System.in);
                System.out.print(">");
                numOfPlayer = inputNum.nextInt();

                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid parameter, it must be a numeric value.");
                choosePlayerNumber();
            }
        }
        clientConnection.sendUserInput(new PlayersNumberChoice(numOfPlayer));
    }

    /**
     * Method showServerMessage prints the server message when it is received
     * @param serverAnswer message coming from the server
     */
    public void showServerMessage(Answer serverAnswer) {
        System.out.println(serverAnswer.getMessage());
    }

    /**
     * Method showError prints in red a message error coming from the server
     * @param message server error message
     */
    public void showError(String message) {
        System.out.println(ANSI_RED + ">Warning! " + message + ANSI_RESET);
    }

    /**
     * Method prints the win message if the player has won
     */
    public void showWinMessage() {
        System.out.println(">Game Over! " + ANSI_RED + "You are the winner!" + ANSI_RESET);
    }

    /**
     * Method showLoseMessage prints the lost message if the player has lost, and it prints the winner's nickname
     * @param winnerNickname -> nickname of the winner
     */
    public void showLoseMessage(String winnerNickname) {
        System.out.println(">Game Over! You lost :(");
        if(modelView.isFourPlayers()) {
            ArrayList<String> winners = new ArrayList<>();
            int winnerTeam = -1;
            for(Player p : modelView.getGameCopy().getPlayers()) {
                if(p.getNickname().equals(winnerNickname)) {
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

    /**
     * Method userNicknameSetup sets the user nickname choice up to the server
     */
    public void userNicknameSetup() {

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
            userNicknameSetup();
        }
        virtualClient.addPropertyChangeListener(new Parser(clientConnection, modelView));
    }


    /**
     * Method endGameMessage shows the end game message and the application closing notification
     */
    public void endGameMessage() {
        System.out.println("Thanks for playing Eriantys!");
        System.out.println("Application will now close...");
        System.exit(0);
    }

    /**
     * Method initialGamePhaseHandler is used in the CLI property change to switch between the different server commands and ask the corresponding
     * @param serverCommand command sent from server
     */
    public void initialGamePhaseHandler(String serverCommand) {
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
                System.out.println(((WizardAnswer) modelView.getServerAnswer()).getMessage() + "\nRemaining:");
                ((WizardAnswer) modelView.getServerAnswer()).getWizardsLeft().forEach(wizardLeft -> System.out.println(wizardLeft + ", "));
                chooseWizard(((WizardAnswer) modelView.getServerAnswer()).getWizardsLeft());
            }
            default -> System.out.println("Nothing to do");
        }
    }

    /**
     * Method main is the main method that runs the cli application,
     * printing the Eriantys' logo and initializing the game with IP address and the server port
     * @param args null
     */
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
        cli.userNicknameSetup();
    }


    /**
     * Method showServerError shows an error message if occurred
     */
    public void showServerError() {
        if(((ServerError) modelView.getServerAnswer()).getError() == ServerErrorTypes.FULLGAMESERVER) {
            showError("Server is full... please try again later!");
            System.exit(-1);
        } else if(((ServerError) modelView.getServerAnswer()).getError() == ServerErrorTypes.SERVEROUT) {
            showError("Server is out... please try again later!");
            System.exit(-1);
        }
    }

    /**
     * Method attributeTowers gives each player its corresponding tower color
     */
    public void attributeTowers() {
        for(Player p : modelView.getGameCopy().getActivePlayers()) {
            if(modelView.getGameCopy().getActivePlayers().size() == 4) {
                if(p.isTeamLeader()) {
                    if(p.getBoard().getTowerArea().getTowerArea().get(0).getColor() == TowerColor.BLACK) {
                        blackP = p.getNickname();

                    } else if (p.getBoard().getTowerArea().getTowerArea().get(0).getColor() == TowerColor.GREY) {
                        greyP = p.getNickname();


                    } else if(p.getBoard().getTowerArea().getTowerArea().get(0).getColor() == TowerColor.WHITE) {
                        whiteP = p.getNickname();
                    }
                }
            } else {
                if(p.getBoard().getTowerArea().getTowerArea().get(0).getColor() == TowerColor.BLACK) {
                    blackP = p.getNickname();

                } else if (p.getBoard().getTowerArea().getTowerArea().get(0).getColor() == TowerColor.GREY) {
                    greyP = p.getNickname();


                } else if(p.getBoard().getTowerArea().getTowerArea().get(0).getColor() == TowerColor.WHITE) {
                    whiteP = p.getNickname();
                }
            }
        }
    }

    /**
     * Method propertyChange overrides the super method
     * by getting notified by the action handler class,
     * and it switches between the actions received in order to show the game in the cli
     * @param changeEvent event
     */
    @Override
    public void propertyChange(PropertyChangeEvent changeEvent) {
        String serverCommand = (changeEvent.getNewValue() != null) ? changeEvent.getNewValue().toString() : null;
        switch(changeEvent.getPropertyName()) {
            case "InitialGamePhase" -> {
                assert serverCommand != null;
                initialGamePhaseHandler(serverCommand);
            }
            case "DynamicAnswer" ->
                    showServerMessage(modelView.getServerAnswer());
            case "ActionPhase" -> {
                assert serverCommand != null;
                actionHandler.makeAction(serverCommand);
            }
            case "UpdateModelView" -> {
                assert serverCommand != null;
                if(modelView.isAction()) {
                    if(firstTurn) {
                        attributeTowers();
                        firstTurn = false;
                    }
                    showIslandsTable();
                    showClouds();
                    showAvailableCharacters();
                    showCoins();
                    showOtherDiningRooms();
                }
                if(modelView.isPianification()) {
                    showIslandsTable();
                    showClouds();
                    showOtherDiningRooms();
                }
            }

            case "WinMessage" -> {
                assert serverCommand != null;
                showWinMessage();
                endGameMessage();
            }
            case "LoseMessage" -> {
                assert serverCommand != null;
                showLoseMessage(changeEvent.getNewValue().toString());
                endGameMessage();

            }
            default -> System.out.println("Unknown answer from server");
        }
    }
}