package com.project.BingoApi.jpa.repository;

import com.project.BingoApi.jpa.domain.*;
import com.project.BingoApi.jpa.dto.MainParamDto;
import com.project.BingoApi.jpa.dto.RestaurantDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.MathExpressions;
import com.querydsl.core.types.dsl.StringTemplate;
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
                .where(
                        filterParkingYn(mainParamDto),
                        filterDistance(mainParamDto),
                        filterCategory(mainParamDto)
                )
                .groupBy(restaurant.id)
                .orderBy(filterSort(mainParamDto))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> total = jpaQueryFactory.select(
                    restaurant.count()
            )
                .from(restaurant)
                .where(
                        filterParkingYn(mainParamDto),
                        filterDistance(mainParamDto),
                        filterCategory(mainParamDto)
                );


        List<RestaurantDto> resultList = new ArrayList<>();
        for(Tuple tuple : result){
            Restaurant tmpRestaurant = tuple.get(restaurant);
            Double avgRating = tuple.get(MathExpressions.round(review.rating.avg(),1));
            Long cnt = tuple.get(review.rating.count());
            resultList.add(new RestaurantDto(tmpRestaurant,avgRating,cnt));
        }
        return PageableExecutionUtils.getPage(resultList,pageable,total::fetchOne);
    }
    //카테고리 필터
    private BooleanExpression filterCategory(MainParamDto mainParamDto){
        if(mainParamDto.getCategoryKey().isEmpty()){
           return null;
        }
        return category.categoryKey.in(mainParamDto.getCategoryKey());
    }


    //주차여부 필터
    private BooleanExpression filterParkingYn(MainParamDto mainParamDto){
        if(!StringUtils.hasLength(mainParamDto.getParkingYn())){
            return null;
        }
        return restaurant.parkingYn.eq(mainParamDto.getParkingYn());
    }

    //반경 ?km이내 필터
    private BooleanExpression filterDistance(MainParamDto mainParamDto){
        if(!StringUtils.hasLength(mainParamDto.getLatitude()) || !StringUtils.hasLength(mainParamDto.getLongitude()) || !StringUtils.hasLength(mainParamDto.getDistanceLimit())){
            return null;
        }
        return callStDistanceSphereFunction(mainParamDto).loe(mainParamDto.getDistanceLimit());
    }

    //리뷰개수, 평점, 거리순 정렬
    private OrderSpecifier<?> filterSort(MainParamDto mainParamDto) {
        if("avg".equals(mainParamDto.getGuBun())){
            return review.rating.avg().desc().nullsLast();
        }else if("distance".equals(mainParamDto.getGuBun()) && StringUtils.hasLength(mainParamDto.getLongitude()) && StringUtils.hasLength(mainParamDto.getLatitude())){
            return callStDistanceSphereFunction(mainParamDto).asc();
        }

        return review.rating.count().desc().nullsLast();
    }

    //위도 경도를 이용해 거리계산 함수 호출
    private StringTemplate callStDistanceSphereFunction(MainParamDto mainParamDto){
        return Expressions.stringTemplate("ST_Distance_Sphere({0}, {1})",
                Expressions.stringTemplate("POINT({0}, {1})",
                        mainParamDto.getLongitude(),
                        mainParamDto.getLatitude()
                ),
                Expressions.stringTemplate("POINT({0}, {1})",
                        restaurant.longitude,
                        restaurant.latitude
                ));
    }
}
