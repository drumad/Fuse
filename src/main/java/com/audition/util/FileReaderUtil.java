package com.audition.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Class to read input files based on its caller classes.
 *
 * @author andrewmadrazo
 */
public class FileReaderUtil {

    private static String readFromInputStream(InputStream inputStream) throws IOException {

        StringBuilder buffer = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                buffer.append(line).append("\n");
            }
        }
        return buffer.toString();
    }

    private static String getCallerClassName() {

        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (int i = 1; i < elements.length; i++) {
            StackTraceElement element = elements[i];
            if (!element.getClassName().equals(FileReaderUtil.class.getName()) && element.getClassName().indexOf("java.lang.Thread") != 0) {
                return element.getClassName();
            }
        }
        return null;
    }

    public static String readInputFile() throws IOException {

        String callingClass = getCallerClassName();

        // Remove the package name
        callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);

        Class<FileReaderUtil> clazz = FileReaderUtil.class;
        InputStream inputStream = clazz.getResourceAsStream("/" + callingClass + ".txt");

        return readFromInputStream(inputStream);
    }
}
