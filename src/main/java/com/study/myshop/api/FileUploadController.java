package com.study.myshop.api;

import com.study.myshop.common.ApiResponse;
import com.study.myshop.dto.FileRequestDto;
import com.study.myshop.dto.PresignedUrlResponse;
import com.study.myshop.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileUploadController {

    private final FileService fileService;

    /**
     * Presigned URL 발급
     */
    @PostMapping("/presigned-url")
    public ResponseEntity<PresignedUrlResponse> getPresignedUrl(@RequestBody FileRequestDto request) {
            return ResponseEntity.ok(fileService.getPresignedUrl(request));
    }

}
