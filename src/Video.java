import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Video {
    protected static PreparedStatement getVideos;
    protected static PreparedStatement getPath;
    static {
        try {
            getVideos = Database.prepareStatement("SELECT name FROM video");
            getPath = Database.prepareStatement("SELECT path FROM video WHERE name = ?");
        } catch (SQLException e){
            System.out.println("Erreur");
        }
    }

    public static ArrayList<String> getVideos(){
        ArrayList<String> res = new ArrayList<>();
        try{
            ResultSet rs = getVideos.executeQuery();
            while(rs.next()){
                res.add(rs.getString("name"));
            }
            for (int i = 0; i < res.size(); i++){
                System.out.println(res.get(i));
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
