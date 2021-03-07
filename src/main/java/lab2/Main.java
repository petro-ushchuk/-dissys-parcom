package lab2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lab2.controller.ComputerController;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main extends Application {

    public static Logger log;
    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        URL resource = getClass().getResource("/sample.fxml");
        loader.setLocation(resource);
        Parent root = loader.load();
        ComputerController controller = new ComputerController();
        loader.setController(controller);
        primaryStage.setTitle("Обчислення виразу x=(y2'Y3^2*y1 + y1')(Y3y1+y2)");
        primaryStage.setScene(new Scene(root, 675, 400));
        primaryStage.show();

    }


    public static void main(String[] args) {
        FileHandler fh;
        try {
            InputStream stream = Main.class.getClassLoader().
                    getResourceAsStream("logging.properties");
            LogManager.getLogManager().readConfiguration(stream);
            log = Logger.getLogger("Application Log");
            fh = new FileHandler("./log.txt");
            log.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
        launch(args);
    }


}
