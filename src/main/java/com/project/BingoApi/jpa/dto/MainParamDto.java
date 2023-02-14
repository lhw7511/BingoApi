package com.project.BingoApi.jpa.dto;

import lombok.Data;

@Data
public class MainParamDto {

    private String gubun;

    private Long regionId;

    private Integer curPage = 1;
}
