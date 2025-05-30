package lk.ijse.finalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Final Project");
        primaryStage.show();

        /*Parent root = FXMLLoader.load(getClass().getResource("/view/MainPage.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();*/
    }
}
