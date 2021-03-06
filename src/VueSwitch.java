import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;

public class VueSwitch {

    protected static Scene scene;

    protected static void setScene(Scene s){
        scene = s;
    }

    /**
     * Change de la vue actuelle vers la vue vers la vue souhaitée
     * @param vue
     */
    public static void switchTo(Vue vue){
        if (scene == null){ 
            System.out.println("pas de scene");
            return;
        }
        try{
            Parent root = FXMLLoader.load(Vue.class.getResource(vue.getFileName())); //charge la vue souhaitée
            scene.setRoot(root); 
        } catch (IOException e){
            e.printStackTrace();
        }

    }
    
}
