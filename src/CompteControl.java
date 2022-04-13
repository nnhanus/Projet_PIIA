import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CompteControl implements Initializable {
    @FXML protected Button b1;
    @FXML protected Button b2;
    @FXML protected Button b3;
    @FXML protected static VBox box;
    public static String id;
    protected static ArrayList<String> comptes;
    @FXML protected HBox hbox;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            ArrayList<String> comptes = Comptes.getUsers();
            ObservableList<Node> liste = hbox.getChildren();
            for (String c : comptes){
                Button b = new Button(c);
                liste.add(b);
                System.out.println(c);
                b.setOnAction(e -> {
                    id = c;
                    change();

                }) ;
            }
        } catch (SQLException e) {}
        
    }
    
    protected void change(){
        try {
            int type = Comptes.getType(id);
            if (type == 2){
                VueSwitch.switchTo(Vue.ACCUEIL);
            } else {
                VueSwitch.switchTo(Vue.PASSWORD);
            }
        } catch (SQLException e) {}  
    }
}
