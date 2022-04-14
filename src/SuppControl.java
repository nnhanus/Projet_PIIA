import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.SortingFocusTraversalPolicy;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class SuppControl implements Initializable{
    @FXML protected HBox box;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            ArrayList<String> comptes = Comptes.getUsers();
            ObservableList<Node> liste = box.getChildren();
            for (String c : comptes){
                Button b = new Button(c);
                liste.add(b);
                b.setOnAction(e -> {
                    Comptes.deleteCompte(c);
                    change();
                }) ;
            }
        } catch (SQLException e) {}
        
    }

    public void change(){
        VueSwitch.switchTo(Vue.ACCOUNT);
    }
    
}
