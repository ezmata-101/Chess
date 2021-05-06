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
    public void handleWinLose(String message){
        String[] strings=message.split("/");
        if(strings[1].equals("LOST")){
            if(ds.getUserByName(strings[2])){
                int lost=ds.getLoseByName(strings[2]);
                if(ds.updateLoseForName(lost+1,strings[2])){
                    System.out.println("Lost game updated for "+strings[2]);
                }
                else {
                    System.out.println("Can't update lost game for "+strings[2]);
                }
            }
            else{
                System.out.println("Can't find the player named : "+strings[2] +" for changing lose.");
            }
        }
        else if(strings[1].equals("WON")){
            if(ds.getUserByName(strings[2])){
                int win=ds.getWinByName(strings[2]);
                if(ds.updateWinForName(win+1,strings[2])){
                    System.out.println("Win game updated for "+strings[2]);
                }
                else {
                    System.out.println("Can't update win game for "+strings[2]);
                }
            }
            else{
                System.out.println("Can't find the player named : "+strings[2] +" for changing win.");
            }
        }
    }
    public void handleTotalMatch(String name){
        if(ds.getUserByName(name)){
            int total_match=ds.getTotalMatchByName(name);
            if(ds.updateTotalMatchForName(total_match+1,name)){
                System.out.println("Successfully updated total match for : " + name);
            }
            else{
                System.out.println("Can't update total match for: "+name);
            }
        }
        else{
            System.out.println("Can't find : "+name +" for updating total match.");
        }
    }
    public boolean isOnline(String name){
        for(int i=0; i<clients.size(); i++){
            if(name.equals(clients.get(i).getName())){
                if(clients.get(i).isOnline())return true;
//                return false;
            }
        }
        return false;
    }


    public void addClient(String name, ServerThread thread) {
        if(name.equals("GUEST")){
            name = name + "_" + clients.size();
            thread.sendToClient("login/successful/"+name);
        }
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
                    player2.sendToClient("JOIN_GAME/SAME_PLAYER");
                    return;
                }
                game.addPlayer2(player2);
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
                message+=firstTurn;

                message+="/2";
                message2+="/1";

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
