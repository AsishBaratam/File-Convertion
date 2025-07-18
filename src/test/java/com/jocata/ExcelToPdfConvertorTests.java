package com.jocata;

import com.jocata.dto.SourceDTO;
import com.jocata.service.impl.ExcelToPdfConvertor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ExcelToPdfConvertorTests {

    @Test
    void testConvertToPDF(@TempDir Path tempDir) throws IOException {
        // Arrange
        String imageFile1 = "D:/pdf/excel-sample-1.xlsx";
        String imageFile2 = "D:/pdf/excel-sample.xlsx";
        SourceDTO sourceDTO = new SourceDTO(Arrays.asList(imageFile1, imageFile2), tempDir.resolve("merged.pdf").toString());

        // Act
        String result = new ExcelToPdfConvertor().converttoPDF(sourceDTO);

        // Assert
        File mergedPdfFile = tempDir.resolve("merged.pdf").toFile();
        assertTrue(mergedPdfFile.exists());
        // Add more assertions to verify the contents of the merged PDF file
    }

    @Test
    void testConvertToPDFWithInvalidSource() throws IOException {
        // Arrange
        String imageFile1 = "D:/pdf/excel-sample-1.xlsx";
        String imageFile2 = "D:/pdf/excel-sample-23.xlsx";
        SourceDTO sourceDTO = new SourceDTO(Arrays.asList(imageFile1, imageFile2), "merged.pdf");

        // Act and Assert
        assertTrue(new ExcelToPdfConvertor().converttoPDF(sourceDTO).contains("File not found"));
    }

    @Test
    void testConvertToPDFWithInvalidDestination() throws IOException {
        // Arrange
        String imageFile1 = "D:/pdf/excel-sample-1.xlsx";
        String imageFile2 = "D:/pdf/excel-sample.xlsx";
        SourceDTO sourceDTO = new SourceDTO(Arrays.asList(imageFile1, imageFile2), "C:/pdf/merged.pdf");

        // Act and Assert
        assertTrue(new ExcelToPdfConvertor().converttoPDF(sourceDTO).contains("File not found"));
    }

    @Test
    void testConvertToPDFWithInvalidSourceAndDestination() throws IOException {
        // Arrange
        String imageFile1 = "D:/pdf/excel-sample-111.xlsx";
        String imageFile2 = "D:/pdf/excel-sample-123.xlsx";
        SourceDTO sourceDTO = new SourceDTO(Arrays.asList(imageFile1, imageFile2), "C:/pdf/merged.pdf");

        // Act and Assert
        assertTrue(new ExcelToPdfConvertor().converttoPDF(sourceDTO).contains("File not found"));
        assertTrue(new ExcelToPdfConvertor().converttoPDF(sourceDTO).contains("File not found"));
    }
}
