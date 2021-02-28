package lab2.service;

import com.sun.javafx.scene.control.behavior.TextInputControlBehavior;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.stage.FileChooser;
import lab2.Main;

import java.io.*;
import java.util.Arrays;
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
            return;
        }

    }

    public void saveTextToFile(TextArea textArea){
        String text = textArea.getText();
        if(text == null || text.isEmpty())
            return;
        FileChooser fileChooser = new FileChooser();
        String fileName = textArea.getId().replace("textArea", "");
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

    public String read(TextInputControl textArea){
        return textArea.getText();
    }



}
