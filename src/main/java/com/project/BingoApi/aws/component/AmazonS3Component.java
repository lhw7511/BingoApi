package com.project.BingoApi.aws.component;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
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
        String key = fileDto.getKey();
        File file = new File(MultipartUtil.getLocalHomeDirectory(), key);
        try {
            multipartFile.transferTo(file);
            amazonS3Client.putObject(new PutObjectRequest(bucket, key, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            fileDto.setFileUrl(amazonS3Client.getUrl(bucket,multipartFile.getOriginalFilename()).toString());
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

    public void delete(FileDto fileDto){
        if (!amazonS3Client.doesObjectExist(bucket, fileDto.getKey())) {
            throw new AmazonS3Exception("Object " +fileDto.getKey()+ " does not exist!");
        }
        amazonS3Client.deleteObject(bucket, fileDto.getKey());
    }
}
