package it.polimi.ingsw.constants;

public class Constants {
    //classe in cui settiamo tutte le costanti del gioco

    //CONSTRUCTOR
    private Constants(){}


    //CLI colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    //----------------------------
    //SERVER CONSTANTS and METHODS
    //----------------------------
    private static String address;
    private static int port;

    public static void setAddress(String address) {
        Constants.address = address;
    }

    public static void setPort(int port) {
        Constants.port = port;
    }

    public static int getPort() {
        return port;
    }

    public static String getAddress() {
        return address;
    }


    //PLAYERS
    public static final int NUM_MIN_PLAYERS = 2;
    public static final int NUM_MAX_PLAYERS = 4;
    private static int playersNum;

    public static int getPlayersNum() {
        return playersNum;
    }

    public static void setPlayersNum(int playersNum) {
        Constants.playersNum = playersNum;
    }


}
