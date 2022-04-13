import java.io.File;
import java.io.FileNotFoundException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CompteControl {
    @FXML protected Button b1;
    @FXML protected Button b2;
    @FXML protected Button b3;
    public static String id;


    @FXML 
    protected void connect(ActionEvent e) throws SQLException{
        id = ((Button) e.getSource()).getText();
        File file = new File("src/password.txt"); //chargement du fichier avec les mots de passes

        int type = Comptes.getType(id);
        if (type == 2){
            VueSwitch.switchTo(Vue.ACCUEIL);
        } else {
            VueSwitch.switchTo(Vue.PASSWORD);
        }



       /* try {
            Scanner sc = new Scanner(file); //scanner pour lire le fichier
            sc.useDelimiter("\n"); //définir le delimiter comme retour à la ligne, permet d'avoir un mot de passe qui est identique à un identifiant



            while (sc.hasNextLine()) { //il reste des lignes au fichier
                String ident = sc.next(); //récupération de la ligne
                String split[] = ident.split(" "); //séparation de la ligne en 2 mots
                if (split[0].equals(id)){
                    if (split[1].equals("Enfant")){
                        VueSwitch.switchTo(Vue.ACCUEIL);
                        break;
                    } else {
                        VueSwitch.switchTo(Vue.PASSWORD);
                        break;
                    }
                } 
            }
        } catch (FileNotFoundException f){
            System.out.println("error");
            f.printStackTrace();
        }*/
        
    }

    
}
