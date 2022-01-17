package org.calculator;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static String[] extractDelimites(String line){
        String[] result = new String[0];
        if (line.length() == 3) {
            result = new String[1];
            result[0] = String.valueOf(line.charAt(2));
            return result;
        } else if (line.length() > 3) {
            String regex = "\\[([^\\]]+)\\]";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(line);
            ArrayList<String> delimiters = new ArrayList<>();
            while (matcher.find()) {
                delimiters.add(matcher.group(1));
            }
            result = delimiters.toArray(new String[delimiters.size()]);
        }
        return result;
    }
}
