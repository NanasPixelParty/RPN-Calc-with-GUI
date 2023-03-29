package hellofx;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.Menu;

import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
 

public class CalculatorController implements ChangeListener<String> {
   
    @FXML

    private TextField anzeigeTextArea;

    @FXML

    private Button button0;

    @FXML

    private Button button1;

    @FXML

    private Button button2;

    @FXML

    private Button button3;

    @FXML

    private Button button4;

    @FXML

    private Button button5;

    @FXML

    private Button button6;

    @FXML

    private Button button7;

    @FXML

    private Button button8;

    @FXML

    private Button button9;

    @FXML

    private Button plusButton;

    @FXML

    private Button malButton;

    @FXML

    private Button clearAllButton;

    @FXML

    private Button loeschenButton;

    @FXML

    private Button quitButton;

    @FXML
    private Button calculateButton;

    @FXML

    private Menu runMenu;

    @FXML

    private RadioMenuItem runModeMenuItem;

    @FXML

    private RadioMenuItem stepModeMenuItem;

    @FXML

    private MenuItem quitMenuItem;

    private boolean zahlEingeben = false;

    private CalculatorModel calculatorModel = new CalculatorModel();

   

    @FXML

    private void initialize() {

        // Initializing the calculator
        calculatorModel.resultProperty().addListener(this);
        

        // Initialize the number buttons

        button0.setOnAction(event -> symbolButtonAction("0"));

        button1.setOnAction(event -> symbolButtonAction("1"));

        button2.setOnAction(event -> symbolButtonAction("2"));

        button3.setOnAction(event -> symbolButtonAction("3"));

        button4.setOnAction(event -> symbolButtonAction("4"));

        button5.setOnAction(event -> symbolButtonAction("5"));

        button6.setOnAction(event -> symbolButtonAction("6"));

        button7.setOnAction(event -> symbolButtonAction("7"));

        button8.setOnAction(event -> symbolButtonAction("8"));

        button9.setOnAction(event -> symbolButtonAction("9"));

        // Initialize the operator buttons

        plusButton.setOnAction(event -> symbolButtonAction("+"));

        malButton.setOnAction(event -> symbolButtonAction("*"));

    }

    @FXML
    private void quit() {
        quitButton.getScene().getWindow().hide();
        
    }
    /**
     * deletes last character in text area
     */

    @FXML
    public void deleteLastCharacter() {
        String text = anzeigeTextArea.getText();
        if (text.length() > 0) {
            text = text.substring(0, text.length() - 1);
            anzeigeTextArea.setText(text);
        }
    }

    /**
     * Deletes text in text area
     */

    @FXML
    private void clearTextArea() {
        anzeigeTextArea.clear();
    }

    /**
     * shows the symbol in the text area
     * @param symbol
     */
    private void symbolButtonAction(String symbol) {

        if (!zahlEingeben) {

            anzeigeTextArea.setText("");
            zahlEingeben = true;

        }

        anzeigeTextArea.appendText(symbol);

    }

    @FXML
    public void calculate() {
        this.calculatorModel.performCaclulationOnString(this.anzeigeTextArea.getText(), stepModeMenuItem.isSelected());
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if(newValue.length() > 24) {
            newValue = newValue.substring(0, 21)+"...";
        }
        anzeigeTextArea.setText(newValue);
    
    }
    
}