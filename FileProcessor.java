package analyzer;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class FileProcessor {
    public void processFiles(List<File> listOfFiles) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (File file : listOfFiles) {
            executor.submit(() -> {
                try {
                    // get file name and file type using probeContentType method
                    String nameAndType = file.getName() + ": " + Files.probeContentType(file.toPath());
                    System.out.println(nameAndType);
                } catch (IOException e) {
                    System.out.println("Error processing file: " + file.getName());
                }
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println("Executor interrupted");
        }
    }
}
