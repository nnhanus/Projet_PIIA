import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JPanel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;

public class PasswordControl {
   // @FXML protected Label ident;
    @FXML protected PasswordField pw;
    @FXML protected Button val;
    @FXML protected VBox vbox;

    @FXML
    protected void passTest(ActionEvent e){
        File file = new File("src/password.txt"); //chargement du fichier avec les mots de passes

        try {
            Scanner sc = new Scanner(file); //scanner pour lire le fichier
            sc.useDelimiter("\n"); //définir le delimiter comme retour à la ligne, permet d'avoir un mot de passe qui est identique à un identifiant

            while (sc.hasNextLine()) { //il reste des lignes au fichier
                String ident = sc.next(); //récupération de la ligne
                String split[] = ident.split(" "); //séparation de la ligne en 2 mots 
                if (CompteControl.id.equals(split[0])){ //l'identifiant est celui saisi
                    if((split[1]).equals(pw.getText())){ //mdp saisi identique au mdp dans le fichier
                        VueSwitch.switchTo(Vue.ACCUEIL); //on se connecte
                    } else {
                        Label error = new Label("Identifiant ou mot de passe incorrect");
                        vbox.getChildren().add(error);

                    }
                    break;
                }
            }
        } catch (FileNotFoundException f){
            System.out.println("error");
            f.printStackTrace();
        }
    }   
}
