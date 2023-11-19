package analyzer;

public interface SearchingMethods {

    static boolean searchPatternKMP(byte[] text, byte[] pattern) {
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

    static int[] computePrefixTable(byte[] pattern) {
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
}

