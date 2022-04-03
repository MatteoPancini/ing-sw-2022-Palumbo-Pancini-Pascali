package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.enumerations.Wizards;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerWizardsTest {

    @Test
    @BeforeEach
    void setup() {
        Wizards.reset();
    }

    @Test
    @DisplayName("Color choose and reset validity test")
    void colorResetTest() {
        assertEquals(4, Wizards.notChosen().size());
        Wizards.choose(Wizards.MONACH);
        Wizards.choose(Wizards.WITCH);
        assertEquals(2, Wizards.notChosen().size());
        Wizards.reset();
        Wizards.choose(Wizards.FOREST);
        assertEquals(3, Wizards.notChosen().size());
        Wizards.reset();
        assertEquals(4, Wizards.notChosen().size());
    }
}
