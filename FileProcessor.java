package analyzer;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    public LinkedHashMap<List<String>, String> getMapOfPatterns(File file) {
        LinkedHashMap<List<String>, String> mapOfPatterns = new LinkedHashMap<>();
        Pattern pattern = Pattern.compile("\"(.*?)\"");

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Matcher matcher = pattern.matcher(line);

                List<String> matches = new ArrayList<>();
                while (matcher.find()) {
                    matches.add(matcher.group(1));
                }

                if (matches.size() >= 2) {
                    List<String> patternAndType = new ArrayList<>();
                    patternAndType.add(matches.get(0));
                    patternAndType.add(matches.get(1));
                    mapOfPatterns.put((patternAndType), line.substring(0,1));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return mapOfPatterns;
    }
}
