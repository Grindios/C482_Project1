package main;

import View_controller.MainScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**Main class that start up up the application. Being that I worked a lot with version control with this project. In a future project I would like to add a user name input.
 * Where the user has to put in his/her id and password to modify or add anything. The user ID or input then has a user name associated with it.
 * when changes are made, it must be logged to display who made the changes, and when.*/
public class Main extends Application {


/** This is the Start method. It handles start up action. */
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

    /** This is the main method. It called when you start up the app.  */
    public static void main(String[] args) {
        launch(args);
    }

}