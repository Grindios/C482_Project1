package main;

import View_controller.MainScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {



    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainScreen.class.getResource("MainScreen.fxml"));


            Parent root = fxmlLoader.load();
            MainScreen mainScreen = fxmlLoader.getController();
            Scene scene = new Scene(root);

            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}