package org.calculator.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class Calculator {

    @Autowired
    private Settings settings;

    public Integer[] stringToNumbers(String str) throws Exception {
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> negativeNumbers = new ArrayList<>();

        for (String snumber : str.split(String.join("|", this.settings.getDelimiters()))){
            try {
                Integer number = Integer.parseInt(snumber.trim());
                if (number < 0){
                    negativeNumbers.add(number);
                } else if (!settings.isDisableUpperBound() && number > 1000) {
                    number = 0; //- skip invalid numbers ( > 1000 )
                }
                result.add(number);
            } catch(NumberFormatException e){
                result.add(0);
            }
        }

        if (!settings.isAllowNegativeNumbers() && negativeNumbers.size() > 0){
            String exceptionMessage = "Input contains negative numbers:";
            for (Integer negativeNumber : negativeNumbers)
                exceptionMessage += " " + negativeNumber;
            throw new Exception(exceptionMessage);
        }
        return result.toArray(new Integer[result.size()]);
    }

    public Integer calc(Integer[] array){
        Integer result = array.length == 0 ? 0 : array[0];
        for (int i = 1; i<array.length; i++) {
            switch (this.settings.getOperation()){
                case Add: {
                    result += array[i];
                    break;
                }
                case Subtraction:{
                    result -= array[i];
                    break;
                }
                case Division:{
                    result /= array[i];
                    break;
                }
                case Multiplication:{
                    result *= array[i];
                    break;
                }
                default: {
                    break;
                }
            }
        }
        return result;
    }

    public String getFormula(Integer[] array){
        if (array.length == 0)
            return "0";
        else {
            return Arrays.stream(array)
                .map(Object::toString)
                    .collect(Collectors.joining(this.settings.getOperationLabel()));
        }
    }
}
