package com.jocata.service;

import com.jocata.dto.PDFDTO;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface PDFService {
    String sayHello(String name, Long id);

    Object saveUserData(PDFDTO data);

    Object getUserData(PDFDTO data);

    void deleteData(PDFDTO data);

    void updateData(PDFDTO data);

    void savePdf(PDFDTO data) throws IOException;

    void loadPdfData(PDFDTO data) throws IOException;

    void removePdfData(PDFDTO data) throws IOException;

    void addingDocAtt(PDFDTO data) throws IOException;

    void addContent(PDFDTO data) throws IOException;

    void mergePdf(PDFDTO data) throws FileNotFoundException, IOException;

    void convertWordToPdf(PDFDTO data) throws IOException, InvalidFormatException;

    void addImage(PDFDTO data) throws IOException;

    void encryptingData(PDFDTO data) throws IOException;

    void convertCsvToPdf(PDFDTO data) throws IOException;

    void convertCsvToPdfBorders(PDFDTO data) throws IOException;

    void convertCsvToPdfBordersA4(PDFDTO data) throws IOException;

    void convertExcelToPdf(PDFDTO data) throws IOException, InvalidFormatException;

    //    @Override
    //    public void convertCsvImageToPdfBordersA4(PDFDTO data) throws IOException, InvalidFormatException {
    //
    //    }
    void convertCsvImageToPdfBorders(PDFDTO data) throws IOException;

//    void convertCsvImageToPdfBordersA4(PDFDTO data) throws IOException, InvalidFormatException;
}
