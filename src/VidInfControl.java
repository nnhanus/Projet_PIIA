import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class VidInfControl implements Initializable {
    @FXML HBox comptes;
    @FXML Label titre;
    @FXML Text descri;
    @FXML ImageView minia;
    public static String nom_vid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titre.setText(nom_vid);
        MPControl.file = Video.getPath("Boca");
        try {
            AccueilControl.getMinia.setString(1, nom_vid);
            ResultSet min = AccueilControl.getMinia.executeQuery();
            minia.setFitHeight(100);
            minia.setFitWidth(150);
            minia.setImage(new Image(min.getString("mini")));
        } catch (SQLException e1) {}
    }

    @FXML 
    protected void play(){
        VueSwitch.switchTo(Vue.MP);
    }

    @FXML 
    protected void back(){
       VueSwitch.switchTo(Vue.ACCUEIL);
    }

    @FXML 
    protected void account(){
        VueSwitch.switchTo(Vue.ACCOUNT);
    }


    
}
