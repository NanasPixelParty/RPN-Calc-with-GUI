package hellofx;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * This class is the main class of the calculator. It performs the calculation
 * and stores the result on the stack
 */

public class Calculator {

    // the stack that stores the elements of the calculation until the result is on top of the stack
    private Stack<Integer> stack = new Stack<>();
    // the calculation string
    private String evaluationString = "";
    // the current position in the string
    private int step = 0;
    // if true we step char by char through the string
    private boolean stepMode = false;

    /**
     * We set the new calculation string and clear the stack
     * @param expression is the calculation string
     * step is the current position in the string
     */

    public void setEvaluationString(String expression) {
        stack.clear();
        step = 0;
        evaluationString = expression;
    }

    /**
     * Gives us the posibillity to change the mode of the calculation
     * @param newMode
     */
    public void setStepMode(boolean newMode) {
        stepMode = newMode;
    }

    private boolean caclulateStep() {
        // we step char by char to the string array until step is equal to the length of the string array which means we are at the end of the calculation
        char c = this.evaluationString.charAt(step++);
        evaluateChar(c);
        if(step == evaluationString.length() && stack.size() > 1) {
            throw new IllegalArgumentException("Ungültiger Ausdruck");
        }
        return step == evaluationString.length();
    }

    private boolean calculateFull() {
        for(char c : this.evaluationString.toCharArray()) {
            evaluateChar(c);
        }

        if(stack.size() > 1){
            throw new IllegalArgumentException("Ungültiger Ausdruck");
        }

        return true;
    }

    /**
     * 
     * @return true if the calulation is finished
     */
    public boolean calculate() {
        if(stepMode) {
            return caclulateStep();
        }

        return calculateFull();
    }

    private void validateStack() {
        if(stack.size() < 2){
            throw new IllegalArgumentException("Ungültiger Ausdruck");
        }
    }

    /**
     * If there is a operator wie pop the last two numbers on top of the stack and perform the calculation
     * after that we push the result back on the stack
     * Is there just a single number we push it on the stack
     * @param c is the charakter that our machine is looking at right now
     */
    private void evaluateChar(char c) {
        switch(c) {
            case '+': {
                validateStack();
                Integer num1 = stack.pop();
                Integer num2 = stack.pop();
                stack.push(num1+num2);
                break;
            }
            case '*': {
                validateStack();
                Integer num1 = stack.pop();
                Integer num2 = stack.pop();
                stack.push(num1*num2);
                break;
            }
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                stack.push(Integer.parseInt(""+c));
                break;
            default:
                throw new IllegalArgumentException(String.format("'%s' ist kein valides Zeichen", c));
        }
    }

    /**
     * returns the result of the calculation that happes rigfht now on the stack
     *
     * @throws ArithmeticException if there is a invalid expression or calculation is not complete
     */
    public int getStackResult() {
        try {
            return stack.pop();
        } catch (EmptyStackException e) {
            throw new ArithmeticException("Ungültiger Ausdruck oder unvollständige Berechnung");
        }
    }

    /**
     * returns tthe actual state of the stack
     * creates a string of the stack and removes the brackets
     * if step-mode is active we show the current calculation with a arrow	
     *
     * @return actual state of the stack
     */
    public String getStackState() {
        String base = stack.toString();
        base = base.substring(1, base.length()-1);
        if(!stepMode) {
            return base;
        }

        if(evaluationString.substring(step).length() > 0) {
            base += " -> ";
            base += evaluationString.substring(step);
        }
        return base;
    }
}
