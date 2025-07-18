package com.jocata.service.impl;




import java.io.FileNotFoundException;

import com.jocata.dto.SourceDTO;
import com.jocata.service.PDFConvertor;
import com.jocata.validators.Validator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.util.Matrix;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelToPdfConvertor implements PDFConvertor {
    @Override
    public String converttoPDF(SourceDTO sourceDTO) throws IOException {
        System.out.println("Converting to PDF");
        if (Validator.validateSources(sourceDTO.getSources()) && Validator.validateTarget(sourceDTO.getTarget())) {
            // Do something with the sourceDTO object
            try {
                List<String> pdfSources = new ArrayList<>();
                for (String fileName : sourceDTO.getSources()) {
                    String sourceFileName = fileName.replaceAll("\\.[^.]+$", "") + ".pdf";
                    pdfSources.add(sourceFileName);

                    // Load the Excel file
                    File excelFile = new File(fileName);
                    Workbook workbook = WorkbookFactory.create(excelFile);


                    // Create a new PDF document
                    PDDocument document = new PDDocument();

                    // Iterate over the sheets in the Excel workbook
                    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                        Sheet sheet = workbook.getSheetAt(i);

                        // Create a new page in the PDF document
                        PDPage page = new PDPage();
                        document.addPage(page);

                        // Create a content stream to write the data to the PDF page
                        PDPageContentStream contentStream = new PDPageContentStream(document, page);

                        // Set the font and font size
                        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);

                        // Begin the text object
                        contentStream.beginText();

                        // Reset the text position to the top of the page with some margin
                        float x = 50;
                        float y = page.getMediaBox().getHeight() - 40;
                        contentStream.setTextMatrix(Matrix.getTranslateInstance(x, y));

                        // Calculate the column widths based on the Excel sheet
                        float[] columnWidths = new float[sheet.getRow(0).getLastCellNum()];
                        for (int col = 0; col < columnWidths.length; col++) {
                            columnWidths[col] = sheet.getColumnWidthInPixels(col) / 72 * 12; // Convert pixels to points
                        }

                        // Iterate over the rows and cells in the Excel sheet
                        for (Row row : sheet) {
                            float cellX = x;
                            for (Cell cell : row) {
                                // Determine the cell type and format the value accordingly
                                String cellValue;
                                switch (cell.getCellType()) {
                                    case 0:
                                        if (DateUtil.isCellDateFormatted(cell)) {
                                            cellValue = cell.getDateCellValue().toString();
                                        } else {
                                            cellValue = String.format("%d", (int) cell.getNumericCellValue());
                                        }
                                        break;
                                    case 1:
                                        cellValue = cell.getStringCellValue();
                                        break;
                                    case 4:
                                        cellValue = String.valueOf(cell.getBooleanCellValue());
                                        break;
                                    case 2:
                                        cellValue = String.valueOf(cell.getCachedFormulaResultType());
                                        break;
                                    default:
                                        cellValue = "";
                                }

                                // Write the cell value to the PDF page
                                // Calculate the text matrix to align the text within the column width
                                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);
                                float textWidth = 30;
                                // float adjustedX = cellX + (columnWidth - textWidth) / 2; // Adjust the x-position for centering
                                float adjustedX = cellX + (columnWidths[cell.getColumnIndex()] - textWidth) / 2; // Adjust the x-position for centering

                                // Write the cell value to the PDF page
                                contentStream.setTextMatrix(Matrix.getTranslateInstance(adjustedX, y));
                                contentStream.showText(cellValue);
                                int columnWidth = 50;
                                cellX += columnWidth + 10; // Move to the next cell position with spacing

                            }
                            x = 50; // Reset the x position for the next row
                            y -= 20; // Move to the next line
                            contentStream.setTextMatrix(Matrix.getTranslateInstance(x, y));
                        }

                        // End the text object
                        contentStream.endText();

                        // Close the content stream
                        contentStream.close();
                    }

                    // Save the PDF document
                    document.save(new File(sourceFileName));

                    // Close the PDF document
                    document.close();
                }
                mergePDF(pdfSources, sourceDTO.getTarget());
                return "PDF Created";
            }catch (InvalidFormatException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                return "File not found";
            }

        } else {
            if (!Validator.validateSources(sourceDTO.getSources())) {
                return "Invalid source";
            } else if (!Validator.validateTarget(sourceDTO.getTarget())) {
                return "Invalid destination";
            }
            return "Invalid input";
        }

    }
}

