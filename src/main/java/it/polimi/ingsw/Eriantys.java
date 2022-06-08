package it.polimi.ingsw;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.server.Server;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Eriantys {
    public static void main(String[] args) {
        System.out.println("Hi! Welcome to Eriantys!");
        System.out.println("What do you want to launch?");
        System.out.println("Please type:");
        System.out.println("0. SERVER\n1. CLIENT -> CLI INTERFACE\n2. CLIENT -> GUI INTERFACE");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int userIn = 0;
        try {
            userIn = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("Please insert a value between [0, 1, 2]!\nApplication will now close...");
            System.exit(-1);
        }
        switch (userIn) {
            case 0 -> Server.main(null);
            case 1 -> CLI.main(null);
            case 2 -> {
                System.out.println("Enjoy Eriantys!\nStarting...");
                GUI.main(null);
            }
            default -> System.err.println("Invalid argument!\n Please try again :(");
        }
    }
}