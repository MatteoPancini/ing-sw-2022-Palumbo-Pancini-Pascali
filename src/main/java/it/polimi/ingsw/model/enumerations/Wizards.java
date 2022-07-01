package it.polimi.ingsw.model.enumerations;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumeration containing the wizards of the game, in a static structure used during pre-match phase
 */
public enum Wizards {
    MONACH,
    WITCH,
    KING,
    FOREST;

    private static final ArrayList<Wizards> availableWizards = new ArrayList<>();

    public static void reset() {
        availableWizards.clear();
        availableWizards.add(MONACH);
        availableWizards.add(WITCH);
        availableWizards.add(KING);
        availableWizards.add(FOREST);
    }

    public static void removeAvailableWizard(Wizards wizard) {
        availableWizards.remove(wizard);
    }

    public static boolean isChosen(Wizards wizard) {
        return !(availableWizards.contains(wizard));
    }

    public static List<Wizards> notChosen() {
        return availableWizards;
    }


    public static Wizards parseWizardInput(String wizardInput) {
        return Enum.valueOf(Wizards.class, wizardInput.toUpperCase());
    }
}
