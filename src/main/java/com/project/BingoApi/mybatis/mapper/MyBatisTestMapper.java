package com.project.BingoApi.mybatis.mapper;

import com.project.BingoApi.mybatis.dto.MyBatisTestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyBatisTestMapper {
    public List<MyBatisTestDto> getMemberList(MyBatisTestDto testDto) throws  Exception;
}
