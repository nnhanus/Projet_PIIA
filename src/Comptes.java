import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.chart.PieChart.Data;

public class Comptes {
    protected static PreparedStatement getUsers;
    protected static PreparedStatement checkPW;
    protected static PreparedStatement getType;
    protected static PreparedStatement newCompte;
    protected static PreparedStatement supprCompte;
    static {
        try {
            checkPW = Database.prepareStatement("SELECT password FROM compte WHERE name = ?");
            getUsers = Database.prepareStatement("SELECT name FROM compte");
            getType = Database.prepareStatement("SELECT status FROM compte WHERE name = ?");
            newCompte = Database.prepareStatement("INSERT INTO compte(name, password, status) VALUES (?, ?, ?)");
            supprCompte = Database.prepareStatement("DELETE * FROM compte WHERE name = ?");
        } catch (SQLException e){
            System.out.println("Erreur");
        }
    }

    public static void deleteCompte(String nom){
        try {
            supprCompte.setString(1, nom);
            supprCompte.executeQuery();
        } catch (SQLException e) {}
        
    }

    public static void newCompte(String nom, String pw, String type) throws SQLException{
        newCompte.setString(1, nom);
        newCompte.setString(2, pw);
        newCompte.setString(3, type);
        newCompte.executeUpdate();
    }

    public static int getType(String nom) throws SQLException{
        getType.setString(1, nom);
        ResultSet rs = getType.executeQuery();
        return rs.getInt("status");
    }

    public static ArrayList<String> getUsers() throws SQLException{
        ArrayList<String> res = new ArrayList<>();
        ResultSet rs = getUsers.executeQuery();
        while(rs.next()){
            res.add(rs.getString("name"));
        }
        for (int i = 0; i < res.size(); i++){
            System.out.println(res.get(i));
        }
        return res;
    }

    
    public static  boolean checkPW(String nom, String pw) throws SQLException{
        checkPW.setString(1, nom);
        ResultSet rs = checkPW.executeQuery();
        if (rs.getString("password").equals(pw)){
            return true;
        }
        return false;
    }





    
}
