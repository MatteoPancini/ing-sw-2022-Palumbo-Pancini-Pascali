package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.enumerations.Wizards;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    Player testPlayer;
    private static final String nickname = "playerNickname";
    private static final int playerID = 0;

    @Test
    @BeforeEach
    void setupPlayer() {
        testPlayer = new Player(nickname, playerID);
    }

    @Test
    @DisplayName("Player nickname and ID test")
    void nicknameTest() {
        assertEquals(nickname, testPlayer.getNickname());
        assertEquals(playerID, testPlayer.getPlayerID());
        assertEquals(playerID, testPlayer.getBoard().getBoardID());
    }

    @Test
    @DisplayName("Wizard choice test")
    void colorTest() {
        testPlayer.setWizard(Wizards.KING);
        assertEquals(Wizards.KING, testPlayer.getWizard());
    }

}