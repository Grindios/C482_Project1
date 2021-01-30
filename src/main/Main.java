package main;

import View_controller.MainScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

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






    public void generateDummyData() {
        //<editor-fold desc="DummyDataCreation">
        // Add parts to product
        Product p1 = new Product();
        p1.setProductID(12);
        p1.setProductInStock(3);
        p1.setProductName("TestProduct1");
        p1.setProductPrice(142.19);
        p1.setMin(1);
        p1.setMax(32);

        Product p2 = new Product();
        p2.setProductID(6);
        p2.setProductInStock(6);
        p2.setProductName("TestProduct3");
        p2.setProductPrice(20.21);
        p2.setMin(4);
        p2.setMax(20);

        Product p3 = new Product();
        p3.setProductID(99);
        p3.setProductInStock(6);
        p3.setProductName("TestProduct4");
        p3.setProductPrice(40.12);
        p3.setMin(4);
        p3.setMax(20);

        InHouse part1 = new InHouse();
        part1.setMachineID(125);
        part1.setPartInStock(4);
        part1.setPartName("TestPart1");
        part1.setPartPrice(2.57);
        part1.setPartID(1);
        part1.setMin(1);
        part1.setMax(15);

        InHouse part2 = new InHouse();
        part2.setMachineID(14);
        part2.setPartInStock(5);
        part2.setPartName("TestPart2");
        part2.setPartPrice(1.27);
        part2.setPartID(2);
        part2.setMin(1);
        part2.setMax(10);

        InHouse part3 = new InHouse();
        part3.setMachineID(125);
        part3.setPartInStock(6);
        part3.setPartName("TestPart3");
        part3.setPartPrice(8.63);
        part3.setPartID(3);
        part3.setMin(1);
        part3.setMax(30);

        Outsourced part4 = new Outsourced();
        part4.setCompanyName("Test Company 1");
        part4.setPartInStock(9);
        part4.setPartName("TestPart4");
        part4.setPartPrice(125.32);
        part4.setPartID(4);
        part4.setMin(1);
        part4.setMax(214);

        p1.addAssociatedPart(part1);
        p1.addAssociatedPart(part2);
        p1.addAssociatedPart(part3);

        p2.addAssociatedPart(part4);
        p2.addAssociatedPart(part1);

        p3.addAssociatedPart(part2);

        Inventory.addProduct(p1);
        Inventory.addProduct(p2);
        Inventory.addProduct(p3);
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        //</editor-fold>
    }






    public static void main(String[] args) {
        launch(args);
    }

}
