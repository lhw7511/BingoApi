package com.project.BingoApi.jpa.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.BingoApi.jpa.domain.Region;
import com.project.BingoApi.jpa.domain.Restaurant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Null 값인 필드 제외
public class RegionDto {

    private Long id;

    private  String regionName;

    private String regionImageUrl;

    private List<Restaurant> restaurants;

    public RegionDto(Region region){
        id = region.getId();
        regionName = region.getRegionName();
        regionImageUrl = region.getRegionImageUrl();
    }
}
