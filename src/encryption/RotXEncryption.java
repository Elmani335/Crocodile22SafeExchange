package encryption;

public class RotXEncryption extends MotherEncryption {



    public RotXEncryption() {
        this.type = "ROT";
    }

    @Override
    public String encryption(String message, String key) {
        return rotate(message, Integer.valueOf(key));
    }

    @Override
    public String decipher(String message, String key) {
        return rotate(message, 26 - Integer.valueOf(key));
    }

    @Override
    public Boolean checkInputUserKey(String key){
        return !key.isEmpty() && key.matches("-?\\d+");
    }

    private String rotate(String text, int shift) {
        StringBuilder result = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (Character.isUpperCase(c)) {
                char rotated = (char) (((c - 'A' + shift) % 26) + 'A');
                result.append(rotated);
            } else if (Character.isLowerCase(c)) {
                char rotated = (char) (((c - 'a' + shift) % 26) + 'a');
                result.append(rotated);
            } else {
                // Non-alphabetical characters remain unchanged
                result.append(c);
            }
        }

        return result.toString();
    }
}