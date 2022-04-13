import java.sql.SQLException;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;

public class PasswordControl {
   // @FXML protected Label ident;
    @FXML protected PasswordField pw;
    @FXML protected Button val;
    @FXML protected VBox vbox;

    @FXML
    protected void passTest(ActionEvent e) throws SQLException{
        if(Comptes.checkPW(CompteControl.id, pw.getText())){
            VueSwitch.switchTo(Vue.ACCUEIL); //on se connecte
        } else {
            Label error = new Label("Identifiant ou mot de passe incorrect");
            vbox.getChildren().add(error);
            pw.setText("");
        }
    }   
}
