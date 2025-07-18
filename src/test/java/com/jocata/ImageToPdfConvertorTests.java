package com.jocata;

import com.jocata.dto.SourceDTO;
import com.jocata.service.impl.CsvToPdfConvertor;
import com.jocata.service.impl.ImageToPdfConvertor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImageToPdfConvertorTests {
    @Test
    void testConvertToPDF(@TempDir Path tempDir) throws IOException {
        // Arrange
        String imageFile1 = "D:/pdf/a.jpeg";
        String imageFile2 = "D:/pdf/b.png";
        SourceDTO sourceDTO = new SourceDTO(Arrays.asList(imageFile1, imageFile2), tempDir.resolve("img-merged.pdf").toString());

        // Act
        String result = new ImageToPdfConvertor().converttoPDF(sourceDTO);

        // Assert
        File mergedPdfFile = tempDir.resolve("img-merged.pdf").toFile();
        assertTrue(mergedPdfFile.exists());
        // Add more assertions to verify the contents of the merged PDF file
    }

    @Test
    void testConvertToPDFWithInvalidSource() throws IOException {
        // Arrange
        String imageFile1 = "D:/pdf/img-sample-11.jpeg";
        String imageFile2 = "D:/pdf/img-sample-22.jpeg";
        SourceDTO sourceDTO = new SourceDTO(Arrays.asList(imageFile1, imageFile2), "img-merged.pdf");

        // Act and Assert
        assertTrue(new ImageToPdfConvertor().converttoPDF(sourceDTO).contains("File not found"));
    }

    @Test
    void testConvertToPDFWithInvalidDestination() throws IOException {
        // Arrange
        String imageFile1 = "D:/pdf/img-sampl.jpeg";
        String imageFile2 = "D:/pdf/img-sampl.jpeg";
        SourceDTO sourceDTO = new SourceDTO(Arrays.asList(imageFile1, imageFile2), "C:/pdf/img-merged.pdf");

        // Act and Assert
        assertTrue(new ImageToPdfConvertor().converttoPDF(sourceDTO).contains("File not found"));
    }

    @Test
    void testConvertToPDFWithInvalidSourceAndDestination() throws IOException {
        // Arrange
        String imageFile1 = "D:/pdf/img-sample-111.jpeg";
        String imageFile2 = "D:/pdf/img-sample-222.jpeg";
        SourceDTO sourceDTO = new SourceDTO(Arrays.asList(imageFile1, imageFile2), "C:/pdf/img-merged.pdf");

        // Act and Assert
        assertTrue(new ImageToPdfConvertor().converttoPDF(sourceDTO).contains("File not found"));
        assertTrue(new ImageToPdfConvertor().converttoPDF(sourceDTO).contains("File not found"));
    }
}
