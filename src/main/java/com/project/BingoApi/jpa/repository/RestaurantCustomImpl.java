package com.project.BingoApi.jpa.repository;

import com.project.BingoApi.jpa.domain.*;
import com.project.BingoApi.jpa.dto.MainParamDto;
import com.project.BingoApi.jpa.dto.RestaurantDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.MathExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.project.BingoApi.jpa.domain.QCategory.*;
import static com.project.BingoApi.jpa.domain.QRegion.region;
import static com.project.BingoApi.jpa.domain.QRestaurant.restaurant;
import static com.project.BingoApi.jpa.domain.QReview.review;

@RequiredArgsConstructor
public class RestaurantCustomImpl implements RestaurantCustom{

    private  final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<RestaurantDto> getMainList(MainParamDto mainParamDto, Pageable pageable) {
        List<Tuple> result = jpaQueryFactory.select(
                        restaurant,
                        MathExpressions.round(review.rating.avg(),1),
                        review.rating.count()
                )
                .from(restaurant)
                .leftJoin(restaurant.reviews, review)
                .leftJoin(restaurant.category, category).fetchJoin()
                .leftJoin(restaurant.region, region).fetchJoin()
                .groupBy(restaurant.id)
                .orderBy(sortStandard(mainParamDto))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> total = jpaQueryFactory.select(
                    restaurant.count()
            )
            .from(restaurant);


        List<RestaurantDto> resultList = new ArrayList<>();
        for(Tuple tuple : result){
            Restaurant tmpRestaurant = tuple.get(restaurant);
            Double avgRating = tuple.get(MathExpressions.round(review.rating.avg(),1));
            Long cnt = tuple.get(review.rating.count());
            resultList.add(new RestaurantDto(tmpRestaurant,avgRating,cnt));
        }
        return PageableExecutionUtils.getPage(resultList,pageable,total::fetchOne);
    }

    private OrderSpecifier<?> sortStandard(MainParamDto mainParamDto) {
        if("avg".equals(mainParamDto.getGubun())){
            return review.rating.avg().desc().nullsLast();
        }else if("distance".equals(mainParamDto.getGubun()) && StringUtils.hasLength(mainParamDto.getLongitude()) && StringUtils.hasLength(mainParamDto.getLatitude())){
            return Expressions.stringTemplate("ST_Distance_Sphere({0}, {1})",
                    Expressions.stringTemplate("POINT({0}, {1})",
                            mainParamDto.getLongitude(),
                            mainParamDto.getLatitude()
                    ),
                    Expressions.stringTemplate("POINT({0}, {1})",
                            restaurant.longitude,
                            restaurant.latitude
                    )).asc();
        }

        return review.rating.count().desc().nullsLast();
    }
}
