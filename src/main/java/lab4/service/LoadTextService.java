package lab4.service;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;
import javafx.stage.FileChooser;
import lab4.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public class LoadTextService {

    public void readTextFromFile(TextArea textArea){
        FileChooser fileChooser = new FileChooser();
        String fileName = textArea.getId().replace("textArea", "");
        fileChooser.setTitle("Open " + fileName);
        fileChooser.setInitialDirectory(new File("\\"));
        File file = fileChooser.showOpenDialog(Main.primaryStage);
        try {
            Scanner scanner = new Scanner(file);
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()){
                stringBuilder.append(scanner.nextLine()).append(System.lineSeparator());
            }
            textArea.setText(stringBuilder.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void saveTextToFile(TextArea textArea){
        String text = textArea.getText();
        if(text == null || text.isEmpty())
            return;
        FileChooser fileChooser = new FileChooser();
        String fileName = textArea.getId().replace("text", "");
        fileName = fileName.replace("Area", "");
        fileChooser.setTitle("Save " + fileName);
        fileChooser.setInitialFileName(fileName);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(Main.primaryStage);

        try(PrintWriter writer = new PrintWriter(file)) {
            writer.println(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static String read(TextInputControl textArea){
        return textArea.getText();
    }



}
