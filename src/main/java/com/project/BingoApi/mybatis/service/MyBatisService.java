package com.project.BingoApi.mybatis.service;

import com.project.BingoApi.mybatis.dto.MyBatisTestDto;
import com.project.BingoApi.mybatis.mapper.MyBatisTestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyBatisService {
    private final MyBatisTestMapper myBatisTestMapper;

    public List<MyBatisTestDto> getMemberList(MyBatisTestDto testDto) throws  Exception{
        return myBatisTestMapper.getMemberList(testDto);
    }
}
