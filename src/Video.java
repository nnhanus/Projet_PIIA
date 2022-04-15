import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.chart.PieChart.Data;

public class Video {
    protected static PreparedStatement getVideos;
    protected static PreparedStatement getPath;
    protected static PreparedStatement getCategories;
    protected static PreparedStatement getVidForCat;
    protected static PreparedStatement getNomForIDC;
    protected static PreparedStatement getAuthor;
    protected static PreparedStatement getNomForIDU;
   
    static {
        try {
            getVideos = Database.prepareStatement("SELECT name FROM video");
            getPath = Database.prepareStatement("SELECT path FROM video WHERE name = ?");
			getCategories = Database.prepareStatement("SELECT * FROM category");
			getVidForCat = Database.prepareStatement("SELECT * FROM video WHERE idc = ?");
            getNomForIDC = Database.prepareStatement("SELECT idc FROM category WHERE name = ?");
            getAuthor = Database.prepareStatement("SELECT idu FROM authorization WHERE idc = ?");
            getNomForIDU = Database.prepareStatement("SELECT name FROM compte WHERE idu = ?");

        } catch (SQLException e){
            System.out.println("Erreur");
        }
    }

    public static int getNomForIDC(String name){
        int res = -1;
        try {
            getNomForIDC.setString(1, name);
            ResultSet rs = getNomForIDC.executeQuery();
            res = rs.getInt("idc");
        } catch (SQLException e) {}
        return res;
        
    }

    public static ArrayList<String> getUsersAutho(String name){
        int idc = getNomForIDC(name);
        ArrayList<String> res = new ArrayList<>();
        //ArrayList<Integer> tmp = new ArrayList<>();
        try{
            getAuthor.setInt(1, idc);
            ResultSet rs = getAuthor.executeQuery();
            while(rs.next()){
                getNomForIDU.setInt(1, rs.getInt("idu"));
            } 
        } catch (SQLException e){}

        return res;
    }

    public static ArrayList<String> getVidFroCat(String s){
        ArrayList<String> res = new ArrayList<>();
        try{
            getNomForIDC.setString(1, s);
            ResultSet idc = getNomForIDC.executeQuery();
            getVidForCat.setInt(1, idc.getInt("idc"));
            ResultSet rs = getVidForCat.executeQuery();
            while(rs.next()){
                res.add(rs.getString("name"));
            }
        } catch (SQLException e){}
        return res;  
    }

    public static ArrayList<String> getCategories(){
        ArrayList<String> res = new ArrayList<>();
        try{
            ResultSet rs = getCategories.executeQuery();
            while(rs.next()){
                res.add(rs.getString("name"));
            }
            for (int i = 0; i < res.size(); i++){
                System.out.println(res.get(i));
            }
        } catch (SQLException e){}
        return res;  
    }

    public static ArrayList<String> getVideos(){
        ArrayList<String> res = new ArrayList<>();
        try{
            ResultSet rs = getVideos.executeQuery();
            while(rs.next()){
                res.add(rs.getString("name"));
            }
        } catch (SQLException e){}
        return res;
    }

    public static String getPath(String nom) {
        try{
            getPath.setString(1, nom);
            ResultSet rs = getPath.executeQuery();
            return rs.getString("path");
        } catch (SQLException e){
            return "";
        }
    }
    
}
