package com.eidiko.serviceimplementation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eidiko.dto.ImageUtils;
import com.eidiko.entity.Attachment;
import com.eidiko.entity.EmpLeave;
import com.eidiko.mapper.Mapper;
import com.eidiko.repository.AttachmentRepository;
import com.eidiko.repository.EmpLeaveRepo;
import com.eidiko.service.EmpLeaveService;

@Service
public class CompensatoryOffService {

    @Autowired
    private EmpLeaveRepo leaveRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private Mapper mapper;

    @Autowired
    private EmpLeaveService empLeaveService;



    public List<String> requestLeave(LocalDate fromDate,LocalDate toDate, String note, List<MultipartFile> files, Long employeeId) throws IOException, SQLException {


        EmpLeave leave = new EmpLeave();
        leave.setFromDate(fromDate);
        leave.setToDate(toDate);
        leave.setLeaveNote(note);
        leave.setEmployeeId(employeeId);
        leave.setStatus("Pending");
        leave.setLeaveType("comp offs");
        leave.setCustomDayStatus("Full Day");
        empLeaveService.saveEmpLeave(mapper.empLeaveToEmpLeaveDto (leave));
//        leaveRepository.save(leave);

        List<String> imageResponses = new ArrayList<>();

        for (MultipartFile file : files) {
            Attachment attachment = new Attachment();
            attachment.setEmpId(employeeId);
            attachment.setAttachmentFileName(file.getOriginalFilename());

            String fileExtension = getFileExtension(file.getOriginalFilename());
            attachment.setFileExtension(fileExtension);

            if (isImageFile(fileExtension)) {
                byte[] fileData = file.getBytes();
                byte[] compressedData = ImageUtils.compressImage(fileData);
                Blob imageData = new SerialBlob(compressedData);
                attachment.setImageData(imageData);

                String base64Image = Base64.getEncoder().encodeToString(fileData);
                imageResponses.add("data:image/" + fileExtension + ";base64," + base64Image);
            } else if (isPdfOrDocFile(fileExtension)) {
                byte[] fileData = file.getBytes();
                attachment.setFileContent(fileData);
            }

            attachmentRepository.save(attachment);
        }

        return imageResponses;
    }

    public boolean isImageFile(String extension) {
        return extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg");
    }

    public boolean isPdfOrDocFile(String extension) {
        return extension.equalsIgnoreCase("pdf") || extension.equalsIgnoreCase("doc") || extension.equalsIgnoreCase("docx");
    }

    private String extractContentFromPdf(byte[] fileContent) throws IOException {
        try (PDDocument document = PDDocument.load(fileContent)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }

    private String extractContentFromDocx(byte[] fileContent) throws IOException {
        try (XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(fileContent))) {
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            return extractor.getText();
        }
    }

    public Attachment getAttachment(Long id) {
        return attachmentRepository.findById(id).orElse(null);
    }

    public String getAttachmentTextContent(Long id) throws IOException {
        Attachment attachment = getAttachment(id);
        if (attachment == null) {
            return null;
        }
        if (isPdfOrDocFile(attachment.getFileExtension())) {
            if (attachment.getFileExtension().equalsIgnoreCase("pdf")) {
                return extractContentFromPdf(attachment.getFileContent());
            } else if (attachment.getFileExtension().equalsIgnoreCase("docx")) {
                return extractContentFromDocx(attachment.getFileContent());
            }
        }
        return null;
    }

    public List<Attachment> getAllAttachments() {
        return attachmentRepository.findAll();
    }

    public String getFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    public boolean isAllowedFileType(String extension) {
        return isImageFile(extension) || isPdfOrDocFile(extension);
    }

}