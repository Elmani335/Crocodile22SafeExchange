package encryption;

public class Enigma {
    private Rotor rotor1;
    private Rotor rotor2;
    private Rotor rotor3;
    private Reflector reflector;
    
    // Initial states to save
    private int initialPos1;
    private int initialPos2;
    private int initialPos3;

    public Enigma() {
        // Default rotor configurations (Enigma I type)
        rotor1 = new Rotor("EKMFLGDQVZNTOWYHXUSPAIBRCJ", 'Q');
        rotor2 = new Rotor("AJDKSIRUXBLHWTMCQGZNPYFVOE", 'E');
        rotor3 = new Rotor("BDFHJLCPRTXVZNYEIWGAKMUSQO", 'V');
        reflector = new Reflector("YRUHQSLDPXNGOKMIEBFZCWVJAT");
    }

    // Method to configure the initial positions of the rotors
    public void setRotorPositions(char pos1, char pos2, char pos3) {
        rotor1.setPosition(pos1);
        rotor2.setPosition(pos2);
        rotor3.setPosition(pos3);
        
        // Save initial positions
        initialPos1 = pos1 - 'A';
        initialPos2 = pos2 - 'A';
        initialPos3 = pos3 - 'A';
    }

    // Method to restore the initial positions of the rotors
    public void resetRotorPositions() {
        rotor1.setPosition((char)(initialPos1 + 'A'));
        rotor2.setPosition((char)(initialPos2 + 'A'));
        rotor3.setPosition((char)(initialPos3 + 'A'));
    }

    // Main method for encryption/decryption
    public String process(String message, boolean encrypt) {
        // Restore initial positions before processing
        resetRotorPositions();
        
        StringBuilder result = new StringBuilder();
        
        for (char c : message.toUpperCase().toCharArray()) {
            if (Character.isLetter(c)) {
                // Rotate rotors before processing each letter
                rotateRotors();
                
                // Pass through rotors from right to left
                int signal = c - 'A';
                signal = rotor3.forward(signal);
                signal = rotor2.forward(signal);
                signal = rotor1.forward(signal);
                
                // Pass through the reflector
                signal = reflector.reflect(signal);
                
                // Pass through rotors from left to right
                signal = rotor1.backward(signal);
                signal = rotor2.backward(signal);
                signal = rotor3.backward(signal);
                
                result.append((char)(signal + 'A'));
            } else {
                result.append(c);
            }
        }
        
        return result.toString();
    }

    // Method for rotor rotation unchanged
    private void rotateRotors() {
        boolean rotateMiddle = rotor2.atNotchPosition();
        
        if (rotateMiddle) {
            rotor1.rotate();
            rotor2.rotate();
        }
        
        rotor3.rotate();
    }

    private class Rotor {
        private String wiring;
        private char notchPosition;
        private int currentPosition;

        public Rotor(String wiring, char notchPosition) {
            this.wiring = wiring;
            this.notchPosition = notchPosition;
            this.currentPosition = 0;
        }

        public void setPosition(char position) {
            this.currentPosition = position - 'A';
        }

        public void rotate() {
            currentPosition = (currentPosition + 1) % 26;
        }

        public boolean atNotchPosition() {
            return (char)((currentPosition) + 'A') == notchPosition;
        }

        public int forward(int signal) {
            signal = (signal + currentPosition) % 26;
            char output = wiring.charAt(signal);
            signal = output - 'A';
            signal = (signal - currentPosition + 26) % 26;
            return signal;
        }

        public int backward(int signal) {
            signal = (signal + currentPosition) % 26;
            for (int i = 0; i < 26; i++) {
                if (wiring.charAt(i) - 'A' == signal) {
                    signal = i;
                    break;
                }
            }
            signal = (signal - currentPosition + 26) % 26;
            return signal;
        }
    }

    private class Reflector {
        private String wiring;

        public Reflector(String wiring) {
            this.wiring = wiring;
        }

        public int reflect(int signal) {
            return wiring.charAt(signal) - 'A';
        }
    }
}
