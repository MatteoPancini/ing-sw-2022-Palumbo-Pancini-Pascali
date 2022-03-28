//TODO: CLASSE DA TOGLIERE NON APPENA SI IMPLEMENTA RoundHandler
//Si potrebbero riciclare alcune righe scritte qui sotto...

package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Round {

    // ogni giocatore pesca una carta assistente e la aggiungo in lastUsedCard, dopo aver ordinato la struttura dati
    // controllo l'ordine del turno
    public void pianificationPhase() {

        //1) METODO DEL CONTROLLER in cui aggiunge x studenti in ogni nuvola -> public void putStudentsOnCLoud()
        for (cloud in Game.clouds) {
            for (int i = 0; i < NUM_STUDENTS; i++) { //costante definita in fase di inizializzazione
                List<Student> newStudents = new ArrayList<Student>();
                //chiamo random/shuffle su Game.studentsBag + assegno a newStudents i primi/ultimi N studenti
                cloud.setStudents(newStudents);
            }
        }

        //2) GIOCO CARTE ASSISTENTE
        for (assistantCard in Game.lastAssistantUsed) {
            lastAssistantUsed.getOwner().pickAssistant();   //dalla carta risale al suo owner e gli fa scegliere un altro assistant, sovrascrivendolo
            //qua ci mettiamo un try catch con una exception in cui se dovesse essere il primo turno fa giocare gli assistant in maniera casuale
        }
        //3) ALGORITMO ORDINAMENTO del lastAssistantUsed basato su value

    }

    ;

    public void actionPhase() {

        //1) Spostare 3/4 studenti o nella sala o su isola
        for (assistantCard in Game.lastAssistantUsed) {
            Player currentPlayer = lastAssistantUsed.getOwner();
            for (int i = 0; i < NUM_STUDENTS; i++) {
                switch (playerChoice) {
                    case diningRoom:
                        putStudentDiningRoom(student);
                        /* DA IMPLEMENTARE IN PlayerTurn
                        Student pickedStudent = Player.pickStudent();
                        Player.board.diningRoom.setStudent(pickedStudent);
                        */
                        break;
                    case island:
                        putStudentIsland(student, island);
                        /* DA IMPLEMENTARE IN PlayerTurn
                        Island pickedIsland = Player.pickIsland();
                        pickedIsland.setStudent(Player.pickStudent());
                        break;
                        */
                }
            }


            //2) Muovi Madre Natura su un'Isola:
            //2.1) verifica se isola controllata o conquistata
            //2.2) verifica se isola si unisce a isole adiacenti
            int moves = Player.chooseMNMoves();
            moveMotherNature(moves); //IMPLEMENTARE IN PlayerTurn
            /*
            setPosition(getPosition() + moves); //MOVES è variabile in base alla chooseMNMoves
            */


            Player islandInfluencer = islandInfluenceCheck(newPosition);


        }


    }




    public void putStudentsOnCLoud(){
        for (cloud in Game.clouds) {
            for (int i = 0; i < NUM_STUDENTS; i++) { //costante definita in fase di inizializzazione
                List<Student> newStudents = new ArrayList<Student>();
                //chiamo random/shuffle su Game.studentsBag + assegno a newStudents i primi/ultimi N studenti
                cloud.setStudents(newStudents);
            }
        }
    }

    public void updateAssistantsState(){
        ...
    }

    public void initialize(){

    }

}
