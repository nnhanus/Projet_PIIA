import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.Action;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AccountControl implements Initializable{

    @FXML protected Label nomC;
    @FXML protected Label type;
    @FXML protected Button creer;
    @FXML protected Button supp;
    @FXML protected HBox hBox;
    @FXML protected VBox vbox;
    

    public void initialize(URL arg0, ResourceBundle arg1){
        vbox.setVisible(false);
        nomC.setText(CompteControl.id);
        int ty = -1;
        try {
            ty = Comptes.getType(CompteControl.id);
        } catch (SQLException e) {}
        if (ty == 0){
            type.setText("Adulte");
            creer.setVisible(true);
            supp.setVisible(true);
        } else if (ty == 1){
            type.setText("Adolescent");
            creer.setVisible(false);
            supp.setVisible(false);
        } else if (ty == 2){
            type.setText("Enfant");
            creer.setVisible(false);
            supp.setVisible(false);
        }
        
    }

    @FXML
    public void back(){
        VueSwitch.switchTo(Vue.ACCUEIL);
    }

    @FXML 
    public void affiche(ActionEvent e){
        vbox.setVisible(true);
    }

    @FXML 
    public void suppr(ActionEvent e){
        VueSwitch.switchTo(Vue.SUPPRESSION);
    }

    @FXML 
    public void creeCompte(ActionEvent e){
        VueSwitch.switchTo(Vue.CREATION);
    }

    @FXML 
    public void creeCompteEnfant(ActionEvent e){
        VueSwitch.switchTo(Vue.CREAT_ENF);
    }

    @FXML
    public void deco(ActionEvent e){
        VueSwitch.switchTo(Vue.COMPTE); 
    }
}
