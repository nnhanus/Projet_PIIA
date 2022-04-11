import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CompteControl {
    @FXML protected Button b1;
    @FXML protected Button b2;
    @FXML protected Button b3;
    public String id;

    @FXML 
    protected void connect(ActionEvent e){
        id = ((Button) e.getSource()).getText();
        if (id.equals("Anna")){
            VueSwitch.switchTo(Vue.PASSWORD);
        } else if (id.equals("Mathéo")){
            VueSwitch.switchTo(Vue.ACCUEIL);
        }
        
    }

    
}
