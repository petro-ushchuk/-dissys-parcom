package lab2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lab2.controller.ComputerController;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        URL resource = getClass().getResource("/sample.fxml");
        loader.setLocation(resource);
        Parent root = loader.load();
        loader.setController(new ComputerController());
        primaryStage.setTitle("Обчислення виразу x=(y2'Y3^2*y1 + y1')(Y3y1+y2)");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
