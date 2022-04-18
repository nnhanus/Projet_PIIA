import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ComptesInfo implements Initializable {
    @FXML static ImageView photo = new ImageView();
    @FXML static Text name = new Text();
    @FXML static Text t = new Text();
    ///**@FXML*/ static VBox comptesInfo = new VBox();
    public static String nom;
    public static String type;
    public static String profil = "/avatar/default.png";
    protected static PreparedStatement getCompte;

    static{
        try {
            getCompte = Database.prepareStatement("SELECT * FROM compte WHERE name = ?");
        } catch (SQLException e) {}
    }

    public static void setNom(String n){
        nom = n;
    }

    public static void setType(int i){
        if (i == 0){
            type = "Adulte";
        } else if (i == 1){
            type = "Adolescent";
        } else if (i == 2){
            type = "Enfant";
        } 
    }

    public static void setProfil(String path){
        profil = path;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setText(nom);
        t.setText(type);
    }

    @FXML 
    protected void connect(){
        if (Integer.valueOf(type) == 2){
            //VueSwitch.switchTo(Vue.ACCUEIL);
            VueSwitch.switchTo(Vue.VIDINFO);
        } else {
            VueSwitch.switchTo(Vue.PASSWORD);
        }
    } 
}
