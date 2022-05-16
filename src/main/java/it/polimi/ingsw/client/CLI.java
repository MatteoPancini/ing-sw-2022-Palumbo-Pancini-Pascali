package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.GameHandler;
import it.polimi.ingsw.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.messages.clienttoserver.ExpertModeChoice;
import it.polimi.ingsw.messages.clienttoserver.PlayersNumberChoice;
import it.polimi.ingsw.messages.clienttoserver.WizardChoice;
import it.polimi.ingsw.messages.servertoclient.Answer;
import it.polimi.ingsw.messages.servertoclient.WizardAnswer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.board.Student;
import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.cards.AssistantDeck;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.cards.CharacterDeck;
import it.polimi.ingsw.model.enumerations.PawnType;
import it.polimi.ingsw.model.enumerations.Wizards;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.SchoolBoard;
import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.model.player.Table;

import java.beans.PropertyChangeEvent;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Scanner;

import static it.polimi.ingsw.constants.Constants.*;

//CLI riceve UserAction dal Controller, switch(userAction.getPropertyName()) e nei case usa ask...()
//es: controller.send(new PickAssistant(Actions.PICKASSISTANT))
//    cli.receiveCommand(??) ???

public class CLI implements Runnable, ListenerInterface {
    private Scanner in;
    private static PrintWriter out;
    private Parser parser;
    private PropertyChangeSupport virtualClient;
    private String chosenWizard;
    private String chosenNickname;
    private String chosenMoves;
    private String chosenAssistant;
    private String chosenCloud;
    private String chosenStudent;
    private String chosenDestination;
    private String chosenTeam;
    private String chosenCharacter;
    private ClientConnection clientConnection;
    private final ModelView modelView;
    private boolean activeGame;
    private ActionHandler actionHandler;
    private final PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    public CLI() {
        in = new Scanner(System.in);
        out = new PrintWriter(out);
        modelView = new ModelView(this, new VisualBoard());
        virtualClient.addPropertyChangeListener(parser);
        activeGame = true;
        actionHandler = new ActionHandler(this, modelView);

    }

    public String printTower(Island isl) {
        if(isl.hasTower()) {
            return "yes";
        }
        return "false";
    }
    public Scanner getIn() {
        return in;
    }
    public ModelView getModelView() {
        return modelView;
    }
    public Parser getParser() {
        return parser;
    }
    public PrintWriter getOutput() {
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

    public void printCharacters() {
        for(CharacterCard c : CharacterDeck.getPlayableCards()) {
            out.println(ANSI_CYAN + c.getName() + "," + ANSI_RESET);
        }
    }
    public void showAvailableCharacters() {
        out.println(">The available characters for this match are: ");
        printCharacters();
    }

    public void showCharactersDescription() {
        for(CharacterCard c : CharacterDeck.getPlayableCards()) {
            out.print(ANSI_CYAN + c.getName() + " : " + c.getEffect() + ANSI_RESET);
        }
    }

    //scrivere diversi show per ogni parte del modello da mostrare
    public void showBoard() {
        out.println("Here's a summary of your board: ");
        out.println(Constants.ANSI_GREEN + "Green = " + modelView.getGreenStudents(modelView.getGameCopy().getCurrentPlayer()) + " - Professor : "
                + modelView.hasGreenProfessor(modelView.getGameCopy().getCurrentPlayer()) + ANSI_RESET);
        out.println(Constants.ANSI_RED + "Red = " + modelView.getRedStudents(modelView.getGameCopy().getCurrentPlayer()) + " - Professor : "
                + modelView.hasRedProfessor(modelView.getGameCopy().getCurrentPlayer()) + ANSI_RESET);
        out.println(ANSI_YELLOW + "Yellow = " + modelView.getYellowStudents(modelView.getGameCopy().getCurrentPlayer()) + " - Professor : "
                + modelView.hasYellowProfessor(modelView.getGameCopy().getCurrentPlayer()) + ANSI_RESET);
        out.println(ANSI_PURPLE + "Pink = " + modelView.getPinkStudents(modelView.getGameCopy().getCurrentPlayer()) + " - Professor : "
                + modelView.hasPinkProfessor(modelView.getGameCopy().getCurrentPlayer()) + ANSI_RESET);
        out.println(ANSI_BLUE + "Blue = " + modelView.getBlueStudents(modelView.getGameCopy().getCurrentPlayer()) + " - Professor : "
                + modelView.hasRedProfessor(modelView.getGameCopy().getCurrentPlayer()) + ANSI_RESET);
    }
    public void showIslands() {
        out.println(">Here's a little description of the islands in the game board: ");
        for (Island island : modelView.getGameCopy().getGameBoard().getIslands()) {
            if(island.getMergedIslands()!=null) {
                out.println("Island " + island.getIslandID() + "(merged with ");
                for (int i = 0; i < island.getMergedIslands().size(); i++) {
                    out.print(island.getMergedIslands().get(i).getIslandID() + " ");
                }
                out.print(")");
                out.println("Students: ");
                for (Student stud : island.getStudents()) {
                    out.print(stud.getType().toString() + "\n");
                }
                for (int i = 0; i < island.getMergedIslands().size(); i++)
                {
                    out.println("\nTowers: " + island.getMergedTowers() + " "  + island.getTower().getColor().toString());
                }
            } else {
                out.println("Island " + island.getIslandID() + " : " + "\n" + " Students : ");
                for (Student stud : island.getStudents()) {
                    out.print(stud.getType().toString() + "\n");
                }
                out.println("\nTower: " + island.getTower().getColor().toString());
            }
        }
    }
    public void showLastAssistantsUsed() {
        out.println(">These are all the assistants used in this turn: ");
        for(AssistantCard ass : modelView.getGameCopy().getGameBoard().getLastAssistantUsed()) {
            out.println("Name: " + ass.getName() + "Value: " + ass.getValue() + "Maximum moves: " + ass.getMoves());
        }
    }
    public void printStudentsOnCLoud(int ID) {
        for(Student s : modelView.getGameCopy().getGameBoard().getClouds().get(ID).getStudents()) {
            out.print("-" + s.getType());
        }
    }
    public void showClouds() {
        out.println(">Clouds status of this turn: ");
        for(CloudTile c : modelView.getGameCopy().getGameBoard().getClouds()) {
            out.println("ID: " + c.getID() + "Students: ");
            printStudentsOnCLoud(c.getID());
        }
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
        out.println(">Here's a summary of the students in your entrance: ");
        for(Student s : modelView.getGameCopy().getCurrentPlayer().getBoard().getEntrance().getStudents()) {
            out.print("•" + printColor(s.getType()) + s.getType() + ANSI_RESET);
        }
    }
    public void showCoins() {
        out.println(">You have " + ANSI_YELLOW + modelView.getGameCopy().getCurrentPlayer().getCoins() + " left." + ANSI_RESET);
    }
    public int[] getPlayerDiningRoom(int id) {
        int r=0, p=0, g=0, y=0, b=0;
        int[] students = null;
        for(Table t : modelView.getGameCopy().getPlayers().get(id).getBoard().getDiningRoom().getDiningRoom()) {
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
        return students;
    }

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
    }

    public void askMoves(AssistantCard card) {
        out.println(">Pick a number of mother nature moves between 1 and "
                + modelView.getGameCopy().getCurrentPlayer().getChosenAssistant().getMoves());
        chosenMoves = in.next();
        virtualClient.firePropertyChange("PickMoves", null, chosenMoves);
    }

    public void printPlayerDeck() {
        for (AssistantCard card : modelView.getGameCopy().getCurrentPlayer().getAssistantDeck().getDeck()) {
            out.print("(Name: " + card.getName() + "," + "Value: " + card.getValue() + "," + "Moves: " + card.getMoves());
        }
    }

    public void askAssistant(AssistantDeck deck) {
        out.println(">Pick an assistant from your deck by typing its name: ");
        printPlayerDeck();
        chosenAssistant = in.next();
        virtualClient.firePropertyChange("PickAssistant", null, chosenAssistant);
    }

    public void askCloud(ArrayList<CloudTile> clouds) {
        out.println(">Pick a cloud by typing its ID: ");
        chosenCloud = in.next();
        virtualClient.firePropertyChange("PickCloud", null, chosenCloud);
    }

    //chiamato dal controller, quando ritorna lo studente esso viene salvato in una variabile
    //che verrà poi usata per la chosenDestination nella userAction successiva
    public void askStudent(SchoolBoard schoolB) {
        out.println(">Pick a student from your Entrance: ");
        chosenStudent = in.next();
        virtualClient.firePropertyChange("PickStudent", null, chosenStudent);
    }

    //prende come input lo studente scelto nella richiesta precedente
    public void askDestination() {
        out.println(">Pick a destination between your DiningRoom or an island: ");
        chosenDestination = in.next();
        virtualClient.firePropertyChange("PickDestination", null, chosenDestination);
    }


    public void askCharacterCard(CharacterDeck cards) {
        out.println(">Type the name of the Character Card you want to play: ");
        printCharacters();
        chosenCharacter = in.next();
        virtualClient.firePropertyChange("PickCharachter", null, chosenCharacter);
    }

    public void askIsland(ArrayList<Island> islands) {
        out.println(">Choose an island by typing its ID!");
        showIslands();
    }

    public void showMotherNature() {
        out.println(">Now Mother Nature is on island " + modelView.getGameCopy().getGameBoard().getMotherNature().getPosition());
    }

    public void chooseExpertMode() {
        String expertModeChoice;
        out.println("Sono in chooseExpertMode");
        out.print(">");
        expertModeChoice = in.nextLine();
        out.println("Ho fatto l'assegnamento: della expert mode " + expertModeChoice);
        clientConnection.sendUserInput(new ExpertModeChoice(expertModeChoice));
    }

    public void chooseWizard(List<Wizards> availableWizards) {
        while (true) {
            out.println(">Choose your wizard!");
            out.print(">");
            try {
                String wizardTyped = in.nextLine();
                Wizards wizardChosen = Wizards.parseWizardInput(wizardTyped);
                if(availableWizards.contains(wizardChosen)) {
                    clientConnection.sendUserInput(new WizardChoice(wizardChosen));
                    modelView.setGameStarted(true);
                    return;
                } else {
                    out.println("Wizard not available!");
                }
            } catch (IllegalArgumentException e) {
                System.err.println(ANSI_RED + "Invalid input! Please provide one of the accepted wizards."+ ANSI_RESET);
                out.print(">");
            }
        }
    }
    public void actionsLoop() {
        in.reset();
        String cmd = in.nextLine();
        listeners.firePropertyChange("action", null, cmd);
    }
    public void choosePlayerNumber() {
        out.println("Sono in choosePlayerNumber");
        int numOfPlayer;
        while (true) {
            try {
                Scanner inputNum = new Scanner(System.in);
                out.print(">");
                numOfPlayer = inputNum.nextInt();
                out.println("Ho fatto l'assegnamento del player"); //SPOILER: NON CI ARRIVA!
                break;
            } catch (NumberFormatException e) {
                out.println("Invalid parameter, it must be a numeric value.");
            }

        }
        clientConnection.sendUserInput(new PlayersNumberChoice(numOfPlayer));

    }
    public void showServerMessage(Answer serverAnswer) {
        out.println(serverAnswer);
    }

    public void showError(String message) {
        out.println(ANSI_RED + ">Warning! " + message + ANSI_RESET);
    }

    public void showWinMessage(Player winner) {
        if (modelView.getGameCopy().getPlayersNumber() != 4) {
            out.println(">Game over! The winner is " + ANSI_RED + winner + ANSI_RESET);
        } else {
            out.println(">Game over! The winner is team " + ANSI_RED +
                    winner.getIdTeam() + ANSI_RESET);
        }
    }

    public void userNicknameSetup() {
        String userNickname = null;
        boolean nickCheck = false;

        while (nickCheck == false) {
            do {
                out.println(">Please, insert your nickname: ");
                out.print(">");
                userNickname = in.nextLine();
            } while (userNickname == null);
            out.println(">Your nickname is: " + userNickname);
            out.println(">Is it right? [y/n] ");
            out.print(">");
            if (in.nextLine().equalsIgnoreCase("y")) {
                nickCheck = true;
            } else {
                userNickname = null;
            }
        }
        clientConnection = new ClientConnection();

        //TODO: PANCI questo posso toglierlo??? Passandoci il Game da server a client non dovrebbe più servire
        // modelView.setPlayerNickname(userNickname);
        try {
            if (!clientConnection.setupNickname(userNickname, modelView, actionHandler)) {
                System.err.println("The entered IP/port doesn't match any active server or the server is not " + "running. Please try again!");
                System.exit(-1);
            }
            out.println("Socket Connection setup completed!");
        } catch (DuplicateNicknameException e) {
            //e.printStackTrace();
            userNicknameSetup();
        }
        listeners.addPropertyChangeListener("action", new Parser(clientConnection, modelView));
    }

    @Override
    public void run() {
        userNicknameSetup();

        while (isActiveGame()) {

            if(modelView.isGameStarted()) {
                actionsLoop();
            }
        }
        in.close();
        out.close();
    }

    public boolean isActiveGame() {
        return activeGame;
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
        out.println("Welcome to the magic world of:");
        out.println(" .----------------.  .----------------.  .----------------.  .----------------.  .-----------------. .----------------.  .----------------.  .----------------. \n" +
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
        out.println("Let's start your client configuration!\n");
        Scanner clientInput = new Scanner(System.in);
        out.println(">Please, insert the server IP address:");
        out.print(">");
        String ipServerAddress = clientInput.nextLine();
        out.println(">Please, insert the server port");
        out.print(">");
        int serverPort = clientInput.nextInt();
        Constants.setAddress(ipServerAddress);
        Constants.setPort(serverPort);

        CLI cli = new CLI();
        cli.run();
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
            case "DynamicAnswer" -> {
                //System.out.println("Sono in propertyChange e ho letto una Dynamic Answer");
                showServerMessage(modelView.getServerAnswer());
            }
            case "ActionPhase" -> {
                assert serverCommand != null;
                actionHandler.makeAction(serverCommand);
            }
            case "UpdateModelView" -> {
                assert serverCommand != null;
                modelView.setGameCopy((Game) changeEvent.getNewValue());
                showIslands();
                showClouds();
                showAvailableCharacters();
                showMotherNature();
            }
            default -> out.println("Unknown answer from server");
        }
    }
}


