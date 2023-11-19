package analyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileProcessor  {

    public void processFiles(List<File> listOfFiles, String fileType, String filePattern) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (File file : listOfFiles) {
            executor.submit(() -> {
                try (InputStream inputStream = new FileInputStream(file)) {
                    byte[] pdfBytes = filePattern.getBytes();
                    byte[] allBytes = inputStream.readAllBytes();

                    if (SearchingMethods.searchPatternKMP(allBytes, pdfBytes)) {
                        System.out.println(file.getName() + ": " + fileType);
                    } else {
                        System.out.println(file.getName() + ": Unknown file type");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
