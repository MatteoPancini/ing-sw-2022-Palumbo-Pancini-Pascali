package it.polimi.ingsw.constants;

public class Constants {
    //classe in cui settiamo tutte le costanti del gioco

    //CONSTRUCTOR
    private Constants(){}

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
