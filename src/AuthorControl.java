import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AuthorControl implements Initializable{
    @FXML Label cat;
    @FXML HBox boxPlus;
    @FXML HBox boxMoins;

    protected void afficheCompteSupp(){
        ArrayList<String> tmp = Video.getUsersAutho(CatInfoControl.categorie);
        try{
            for (String c : tmp){
                CompteControl.getCompte.setString(1, c);
                ResultSet rs = CompteControl.getCompte.executeQuery();
                HBox h = new HBox();
                h.setPadding(new Insets(15, 15, 15, 15));
                ImageView im = new ImageView();
                im.setImage(new Image(rs.getString("photo"), 30, 30, false, false)); 
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
                boxMoins.getChildren().add(h);
                b.setOnAction(e -> {
                    Video.supprAuthor(c);
                    reload();
                 });
            }
        } catch (SQLException e){}
    }

    protected void reload(){
        VueSwitch.switchTo(Vue.AUTHOR);
    }

    protected void afficheCompteAjout(){
        ArrayList<String> tmp = Video.getNonAuthor(CatInfoControl.categorie);
        try{
            for (String c : tmp){
                CompteControl.getCompte.setString(1, c);
                ResultSet rs = CompteControl.getCompte.executeQuery();
                HBox h = new HBox();
                h.setPadding(new Insets(15, 15, 15, 15));
                ImageView im = new ImageView();
                im.setImage(new Image(rs.getString("photo"), 30, 30, false, false)); 
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
                boxPlus.getChildren().add(h);
                b.setOnAction(e -> { 
                    Video.ajoutAuthor(c);
                    reload();
                });
            }
        } catch (SQLException e){}

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        afficheCompteAjout();
        afficheCompteSupp();
        cat.setText("Authorisations : Catégorie ' " + CatInfoControl.categorie + " ' ");
    }

    @FXML void back(){
        VueSwitch.switchTo(Vue.CAT_INFO);
    }
    
}
