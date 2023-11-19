package analyzer;

import javax.imageio.IIOException;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        File pdfFile = new File(args[0]);
        List<File> listOfFiles = Arrays.asList(Objects.requireNonNull(pdfFile.listFiles()));
        String pdfPattern = args[1];



        for (File file : listOfFiles) {
            long startTime = System.currentTimeMillis();
            try (InputStream inputStream = new FileInputStream(file)) {
                byte[] pdfBytes = pdfPattern.getBytes();
                byte[] allBytes = inputStream.readAllBytes();


                if (SearchingMethods.searchPatternKMP(allBytes, pdfBytes)) {
//                    System.out.println(args[2]);
                    System.out.println(file.getName() + ": " + args[2]);
//                    long elapsedTime = System.currentTimeMillis() - startTime;
//                    System.out.println("It took " + (double) elapsedTime / 10000 + "seconds");

                } else {
                    System.out.println(file.getName() + ": Unknown file type");
//                    long elapsedTime = System.currentTimeMillis() - startTime;
//                    System.out.println("It took " + (double) elapsedTime / 10000 + "seconds");

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


