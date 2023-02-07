package com.project.BingoApi.aws.dto;

import com.project.BingoApi.aws.util.MultipartUtil;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class FileDto {
    private String id;
    private String key;
    private String name;
    private String format;
    private String fileUrl;
    private long bytes;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public static FileDto multipartOf(MultipartFile multipartFile) {
        final String fileId = MultipartUtil.createFileId();
        final String format = MultipartUtil.getFormat(multipartFile.getContentType());
        return FileDto.builder()
                .id(fileId)
                .key(MultipartUtil.createPath(fileId,format))
                .name(multipartFile.getOriginalFilename())
                .format(format)
                .bytes(multipartFile.getSize())
                .build();
    }
}