package com.sports.teambuilder.functions;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CommonUtilFunctions {
    public static String replaceString(String input, Map<String, String> replacementValues) {
        // Define the pattern to match text within {}
        Pattern pattern = Pattern.compile("\\{([^}]*)\\}");

        // Perform the replacement
        StringBuffer resultString = new StringBuffer();
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String key = matcher.group(1);
            String replacement = replacementValues.getOrDefault(key, "");
            matcher.appendReplacement(resultString, replacement);
        }
        matcher.appendTail(resultString);

        // Return the result
        return resultString.toString();
    }
}
