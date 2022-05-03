package it.polimi.ingsw.client.view;

import it.polimi.ingsw.constants.Constants;

import java.util.Scanner;


public class ClientApp {
    //Client Main



    public static void main(String[] args) {
        System.out.println("Welcome to the magic world of: ERIANTYS\nLet's start your client configuration!");
        Scanner clientInput = new Scanner(System.in);
        System.out.println(">Please, insert the server IP address:");
        System.out.print(">");
        String ipServerAddress = clientInput.nextLine();
        System.out.println(">Please, insert the server port");
        System.out.print(">");
        int serverPort = clientInput.nextInt();
        Constants.setAddress(ipServerAddress);
        Constants.setPort(serverPort);

        SimpleCLI cli = new SimpleCLI();
        cli.run();


    }
}


