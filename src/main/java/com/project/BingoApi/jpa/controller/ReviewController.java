package com.project.BingoApi.jpa.controller;

import com.project.BingoApi.jpa.dto.ParamDto;
import com.project.BingoApi.jpa.dto.ReviewDto;
import com.project.BingoApi.jpa.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mobile")
public class ReviewController {

    private final ReviewService reviewService;

    @RequestMapping("reviewList")
    public Page<ReviewDto> getReviewList(ParamDto paramDto){
        return reviewService.getReviewList(paramDto);
    }
}
