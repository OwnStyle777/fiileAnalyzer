package analyzer;


import java.io.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class Main {
    public static void main(String[] args) {
        String folderPath = "C:\\Users\\DELL\\Desktop\\";
        String folderName = args[0];
        File folderOfFiles = new File(folderPath + folderName);
        List<File> listOfFiles = Arrays.asList(Objects.requireNonNull(folderOfFiles.listFiles()));

        FileProcessor fileProcessor = new FileProcessor();
        fileProcessor.processFiles(listOfFiles);
    }
}


