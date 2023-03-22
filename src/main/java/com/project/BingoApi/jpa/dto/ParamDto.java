package com.project.BingoApi.jpa.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ParamDto {

    private String guBun;

    private Long regionId;

    private Integer curPage = 1;

    private String latitude;

    private String longitude;

    private String parkingYn;

    private String distanceLimit;

    private List<String> categoryKey = new ArrayList<>();

    private Long restaurantId;

    private String keyword;

    private String distanceGap;
}
