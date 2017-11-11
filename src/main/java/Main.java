import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = ;
        Scene sceneMain = new Scene(FXMLLoader.load(getClass().getResource("enter.fxml")), 294, 103);
        primaryStage.setTitle("Вход");
        primaryStage.setScene(sceneMain);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
