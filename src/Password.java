import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class Password extends Application {
   public void start(Stage stage) {
      //Creating nodes
      TextField textField = new TextField();
      PasswordField pwdField = new PasswordField();
      //Creating labels
      Label label1 = new Label("Name: ");
      Label label2 = new Label("Pass word: ");
      //Adding labels for nodes
      HBox box = new HBox(5);
      box.setPadding(new Insets(25, 5 , 5, 50));
      box.getChildren().addAll(label1, textField, label2, pwdField);
      //Setting the stage
      Scene scene = new Scene(box, 595, 150, Color.BEIGE);
      stage.setTitle("Password Field Example");
      stage.setScene(scene);
      stage.show();
   }
   public static void main(String args[]){
      launch(args);
   }
}
