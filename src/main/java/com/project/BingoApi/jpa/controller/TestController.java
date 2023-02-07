package com.project.BingoApi.jpa.controller;

import com.project.BingoApi.aws.component.AmazonS3Component;
import com.project.BingoApi.aws.dto.FileDto;
import com.project.BingoApi.jpa.domain.Member;
import com.project.BingoApi.jpa.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    private  final AmazonS3Component amazonS3Component;
    @GetMapping("test")
    public Member test(){
        return testService.save("testName");
    }

    @GetMapping("queryDslTest")
    public List<Member> queryDslTest(Long id) throws  Exception{
        return testService.getMemberList(id);
    }

    @PostMapping("fileUploadTest")
    public FileDto fileUploadTest(@RequestPart("file") MultipartFile multipartFile){
        return amazonS3Component.save(multipartFile);
    }

    @PostMapping("fileDeleteTest")
    public String fileRemoveTest(FileDto fileDto){
        amazonS3Component.delete(fileDto);
        return "success";
    }
}
