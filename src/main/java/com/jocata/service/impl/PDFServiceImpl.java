package com.jocata.service.impl;

import com.jocata.dao.PDFRepository;
import com.jocata.dto.PDFDTO;
import com.jocata.entity.PDF;
import com.jocata.service.PDFService;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


@Service
//business logic(PracticeServiceImpl)class
public class PDFServiceImpl implements PDFService {
    @Autowired
    //instance(PracticeRepository) used to interact with database
    PDFRepository PDFRepository;

    public PDFServiceImpl() throws IOException {
    }


    @Override
    public String sayHello(String name, Long id) {
        return "Hello " + name + id;


//        Practice user = new Practice();
//        user.setName("John Doe");
//        practiceRepository.save(user);

//        PracticeService ps=new PracticeService();
//        ps.setName("Jocata");
//        practiceRepository.save(ps);
//        return user;

    }

    @Override
    public Object saveUserData(PDFDTO data) {
        PDF user = new PDF();
        user.setName(data.getName());
        PDFRepository.save(user);

        return user;

    }

    @Override
    public Object getUserData(PDFDTO data) {
        return PDFRepository.findById(data.getId());
    }

    @Override
    public void deleteData(PDFDTO data) {

        PDFRepository.deleteById(data.getId());
    }

    @Override
    public void updateData(PDFDTO data) {
        PDF user = new PDF();

        user.setId(data.getId());
        user.setName(data.getName());
        PDFRepository.save(user);

    }
    //pdf file creation
    @Override
    public void savePdf(PDFDTO data) throws IOException {
        PDDocument document = new PDDocument();
        //Saving the document
        for (int i = 0; i < 3; i++) {
            //Creating a blank page
            PDPage blankPage = new PDPage();

            //Adding the blank page to the document
            document.addPage(blankPage);
        }

        document.save("D:/pdf/my_doc1.pdf");
        System.out.println("PDF created");

        //Closing the document
        document.close();


    }

    @Override
    public void loadPdfData(PDFDTO data) throws IOException {
        File file = new File("D:/pdf/my_doc.pdf");
        PDDocument document = Loader.loadPDF(file);
        document.addPage(new PDPage());
        //used to add extra page to the document
        //Saving the document
        document.save("D:/pdf/my_doc.pdf");

        //Closing the document
        document.close();


    }

    @Override
    public void removePdfData(PDFDTO data) throws IOException {
        File file = new File("D:/pdf/my_doc1.pdf");
        PDDocument document = Loader.loadPDF(file);
        document.removePage(2);
        System.out.println(document.getNumberOfPages());
        System.out.println("page removed");

        //Saving the document
        document.save("D:/pdf/my_doc1.pdf");

        //Closing the document
        document.close();


    }

    @Override
    public void addingDocAtt(PDFDTO data) throws IOException {
        PDDocument document = new PDDocument();
        PDDocumentInformation pdd = document.getDocumentInformation();

        //Setting the author of the document
        pdd.setAuthor("Tutorialspoint");

        // Setting the title of the document
        pdd.setTitle("Sample document");

        //Setting the creator of the document
        pdd.setCreator("PDF Examples");

        //Setting the subject of the document
        pdd.setSubject("Example document");

        //Setting the created date of the document
        Calendar date = new GregorianCalendar();
        date.set(2015, 11, 5);
        pdd.setCreationDate(date);
        //Setting the modified date of the document
        date.set(2016, 6, 5);
        pdd.setModificationDate(date);

        //Setting keywords for the document
        pdd.setKeywords("sample, first example, my pdf");

        //Saving the document
        document.save("D:/pdf/my_doc.pdf");

        System.out.println("Properties added successfully ");

        //Closing the document
        document.close();


    }

    @Override
    public void addContent(PDFDTO data) throws IOException {
        File file = new File("D:/pdf/my_doc1.pdf");
        PDDocument document = Loader.loadPDF(file);


        for (int pageNo = 0; pageNo < 3; ++pageNo) {
            //Retrieving the pages of the document
            PDPage page = document.getPage(pageNo);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            //Begin the Content stream
            contentStream.beginText();

            //Setting the font to the Content stream
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);

            //Setting the position for the line
            contentStream.newLineAtOffset(25, 500);

// Set the starting position for the text
            float startX = 50;
            float startY = 50;
            float fontSize = 12;

            float leading = 1.5f * fontSize;

            for (int i = pageNo * 10; i < (pageNo + 1) * 10; i++) {
                contentStream.newLineAtOffset(0, -leading);
                contentStream.showText("Number:" + String.valueOf(i));

            }

            contentStream.endText();
            contentStream.close();
        }

        System.out.println("Content added");

        //Saving the document
        document.save(new File("D:/pdf/my_doc1.pdf"));

        //Closing the document
        document.close();
    }

    @Override
    public void mergePdf(PDFDTO data) throws FileNotFoundException, IOException {
        File file1 = new File("D:/pdf/my_doc.pdf");
        File file2 = new File("D:/pdf/my_doc1.pdf");
        PDFMergerUtility PDFmerger = new PDFMergerUtility();

        //Setting the destination file
        PDFmerger.setDestinationFileName("D:/pdf/merged.pdf");

        //adding the source files
        PDFmerger.addSource(file1);
        PDFmerger.addSource(file2);

        //Merging the two documents
        PDFmerger.mergeDocuments(null);
        System.out.println("Documents merged");
    }

    @Override
    public void convertWordToPdf(PDFDTO data) throws IOException, InvalidFormatException {
//        String wordFilePath = "D:/pdf/rohit.docx";
//        String pdfFilePath = "D:/pdf/asish  .pdf";
        String wordFilePath = "D:/pdf/asish_resume(pdf).docx";
       String pdfFilePath = "D:/pdf/asish_resume(pdf).pdf";
        //taking input from docx file
        InputStream doc = new FileInputStream(new File(wordFilePath));
        //process for creating pdf started
        XWPFDocument document = new XWPFDocument(doc);
        PdfOptions options = PdfOptions.create();
        OutputStream out = new FileOutputStream(new File(pdfFilePath));
        PdfConverter.getInstance().convert(document, out, options);


//        try (FileInputStream fis = new FileInputStream(wordFilePath);
//             FileOutputStream fos = new FileOutputStream(pdfFilePath)) {
//
//            POIXMLDocument document = new XWPFDocument(OPCPackage.open(fis));
//
//            // Convert Word to PDF
//            PdfOptions options = PdfOptions.create();
//            PdfConverter.getInstance().convert((XWPFDocument) document, fos, options);
//
//            System.out.println("Word document converted to PDF successfully.");
//        }

//        // Load Word document
//        FileInputStream fis = new FileInputStream(wordFilePath);
////        BasicImageReader OPCPackage = null;
//        XWPFDocument document = new XWPFDocument(OPCPackage.open(fis));
//
//        // Convert Word to PDF
//        FileOutputStream fos = new FileOutputStream(pdfFilePath);
//        PdfOptions options = PdfOptions.create();
//        PdfConverter.getInstance().convert(document, fos, options);
//
//        // Close streams
//        fis.close();
//        fos.close();
    }

    @Override
        public void addImage(PDFDTO data) throws IOException {
        File file = new File("D:/pdf/my_docsamp_csv.pdf");
        PDDocument document = Loader.loadPDF(file);

        // Add image to the first page
        PDPage page = document.getPage(0);
        PDImageXObject imageXObject = PDImageXObject.createFromFile("D:/pdf/water.jpeg", document);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.drawImage(imageXObject, 10, 10);
        contentStream.close();

        // Save the document
        document.save(new File("D:/pdf/docc.pdf"));
        document.close();
    }

    @Override
    public void encryptingData(PDFDTO data) throws IOException {
        File file = new File("D:/pdf/traintic.pdf");
        PDDocument document = Loader.loadPDF(file);

        //Creating access permission object
        AccessPermission ap = new AccessPermission();

        //Creating StandardProtectionPolicy object
        StandardProtectionPolicy spp = new StandardProtectionPolicy("1234", "1234", ap);

        //Setting the length of the encryption key
        spp.setEncryptionKeyLength(128);

        //Setting the access permissions
        spp.setPermissions(ap);

        //Protecting the document
        document.protect(spp);

        System.out.println("Document encrypted");

        //Saving the document
        document.save("D:/pdf/train.pdf");
        //Closing the document
        document.close();

    }

    @Override
    public void convertCsvToPdf(PDFDTO data) throws IOException {
        // Read the CSV file
//        List<String> csvLines = Files.readAllLines(Paths.get("D:/pdf/50_Startups.csv"));
//
//        // Create a new PDF document
//        PDDocument document = new PDDocument();
//        PDPage page = new PDPage();
//        document.addPage(page);
//
//        // Create a content stream to write the CSV data to the PDF
//        PDPageContentStream contentStream = new PDPageContentStream(document, page);
//
//        // Set the font and font size
//        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);
//
//        // Define the table dimensions
//        float tableWidth = page.getMediaBox().getWidth() ; // Adjust as needed
//        float tableHeight = page.getMediaBox().getHeight() - 10; // Adjust as needed
//        float y = tableHeight;
//        float margin = 20;
//
//        // Draw the table header
//        String[] header = csvLines.get(0).split(",");
//        float columnWidth = tableWidth / header.length;
//        for (int i = 0; i < header.length; i++) {
//            contentStream.beginText();
//            contentStream.newLineAtOffset(margin + i * columnWidth, y);
//            contentStream.showText(header[i]);
//            contentStream.endText();
//        }
//        y -= 20; // Adjust the line spacing as needed
//
//        // Draw the table rows
//        for (int i = 1; i < csvLines.size(); i++) {
//            String[] row = csvLines.get(i).split(",");
//            for (int j = 0; j < row.length; j++) {
//                contentStream.beginText();
//                contentStream.newLineAtOffset(margin + j * columnWidth, y);
//                contentStream.showText(row[j]);
//                contentStream.endText();
//            }
//            y -= 15; // Adjust the line spacing as needed
//        }
//
//        // Close the content stream
//        contentStream.close();
//
//        // Save the PDF document
//        document.save(new File("D:/pdf/my_doc1_csv.pdf"));
//
//        // Close the document
//        document.close();
//    }

//        contentStream.close();
//
//        // Save the PDF document
//        document.save(new File("D:/pdf/my_doc1_csv.pdf"));
//
//        // Close the document
//        document.close();(data within one page)
        List<String> csvLines = Files.readAllLines(Paths.get("D:/pdf/diabetes.csv"));

        // Create a new PDF document
        PDDocument document = new PDDocument();

        // Set the desired page size (in points, 1 inch = 72 points)
        float pageWidth = 1792; // 11 inches
        float pageHeight = 12000; // 17 inches

        // Create a new page with the custom size
        PDPage page = new PDPage(new PDRectangle(pageWidth, pageHeight));
        document.addPage(page);

        // Create a content stream to write the CSV data to the PDF
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Set the font and font size
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);

        // Define the table dimensions
        float tableWidth = page.getMediaBox().getWidth() - 50; // Adjust as needed
        float tableHeight = page.getMediaBox().getHeight() - 100; // Adjust as needed
        float y = tableHeight;
        float margin = 10;

        // Draw the table header
        String[] header = csvLines.get(0).split(",");
        float columnWidth = tableWidth / header.length;
        for (int i = 0; i < header.length; i++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(margin + i * columnWidth, y);
            contentStream.showText(header[i]);
            contentStream.endText();
        }
        y -= 20; // Adjust the line spacing as needed

        // Draw the table rows
        for (int i = 1; i < csvLines.size(); i++) {
            String[] row = csvLines.get(i).split(",");
            for (int j = 0; j < row.length; j++) {
                contentStream.beginText();
                contentStream.newLineAtOffset(margin + j * columnWidth, y);
                contentStream.showText(row[j]);
                contentStream.endText();
            }
            y -= 15; // Adjust the line spacing as needed
        }

        // Close the content stream
        contentStream.close();

        // Save the PDF document
        document.save(new File("D:/pdf/my_doc1_csv.pdf"));

        // Close the document
        document.close();
    }


    @Override
    public void convertCsvToPdfBorders(PDFDTO data) throws IOException {
        List<String> csvLines = Files.readAllLines(Paths.get("D:/pdf/diabetes.csv"));

        // Create a new PDF document
        PDDocument document = new PDDocument();

        // Set the desired page size (in points, 1 inch = 72 points)
        float pageWidth = 1792; // 11 inches
        float pageHeight = 12000; // 17 inches

        // Create a new page with the custom size
        PDPage page = new PDPage(new PDRectangle(pageWidth, pageHeight));
        document.addPage(page);

        // Create a content stream to write the CSV data to the PDF
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Set the font and font size
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);

        // Define the table dimensions
        float tableWidth = page.getMediaBox().getWidth() - 50; // Adjust as needed
        float tableHeight = page.getMediaBox().getHeight() - 100; // Adjust as needed
        float y = tableHeight;
        float margin = 10;

        // Draw the table borders
        contentStream.setLineWidth(1); // Set the line width for borders
        contentStream.addRect(margin, tableHeight - 20, tableWidth, -tableHeight + 40); // Draw the outer border
        contentStream.stroke();

        // Draw the table header
        String[] header = csvLines.get(0).split(",");
        float columnWidth = tableWidth / header.length;
        float x = margin;
        for (int i = 0; i < header.length; i++) {
            float textWidth = 30;
            float adjustedX = x + (columnWidth - textWidth) / 2;
            contentStream.beginText();
            contentStream.newLineAtOffset(adjustedX, y);
            contentStream.showText(header[i]);
            contentStream.endText();

            // Draw a vertical line after each header cell (except the last one)
            if (i < header.length - 1) {
                contentStream.moveTo(x + columnWidth, tableHeight);
                contentStream.lineTo(x + columnWidth, tableHeight - tableHeight + 20);
                contentStream.stroke();
            }

            x += columnWidth;
        }
        y -= 40; // Adjust the line spacing as needed

// Draw the table rows
        for (int i = 1; i < csvLines.size(); i++) {
            String[] row = csvLines.get(i).split(",");
            x = margin;
            for (int j = 0; j < row.length; j++) {
                float textWidth = 30;
                float adjustedX = x + (columnWidth - textWidth) / 2;
                contentStream.beginText();
                contentStream.newLineAtOffset(adjustedX, y);
                contentStream.showText(row[j]);
                contentStream.endText();

                // Draw a vertical line after each cell (except the last one)
                if (j < row.length - 1) {
                    contentStream.moveTo(x + columnWidth, y);
                    contentStream.stroke();
                }

                x += columnWidth;
            }

            // Draw a horizontal line after each row
            contentStream.moveTo(margin, y - 15);
            contentStream.stroke();

            y -= 15; // Adjust the line spacing as needed
        }


        // Close the content stream
        contentStream.close();

        // Save the PDF document
        document.save(new File("D:/pdf/my_doc1_csv.pdf"));

        // Close the document
        document.close();


    }
    //conversion of csv file to pdf file
    @Override
    public void convertCsvToPdfBordersA4(PDFDTO data) throws IOException {
        List<String> csvLines = Files.readAllLines(Paths.get("D:/pdf/vertical-table.csv"));

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
        document.save(new File("D:/pdf/my_docsamp_csv.pdf"));

        // Close the document
        document.close();
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




    @Override
    public void convertExcelToPdf(PDFDTO data) throws IOException, InvalidFormatException {
        try {
            // Load the Excel file
            File excelFile = new File("D:/pdf/excel-sample.xlsx");
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
                        int columnWidth=50;
                        cellX += columnWidth + 10; // Move to the next cell position with spacing

                        //contentStream.showTextAligned(PDType1Font.ALIGN_LEFT, cellValue, cellX, y, columnWidths[cell.getColumnIndex()]);
                        // cellX += columnWidths[cell.getColumnIndex()] + 10; // Move to the next cell position with spacing
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
            document.save(new File("D:/pdf/e_pdf.pdf"));

            // Close the PDF document
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //    @Override
//    public void convertCsvImageToPdfBordersA4(PDFDTO data) throws IOException, InvalidFormatException {
//
//    }
@Override
public void convertCsvImageToPdfBorders(PDFDTO data) throws IOException {
    List<String> csvLines = Files.readAllLines(Paths.get("D:/pdf/vertical-table.csv"));

    // Create a new PDF document
    PDDocument document = new PDDocument();

    // Set the desired page size (in points, 1 inch = 72 points)
    float pageWidth = 1792; // 11 inches
    float pageHeight = 12000; // 17 inches

    // Create a new page with the custom size
    PDPage page = new PDPage(new PDRectangle(pageWidth, pageHeight));
    document.addPage(page);

    // Create a content stream to write the CSV data to the PDF
    PDPageContentStream contentStream = new PDPageContentStream(document, page);

    // Set the font and font size
    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);

    // Define the table dimensions
    float tableWidth = page.getMediaBox().getWidth() - 50; // Adjust as needed
    float tableHeight = page.getMediaBox().getHeight() - 100; // Adjust as needed
    float y = tableHeight;
    float margin = 10;

    // Draw the table borders
    contentStream.setLineWidth(1); // Set the line width for borders
    contentStream.addRect(margin, tableHeight - 20, tableWidth, -tableHeight + 40); // Draw the outer border
    contentStream.stroke();

    // Draw the table header
    String[] header = csvLines.get(0).split(",");
    float columnWidth = tableWidth / header.length;
    float x = margin;
    for (int i = 0; i < header.length; i++) {
        float textWidth = 30;
        float adjustedX = x + (columnWidth - textWidth) / 2;
        contentStream.beginText();
        contentStream.newLineAtOffset(adjustedX, y);
        contentStream.showText(header[i]);
        contentStream.endText();

        // Draw a vertical line after each header cell (except the last one)
        if (i < header.length - 1) {
            contentStream.moveTo(x + columnWidth, tableHeight);
            contentStream.lineTo(x + columnWidth, tableHeight - tableHeight + 20);
            contentStream.stroke();
        }

        x += columnWidth;
    }
    y -= 40; // Adjust the line spacing as needed

    // Draw the table rows
    for (int i = 1; i < csvLines.size(); i++) {
        String[] row = csvLines.get(i).split(",");
        x = margin;
        for (int j = 0; j < row.length; j++) {
            if (row[j].startsWith("data:image/")) {
                // Handle image data
                String base64ImageData = row[j].split(",")[1];
                byte[] imageBytes = Base64.getDecoder().decode(base64ImageData);;
                PDImageXObject imageXObject = PDImageXObject.createFromByteArray(document, imageBytes, "");
                float imageWidth = columnWidth - 20; // Adjust as needed
                float imageHeight = 0;
                contentStream.drawImage(imageXObject, x + 10, y - imageHeight - 5, imageWidth, imageHeight);
            } else {
                // Handle text data
                float textWidth = 30;
                float adjustedX = x + (columnWidth - textWidth) / 2;
                contentStream.beginText();
                contentStream.newLineAtOffset(adjustedX, y);
                contentStream.showText(row[j]);
                contentStream.endText();
            }

            // Draw a vertical line after each cell (except the last one)
            if (j < row.length - 1) {
                contentStream.moveTo(x + columnWidth, y);
                contentStream.stroke();
            }

            x += columnWidth;
        }

        // Draw a horizontal line after each row
        contentStream.moveTo(margin, y - 15);
        contentStream.stroke();

        y -= 15; // Adjust the line spacing as needed
    }

    // Close the content stream
    contentStream.close();

    // Save the PDF document
    document.save(new File("D:/pdf/csv_image.pdf"));

    // Close the document
    document.close();
}


}

