package com.jocata;

import com.jocata.dto.SourceDTO;

import com.jocata.service.impl.WordToPDFConvertor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;

import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordToPdfConvertorTests {
    @Test
    void testConvertToPDF(@TempDir Path tempDir) throws IOException {
        // Arrange
        String imageFile1 = "D:/pdf/docc.docx";
        String imageFile2 = "D:/pdf/my.docx";
        SourceDTO sourceDTO = new SourceDTO(Arrays.asList(imageFile1, imageFile2), tempDir.resolve("word-merged.pdf").toString());

        // Act
        new WordToPDFConvertor().converttoPDF(sourceDTO);

        // Assert
        File mergedPdfFile = tempDir.resolve("word-merged.pdf").toFile();
        assertTrue(mergedPdfFile.exists());
        // Add more assertions to verify the contents of the merged PDF file
    }

    @Test
    void testConvertToPDFWithInvalidSource() throws IOException {
        // Arrange
        String imageFile1 = "D:/pdf/my.docx";
        String imageFile2 = "D:/pdf/word-sample-22.docx";
        SourceDTO sourceDTO = new SourceDTO(Arrays.asList(imageFile1, imageFile2), "word-merged.pdf");

        // Act and Assert
        assertTrue(new WordToPDFConvertor().converttoPDF(sourceDTO).contains("File not found"));
    }

    @Test
    void testConvertToPDFWithInvalidDestination() throws IOException {
        // Arrange
        String imageFile1 = "D:/pdf/word-sampl.docx";
        String imageFile2 = "D:/pdf/word-sampl.docx";
        SourceDTO sourceDTO = new SourceDTO(Arrays.asList(imageFile1, imageFile2), "C:/pdf/word-merged.pdf");

        // Act and Assert
        assertTrue(new WordToPDFConvertor().converttoPDF(sourceDTO).contains("File not found"));
    }

    @Test
    void testConvertToPDFWithInvalidSourceAndDestination() throws IOException {
        // Arrange
        String imageFile1 = "D:/pdf/word-sample-111.docx";
        String imageFile2 = "D:/pdf/word-sample-222.docx";
        SourceDTO sourceDTO = new SourceDTO(Arrays.asList(imageFile1, imageFile2), "C:/pdf/word-merged.pdf");

        // Act and Assert
        assertTrue(new WordToPDFConvertor().converttoPDF(sourceDTO).contains("File not found"));
        assertTrue(new WordToPDFConvertor().converttoPDF(sourceDTO).contains("File not found"));
    }
}
