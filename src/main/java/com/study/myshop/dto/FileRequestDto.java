package com.study.myshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileRequestDto {

    private String fileName; // 업로드할 파일 이름 (예: "store_123.png")
    private String folder; // 업로드할 파일 이름 (예: "store_123.png")
    private String contentType; // MIME 타입 (예: "image/png")


}
