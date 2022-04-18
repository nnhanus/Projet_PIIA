import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ajoutVidControl{
    @FXML TextField nom;
    @FXML TextField path;
    @FXML TextField descri;

    
    @FXML void ajout(){
        Video.ajoutVid(nom.getText(), path.getText(), descri.getText());
        VueSwitch.switchTo(Vue.ACCUEIL);
    }

    @FXML void ajoutCat(){
        Video.ajoutVidCat(nom.getText(), path.getText(), descri.getText());
        VueSwitch.switchTo(Vue.CAT_INFO);
    }

    @FXML void backAcc(){
        VueSwitch.switchTo(Vue.ACCUEIL);
    }

    @FXML void backCat(){
        VueSwitch.switchTo(Vue.ACCUEIL);
    }


    
}
