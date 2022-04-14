import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AjoutCompteControl {
    @FXML TextField nom;
    @FXML TextField type;
    @FXML PasswordField mdp;
    @FXML VBox box;

    @FXML protected void addCompte() throws SQLException{
        if (type.getText().equals("Adolescent")){
            Comptes.newCompte(nom.getText(), mdp.getText(), "1");
        } else if (type.getText().equals("Adulte")){
            Comptes.newCompte(nom.getText(), mdp.getText(), "0");
        } else {
            box.getChildren().add(new Label("Erreur: type de compte"));
        }
        VueSwitch.switchTo(Vue.ACCOUNT);
    }

    @FXML protected void addCompteEnfant() throws SQLException{
        Comptes.newCompte(nom.getText(), " ", "2");
        VueSwitch.switchTo(Vue.ACCOUNT);
    }

    @FXML protected void back(){
        VueSwitch.switchTo(Vue.ACCOUNT);
    }
    
}
