package utils;

import java.io.*;

public class FileManager {

    // Write content to a file
    public static void writeToFile(String content, String filePath) {
        // Use try-with-resources to close the resources automatically
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { // Set the second argument to true to append to the file
            if (new File(filePath).length() > 0) { // Check if the file is not empty
                writer.newLine(); // Add a new line if the file already contains data
            }
            // Write the content to the file
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Read content from a file
    public static String readFromFile(String filePath) {
        // Use a StringBuilder to store the content read from the file
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) { // Read each line from the file
                content.append(line).append(System.lineSeparator()); // Append the line to the content
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return content.toString().trim(); // Return the content as a string
    }

    // Delete a file
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) { // Check if the file exists
            if (file.delete()) { // Delete the file
                System.out.println("File deleted successfully.");
            } else {
                System.err.println("Failed to delete the file.");
            }
        } else {
            System.err.println("File does not exist.");
        }
    }

}
