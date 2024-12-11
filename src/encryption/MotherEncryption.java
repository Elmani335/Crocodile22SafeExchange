package encryption;

public abstract class MotherEncryption {

    /**
     * Encrypts a message according to a key
     * @param message : String, words to encrypt
     * @param key : String, key used to encrypt
     * @return String : Ciphered words
     */
    public abstract String encryption(String message, String key);

    /**
     * Decript the message according to a key
     * @param messageEncryption : String, message encrypted
     * @param key : String, the same key that was used for encryption
     */
    public abstract String decipher(String messageEncryption, String key);

}
