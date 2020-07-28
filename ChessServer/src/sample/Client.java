package sample;

public class Client {
    private ServerThread thread;
    private String name;
    private boolean inGame;
    private Game game;
    private String color;
    private boolean isOnline;
    private int index;
    Client(ServerThread thread, String name){
        this.thread = thread;
        thread.setClient(this);
        this.name = name;
        inGame = false;
    }
    public void sendToClient(String message){
        thread.sendToClient(message);
    }

    public ServerThread getThread() {
        return thread;
    }

    public void setThread(ServerThread thread) {
        this.thread = thread;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void gameMovement(String message) {
        Client c = game.getPlayer1();
        if(c.getName() == this.name){
            c = game.getPlayer2();
        }
        c.sendToClient(message);
    }
}