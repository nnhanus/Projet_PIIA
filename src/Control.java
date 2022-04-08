import java.util.ResourceBundle;

import java.net.URL;

import javafx.fxml.Initializable;
import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Control implements Initializable{

    @FXML MediaView mv;
    @FXML Slider time;
    @FXML Button play;

    String file = "/Users/noemiehanus/Desktop/forever together/stages/Adios.mp4";
    Media m = new Media(new File(file).toURI().toString());
    MediaPlayer mp;

  public void initialize(URL arg0, ResourceBundle arg1){
    mp = new MediaPlayer(m);
    //mv = new MediaView(mp);
    mv.setMediaPlayer(mp);
    mv.setRotate(-90);
    final DoubleProperty width = mv.fitWidthProperty();
    final DoubleProperty height = mv.fitHeightProperty();
    
    width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
    height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
    //time = new Slider(); // Slider for time
    //play = new Button("||");
    //mv.setMediaPlayer(mp);
    //SplitPane root = (SplitPane)FXMLLoader.load(getClass().getResource("medplay.fxml"));
  
    // Providing functionality to time slider
    mp.currentTimeProperty().addListener(new InvalidationListener() {
      public void invalidated(Observable ov)
      {
          updatesValues();
      }
  });

  // Inorder to jump to the certain part of video
  time.valueProperty().addListener(new InvalidationListener() {
      public void invalidated(Observable ov)
      {
          if (time.isPressed()) { // It would set the time
              // as specified by user by pressing
              mp.seek(mp.getMedia().getDuration().multiply(time.getValue() / 100));
          }
      }
  });

    //mv.setRotate(-90); 
    mp.play();
  }

  protected void updatesValues()
  {
      Platform.runLater(new Runnable() {
          public void run()
          {
              // Updating to the new time value
              // This will move the slider while running your video
              time.setValue((mp).getCurrentTime().toMillis()/(mp).getTotalDuration().toMillis() * 100);
          }
      });
  }

  @FXML
  protected void play(ActionEvent e){
    Status status = mp.getStatus();
    if (status == status.PLAYING){
      mp.pause();
    } else {
      mp.play();
    }
  }

    


}
