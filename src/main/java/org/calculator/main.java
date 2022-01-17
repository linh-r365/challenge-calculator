package org.calculator;

import org.calculator.components.Calculator;
import org.calculator.components.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.calculator.components.Settings.Operation.*;

public class main {

    private static Calculator calculator;
    private static Settings settings;

    private static boolean keepRuning = true;

    public static void setUpShutDownHook(){
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){
                keepRuning = false;
            }
        });
    }

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DIMainConfig.class);
        settings = context.getBean(Settings.class);
        calculator = context.getBean(Calculator.class);

        setUpShutDownHook();

        for (String arg : args) {
            if (arg.compareTo("-disable-new-line") == 0)
                settings.setDisableNewLine(true);
            else if (arg.compareTo("-allow-negative-numbers") == 0)
                settings.setAllowNegativeNumbers(true);
            else if (arg.compareTo("-disable-upper-bound") == 0)
                settings.setDisableUpperBound(true);
            else if (arg.compareTo("-add")==0)
                settings.setOperation(Add);
            else if (arg.compareTo("-subtraction") == 0)
                settings.setOperation(Subtraction);
            else if (arg.compareTo("-multiplication") ==0)
                settings.setOperation(Multiplication);
            else if (arg.compareTo("-division")==0)
                settings.setOperation(Division);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String line = "";
            while(keepRuning){
                if (settings.isDisableNewLine()){
                    line = reader.readLine();
                } else {
                    line = reader.readLine();
                    if (line==null || line.isEmpty()){
                        continue;
                    } else if (line.indexOf("//") == 0){
                        settings.addDelimiters(Utils.extractDelimites(line));
                        line = "";
                    }
                    line += "\n" + reader.readLine();
                }
                if (line == null || line.isEmpty())
                    continue;
                try {
                    Integer[] numbers = calculator.stringToNumbers(line.trim());
                    Integer result = calculator.calc(numbers);
                    System.out.println("Output: " + calculator.getFormula(numbers) + "=" + result);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

                settings.reset();
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("unexpected error: " + e.getMessage());
        }

    }
}
