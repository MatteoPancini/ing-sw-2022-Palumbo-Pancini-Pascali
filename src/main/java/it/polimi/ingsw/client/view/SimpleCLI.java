package it.polimi.ingsw.client.view;
/*
import it.polimi.ingsw.client.ClientConnection;
import it.polimi.ingsw.client.SimpleModelView;

import java.io.PrintStream;
import java.util.Scanner;

public class SimpleCLI implements Runnable {
    //this class is created just for client testing, doing JUST client-side operations -> it will be part of CLI final class

    private final PrintStream out;
    private final Scanner in;
    private ClientConnection clientConnection;
    private final SimpleModelView simpleModelView;



    public void userNicknameSetup() {
        String userNickname = null;
        boolean nickCheck = false;

        while(nickCheck == false) {
            do {
                System.out.println(">Please, insert your nickname: ");
                System.out.print(">");
                userNickname = in.nextLine();
            } while (userNickname == null);
            System.out.println(">Your nickname is: " + userNickname);
            System.out.println(">Is it right? [yes/no] ");
            System.out.print(">");
            if (in.nextLine().equalsIgnoreCase("yes")) {
                nickCheck = true;
            } else {
                userNickname = null;
            }

            clientConnection = new ClientConnection();
            simpleModelView.setPlayerNickname(userNickname);



        }

    }

    @Override
    public void run() {
        userNicknameSetup();

    }
}


 */