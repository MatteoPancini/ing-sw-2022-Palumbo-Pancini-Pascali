package Enum;

public class AssistantCard {
    private Wizard type;
    private OrderValue value;
    private MotherNatureMoves moves;
    private CardState state;

    public Wizard getType() {
        return type;
    }

    public OrderValue getValue() {
        return value;
    }

    public MotherNatureMoves getMoves() {
        return moves;
    }

    public CardState getState() {
        return state;
    }

    public void setState(CardState state){
        this.state = state;
    }
}