package sample;

import animatefx.animation.FadeIn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DictionaryApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("firstScene.fxml"));
        primaryStage.setTitle("Dictionary");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        new FadeIn(root).play();
    }

    public void runApplication(String[] s) {
        launch(s);
    }
}
