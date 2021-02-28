package lab2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lab2.service.LoadTextService;
import lab2.service.RandomNumbersService;
import lab2.service.ThreadBasedComputing;

import java.time.LocalDateTime;

public class ComputerController {

    static final LoadTextService textService = new LoadTextService();
    private static final RandomNumbersService random = new RandomNumbersService();

    final StringBuffer stringBuffer = new StringBuffer();

    @FXML private TextArea log;

    @FXML private TextField textFieldN;
    @FXML private TextField result;

    @FXML private TextArea textAreaA;
    @FXML private TextArea textAreaA1;
    @FXML private TextArea textAreaB1;
    @FXML private TextArea textAreaC1;
    @FXML private TextArea textAreaA2;
    @FXML private TextArea textAreaB2;

    @FXML
    public void start() throws InterruptedException {
        stringBuffer.delete(0, stringBuffer.length());
        long start = System.currentTimeMillis();
        Double x = ThreadBasedComputing.calculate(this, Integer.parseInt(textFieldN.getText()));
        result.setText(x.toString());
        long end = System.currentTimeMillis() - start;
        log("Calculated in " + end + " ms.");
        log.setText(stringBuffer.toString());
    }

    @FXML public void setTextA(){ textService.readTextFromFile(textAreaA); }
    @FXML public void saveTextA(){ textService.saveTextToFile(textAreaA); }
    @FXML public void generateA(){textAreaA.setText(random.generateMatrix(textFieldN));}

    @FXML public void setTextA1(){ textService.readTextFromFile(textAreaA1); }
    @FXML public void saveTextA1(){ textService.saveTextToFile(textAreaA1); }
    @FXML public void generateA1(){textAreaA1.setText(random.generateMatrix(textFieldN));}

    @FXML public void setTextB1(){ textService.readTextFromFile(textAreaB1); }
    @FXML public void saveTextB1(){ textService.saveTextToFile(textAreaB1); }
    @FXML public void generateB1(){textAreaB1.setText(random.generateVector(textFieldN));}

    @FXML public void setTextC1(){ textService.readTextFromFile(textAreaC1); }
    @FXML public void saveTextC1(){ textService.saveTextToFile(textAreaC1); }
    @FXML public void generateC1(){textAreaC1.setText(random.generateVector(textFieldN));}

    @FXML public void setTextA2(){ textService.readTextFromFile(textAreaA2); }
    @FXML public void saveTextA2(){ textService.saveTextToFile(textAreaA2); }
    @FXML public void generateA2(){textAreaA2.setText(random.generateMatrix(textFieldN));}

    @FXML public void setTextB2(){ textService.readTextFromFile(textAreaB2); }
    @FXML public void saveTextB2(){ textService.saveTextToFile(textAreaB2); }
    @FXML public void generateB2(){textAreaB2.setText(random.generateMatrix(textFieldN));}

    @FXML public void generate(){
        generateA();
        generateA1();
        generateA2();
        generateB1();
        generateB2();
        generateC1();
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

    public void saveLog() {
        textService.saveTextToFile(log);
    }

    public void log(String text){
        synchronized (stringBuffer) {
            stringBuffer.append("[").append(LocalDateTime.now()).append("] ").append(text).append("\r\n");
        }
    }
}
