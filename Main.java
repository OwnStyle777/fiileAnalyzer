package analyzer;

import javax.imageio.IIOException;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        File pdfFile = new File( args[0]);
        List<File> listOfFiles = Arrays.asList(Objects.requireNonNull(pdfFile.listFiles()));
        String filePattern = args[1];
        String fileType = args[2];

        FileProcessor fileProcessor = new FileProcessor();
        fileProcessor.processFiles(listOfFiles, fileType, filePattern);
    }
}


