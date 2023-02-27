package com.project.BingoApi.jpa.service;

import com.project.BingoApi.jpa.domain.Review;
import com.project.BingoApi.jpa.dto.ParamDto;
import com.project.BingoApi.jpa.dto.ReviewDto;
import com.project.BingoApi.jpa.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Page<ReviewDto> getReviewList(ParamDto paramDto){
        PageRequest pageRequest = PageRequest.of(paramDto.getCurPage() -1 ,4);
        Page<Review> reviewList = reviewRepository.getReviewList(paramDto.getRestaurantId(), pageRequest);
        return reviewList.map(rv -> new ReviewDto(rv));
    }
}
