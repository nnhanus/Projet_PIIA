import javax.swing.Action;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AccueilControl {
    @FXML protected Button deco;

    @FXML 
    protected void deco(ActionEvent e){
        VueSwitch.switchTo(Vue.COMPTE); 
    }

    @FXML protected void switchCompte(ActionEvent e){
        VueSwitch.switchTo(Vue.ACCOUNT);
    }
    
}
