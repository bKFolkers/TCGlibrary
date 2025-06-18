package nl.miwnn.ch16.bas.tcglibrary.model;

import java.io.*;
import java.nio.file.*;

/**
 * @author Bas Folkers
 * Schrijf hier wat je programma doet
 */

public class CsvUploader {

    public static void main(String[] args) {
        Path sourcePath = Paths.get("src/main/resources/tcg_data/JungleProductsAndPrices.csv");
        Path targetPath = Paths.get("src/main/resources/tcg_data/jungleCards.csv");

        try (
                BufferedReader reader = Files.newBufferedReader(sourcePath);
                BufferedWriter writer = Files.newBufferedWriter(targetPath)
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("CSV data copied successfully.");
        } catch (IOException e) {
            System.err.println("Error copying CSV data: " + e.getMessage());
        }
    }
}
