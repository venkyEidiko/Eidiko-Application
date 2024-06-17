package com.eidiko.controller;

import com.eidiko.dto.ImageUtils;
import com.eidiko.entity.Attachment;
import com.eidiko.entity.ResponseModel;
import com.eidiko.exception_handler.FileUploadException;
import com.eidiko.serviceimplementation.CompensatoryOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

    @CrossOrigin(origins = "*")
    @RestController
    @RequestMapping("/api/leave")
    public class CompensatoryOffController {

        @Autowired
        private CompensatoryOffService compensatoryService;

        @PostMapping("/requestCompensatory")
        public ResponseEntity<ResponseModel<String>> requestCompensatory(
                @RequestParam String compensatoryOffDate,
                @RequestParam String note,
                @RequestParam("files") List<MultipartFile> files,
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

                List<String> imageResponses = compensatoryService.requestLeave(compensatoryOffDate, note, files, employeeId);

                responseModel.setStatus("SUCCESS");
                responseModel.setStatusCode(200);
                responseModel.setResult(imageResponses);
                responseModel.setResult(Collections.singletonList("Compensatory leave requested successfully"));
                responseModel.setFileName(fileNames);
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


        @GetMapping("/attachments/{id}")
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

    }

