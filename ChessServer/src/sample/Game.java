package sample;

public class Game {
    private Client player1;
    private Client player2;
    public static boolean WHITE = true;
    public static boolean BLACK = false;
    private boolean player1Color;
    private int firstTurn;
    private String gameCode;
    private boolean isOccurring;
    Game(Client client, String gameCode, boolean color){
        this.player1 = client;
        this.gameCode = gameCode;
        player1Color = color;
        isOccurring = false;
    }
    public void addPlayer2(Client player2){
        this.player2 = player2;
        isOccurring = true;
        player1.setInGame(true);
        player2.setInGame(true);
        player1.updateTotalMatch(player1.getName());
        player2.updateTotalMatch(player2.getName());
        player1.setGame(this);
        player2.setGame(this);
    }

    public Client getPlayer1() {
        return player1;
    }

    public void setPlayer1(Client player1) {
        this.player1 = player1;
    }

    public Client getPlayer2() {
        return player2;
    }

    public void setPlayer2(Client player2) {
        this.player2 = player2;
    }

    public static boolean isWHITE() {
        return WHITE;
    }

    public static void setWHITE(boolean WHITE) {
        Game.WHITE = WHITE;
    }

    public static boolean isBLACK() {
        return BLACK;
    }

    public static void setBLACK(boolean BLACK) {
        Game.BLACK = BLACK;
    }

    public boolean isPlayer1Color() {
        return player1Color;
    }

    public void setPlayer1Color(boolean player1Color) {
        this.player1Color = player1Color;
    }

    public int getFirstTurn() {
        return firstTurn;
    }

    public void setFirstTurn(int firstTurn) {
        this.firstTurn = firstTurn;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public void dismiss(Client client) {
        if(isOccurring && player1.isInGame() && player2.isInGame()){
            if(client.getName() == player1.getName()){
                player1.controlGame("GAME/LOST/"+player1.getName()+"/ ");
                player2.controlGame("GAME/WON/"+player2.getName()+"/ ");
                //player2.sendToClient("Off_the_game_pane/");
            }else{
                player2.controlGame("GAME/LOST/"+player2.getName()+"/ ");
                player1.controlGame("GAME/WON/"+player1.getName()+"/ ");
            }
            player1.setInGame(false);
            player2.setInGame(false);
        }
        isOccurring = false;
    }

    public boolean isOccurring() {
        return isOccurring;
    }

    public void setOccurring(boolean occurring) {
        isOccurring = occurring;
    }
}
