package org.calculator.components;

import org.calculator.Constants;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class Settings {

    public enum Operation {
        Add,
        Subtraction,
        Multiplication,
        Division
    }

    private String[] delimiters = { ",", "\n" };
    private Operation operation = Operation.Add;
    private boolean disableNewLine = false;
    private boolean allowNegativeNumbers = false;
    private boolean disableUpperBound = false;

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public String getOperationLabel(){
        switch (operation){
            case Add:
                return "+";
            case Subtraction:
                return "-";
            case Multiplication:
                return "*";
            case Division:
                return "/";
            default:
                return "+";
        }
    }

    public String[] getDelimiters() {
        return this.delimiters;
    }

    public void addDelimiters(String[] mDelimiters) {
        for (char regexSpecialCharacter : Constants.RegexSpecialCharacters) {
            for (int i = 0; i < mDelimiters.length; i++) {
                if (mDelimiters[i].contains(Character.toString(regexSpecialCharacter))){
                    StringBuilder sb = new StringBuilder(mDelimiters[i]);
                    for (int index=0; index<sb.length(); index++){
                        if (index == 0 && sb.charAt(index) == regexSpecialCharacter ||
                                index != 0 && sb.charAt(index) == regexSpecialCharacter && sb.charAt(index-1) != '\\'){
                            sb.insert(index, '\\');
                            index++;
                        }
                    }
                    mDelimiters[i] = sb.toString();

                }
            }
        }
        ArrayList<String> tmpDelimiters = new ArrayList<>();
        tmpDelimiters.addAll(Arrays.asList(delimiters));
        tmpDelimiters.addAll(Arrays.asList(mDelimiters));
        this.delimiters = tmpDelimiters.toArray(new String[tmpDelimiters.size()]);
    }

    public void reset(){
        this.delimiters = new String[] { ",", "\n" };
    }

    public boolean isDisableNewLine() {
        return disableNewLine;
    }

    public void setDisableNewLine(boolean disableNewLine) {
        this.disableNewLine = disableNewLine;
    }

    public boolean isAllowNegativeNumbers() {
        return allowNegativeNumbers;
    }

    public void setAllowNegativeNumbers(boolean allowNegativeNumbers) {
        this.allowNegativeNumbers = allowNegativeNumbers;
    }

    public boolean isDisableUpperBound() {
        return disableUpperBound;
    }

    public void setDisableUpperBound(boolean disableUpperBound) {
        this.disableUpperBound = disableUpperBound;
    }
}
