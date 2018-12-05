package com.homedev.smart_home.smart89.v1.jdk.libs.file_utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

    public static String readFileAsStringBuffered(String fileName) throws IOException {

        File file = new File(fileName);

        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(fileReader);

        StringBuilder sb = new StringBuilder();
        String st;
        while ((st = br.readLine()) != null) {
            sb.append(st);
            sb.append("\n");
        }

        return sb.toString();
    }
}
