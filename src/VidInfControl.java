import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
        nom_vid = "Boca";
        titre.setText("Boca");
        descri.setText("Concert Dreamcatcher");
        MPControl.file = Video.getPath("Boca");
    }

    @FXML 
    protected void play(){
        VueSwitch.switchTo(Vue.MP);
    }


    
}
