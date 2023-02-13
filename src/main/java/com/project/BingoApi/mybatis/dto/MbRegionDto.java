package com.project.BingoApi.mybatis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // Null 값인 필드 제외
public class MbRegionDto {

    private Long regionId;

    private String regionName;

    private String regionImageUrl;

}
