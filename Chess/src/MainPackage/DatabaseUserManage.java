package MainPackage;

import java.sql.*;

public class DatabaseUserManage {
    Connection connection;
    private static final String DATABASE_PATH = "jdbc:sqlite:src\\MainPackage\\";
    private static final String DATABASE_NAME = "userinfo.db";
    private static final String USER_INFO_TABLE = "userinfo";


    private static final String NAME_COLUMN = "Username";
    private static final String PASSWORD_COLUMN = "Password";
    private static final String Total_Match__COLUMN = "Total_Matches";
    private static final String Win_COLUMN = "Win";
    private static final String Lose_COLUMN = "Lose";
    private static final String Points_COLUMN = "Total_Points";

    private static final String Find_User_By_Name = "SELECT COUNT(*) FROM "+USER_INFO_TABLE+" WHERE "+NAME_COLUMN+" = ?";
    private static final String Check_Pass_By_Name = "SELECT "+PASSWORD_COLUMN+" From "+USER_INFO_TABLE+" WHERE "+NAME_COLUMN+" = ?";

    private static final String Insert_New_User = "INSERT INTO "+USER_INFO_TABLE+" ( " +
            NAME_COLUMN+", "+PASSWORD_COLUMN+", "+Total_Match__COLUMN+", "+Win_COLUMN +", "+Lose_COLUMN+", "+Points_COLUMN+") VALUES (?, ?, ?, ?, ?, ?)";

    public DatabaseUserManage(){}
    public void open(){
        try{
            connection = DriverManager.getConnection(DATABASE_PATH+DATABASE_NAME);
            System.out.println("Database Opened...");
            //return true;
        }catch (SQLException e){
            System.out.println("Error: Cannot open database...\n"+e.getMessage());
            //return false;
        }
    }
    public boolean close(){
        if(connection != null){
            try{
                connection.close();
                System.out.println("Connection closed successfully...");
                return true;
            }catch (SQLException e){
                System.out.println("Cannot Close...");
                return false;
            }
        }
        return false;
    }

    public boolean getUserByName(String name){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Find_User_By_Name);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            int count = resultSet.getInt(1);
            if(count==0){
                return false;
            }
            return true;
        } catch (SQLException e) {
            System.out.println("No User Exists of This Name...\n"+e.getMessage());
            return false;
        }
    }

    public boolean AccountValidityCheck(String name,String pass){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Check_Pass_By_Name);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            String password=resultSet.getString(1);
            if(password.equals(pass)){
                return true;
            }
            return false;
        } catch (SQLException throwables) {
            System.out.println("GET ID FAILED...\n"+throwables.getMessage());
            return false;
        }
    }
    public boolean AddUserToUserInfoTable(String name,String pass){
        try {
            PreparedStatement ps = connection.prepareStatement(Insert_New_User);
            ps.setString(1, name);
            ps.setString(2,pass);
            ps.setInt(3,0);
            ps.setInt(4,0);
            ps.setInt(5,0);
            ps.setInt(6,0);
            ps.execute();
            return true;
        } catch (SQLException throwables) {
            System.out.println("Adding New User FAILED...\n"+throwables.getMessage());
            return false;
        }
    }
}
