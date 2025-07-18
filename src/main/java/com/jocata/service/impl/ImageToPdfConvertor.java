package com.jocata.service.impl;

import com.jocata.dto.SourceDTO;
import com.jocata.service.PDFConvertor;
import com.jocata.validators.Validator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageToPdfConvertor implements PDFConvertor {

    @Override
    public String converttoPDF(SourceDTO sourceDTO) throws IOException {
        System.out.println("Converting Image to PDF");
        List<String> pdfSources = new ArrayList<>();
        if (Validator.validateSources(sourceDTO.getSources()) && Validator.validateTarget(sourceDTO.getTarget())) {
            for (String fileName : sourceDTO.getSources()) {
                PDPageContentStream contentStream = null;
                PDDocument document = null;
                try {
                    String sourceFileName = fileName.replaceAll("\\.[^.]+$", "") + ".pdf";

                    File file = new File(sourceFileName);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    pdfSources.add(sourceFileName);

                    // Create a new PDF document
                    document = new PDDocument();

                    // Add a new blank page to the document
                    PDPage page = new PDPage();
                    document.addPage(page);

                    // Load the image
                    PDImageXObject imageXObject = PDImageXObject.createFromFile(fileName, document);

                    // Get the image dimensions
                    float imageWidth = imageXObject.getWidth();
                    float imageHeight = imageXObject.getHeight();

                    // Get the page dimensions
                    PDRectangle pageSize = page.getMediaBox();
                    float pageWidth = pageSize.getWidth();
                    float pageHeight = pageSize.getHeight();

                    // Calculate the scaling factors to fit the image to the page
                    float scaleX = pageWidth / imageWidth;
                    float scaleY = pageHeight / imageHeight;
                    float scale = Math.min(scaleX, scaleY);

                    // Adjust the image dimensions to fit the page
                    float adjustedWidth = imageWidth * scale;
                    float adjustedHeight = imageHeight * scale;

                    contentStream = new PDPageContentStream(document, page);
                    contentStream.drawImage(imageXObject, (pageWidth - adjustedWidth) / 2, (pageHeight - adjustedHeight) / 2, adjustedWidth, adjustedHeight);
                    contentStream.close();

                    // Save the document
                    document.save(file);
                    document.close();
                }
                catch(IOException e){
                    return "File not found";
                } finally {
                    if(contentStream != null) {
                        contentStream.close();
                    }
                    if(document != null) {
                        document.close();
                    }
                }
            }


            mergePDF(pdfSources, sourceDTO.getTarget());
            return "";
        } else {
            if (!Validator.validateSources(sourceDTO.getSources())) {
                return "Invalid source";
            } else if (!Validator.validateTarget(sourceDTO.getTarget())) {
                return "Invalid destination";
            }
            return "Invalid Source or Target";
        }
    }




}