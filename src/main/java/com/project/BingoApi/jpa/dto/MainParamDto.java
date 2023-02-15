package com.project.BingoApi.jpa.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MainParamDto {

    private String gubun;

    private Long regionId;

    private Integer curPage = 1;

    private String latitude;

    private String longitude;
}
