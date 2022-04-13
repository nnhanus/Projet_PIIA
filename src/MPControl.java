import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;

public class MPControl implements Initializable{

  @FXML private VBox box; //le contenant principal
  @FXML private MediaView mv; //mediaView
  @FXML private Slider time; //slider du temps
  @FXML private Button play; //le bouton play
  @FXML private HBox hbox;  // les différents boutons
  @FXML Slider slidVol; //slider pour le volume
  @FXML Label volPourc; //indique le % du volume
  @FXML HBox boxVol;  //hbox pour le volume
  @FXML HBox boxTime; //hbow pour le temps
  @FXML Label currentTime; //temps actuel
  @FXML Label totTime; //temps total du media

  boolean isSpedUp = false; //vrai si la vidéo est en x2
 
  String file = "/Users/noemiehanus/Desktop/forever together/stages/a song written easily.mp4"; //lien du média
  Media m; //media à lire
  MediaPlayer mp; //le mediaPlayer


public void initialize(URL arg0, ResourceBundle arg1){
  //lié le media, le mediaPlayer et le mediaViews
  m = new Media(new File(file).toURI().toString());
  mp = new MediaPlayer(m);
  mv.setMediaPlayer(mp);
  //rotate la vidéo parce que apparemment elle est jamais dans le bon sens 
  mv.setRotate(-90);
  //lancer la vidéo quand le player se lance
  mp.play();

  //gestion du volume par rapport au slider
  //Comme le volume de media player est entre 0 et 1 on ramène le volume entre 0 et 1
  slidVol.setMin(0.0); 
  slidVol.setMax(1.0);
  //volume de départ
  mp.setVolume(0.3);
  slidVol.setValue(0.3); 
  volPourc.setText("30%"); 
  

  play.setText("||");//initialisation de l'affichage du bouton de lecture

  currentTime.setText("0:00");//initialisation de l'affichage du temps


  /**
   * Récupération du temps total du média
   * Pour ne pas avoir "UNKNOWN", on le récupère une fois que le player est prêt
   */
  mp.setOnReady(new Runnable() {
    @Override
    public void run() {
      totTime.setText(toTime(m.getDuration().toMinutes()));
    }
  });
  

  //Pour s'assurer de ne pas avoir un temps final de 3:34/3:35 par exemple
  mp.setOnEndOfMedia(new Runnable() {
    @Override
    public void run(){
      currentTime.setText(totTime.getText());
    }
  });

  //défilement du time slider et actualisation du temps affiché
  mp.currentTimeProperty().addListener(new InvalidationListener() {
    public void invalidated(Observable ov)
    {
      time.setValue((mp).getCurrentTime().toMillis()/(mp).getTotalDuration().toMillis() * 100);
      currentTime.setText(toTime(mp.getCurrentTime().toMinutes()));
    }
  });

  //déplacer le time slider pour accéder à différents moments de la vidéo
  time.valueProperty().addListener(new InvalidationListener() {
    public void invalidated(Observable ov)
    {
        if (time.isPressed()) { 
            mp.seek(mp.getMedia().getDuration().multiply(time.getValue() / 100));
        }
    }
  });

  //adapter la taille du videoPlayer à la fenêtre
  box.sceneProperty().addListener(new ChangeListener<Scene>() {
    @Override
    public void changed(ObservableValue<? extends Scene> observableValue, Scene scene, Scene newScene) {
      if (scene == null && newScene != null) {
          mv.fitHeightProperty().bind(newScene.heightProperty().subtract(hbox.heightProperty().add(20)));
      }
    }
  });
  
  //lier le slider du volume avec le volume du player
  slidVol.valueProperty().addListener(new InvalidationListener() {
    @Override
    public void invalidated(Observable observable) {
        mp.setVolume(slidVol.getValue());
        int print = (int) (slidVol.getValue()*100);
        volPourc.setText(""+print+"%");
    }
  });
}

  @FXML
  protected void speedUp(ActionEvent e){
    if(isSpedUp){
      mp.setRate(1.0);
      isSpedUp = false;
    } else {
      mp.setRate(2.0);
      isSpedUp = true;
    }
  }

  @FXML
  protected void play(ActionEvent e){
    Status status = mp.getStatus();
    if (status == status.PLAYING){
      mp.pause();
      play.setText(">");
    } else {
      mp.play();
      play.setText("||");
    }
  }

  protected String toTime(double x){
    String res = "";
    int tmp = (int) x;
    int virg = (int) ((x - tmp)*100);
    res = res+tmp+":"+virg;
    return res;
  }

}
