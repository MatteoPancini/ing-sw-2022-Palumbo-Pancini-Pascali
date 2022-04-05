package it.polimi.ingsw.network.server;

import it.polimi.ingsw.constants.Constants;
import it.polimi.ingsw.network.Server;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ServerApp {

    public static void main(String[] args) {
        System.out.print("This is the Server of Eryantis: Welcome!");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you want to use a default configuration?[y/n]");
        String configServer = scanner.nextLine();
        if(configServer.equalsIgnoreCase("y")) {
            Constants.setPort(1234);
        }
        else {
            System.out.println(">Please insert the server's port number");
            System.out.print(">");
            int portNumber = 0;
            try {
                portNumber = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Sorry, you have to insert a number! Application will now close...");
                System.exit(-1);
            }
            if (portNumber < 0 || (portNumber > 0 && portNumber < 1024)) {
                System.err.println("Sorry, insert a port between 1024 and 65535");
                main(null);
            }
            Constants.setPort(portNumber);

        }

        System.out.println("Starting Socket Server");

        Server server = new Server();

    }
}
