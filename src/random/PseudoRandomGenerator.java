package random;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PseudoRandomGenerator {
    private int[] bitSequence;
    private int[] lfsr;
    private int tapPosition = 2;

    public PseudoRandomGenerator(String seed) {
        bitSequence = toBitSequence(seed);
        lfsr = new int[bitSequence.length];
        System.arraycopy(bitSequence, 0, lfsr, 0, bitSequence.length);
    }

    private int[] toBitSequence(String seed) {
        byte[] bytes = seed.getBytes(StandardCharsets.UTF_8);
        int[] bits = new int[bytes.length * 8];
        for (int i = 0; i < bytes.length; i++) {
            for (int j = 0; j < 8; j++) {
                bits[i * 8 + j] = (bytes[i] >> (7 - j)) & 1;
            }
        }
        return bits;
    }

    private int step() {
        int newBit = (lfsr[0] ^ lfsr[tapPosition]) & 1;
        System.arraycopy(lfsr, 1, lfsr, 0, lfsr.length - 1);
        lfsr[lfsr.length - 1] = newBit;
        return newBit;
    }

    public int generateFirstRandomNumber() {
        int result = 0;
        for (int j = 0; j < bitSequence.length; j++) {
            result = (result << 1) | step();
        }
        return result;
    }

    public void runLFSR() {
        int iterationCount = 3;
        Map<Integer, Integer> occurrences = new HashMap<>();
        for (int i = 0; i < iterationCount; i++) {
            int result = generateFirstRandomNumber();
            System.out.println("Tour " + (i + 1) + ": Binaire -> " + Integer.toBinaryString(result) + ", Décimal -> " + result);
            occurrences.put(result, occurrences.getOrDefault(result, 0) + 1);
        }
        detectRepetitions(occurrences);
    }

    private void detectRepetitions(Map<Integer, Integer> occurrences) {
        System.out.println("Répétitions détectées :");
        for (Map.Entry<Integer, Integer> entry : occurrences.entrySet()) {
            if (entry.getValue() > 1) {
                System.out.println("Nombre " + entry.getKey() + " répété " + entry.getValue() + " fois.");
            }
        }
    }
}