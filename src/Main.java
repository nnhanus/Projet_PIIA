
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent myPane = FXMLLoader.load(getClass().getResource("med2.fxml"));
    Scene scene = new Scene(myPane, 600, 400);
    scene.setFill(Color.BLACK);
    
    primaryStage.setScene(scene);
    primaryStage.setTitle("Video Player");
    primaryStage.setFullScreen(false);
    primaryStage.show();
    
    
  }
}
