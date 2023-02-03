package com.project.BingoApi.aws.component;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.project.BingoApi.aws.dto.FileDto;
import com.project.BingoApi.aws.util.MultipartUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Component
@RequiredArgsConstructor
public class AmazonS3Component {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;

    public FileDto save(MultipartFile multipartFile) {
        FileDto fileDto = FileDto.multipartOf(multipartFile);
        String fullPath = fileDto.getPath();
        File file = new File(MultipartUtil.getLocalHomeDirectory(), fullPath);
        try {
            multipartFile.transferTo(file);
            amazonS3Client.putObject(new PutObjectRequest(bucket, fullPath, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            fileDto.setPath(amazonS3Client.getUrl(bucket,multipartFile.getOriginalFilename()).toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        } finally {
            if (file.exists()) {
                file.delete();
            }
        }
        return fileDto;
    }
}
