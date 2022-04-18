import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.swing.Action;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AccueilControl implements Initializable{
    @FXML protected Button deco;
    @FXML protected HBox HBoxComptes;
    @FXML protected VBox VBoxCat;
	@FXML protected VBox VBoxComptes;
	protected static PreparedStatement getCompte;
	protected static PreparedStatement getMinia;
	@FXML protected Button ajoutVid;
	@FXML protected Button ajoutCat;
	@FXML VBox newCat;
	@FXML TextField catNom;

	static {
		try{
			getCompte = Database.prepareStatement("SELECT * FROM compte WHERE name = ?");
			getMinia = Database.prepareStatement("SELECT mini FROM video WHERE name = ?");
		} catch (SQLException e){
			System.err.println(e.getMessage() + " " + e.getCause());;
		}
	}
    
   // private List<String> categories = Arrays.asList(new String[] {"Famille", "Horreur", "Animation"});

    @FXML 
    protected void deco(ActionEvent e){
        VueSwitch.switchTo(Vue.COMPTE); 
    }

    @FXML protected void switchCompte(){
        VueSwitch.switchTo(Vue.ACCOUNT);
    }
    
    protected void  affichageComptes() {
    	try{
			ArrayList<String> comptes = Comptes.getUsers();
			for (String c : comptes){
				getCompte.setString(1, c);
				ResultSet rs = getCompte.executeQuery();
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
				HBoxComptes.getChildren().add(h);
				/*b.setOnAction(e -> { });*/
			}
		} catch (SQLException ex){}

	}
    
    @FXML protected void affichageCat() {
		ArrayList<String> cats = Video.getCatForUser();
		for (int i = 0; i < cats.size(); i++){
			String nom = cats.get(i);
			Button cat = new Button(nom);
			VBox tmp = new VBox();
			tmp.setPadding(new Insets(15, 0, 0, 15));
			tmp.getChildren().add(cat);
			ScrollPane p = new ScrollPane();
			HBox vi = new HBox();
			p.setContent(vi);
			ArrayList<String> vids = Video.getVidFroCat(nom);
			for (int j = 0; j < vids.size(); j++){
				VBox vid = new VBox();
				String nomVid = vids.get(j);
				Label nameVid = new Label(nomVid);
				vi.setPadding(new Insets(10,0,20,0));
				vid.setPadding(new Insets(10, 20, 0, 0));
				vid.getChildren().add(nameVid);
				try {
					getMinia.setString(1, nomVid);
					ResultSet min = getMinia.executeQuery();
					ImageView mini = new ImageView();
					mini.setFitHeight(50);
					mini.setFitWidth(75);
					mini.setImage(new Image(min.getString("mini")));
					vid.getChildren().add(mini);
				} catch (SQLException e1) {
					System.err.println(e1.getMessage() + " " + e1.getCause());;
				}
				nameVid.setOnMouseClicked(e -> {
					VidInfControl.nom_vid = nomVid;
					VueSwitch.switchTo(Vue.VIDINFO);
				});
				vi.getChildren().add(vid);
			}
			cat.setOnAction(e -> {
				CatInfoControl.categorie = nom;
				changeCat();	
			});
			tmp.getChildren().add(vi);
			VBoxCat.getChildren().add(tmp);
		}
    }

	protected void changeCat(){
		VueSwitch.switchTo(Vue.CAT_INFO);
	}

	@FXML protected void ajoutVid(){
		VueSwitch.switchTo(Vue.AJOUT_VID);
	}

	@FXML protected void ajoutCat(){
		newCat.setVisible(true);
	}

	@FXML protected void valCat(){
		Video.ajoutCat(catNom.getText());
		newCat.setVisible(false);
		VueSwitch.switchTo(Vue.ACCUEIL); //reload de l'affichage
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		affichageCat();
		newCat.setVisible(false);
		try {
			CompteControl.getCompte.setString(1, CompteControl.id);
			ResultSet rs = CompteControl.getCompte.executeQuery();
			if (rs.getInt("status") == 0){
				affichageComptes();
			} else {
				VBoxComptes.setVisible(false);
				ajoutCat.setVisible(false);
				ajoutVid.setVisible(false);
			}
		} catch (SQLException e) {}
		
	}
   
}
