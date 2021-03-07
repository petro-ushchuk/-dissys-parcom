package lab2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import lab2.service.LoadTextService;
import lab2.service.OneThreadBasedComputing;
import lab2.service.RandomNumbersService;
import lab2.service.ThreadBasedComputing;
import lab4.Client;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.concurrent.ExecutionException;

import static lab2.Main.log;

public class ComputerController implements Serializable {
    private static final LoadTextService textService = new LoadTextService();
    private static final RandomNumbersService random = new RandomNumbersService();


    @FXML private final ToggleGroup group = new ToggleGroup();
    @FXML private Pane remotePane;

    @FXML private TextArea logArea;
    @FXML private Label time;

    @FXML private RadioButton multithreading;
    @FXML private RadioButton oneThread;
    @FXML private RadioButton remote;
    @FXML private TextField address;
    @FXML private TextField port;
    @FXML private TextField host;

    @FXML private TextField textFieldN;
    @FXML private TextField result;

    @FXML private TextArea textAreaA;
    @FXML private TextArea textAreaA1;
    @FXML private TextArea textAreaB1;
    @FXML private TextArea textAreaC1;
    @FXML private TextArea textAreaA2;
    @FXML private TextArea textAreaB2;

    public void start() throws InterruptedException, ExecutionException, IOException {
        log.info("Started...");
        long start = System.currentTimeMillis();
        float x = 0.0F;
        if(remote.isSelected()) {
            x = Client.calculate(this, Integer.parseInt(textFieldN.getText()), address.getText(), host.getText(), Integer.parseInt(port.getText()));
        }
        if (multithreading.isSelected()) {
            x = ThreadBasedComputing.calculate(this, Integer.parseInt(textFieldN.getText()));
        }
        if (oneThread.isSelected()) {
            x = OneThreadBasedComputing.calculate(this, Integer.parseInt(textFieldN.getText()));
        }
        result.setText(String.valueOf(x));
        long end = System.currentTimeMillis() - start;
        time.setText(end + " ms.");
        log.info("Finished in " + end + "ms.");
        setOnLog();
    }

    private void setOnLog() throws IOException {
        int i = Integer.MAX_VALUE;
        File file = new File("./log.txt");
        byte[] bytes;
        long start;
        int end;
        if (file.length() < i) {
            bytes = new byte[(int) file.length()];
            start = 0;
            end = (int) file.length();
        } else {
            bytes = new byte[i];
            start = file.length() - i;
            end = i;
        }

        RandomAccessFile raf = new RandomAccessFile(file, "r");
        raf.seek(start);
        raf.read(bytes, 0, end);
        logArea.setText(new String(bytes));
    }

    public void setTextA() {
        textService.readTextFromFile(textAreaA);
    }

    public void saveTextA() {
        textService.saveTextToFile(textAreaA);
    }

    public void setTextA1() {
        textService.readTextFromFile(textAreaA1);
    }

    public void saveTextA1() {
        textService.saveTextToFile(textAreaA1);
    }

    public void setTextB1() {
        textService.readTextFromFile(textAreaB1);
    }

    public void saveTextB1() {
        textService.saveTextToFile(textAreaB1);
    }

    public void setTextC1() {
        textService.readTextFromFile(textAreaC1);
    }

    public void saveTextC1() {
        textService.saveTextToFile(textAreaC1);
    }

    public void setTextA2() {
        textService.readTextFromFile(textAreaA2);
    }

    public void saveTextA2() {
        textService.saveTextToFile(textAreaA2);
    }

    public void setTextB2() {
        textService.readTextFromFile(textAreaB2);
    }

    public void saveTextB2() {
        textService.saveTextToFile(textAreaB2);
    }

    public void generate() {
        int n = getInteger(textFieldN);
        if (n < 1)
            return;
        new Thread(() -> textAreaA.setText(random.generateMatrix(n))).start();
        new Thread(() -> textAreaA1.setText(random.generateMatrix(n))).start();
        new Thread(() -> textAreaA2.setText(random.generateMatrix(n))).start();
        new Thread(() -> textAreaB1.setText(random.generateVector(n))).start();
        new Thread(() -> textAreaB2.setText(random.generateMatrix(n))).start();
        new Thread(() -> textAreaC1.setText(random.generateVector(n))).start();
        System.gc();
    }

    public TextArea getTextAreaA() {
        return textAreaA;
    }

    public TextArea getTextAreaA1() {
        return textAreaA1;
    }

    public TextArea getTextAreaB1() {
        return textAreaB1;
    }

    public TextArea getTextAreaC1() {
        return textAreaC1;
    }

    public TextArea getTextAreaA2() {
        return textAreaA2;
    }

    public TextArea getTextAreaB2() {
        return textAreaB2;
    }

    private int getInteger(TextField textAreaN) {
        int n = 0;
        String textN = textAreaN.getText();
        if (textN.isEmpty()) {
            textAreaN.requestFocus();
            return n;
        }
        try {
            n = Integer.parseInt(textN);
        } catch (NumberFormatException e) {
            textAreaN.requestFocus();
            return n;
        }
        return n;
    }

    public void saveLog() {
        textService.saveTextToFile(logArea);
    }

    public ToggleGroup getGroup() {
        return group;
    }

    public RadioButton getRemote() {
        return remote;
    }

    public Pane getRemotePane() {
        return remotePane;
    }

    public void showPane(){
        remotePane.setVisible(remote.isSelected());
    }
}
