package sample;


import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class MessageHandler {

    ArrayList<Client> clients;
    ArrayList<Game> games;

    DatabaseUserManage ds=new DatabaseUserManage();
    public MessageHandler(){
        ds.open();
        clients = new ArrayList<>();
        games = new ArrayList<>();
    }
    public int handleSignIn(String message){
        String[] strings=message.split("/");
        if(strings[0].equals("signin")){
            if(ds.getUserByName(strings[1])){
                System.out.println("User already exist.");
                return 2;
            }
            else{
                if(strings[1].length()!=0 && strings[2].length()>=8){
                    if(ds.AddUserToUserInfoTable(strings[1],strings[2])){
                        return 1;
                    }
                    else{
                        return 0;
                    }
                }
            }
        }
        return 0;
    }
    public int handleLogIn(String message){
        String[] strings=message.split("/");
        if(strings[0].equals("login")){
            if(ds.getUserByName(strings[1])){
                if(ds.AccountValidityCheck(strings[1],strings[2])){
                    return 1;
                }
                else{
                    return 0;
                }
            }
            else {
                return 2;
            }
        }
        return 0;
    }
    public boolean isOnline(String name){
        for(int i=0; i<clients.size(); i++){
            if(name.equals(clients.get(i).getName())){
                if(clients.get(i).isOnline())return true;
                return false;
            }
        }
        return false;
    }


    public void addClient(String name, ServerThread thread) {
        Client c = new Client(thread, name);
        c.setOnline(true);
        c.setInGame(false);
        c.setIndex(clients.size());
        clients.add(c);
    }

    public void createNewGame(Client client, String color){
        if(client.isInGame()){
            client.sendToClient("CREATE_GAME/YOU_ARE_ALREADY_IN_GAME");
            return;
        }
        String name = client.getName();
        String code = "" + Character.toLowerCase(name.charAt(0));
        for(int i=1; i<name.length(); i++){
            char c = Character.toLowerCase(name.charAt(i));
            if(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c=='u') continue;
            code+=c;
        }
        code+="00"+games.size();
        Game game = new Game(client, code, color.equals("WHITE"));
        games.add(game);
        String message = "CREATE_GAME/SUCCESS/"+code+"/";
        if(color.equals("WHITE")) message+="WHITE/";
        else message+="BLACK/";
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<51; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        int i = list.get(25)%2;
        game.setFirstTurn(i);
        message += game.getFirstTurn();
        client.sendToClient(message);
    }

    public void leaveGame(Client client){
        if(!client.isInGame()){
            client.sendToClient("LEAVE_GAME/YOU_ARE_NOT_IN_GAME");
            return;
        }
        Game game = client.getGame();
        game.dismiss(client);
    }

    public void joinGame(Client player2, String code){
        if(player2.isInGame()){
            player2.sendToClient("JOIN_GAME/YOU_ARE_ALREADY_IN_GAME");
            return;
        }
        for(int i=0; i<games.size(); i++){
            if(games.get(i).getGameCode().equals(code)){
                Game game = games.get(i);
                Client player1 = game.getPlayer1();
                if(game.getPlayer1().getName().equals(player2.getName())){
                    player2.sendToClient("JOIN_GAME/FAILED/SAME_PLAYER");
                    return;
                }
                game.setPlayer2(player2);
                String message = "JOIN_GAME/SUCCESS/";
                String message2 = "JOIN_GAME/SUCCESS/";

                if(game.isPlayer1Color()){
                    message+="BLACK/";
                    message2+="WHITE/";
                }
                else{
                    message+="WHITE/";
                    message2+="BLACK/";
                }
                //int firstTurn = //Math.abs(game.getFirstTurn()-1);
                int firstTurn = game.getFirstTurn();
                message2+=firstTurn;
                message+=Math.abs(firstTurn - 1);
                player2.sendToClient(message);
                player1.sendToClient(message2);
                return;
            }
        }
        player2.sendToClient("JOIN_GAME/GAME_CODE_NOT_FOUND");
    }

    public void handleTemporarily(Socket s) {
        ServerThread tempThread = new ServerThread(s, this);
        tempThread.start();
    }
}
