import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CatInfoControl implements Initializable{
    public static String categorie;
    @FXML VBox pane;
    @FXML Label cate;
    @FXML HBox comptesPane;
    
	protected static PreparedStatement getMinia;


    @FXML void back(){
        VueSwitch.switchTo(Vue.ACCUEIL);
    }

    @FXML void ajout(){}

    protected void affichVid(){
        ArrayList<String> vids = Video.getVidFroCat(categorie);
        for (int j = 0; j < vids.size(); j++){
            String nomVid = vids.get(j);
            System.out.println(nomVid);
            VBox vid = new VBox();
            Label nameVid = new Label(nomVid);
            vid.setAlignment(Pos.TOP_CENTER);
            vid.setPadding(new Insets(15, 15, 15, 15));
            vid.getChildren().add(nameVid);
            try {
                getMinia = Database.prepareStatement("SELECT mini FROM video WHERE name = ?");
                getMinia.setString(1, nomVid);
                ResultSet min = getMinia.executeQuery();
                ImageView mini = new ImageView();
                mini.setFitHeight(75);
                mini.setFitWidth(100);
                mini.setImage(new Image(min.getString("mini")));
                vid.getChildren().add(mini);
            } catch (SQLException e1) {
                System.err.println(e1.getMessage() + " " + e1.getCause());;
            }
            pane.getChildren().add(vid);
            nameVid.setOnMouseClicked(e -> {
                VidInfControl.nom_vid = nomVid;
                VueSwitch.switchTo(Vue.VIDINFO);
            });
        }
    }

    protected void afficheCompte(){
        ArrayList<String> comptes = Video.getUsersAutho(categorie);
        try{
            for (String c : comptes){
                CompteControl.getCompte.setString(1, c);
                ResultSet rs = CompteControl.getCompte.executeQuery();
                HBox h = new HBox();
                h.setPadding(new Insets(15, 15, 15, 15));
                ImageView im = new ImageView();
                im.setImage(new Image(rs.getString("photo"), 30, 30, false, false)); 
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
                v.setPadding(new Insets(10,0,10,10));
                Button b = new Button(c);
                b.setMinWidth(50);
                v.setAlignment(Pos.TOP_CENTER);
                v.getChildren().addAll(b, ty);
                h.setAlignment(Pos.TOP_CENTER);
                h.getChildren().add(im);
                h.getChildren().add(v);
                comptesPane.getChildren().add(h);
                /*b.setOnAction(e -> { });*/
            }
        } catch (SQLException e){}
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cate.setText(categorie);
        
        
    }
    
}
