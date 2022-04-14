import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CompteControl implements Initializable {
    @FXML protected VBox box;
    public static String id;
    protected static ArrayList<String> comptes;
    //@FXML protected HBox hbox;
    protected static PreparedStatement getCompte;

    static{
        try {
            getCompte = Database.prepareStatement("SELECT * FROM compte WHERE name = ?");
        } catch (SQLException e) {}
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            ArrayList<String> comptes = Comptes.getUsers();
            for (String c : comptes){
                getCompte.setString(1, c);
                ResultSet rs = getCompte.executeQuery();
                HBox h = new HBox();
                h.setPadding(new Insets(15, 15, 15, 15));
                ImageView im = new ImageView();
                im.setImage(new Image(rs.getString("photo"), 75, 75, false, false)); 
                //im.setImage(new Image(getClass().getResource(rs.getString("photo")).toString()));
                Label ty = new Label();
                if (rs.getInt("status") == 0){
                    ty.setText("Adulte");
                } else if (rs.getInt("status") == 1){
                    ty.setText("Adolescent");
                } else if (rs.getInt("status") == 2){
                    ty.setText("Enfant");
                }
                VBox v = new VBox();
                v.setPadding(new Insets(10,10,10,10));
                Button b = new Button(c);
                v.getChildren().addAll(b, ty);
                h.getChildren().add(im);
                h.getChildren().add(v);
                box.getChildren().add(h);
                b.setOnAction(e -> {
                    id = c;
                    change();
                });
            }
        } catch (SQLException e) {}
        
    }
    
    protected static void change(){
        try {
            int type = Comptes.getType(id);
            if (type == 2){
                //VueSwitch.switchTo(Vue.ACCUEIL);
                VueSwitch.switchTo(Vue.VIDINFO);
            } else {
                VueSwitch.switchTo(Vue.PASSWORD);
            }
        } catch (SQLException e) {}  
    }
}
