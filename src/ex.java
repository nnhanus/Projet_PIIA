package proj;

import java.io.File;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author dean
 */
public class ex extends Application {

  public static void main(String[] args) {
    launch(args);
  }
  
  @Override
  public void start(Stage primaryStage) {
    //String workingDir = System.getProperty("user.dir");
    //final File f = new File(workingDir, "../media/omgrobots.flv");
    String file = "https://youtu.be/IyT1_C5OQZc";
    
    final Media m = new Media(new File(file).toURI().toString());
    final MediaPlayer mp = new MediaPlayer(m);
    final MediaView mv = new MediaView(mp);
    
    final DoubleProperty width = mv.fitWidthProperty();
    final DoubleProperty height = mv.fitHeightProperty();
    
    width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
    height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
    
    mv.setPreserveRatio(true);

    mv.setRotate(-90); 

    /*ButtonBar bar = new ButtonBar();

    Button button1 = new Button("Play");
    Button button2 = new Button("Pause");
    Button button3 = new Button("Stop");*/
    
        
    /*button1.setOnAction(e -> {
        mp.play();
    });
        
    button2.setOnAction(e -> {
        mp.pause();
    });
        
    button3.setOnAction(e -> {
        mp.stop();
    });*/
    
    StackPane root = new StackPane();
   // GridPane root = new GridPane();
    
    root.getChildren().add(mv);
    //root.getChildren().add(button1);
    //root.getChildren().add(button2);
    //root.getChildren().add(button3);
    
    final Scene scene = new Scene(root, 960, 540);
    scene.setFill(Color.BLACK);
    
    primaryStage.setScene(scene);
    primaryStage.setTitle("Full Screen Video Player");
    primaryStage.setFullScreen(true);
    primaryStage.show();
    
    mp.play();
  }
}
