package crypto.steganography;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LSB {

    // Encrypt a message into an image
    public static void encryptMessage(BufferedImage image, String message, String outputFilePath) throws IOException {
        // Convert the message to binary
        String binaryMessage = toBinaryString(message + '\0'); // Add a null terminator at the end to prevent unreadable characters

        // The message index is used to keep track of the current character in the message
        int messageIndex = 0;

        // outerLoop is used to break out of the nested loop when the message is fully inserted
        outerLoop:
        // Loop through each pixel in the image
        for (int y = 0; y < image.getHeight(); y++) {  // Loop through each row
            for (int x = 0; x < image.getWidth(); x++) { // Loop through each column
                int pixel = image.getRGB(x, y); // Get the pixel at the current position
                if (messageIndex < binaryMessage.length()) { // Check if there are more characters to insert
                    // Get the current bit to insert
                    int lsb = binaryMessage.charAt(messageIndex) - '0';
                    // Get the blue component of the pixel
                    int blue = pixel & 0xFF;
                    // Replace the least significant bit of the blue component with the message bit
                    blue = (blue & 0xFE) | lsb;
                    // Create a new pixel with the modified blue component
                    int newPixel = (pixel & 0xFFFFFF00) | blue;
                    // Set the new pixel value
                    image.setRGB(x, y, newPixel);
                    // Move to the next bit in the message
                    messageIndex++;
                } else {
                    // If the message is fully inserted, break out of the loop
                    break outerLoop;
                }
            }
        }
        // Write the image to the output file
        ImageIO.write(image, "png", new File(outputFilePath));
    }

    // Decrypt a message from an image
    public static String decryptMessage(BufferedImage image) {
        // Create a StringBuilder to store the binary message
        StringBuilder binaryMessage = new StringBuilder();
        // Loop through each pixel in the image
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                // Get the blue component of the pixel
                int pixel = image.getRGB(x, y);
                // Extract the least significant bit of the blue component
                int blue = pixel & 0xFF;
                int lsb = blue & 1;
                // Append the extracted bit to the binary message
                binaryMessage.append(lsb);
            }
        }

        // Convert the binary message to text
        return toText(binaryMessage.toString());
    }

    // Convert a text to a binary string
    private static String toBinaryString(String text) {
        // Create a StringBuilder to store the binary string
        StringBuilder binary = new StringBuilder();
        // Loop through each character in the text
        for (char c : text.toCharArray()) {
            // Convert the character to its ASCII value and then to a binary string
            String bin = String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0');
            binary.append(bin);
        }
        return binary.toString();
    }

    // Convert a binary string to text
    private static String toText(String binary) {
        // Create a StringBuilder to store the text
        StringBuilder text = new StringBuilder();
        // Loop through the binary string in chunks of 8 bits
        for (int i = 0; i < binary.length(); i += 8) {
            // Break if the remaining bits are less than 8
            if (i + 8 > binary.length()) break;
            // Get the next 8 bits
            String byteString = binary.substring(i, i + 8);
            // Convert the 8 bits to a character code
            int charCode = Integer.parseInt(byteString, 2);
            if (charCode == 0) break; // Stop if the null terminator is reached
            text.append((char) charCode);
        }
        return text.toString();
    }
}
