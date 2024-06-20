package com.eidiko.controller;

import com.eidiko.dto.ImageUtils;
import com.eidiko.entity.Attachment;
import com.eidiko.entity.ResponseModel;
import com.eidiko.exception_handler.FileUploadException;
import com.eidiko.responce.CommonResponse;
import com.eidiko.serviceimplementation.CompensatoryOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/leave")
public class CompensatoryOffController {

    @Autowired
    private CompensatoryOffService compensatoryService;

    @Autowired
    private CommonResponse<String> commonResponse;

    @PostMapping("/requestCompensatory")
    public ResponseEntity<ResponseModel<String>> requestCompensatory(
            @RequestParam String compensatoryOffDate,
            @RequestParam String note,
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam Long employeeId) {

        if (files.size() > 5) {
            return commonResponse.prepareErrorResponseObject("You can only upload up to 5 files.", HttpStatus.BAD_REQUEST);
        }

        List<String> fileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.getSize() > 20 * 1024 * 1024) { // 20MB in bytes
                return commonResponse.prepareErrorResponseObject("File size should not exceed 20MB.", HttpStatus.BAD_REQUEST);
            }

            String fileExtension = compensatoryService.getFileExtension(file.getOriginalFilename());
            if (!compensatoryService.isAllowedFileType(fileExtension)) {
                return commonResponse.prepareErrorResponseObject("Invalid file type.", HttpStatus.BAD_REQUEST);
            }
            fileNames.add(file.getOriginalFilename());
        }

        try {
            List<String> imageResponses = compensatoryService.requestLeave(compensatoryOffDate, note, files, employeeId);
            return commonResponse.prepareSuccessResponseObject("Compensatory leave requested successfully");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return commonResponse.prepareErrorResponseObject("Error processing files", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //this method is used to get the image from db
    @GetMapping("/displayattachments/{id}")
    public ResponseEntity<byte[]> getAttachment(@PathVariable Long id) {
        try {
            Attachment attachment = compensatoryService.getAttachment(id);
            if (attachment != null) {
                byte[] decompressedImage = ImageUtils.decompressImage(attachment.getImageData().getBytes(1, (int) attachment.getImageData().length()));

                return ResponseEntity.ok()
                        .header("Content-Type", "image/" + attachment.getFileExtension())
                        .body(decompressedImage);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //this method is used to get the content from the pdf or docx
    @GetMapping("/attachments/text/{id}")
    public ResponseEntity<ResponseModel<String>> getAttachmentText(@PathVariable Long id) {
        try {
            Attachment attachment = compensatoryService.getAttachment(id);
            if (attachment != null && attachment.getFileContent() != null) {
                return commonResponse.prepareSuccessResponseObject(Arrays.toString(attachment.getFileContent()));
            } else {
                return commonResponse.prepareErrorResponseObject("Attachment not found or no text content available", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return commonResponse.prepareErrorResponseObject("Error retrieving text content", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    //THIS METHOD IS USED TO GET ALL THE IMAGES FROM THE DB
    @GetMapping("/attachments/images")
    public ResponseEntity<List<byte[]>> getAllImages() {
        try {
            List<Attachment> attachments = compensatoryService.getAllAttachments();
            List<byte[]> images = new ArrayList<>();

            for (Attachment attachment : attachments) {
                if (attachment != null && attachment.getImageData() != null) {
                    byte[] decompressedImage = ImageUtils.decompressImage(attachment.getImageData().getBytes(1, (int) attachment.getImageData().length()));
                    images.add(decompressedImage);
                }
            }

            return ResponseEntity.ok(images);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


 //THIS IS FOR DOWNLOAD U CAN DELETE IT NOT WORKING
    //this method is used for downloading the files
//    @GetMapping("/downloadattachments/{id}")
//    public ResponseEntity<byte[]> getAttachment(@PathVariable Long id) {
//        try {
//            Attachment attachment = compensatoryService.getAttachment(id);
//            if (attachment != null) {
//                String contentType = "application/octet-stream";
//                if (attachment.getFileExtension().equalsIgnoreCase("jpg") || attachment.getFileExtension().equalsIgnoreCase("jpeg")) {
//                    contentType = "image/jpeg";
//                } else if (attachment.getFileExtension().equalsIgnoreCase("png")) {
//                    contentType = "image/png";
//                } else if (attachment.getFileExtension().equalsIgnoreCase("pdf")) {
//                    contentType = "application/pdf";
//                } else if (attachment.getFileExtension().equalsIgnoreCase("doc") || attachment.getFileExtension().equalsIgnoreCase("docx")) {
//                    contentType = "application/msword";
//                }
//
//                byte[] fileContent = attachment.getFileContent();
//                String fileName = attachment.getAttachmentFileName();
//
//                return ResponseEntity.ok()
//                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
//                        .header(HttpHeaders.CONTENT_TYPE, contentType)
//                        .body(fileContent);
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
}

