import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class PasswordControl {
   // @FXML protected Label ident;
    @FXML protected PasswordField pw;
    @FXML protected Button val;

    @FXML
    protected void passTest(ActionEvent e){
        String pass = pw.getText();
        if (pass.equals("hello")){
            VueSwitch.switchTo(Vue.ACCUEIL);
        }
        

    }



    
    
    
}
