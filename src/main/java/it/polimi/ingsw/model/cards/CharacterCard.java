package it.polimi.ingsw.model.cards;

//TODO: Consigliato di usare un Factory Method per il JSON nel costruttore


public abstract class CharacterCard {
    protected int price;

    public CharacterCard(int cost) {
        price = cost;
    };

    public CharacterCard() {
        price = 0;
    }

    public void cardEffect() { ... };
    public void incrementPrice() {
        price++;
    };
    public int getPrice() {
        return price;
    }
}

public class Priest extends CharacterCard {
    public Priest(int cost) {
        super();
    }
    @Override
    public void cardEffect() {
        cardEffect() { ... };
    }
}

public class CheeseMan extends CharacterCard {
    public CheeseMan(int cost) {
        super();
    }
    @Override
    public void cardEffect() {
        cardEffect() { ... };
    }
}
public class TheReader extends CharacterCard {
    public TheReader(int cost) {
        super();
    }
    @Override
    public void cardEffect() {
        cardEffect() { ... };
    }
}
public class LetterMan extends CharacterCard {
    public LetterMan(int cost) {
        super();
    }
    @Override
    public void cardEffect() {
        cardEffect() { ... };
    }
}
public class Prohibitioner extends CharacterCard {
    public Prohibitioner(int cost) {
        super();
    }
    @Override
    public void cardEffect() {
        cardEffect() { ... };
    }
}
public class Centaur extends CharacterCard {
    public Centaur(int cost) {
        super();
    }
    @Override
    public void cardEffect() {
        cardEffect() { ... };
    }
}
public class Joker extends CharacterCard {
    public Joker(int cost) {
        super();
    }
    @Override
    public void cardEffect() {
        cardEffect() { ... };
    }
}
public class Cavalier extends CharacterCard {
    public Cavalier(int cost) {
        super();
    }
    @Override
    public void cardEffect() {
        cardEffect() { ... };
    }
}
public class MushroomLover extends CharacterCard {
    public MushroomLover(int cost) {
        super();
    }
    @Override
    public void cardEffect() {
        cardEffect() { ... };
    }
}
public class Guitarist extends CharacterCard {
    public Guitarist(int cost) {
        super();
    }
    @Override
    public void cardEffect() {
        cardEffect() { ... };
    }
}
public class Princess extends CharacterCard {
    public Princess(int cost) {
        super();
    }
    @Override
    public void cardEffect() {
        cardEffect() { ... };
    }
}
public class DrWitch extends CharacterCard {
    public DrWitch(int cost) {
        super();
    }
    @Override
    public void cardEffect() {
        cardEffect() { ... };
    }
}

