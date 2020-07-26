package sample;



public class MessageHandler {
    DatabaseUserManage ds=new DatabaseUserManage();
    public MessageHandler(){
        ds.open();
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
}
