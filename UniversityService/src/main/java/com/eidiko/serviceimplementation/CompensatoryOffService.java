package com.eidiko.serviceimplementation;

import com.eidiko.dto.ImageUtils;
import com.eidiko.entity.Attachment;
import com.eidiko.entity.EmpLeave;
import com.eidiko.entity.EmpLeave;
import com.eidiko.repository.AttachmentRepository;
import com.eidiko.repository.EmpLeaveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CompensatoryOffService {

    @Autowired
    private EmpLeaveRepo leaveRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    public List<String> requestLeave(String compensatoryOffDate, String note, List<MultipartFile> files, Long employeeId) throws IOException, SQLException {
        String[] dates = compensatoryOffDate.split(" - ");
        String fromDate = dates[0];
        String toDate = dates[1];

        EmpLeave leave = new EmpLeave();
        leave.setFromDate(LocalDate.parse(fromDate));
        leave.setToDate(LocalDate.parse(toDate));
        leave.setLeaveNote(note);
        leave.setEmployeeId(employeeId);
        leave.setStatus("Pending");
        leave.setLeaveType("comp offs");
        leaveRepository.save(leave);

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
                attachment.setImageData(new SerialBlob(compressedData));

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

    public Attachment getAttachment(Long id) {
        return attachmentRepository.findById(id).orElse(null);
    }

    public List<Attachment> getAllAttachments() {
        return attachmentRepository.findAll();
    }
}