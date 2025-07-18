package com.jocata;

import com.jocata.dto.SourceDTO;
import com.jocata.service.impl.CsvToPdfConvertor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CsvToPdfConvertorTests {

    @Test
    void testConvertToPDF(@TempDir Path tempDir) throws IOException {
        // Arrange
        String imageFile1 = "D:/pdf/traintic.csv";
        String imageFile2 = "D:/pdf/diabetes.csv";
        SourceDTO sourceDTO = new SourceDTO(Arrays.asList(imageFile1, imageFile2), tempDir.resolve("csv-merged.pdf").toString());

        // Act
        String result = new CsvToPdfConvertor().converttoPDF(sourceDTO);

        // Assert
        File mergedPdfFile = tempDir.resolve("csv-merged.pdf").toFile();
        assertTrue(mergedPdfFile.exists());
        // Add more assertions to verify the contents of the merged PDF file
    }

    @Test
    void testConvertToPDFWithInvalidSource() throws IOException {
        // Arrange
        String imageFile1 = "D:/pdf/50_Startups.csv";
        String imageFile2 = "D:/pdf/csv-sample-22.csv";
        SourceDTO sourceDTO = new SourceDTO(Arrays.asList(imageFile1, imageFile2), "csv-merged.pdf");
        System.out.println("tested sucessful");
        // Act and Assert
        assertTrue(new CsvToPdfConvertor().converttoPDF(sourceDTO).contains("File not found"));
    }

    @Test
    void testConvertToPDFWithInvalidDestination() throws IOException {
        // Arrange
        String imageFile1 = "D:/pdf/csv-sampl.csv";
        String imageFile2 = "D:/pdf/csv-sampl.csv";
        SourceDTO sourceDTO = new SourceDTO(Arrays.asList(imageFile1, imageFile2), "C:/pdf/csv-merged.pdf");

        // Act and Assert
        assertTrue(new CsvToPdfConvertor().converttoPDF(sourceDTO).contains("File not found"));
    }

    @Test
    void testConvertToPDFWithInvalidSourceAndDestination() throws IOException {
        // Arrange
        String imageFile1 = "D:/pdf/csv-sample-111.csv";
        String imageFile2 = "D:/pdf/csv-sample-222.csv";
        SourceDTO sourceDTO = new SourceDTO(Arrays.asList(imageFile1, imageFile2), "C:/pdf/csv-merged.pdf");

        // Act and Assert
        assertTrue(new CsvToPdfConvertor().converttoPDF(sourceDTO).contains("File not found"));
        assertTrue(new CsvToPdfConvertor().converttoPDF(sourceDTO).contains("File not found"));
    }
}
