package it.polimi.ingsw.model.enumerations;

import it.polimi.ingsw.model.cards.AssistantCard;


import java.io.InputStreamReader;
import java.io.Reader;

public enum Assistants {
    EAGLE,
    DOG,
    ELEPHANT,
    CAT,
    CHEETAH,
    LIZARD,
    OCTOPUS,
    OSTRICH,
    TURTLE,
    FOX;

    /* DA VEDERE SE MEGLIO QUI OPPURE NEL PARSER
    public static createAssistants() {
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(Eryantis.class.getResourceAsStream("/json/gods.json"));
        AssistantCards[] assistantCards = gson.fromJson(reader, AssistantCard[].class);
    }
     */
}
