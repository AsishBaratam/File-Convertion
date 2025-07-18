package com.jocata.service;

import com.jocata.dto.SourceDTO;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;

import java.io.IOException;
import java.util.List;


public interface PDFConvertor {

    public String converttoPDF(SourceDTO sourceDTO) throws IOException;

    public default void mergePDF(List<String> sources, String destination) throws IOException {

        if (sources.isEmpty()) {
            System.out.println("No source files provided.");
            return;
        }

        PDFMergerUtility pdfMerger = new PDFMergerUtility();

        // Set the first file in the list as the destination
        String destinationFilePath = sources.get(0);
        pdfMerger.setDestinationFileName(destination);

        for (int i = 0; i < sources.size(); i++) {
            String source = sources.get(i);
            File sourceFile = new File(source);

            // Add the source files (except the first one) to the merger
            pdfMerger.addSource(sourceFile);
        }

        // Merge the documents
        pdfMerger.mergeDocuments(null);

        System.out.println("Documents merged into " + destinationFilePath);
    }

}
