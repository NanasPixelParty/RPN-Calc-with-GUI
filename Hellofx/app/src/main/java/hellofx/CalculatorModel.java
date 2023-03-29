package hellofx;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for the calculator. This class contains the logic for the
 * calculator.
 * It is used by the {@link CalculatorController} to perform the calculations.
 */

public class CalculatorModel {
    // the calculator instance
    Calculator calculator = new Calculator();

    /**
     * using a StringProperty to store the result value.
     * This allows to bind the value to a UI element or
     * add a {@link javafx.beans.value.ChangeListener} to react on changes.
     */
    private final StringProperty result = new SimpleStringProperty();
    // the thread that runs the step mode
    private Thread stepRunner;

    /** Enum to store the validity state of the form values */
    enum ValuesState {
        UNDEFINED, OK, ERROR
    }

    private ValuesState valuesState = ValuesState.UNDEFINED;

    public CalculatorModel() {
    }

    /**
     * Perform the calculation with a thread sleep to simulate the step mode
     * 
     * @param calulationString the calculation string
     * @param stepMode         if true we step char by char through the string
     * 
     *                         creating a calculator, set the parameters,
     */

    public void performCaclulationOnString(String calulationString, boolean stepMode) {
        // setting the calculation string and the step mode
        this.calculator.setEvaluationString(calulationString);
        this.calculator.setStepMode(stepMode);
        // result is set to "" to clear the result field
        this.result.set("");
        // if we are in step mode we start a thread that helps us to step through the
        // calculation
        if (stepMode) {
            // we need a final reference to the calculator instance to use it in the thread
            final Calculator threadCalc = this.calculator;
            // we start a new thread that runs the calculation
            stepRunner = new Thread() {
                // we override the run method to run the calculation
                @Override
                public void run() {
                    // the loop runs until the calculation is finished
                    try {
                        while (!calculator.calculate()) {
                            // we set the result to the current stack state and sleep for 1 second
                            result.set(threadCalc.getStackState());
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                            }
                        }
                        result.set(threadCalc.getStackState());
                    } catch (IllegalArgumentException e) {
                        result.set("INVALID EXPRESSION");
                    }
                }
            };
            // we start the thread
            stepRunner.start();
        } else {
            // if we are not in step mode we just run the calculation
            try {
                this.calculator.calculate();
                result.set(this.calculator.getStackState());
            } catch (IllegalArgumentException e) {
                result.set("INVALID EXPRESSION");
            }
        }
    }

    /** returns the validation state of the form values */
    public ValuesState getValuesState() {
        return this.valuesState;
    }

    /** Reset the values state to the default value (UNDEFINED) */
    public void resetValuesState() {
        this.valuesState = ValuesState.UNDEFINED;
    }

    /**
     * String containing the result of the calculation
     * Can be "", if no calculation or check is done or could contain the error
     * message on invalid values
     *
     * @return String with the result of the value checking or the calculation
     */
    public String getResult() {
        return result.get();
    }

    // Solution with bound properties

    /**
     * Sets the result of the calculation
     *
     * @param resultText text to be displayed in the result field
     */
    public void setResult(String resultText) {
        result.set(resultText);
    }

    /**
     * Gives access to the StringProperty holding the result of the calculation
     *
     * @return result String property which can be bound to UI elements
     */
    public StringProperty resultProperty() {
        return result;
    }
}
