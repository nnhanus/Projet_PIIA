
import java.sql.SQLException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {
  public static void main(String[] args){
    if (Database.isValid() || Database.open()) {
      launch(args);
      Database.close();
    } else {
      System.err.println("Echec de l'ouverture de la base de données");
    }
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    //Parent myPane = FXMLLoader.load(getClass().getResource("med2.fxml"));
    Scene scene = new Scene(new Pane(), 1000, 600);
    //scene.setFill(Color.BLACK);
  
    Database.test();
    //Comptes.getUsers(); 

    VueSwitch.setScene(scene);
    VueSwitch.switchTo(Vue.COMPTE);
    
    primaryStage.setScene(scene);
    primaryStage.setTitle("Video Player");
    primaryStage.setFullScreen(false);
    primaryStage.show();
  }
}
