package encryption;

import java.util.Base64;

public class RC4Method extends MotherEncryption {

    public RC4Method() {
        this.type = "RC4";
    }

    @Override
    public String encryption(String message, String key) {
        byte[] encryptedBytes = rc4(message.getBytes(), key.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    @Override
    public String decipher(String messageEncryption, String key) {
        byte[] encryptedBytes = Base64.getDecoder().decode(messageEncryption);
        byte[] decryptedBytes = rc4(encryptedBytes, key.getBytes());
        return new String(decryptedBytes);
    }

    /**
     * The core RC4 algorithm processing the input with the key.
     */
    private byte[] rc4(byte[] input, byte[] key) {
        int[] S = new int[256];
        int[] T = new int[256];
        int keyLength = key.length;

        // Initialization phase
        for (int i = 0; i < 256; i++) {
            S[i] = i;
            T[i] = key[i % keyLength];
        }

        // Initial permutation of S
        int j = 0;
        for (int i = 0; i < 256; i++) {
            j = (j + S[i] + T[i]) % 256;
            int temp = S[i];
            S[i] = S[j];
            S[j] = temp;
        }

        // Stream generation and input processing
        byte[] result = new byte[input.length];
        int i = 0;
        j = 0;
        for (int k = 0; k < input.length; k++) {
            i = (i + 1) % 256;
            j = (j + S[i]) % 256;
            int temp = S[i];
            S[i] = S[j];
            S[j] = temp;
            int t = (S[i] + S[j]) % 256;
            int encryptedChar = input[k] ^ S[t];
            result[k] = (byte) encryptedChar;
        }

        return result;
    }

    @Override
    public Boolean checkInputUserKey(String key) {
        return !key.isEmpty();
    }

    @Override
    public Boolean checkInputUserMessageToDecryption(String message){
        return message.isEmpty();
    }
    
    @Override
    public Boolean checkInputUserMessageToEncryption(String message){
        return message.isEmpty();
    }
}