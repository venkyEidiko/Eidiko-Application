package com.eidiko.serviceimplementation;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

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
    private EmpLeaveService empLeaveService;

    @Autowired
	private Mapper mapper;
    
    @Autowired
    private AttachmentRepository attachmentRepository;

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

        List<String> imageResponses = new ArrayList<>();

        for (MultipartFile file : files) {
            Attachment attachment = new Attachment();
            attachment.setEmpId(employeeId);
            attachment.setAttachmentFileName(file.getOriginalFilename());

            String fileExtension = getFileExtension(file.getOriginalFilename());
            attachment.setFileExtension(fileExtension);

            byte[] fileData = file.getBytes();
            byte[] compressedData = ImageUtils.compressImage(fileData);
            Blob imageData = new SerialBlob(compressedData);
            attachment.setImageData(imageData);

            if (isImageFile(fileExtension)) {
                String base64Image = Base64.getEncoder().encodeToString(fileData);
                imageResponses.add("data:image/" + fileExtension + ";base64," + base64Image);
            }

            attachmentRepository.save(attachment);
        }

        return imageResponses;
    }

    public boolean isImageFile(String extension) {
        return extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg");
    }

    public String getFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    public boolean isAllowedFileType(String extension) {
        return extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") ||
                extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("doc") ||
                extension.equalsIgnoreCase("docx") || extension.equalsIgnoreCase("pdf");
    }

    public Attachment getAttachment(Long id) {
        return attachmentRepository.findById(id).orElse(null);
    }
}
