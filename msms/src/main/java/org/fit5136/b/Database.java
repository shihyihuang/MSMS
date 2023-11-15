package org.fit5136.b;

import java.io.*;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.ArrayList;

/**
 * Class which is for reading and writing data from text file
 *
 * @version 1.0.0
 */
public class Database {

    final static String fileLocation = "src/main/resources/";

    public Database() {
    }

    public static <T> ArrayList<T> loadData(String filename, BiConsumer<String, ArrayList<T>> processData) {
        ArrayList<T> data = new ArrayList<>();

        try {
            ArrayList<String> rawData = Database.read(filename);

            for (String dataStr : rawData) {
                if (!dataStr.isEmpty()) {
                    processData.accept(dataStr, data);
                }
            }
        } catch (Exception exception) {
            System.out.print("Exception: " + exception);
        }

        return data;
    }

    /**
     * Reads the content of a text file line by line and returns it as an ArrayList
     * of strings.
     *
     * @return An ArrayList of strings containing the lines from the text file.
     * @throws IOException           If an I/O error occurs while reading from the
     *                               file.
     * @throws FileNotFoundException If the specified file is not found.
     */
    public static ArrayList<String> read(String fileName)
            throws IOException, FileNotFoundException {
        ArrayList<String> lines = new ArrayList<String>();

        File file = new File(fileLocation + fileName);
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            lines.add(reader.nextLine());
        }
        reader.close();

        return lines;
    }

    /**
     * Writes a list of lines to a text file, replacing its existing content.
     *
     * @param lines The ArrayList of lines to be written to the text file.
     * @throws IOException           If an I/O error occurs while writing to the
     *                               file.
     * @throws FileNotFoundException If the specified file is not found or cannot be
     *                               created.
     */
    public static void write(ArrayList<String> lines, String fileName) throws IOException, FileNotFoundException {

        PrintWriter writer = new PrintWriter(fileLocation + fileName, "UTF-8");
        for (String line : lines) {
            writer.println(line);
        }
        writer.close();
    }

    /**
     * Appends a list of lines to a text file, creating the file if it doesn't
     * exist.
     *
     * @param lines The ArrayList of lines to be appended to the text file.
     */
    public static void writeAppend(ArrayList<String> lines, String fileName) throws IOException, FileNotFoundException {

        File file = new File(fileLocation + fileName);
        // Check if the file is empty
        boolean isEmpty = true;
        if (file.exists() && file.length() > 0) {
            isEmpty = false;
        }

        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileLocation + fileName, true), "UTF-8")));

        // If the file is not empty, add a newline before appending
        if (!isEmpty) {
            writer.println();
        }

        for (String line : lines) {
            writer.print(line);
        }
        writer.close();
    }

    /**
     * Removes a specific line from a text file.
     *
     * @param inputBooking The line of text to be removed from the text file.
     * @return True if the line was successfully removed; false otherwise.
     */
    public static boolean remove(String inputBooking, String fileName) throws IOException {
        File file = new File(fileLocation + fileName);
        File tempFile = new File("tempFile.txt");

        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String lineToRemove = inputBooking;
        String currentLine;
        boolean removed = false;

        while ((currentLine = reader.readLine()) != null) {
            // if the currentLine is same as the lineToRemove
            // then skip the line without writing it in to the tempFile
            String trimmedLine = currentLine.trim();
            if (trimmedLine.equals(lineToRemove)) {
                removed = true;
                continue;
            }
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        file.delete();
        // replace the original file with the tempFile
        boolean successful = tempFile.renameTo(file);

        // Return true if the line was removed and renaming was successful
        return removed && successful;
    }

    /**
     * Updates the booking status to true in a text file.
     *
     * @param checkingBooking The line of text to be updated in the text file.
     * @return True if the status was successfully updated; false otherwise.
     * @throws IOException If an I/O error occurs while reading from or writing to
     *                     the file.
     */
    public static boolean updateStatus(String checkingBooking, String fileName) throws IOException {
        File file = new File(fileLocation + fileName);
        File tempFile = new File("tempFile.txt");

        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String lineToUpdate = checkingBooking;
        String currentLine;
        boolean updated = false;
        while ((currentLine = reader.readLine()) != null) {

            String trimmedLine = currentLine.trim();
            // if the currentLine is same as the lineToUpdate
            // then replace the value false to true to update the check-in status
            if (trimmedLine.equals(lineToUpdate)) {
                currentLine = currentLine.replaceAll("\\bfalse\\b", "true");
                updated = true;
            }
            // write the updated line into the tempFile
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        file.delete();
        // replace the original file with the tempFile
        boolean successful = tempFile.renameTo(file);

        // Return true if successfully update and rename
        return updated && successful;
    }

}
