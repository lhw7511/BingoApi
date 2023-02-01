package com.project.BingoApi.jpa.controller;

import com.project.BingoApi.jpa.domain.Member;
import com.project.BingoApi.jpa.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("test")
    public Member test(){
        return testService.save("testName");
    }
}
