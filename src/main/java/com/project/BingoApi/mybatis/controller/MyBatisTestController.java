package com.project.BingoApi.mybatis.controller;

import com.project.BingoApi.mybatis.dto.MyBatisTestDto;
import com.project.BingoApi.mybatis.service.MyBatisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyBatisTestController {

    private final MyBatisService myBatisService;

    @GetMapping("myBatisTest")
    public List<MyBatisTestDto> myBatisTestDto(MyBatisTestDto testDto) throws Exception{
        return myBatisService.getMemberList(testDto);
    }
}
