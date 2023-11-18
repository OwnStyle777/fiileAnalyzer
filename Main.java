package analyzer;

import javax.imageio.IIOException;
import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        File pdfFile = new File( args[0]);
        String pdfPattern = args[1];
        long startTime = System.currentTimeMillis();


            try (InputStream inputStream = new FileInputStream(pdfFile)) {
                byte[] pdfBytes = pdfPattern.getBytes();
                byte[] allBytes = inputStream.readAllBytes();


                if (searchPatternKMP(allBytes, pdfBytes)) {
                    System.out.println(args[2]);
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    System.out.println("It took " + (double) elapsedTime / 10000 + "seconds");
                } else {
                    System.out.println("Unknown file type");
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    System.out.println("It took " + (double) elapsedTime / 10000 + "seconds");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    private static int[] computePrefixTable(byte[] pattern) {
        int[] prefixTable = new int[pattern.length];
        int j = 0;
        for (int i = 1; i < pattern.length; i++) {
            while (j > 0 && pattern[i] != pattern[j]) {
                j = prefixTable[j - 1];
            }
            if (pattern[i] == pattern[j]) {
                j++;
            }
            prefixTable[i] = j;
        }
        return prefixTable;
    }
    private static boolean searchPatternKMP(byte[] text, byte[] pattern) {
        if (pattern.length == 0) {
            return true; // Ak je pattern prázdny, vždy je nájdený
        }

        int[] prefixTable = computePrefixTable(pattern);
        int j = 0; // Index do patternu
        for (int i = 0; i < text.length; i++) {
            while (j > 0 && text[i] != pattern[j]) {
                j = prefixTable[j - 1];
            }
            if (text[i] == pattern[j]) {
                j++;
            }
            if (j == pattern.length - 1) {
                return true; // Pattern nájdený
            }
        }
        return false; // Pattern nenájdený
    }
}
