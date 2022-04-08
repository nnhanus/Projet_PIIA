

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class ex extends Application {
  
  
  
  public static void main(String[] args) {
    launch(args);
  }


  
  
  @Override
  public void start(Stage primaryStage) throws IOException {
    //BorderPane myPane = (BorderPane)FXMLLoader.load(getClass().getResource("medplay.fxml"));
    VBox myPane = (VBox)FXMLLoader.load(getClass().getResource("medplay.fxml"));
    
    final Scene scene = new Scene(myPane, 600, 400);
    scene.setFill(Color.BLACK);
    
    primaryStage.setScene(scene);
    primaryStage.setTitle("Video Player");
    primaryStage.setFullScreen(true);
    primaryStage.show();
    
    
  }
}
