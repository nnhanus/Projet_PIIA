import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.Action;

import javafx.event.ActionEvent;
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

public class AccueilControl implements Initializable{
    @FXML protected Button deco;
    /*@FXML protected HBox HBoxHistorique;
    @FXML protected HBox HBoxAjoutsRecents;*/
    @FXML protected HBox HBoxComptes;
    @FXML protected VBox VBoxCat;
	@FXML protected VBox VBoxComptes;
	protected static PreparedStatement getCategories;
	protected static PreparedStatement getVideos;
	protected static PreparedStatement getCompte;

	static {
		try{
			getCategories = Database.prepareStatement("SELECT * FROM category");
			getVideos = Database.prepareStatement("SELECT * FROM video WHERE idc = ?");
			getCompte = Database.prepareStatement("SELECT * FROM compte WHERE name = ?");
		} catch (SQLException e){}
	}
    
    private List<String> categories = Arrays.asList(new String[] {"Famille", "Horreur", "Animation"});

    @FXML 
    protected void deco(ActionEvent e){
        VueSwitch.switchTo(Vue.COMPTE); 
    }

    @FXML protected void switchCompte(ActionEvent e){
        VueSwitch.switchTo(Vue.ACCOUNT);
    }
    
   /* @FXML protected void affichageHistorique(ActionEvent e) {
    	for (int i = 0; i < 2; i++) {		// il faudrait parcourir l'historique
        	Button button = new Button("test" + i);
        	//HBoxHistorique.setAlignment(Pos.BOTTOM_RIGHT);
        	HBoxHistorique.getChildren().add(button);
    		
    	}
    }*/
    
   /* @FXML protected void  affichageAjoutsRecents(ActionEvent e) {
    	for (int i = 0; i < 2; i++) {		// il faudrait parcourir les ajouts r�cents
        	Button button = new Button("test" + i);
        	//HBoxHistorique.setAlignment(Pos.BOTTOM_RIGHT);
        	HBoxAjoutsRecents.getChildren().add(button);
    		
    	}
    }*/
    
    protected void  affichageComptes() {
    	/*for (int i = 0; i < 2; i++) {		// il faudrait parcourir les comptes
        	Button button = new Button("test" + i);
        	//HBoxHistorique.setAlignment(Pos.BOTTOM_RIGHT);
        	HBoxComptes.getChildren().add(button);*/
		
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
				v.setPadding(new Insets(10,10,10,10));
				Button b = new Button(c);
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
    	for (String categorie : categories) {				//on pourrait faire une choice box plutot, jsp
    		Label label = new Label(categorie + ">");
    		HBox hbox = new HBox();
    		Button button = new Button("test");
    		hbox.getChildren().add(button);
    		VBoxCat.getChildren().addAll(label, hbox);	
    	}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		affichageCat();
		try {
			CompteControl.getCompte.setString(1, CompteControl.id);
			ResultSet rs = CompteControl.getCompte.executeQuery();
			if (rs.getInt("status") == 0){
				affichageComptes();
			} else {
				VBoxComptes.setVisible(false);
			}
		} catch (SQLException e) {}
		
	}
   
}
