package steganography;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Steganography {

    /**
     * Embeds a hidden message into an image file using LSB steganography.
     * Includes the length of the message as metadata in the first 32 bits.
     *
     * @param imagePath    Path to the input image file.
     * @param outputImage  Path to save the image with the hidden message.
     * @param message      The message to hide.
     * @throws IOException If there is an error reading or writing the image.
     */
    public void hideMessage(String imagePath, String outputImage, String message) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));

        // Convert the message into binary
        byte[] messageBytes = message.getBytes();
        int messageLength = messageBytes.length;
        int totalBits = 32 + messageLength * 8; // 32 bits for the length + message bits

        // Ensure the image has enough capacity
        int totalPixels = image.getWidth() * image.getHeight();
        if (totalBits > totalPixels) {
            throw new IllegalArgumentException("The message is too long to fit in the given image.");
        }

        int bitIndex = 31; // Start with the most significant bit for the length
        int messageIndex = 0;
        int messageBitIndex = 7; // Start with the most significant bit of each byte

        outerLoop:
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);

                // Extract the blue channel (least noticeable)
                int blue = pixel & 0xFF;

                if (bitIndex >= 0) {
                    // Embed the message length in the first 32 bits
                    int bitToEmbed = (messageLength >> bitIndex) & 1;
                    blue = (blue & 0xFE) | bitToEmbed;
                    bitIndex--;
                } else if (messageIndex < messageLength) {
                    // Embed the message bits
                    int bitToEmbed = (messageBytes[messageIndex] >> messageBitIndex) & 1;
                    blue = (blue & 0xFE) | bitToEmbed;

                    messageBitIndex--;
                    if (messageBitIndex < 0) {
                        messageBitIndex = 7;
                        messageIndex++;
                    }
                } else {
                    // Exit the loop if the entire message is embedded
                    break outerLoop;
                }

                // Update the pixel
                pixel = (pixel & 0xFFFFFF00) | blue;
                image.setRGB(x, y, pixel);
            }
        }

        // Save the output image
        ImageIO.write(image, "png", new File(outputImage));
    }

    /**
     * Extracts a hidden message from an image file using LSB steganography.
     * Automatically determines the message length.
     *
     * @param imagePath Path to the image with the hidden message.
     * @return The extracted message.
     * @throws IOException If there is an error reading the image.
     */
    public String retrieveMessage(String imagePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));

        int messageLength = 0;
        int bitIndex = 31; // Start with the most significant bit for the length
        byte[] messageBytes = null;
        int messageIndex = 0;
        int messageBitIndex = 7;

        outerLoop:
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);

                // Extract the blue channel (least noticeable)
                int blue = pixel & 0xFF;

                if (bitIndex >= 0) {
                    // Retrieve the message length from the first 32 bits
                    messageLength = (messageLength << 1) | (blue & 1);
                    bitIndex--;
                } else {
                    // Initialize the byte array once the length is determined
                    if (messageBytes == null) {
                        messageBytes = new byte[messageLength];
                    }

                    // Retrieve the message bits
                    messageBytes[messageIndex] = (byte) ((messageBytes[messageIndex] << 1) | (blue & 1));

                    messageBitIndex--;
                    if (messageBitIndex < 0) {
                        messageBitIndex = 7;
                        messageIndex++;
                        if (messageIndex == messageLength) {
                            break outerLoop;
                        }
                    }
                }
            }
        }

        // Convert the message bytes back to a string
        return new String(messageBytes);
    }
}
