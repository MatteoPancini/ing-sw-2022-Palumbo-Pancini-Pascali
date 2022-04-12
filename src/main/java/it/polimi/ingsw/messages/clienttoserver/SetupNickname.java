package it.polimi.ingsw.messages.clienttoserver;

public class SetupNickname implements Message {

    private final String nickname;

    public SetupNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

}
