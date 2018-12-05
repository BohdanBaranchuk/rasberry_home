package com.homedev.smart_home.smart89.v1.jdk.libs.string_utils;


public class StringUtils {

    public static String firstStingBetween(
            String inputString,
            String beginDelimiter,
            String endDelimiter) throws StringUtilsException {

        int beginIndex = inputString.indexOf(beginDelimiter);

        if (beginIndex == -1) {
            throw new StringUtilsException(
                    "Not fount beginDelimeter: " + beginDelimiter +
                            " in inputString: " + inputString);
        }
        beginIndex = beginIndex + beginDelimiter.length();

        if (beginIndex >= inputString.length()) {
            throw new StringUtilsException(
                    "BeginDelimeter find at the end of the inputString. BeginDelimeter index: " + beginIndex);
        }

        int endIndex = inputString.indexOf(endDelimiter, beginIndex);

        if (endIndex == -1) {
            throw new StringUtilsException(
                    "Not found endDelimeter after beginDelimeter.\n" +
                            "BeginDelimeter: " + beginDelimiter + "\n" +
                            "EndDelimeter: " + endDelimiter + "\n" +
                            "BeginDelimeter index: " + beginIndex + "\n" +
                            "EndDelimeter index: " + endIndex + "\n" +
                            "InputString: " + inputString);
        }

        if (beginIndex >= endIndex) {
            throw new StringUtilsException(
                    "Found beginDelimeter after endDelimeter." + "\n" +
                            "BeginDelimeter index: " + beginIndex + "\n" +
                            "EndDelimeter index: " + endIndex + "\n" +
                            "BeginDelimeter: " + beginDelimiter + "\n" +
                            "EndDelimeter: " + endDelimiter + "\n" +
                            "InputString: " + inputString);
        }

        return inputString.substring(beginIndex, endIndex);
    }
}
