package com.jocata.controller;

import com.jocata.dto.PDFDTO;
import com.jocata.service.PDFService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class PDFController {
    @Autowired
    private PDFService PDFService;

    @PostMapping("/userData")
    public Object userData(@RequestBody PDFDTO data) {
        return PDFService.saveUserData(data);

    }

    @GetMapping("/usersData")
    public Object usersData(@RequestBody PDFDTO data) {
        return PDFService.getUserData(data);
    }

    @DeleteMapping("/deleteUserData")
    public void deleteData(@RequestBody PDFDTO data) {
        PDFService.deleteData(data);
    }

    @PostMapping("/updateUserData")
    public void updateData(@RequestBody PDFDTO data) {
        PDFService.updateData(data);
    }

    @PostMapping("/savePdf")
    public void save(@RequestBody PDFDTO data) throws IOException {
        PDFService.savePdf(data);
    }

    @PostMapping("/loadPdfData")
    public void loadPdfData(@RequestBody PDFDTO data) throws IOException {
        PDFService.loadPdfData(data);
    }

    @PostMapping("/removePdfData")
    public void removePdfData(@RequestBody PDFDTO data)
            throws IOException {
        PDFService.removePdfData(data);
    }

    @PostMapping("/addingDocAtt")
    public void addingDocAtt(@RequestBody PDFDTO data) throws
            IOException {
        PDFService.addingDocAtt(data);
    }

    @PostMapping("/addContent")
    public void addContent(@RequestBody PDFDTO data) throws IOException {
        PDFService.addContent(data);
    }

    @PostMapping("/mergePdf")
    public void mergePdf(@RequestBody PDFDTO data) throws IOException {
        PDFService.mergePdf(data);
    }

    @PostMapping("/convertWordToPdf")
    public void convertWordToPdf(@RequestBody PDFDTO data) throws IOException, InvalidFormatException {
        PDFService.convertWordToPdf(data);
    }

    @PostMapping("/addImage")
    public void addImage(@RequestBody PDFDTO data) throws IOException {
        PDFService.addImage(data);
    }

    @PostMapping("/encryptingData")
    public void encryptingData(@RequestBody PDFDTO data) throws IOException {
        PDFService.encryptingData(data);
    }

    @PostMapping("/convertCsvToPdf")
    public void convertCsvToPdf(@RequestBody PDFDTO data) throws IOException {
        PDFService.convertCsvToPdf(data);
    }

    @PostMapping("/convertCsvToPdfBorders")
    public void convertCsvToPdfBorders(@RequestBody PDFDTO data) throws IOException {
        PDFService.convertCsvToPdfBorders(data);
    }

    @PostMapping("/convertCsvToPdfBordersA4")
    public void convertCsvToPdfBordersA4(@RequestBody PDFDTO data) throws IOException {
        PDFService.convertCsvToPdfBordersA4(data);
    }

    @PostMapping("/convertExcelToPdf")
    public void convertExcelToPdf(@RequestBody PDFDTO data) throws IOException, InvalidFormatException {
        PDFService.convertExcelToPdf(data);

    }

    @PostMapping("/convertCsvImageToPdfBorders")
    public void convertCsvImageToPdfBorders(@RequestBody PDFDTO data) throws IOException {
        PDFService.convertCsvImageToPdfBorders(data);
    }
}
