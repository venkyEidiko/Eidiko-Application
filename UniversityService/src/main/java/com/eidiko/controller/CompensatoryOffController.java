package com.eidiko.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eidiko.dto.ImageUtils;
import com.eidiko.entity.Attachment;
import com.eidiko.entity.ResponseModel;
import com.eidiko.exception_handler.FileUploadException;
import com.eidiko.responce.CommonResponse;
import com.eidiko.serviceimplementation.CompensatoryOffService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/leave")
public class CompensatoryOffController {

    @Autowired
    private CompensatoryOffService compensatoryService;

    @Autowired
    private CommonResponse<String> commonResponse;


    //this api is used for making a request for compensatory request
    @PostMapping("/requestCompensatory")
    public ResponseEntity<ResponseModel<String>> requestCompensatory(@RequestParam LocalDate fromDate,
                                                                     @RequestParam LocalDate toDate, @RequestParam String note, @RequestParam("files") List<MultipartFile> files,
                                                                     @RequestParam Long employeeId) {

    	
        ResponseModel<String> responseModel = new ResponseModel<>();

        try {
            if (files.size() > 5) {
                throw new FileUploadException("You can only upload up to 5 files.");
            }
            List<String> fileNames = new ArrayList<>();

            for (MultipartFile file : files) {
                if (file.getSize() > 20 * 1024 * 1024) { // 20MB in bytes
                    throw new FileUploadException("File size should not exceed 20MB.");
                }

                String fileExtension = compensatoryService.getFileExtension(file.getOriginalFilename());
                if (!compensatoryService.isAllowedFileType(fileExtension)) {
                    throw new FileUploadException("Invalid file type.");
                }
                fileNames.add(file.getOriginalFilename());
            }

            List<String> imageResponses = compensatoryService.requestLeave(fromDate, toDate, note, files, employeeId);

            responseModel.setStatus("SUCCESS");
            responseModel.setStatusCode(200);
            responseModel.setResult(imageResponses);
            responseModel.setResult(Collections.singletonList("Compensatory leave requested successfully"));

            return ResponseEntity.ok(responseModel);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
            responseModel.setError("Error processing files");
            responseModel.setStatus("FAILED");
            return ResponseEntity.status(500).body(responseModel);
        } catch (FileUploadException e) {
            responseModel.setError(e.getMessage());
            responseModel.setStatus("FAILED");
            return ResponseEntity.status(400).body(responseModel);
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
            String textContent = compensatoryService.getAttachmentTextContent(id);
            if (textContent != null) {
                return commonResponse.prepareSuccessResponseObject("Text content extracted successfully", textContent);
            } else {
                return commonResponse.prepareErrorResponseObject("Attachment not found or unsupported file type", null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return commonResponse.prepareErrorResponseObject("Error extracting text content", null);
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



}
