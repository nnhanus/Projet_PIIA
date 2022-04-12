import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CompteControl {
    @FXML protected Button b1;
    @FXML protected Button b2;
    @FXML protected Button b3;
    public static String id;

    @FXML 
    protected void connect(ActionEvent e){
        id = ((Button) e.getSource()).getText();
        if (id.equals("Mathéo")){
            VueSwitch.switchTo(Vue.ACCUEIL);
        } else {
            VueSwitch.switchTo(Vue.PASSWORD);
        }
        
    }

    
}
