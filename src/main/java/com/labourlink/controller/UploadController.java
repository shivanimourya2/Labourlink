package com.labourlink.controller;

import com.labourlink.dto.ApiResponse;
import com.labourlink.service.FileUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin(origins = "*")
public class UploadController {

    private final FileUploadService fileUploadService;

    public UploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        try {
            List<String> urls = new ArrayList<>();
            for (MultipartFile file : files) {
                String url = fileUploadService.uploadFile(file);
                urls.add(url);
            }
            return ResponseEntity.ok(ApiResponse.ok("Files uploaded", urls));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Upload failed: " + e.getMessage()));
        }
    }
}
