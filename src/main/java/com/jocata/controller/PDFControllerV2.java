package com.jocata.controller;


import com.jocata.service.impl.CsvToPdfConvertor;
import com.jocata.service.impl.ExcelToPdfConvertor;
import com.jocata.service.impl.ImageToPdfConvertor;
import com.jocata.service.impl.WordToPDFConvertor;
import org.springframework.web.bind.annotation.RestController;

import com.jocata.dto.SourceDTO;
import com.jocata.service.PDFConvertor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;


@RestController
public class PDFControllerV2 {

    @Autowired
    private WordToPDFConvertor wordToPDFConvertor;

    @Autowired
    private ImageToPdfConvertor imageToPdfConvertor;

    @Autowired
    private CsvToPdfConvertor csvTopdfConvertor;

    @Autowired
    private ExcelToPdfConvertor excelToPdfConvertor;

    @PostMapping("/convertWordToPdfV2")
    public void convertWordToPdf(@RequestBody SourceDTO sourceDTO) throws IOException, InvalidFormatException {
        wordToPDFConvertor.converttoPDF(sourceDTO);
    }
    @PostMapping("/convertImageToPdfV2")
    public void convertImageToPdf(@RequestBody SourceDTO sourceDTO) throws IOException {
        imageToPdfConvertor.converttoPDF(sourceDTO);
    }
    @PostMapping("/convertCsvToPdfV2")
    public void convertCsvToPdf(@RequestBody SourceDTO sourceDTO) throws IOException {
        csvTopdfConvertor.converttoPDF(sourceDTO);
    }
    @PostMapping("/convertExcelToPdfv2")
    public void convertExcelToPdf(@RequestBody SourceDTO sourceDTO) throws IOException, InvalidFormatException {
        excelToPdfConvertor.converttoPDF(sourceDTO);
    }
}
