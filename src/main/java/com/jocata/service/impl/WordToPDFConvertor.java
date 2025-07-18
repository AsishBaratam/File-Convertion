package com.jocata.service.impl;

import java.io.FileNotFoundException;


import com.jocata.dto.SourceDTO;
import com.jocata.service.PDFConvertor;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class WordToPDFConvertor implements PDFConvertor {

    @Override
    public String converttoPDF(SourceDTO sourceDTO) throws IOException {
        System.out.println("Converting Word to PDF");
        List<String> pdfSources = new ArrayList<>();
        for(String fileName : sourceDTO.getSources()) {
            try {
                System.out.println(fileName);
                //path of word file(docx file
                String wordFilePath = fileName;
                String sourcepdfFilePath = fileName.substring(0, fileName.length() - 4) + "pdf";
                //taking input from docx file
                pdfSources.add(sourcepdfFilePath);
                InputStream doc = new FileInputStream(new File(wordFilePath));
                //process for creating pdf started
                XWPFDocument document = new XWPFDocument(doc);
                PdfOptions options = PdfOptions.create();
                OutputStream out = new FileOutputStream(new File(sourcepdfFilePath));
                PdfConverter.getInstance().convert(document, out, options);
            } catch (FileNotFoundException e) {
                return "File not found";
            }
        }
        mergePDF(pdfSources, sourceDTO.getTarget());
        return "";
    }
}
