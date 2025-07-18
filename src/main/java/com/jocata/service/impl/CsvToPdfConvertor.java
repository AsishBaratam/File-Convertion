package com.jocata.service.impl;

import com.jocata.dto.SourceDTO;
import com.jocata.service.PDFConvertor;
import com.jocata.validators.Validator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvToPdfConvertor implements PDFConvertor {

    @Override
    public String converttoPDF(SourceDTO sourceDTO) throws IOException {
        System.out.println("Converting CSV to PDF");
        if (Validator.validateSources(sourceDTO.getSources()) && Validator.validateTarget(sourceDTO.getTarget())) {
            List<String> pdfSources = new ArrayList<>();
            for (String fileName : sourceDTO.getSources()) {
                try {
                    String sourceFileName = fileName.replaceAll("\\.[^.]+$", "") + ".pdf";
                    pdfSources.add(sourceFileName);
                    System.out.println(fileName);
                    // Read the CSV file (replace with your own path
                    List<String> csvLines = Files.readAllLines(Paths.get(fileName));

                    // Create a new PDF document
                    PDDocument document = new PDDocument();

                    // Set the A4 page size (in points, 1 inch = 72 points)
                    float pageWidth = 595.28f; // A4 width in points
                    float pageHeight = 841.89f; // A4 height in points

                    // Define the table dimensions
                    float tableWidth = pageWidth - 50; // Adjust as needed
                    float tableHeight = pageHeight - 50; // Adjust as needed
                    float y = tableHeight;
                    float margin = 10;
                    float rowHeight = 15; // Adjust this value based on your row height

                    // Draw the table header
                    String[] header = csvLines.get(0).split(",");
                    float columnWidth = tableWidth / header.length;

                    PDPageContentStream contentStream = null;
                    PDPage page = new PDPage(new PDRectangle(pageWidth, pageHeight));
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);

                    // Draw the table borders
                    contentStream.setLineWidth(1); // Set the line width for borders
                    contentStream.addRect(margin, tableHeight - 20, tableWidth, -tableHeight + 40); // Draw the outer border
                    contentStream.stroke();

                    y -= 20; // Adjust for the header height

                    // Draw the table header
                    drawTableHeader(contentStream, header, columnWidth, tableHeight, y, margin);
                    y -= rowHeight; // Adjust the line spacing as needed

                    // Iterate over the CSV lines
                    for (int i = 1; i < csvLines.size(); i++) {
                        // Check if a new page is needed
                        if (y < margin + rowHeight) {
                            contentStream.close();
                            page = new PDPage(new PDRectangle(pageWidth, pageHeight));
                            document.addPage(page);
                            contentStream = new PDPageContentStream(document, page);
                            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);

                            // Draw the table borders
                            contentStream.setLineWidth(1); // Set the line width for borders
                            contentStream.addRect(margin, tableHeight - 20, tableWidth, -tableHeight + 40); // Draw the outer border
                            contentStream.stroke();

                            y = tableHeight - 20;

                            // Draw the table header on the new page
                            drawTableHeader(contentStream, header, columnWidth, tableHeight, y, margin);
                            y -= rowHeight;
                        }

                        // Draw the table rows
                        String[] row = csvLines.get(i).split(",");
                        float x = margin;
                        for (int j = 0; j < row.length; j++) {
                            contentStream.beginText();
                            contentStream.newLineAtOffset(x + columnWidth / 2, y);
                            contentStream.showText(row[j]);
                            contentStream.endText();

                            // Draw a vertical line after each cell
                            contentStream.moveTo(x + columnWidth, y);
                            contentStream.lineTo(x + columnWidth, y - rowHeight);
                            contentStream.stroke();

                            x += columnWidth;
                        }

                        // Draw a horizontal line after each row
                        contentStream.moveTo(margin, y - rowHeight);
                        contentStream.stroke();

                        y -= rowHeight; // Adjust the y-coordinate for the next row
                    }

                    contentStream.close();

                    // Save the PDF document
                    document.save(new File(sourceFileName));

                    // Close the document
                    document.close();
                } catch (NoSuchFileException e) {
                    return "File not found";
                }
            }
            mergePDF(pdfSources, sourceDTO.getTarget());
            return "";
        }else{
            if (!Validator.validateSources(sourceDTO.getSources())) {
                return "Invalid source";
            } else if (!Validator.validateTarget(sourceDTO.getTarget())) {
                return "Invalid destination";
            }
            return "Invalid source or target";
        }
//        return "";
    }
    private void drawTableHeader(PDPageContentStream contentStream, String[] header, float columnWidth, float tableHeight, float y, float margin) throws IOException {
        float x = margin;
        for (int j = 0; j < header.length; j++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(x + columnWidth / 2, y);
            contentStream.showText(header[j]);
            contentStream.endText();

            // Draw a vertical line after each header cell
            contentStream.moveTo(x + columnWidth, tableHeight);
            contentStream.lineTo(x + columnWidth, tableHeight - tableHeight + 20);
            contentStream.stroke();

            x += columnWidth;
        }


    }

}
