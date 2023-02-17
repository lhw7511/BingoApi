package com.project.BingoApi.jpa.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MainParamDto {

    private String guBun;

    private Long regionId;

    private Integer curPage = 1;

    private String latitude;

    private String longitude;

    private String parkingYn;

    private String distanceLimit;
}
