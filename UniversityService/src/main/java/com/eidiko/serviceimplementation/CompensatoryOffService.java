package com.eidiko.serviceimplementation;

import com.eidiko.dto.ImageUtils;
import com.eidiko.entity.Attachment;
import com.eidiko.entity.Leave;
import com.eidiko.repository.AttachmentRepository;
import com.eidiko.repository.LeaveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CompensatoryOffService {

    @Autowired
    private LeaveRepo leaveRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    public List<String> requestLeave(String compensatoryOffDate, String note, List<MultipartFile> files, Long employeeId) throws IOException, SQLException {
        String[] dates = compensatoryOffDate.split(" - ");
        String fromDate = dates[0];
        String toDate = dates[1];

        Leave leave = new Leave();
        leave.setFromDate(fromDate);
        leave.setToDate(toDate);
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
