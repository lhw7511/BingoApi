package com.project.BingoApi.jpa.repository;


import com.project.BingoApi.jpa.domain.*;
import com.project.BingoApi.jpa.dto.RestaurantDto;
import com.project.BingoApi.jpa.dto.UserDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.MathExpressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.BingoApi.jpa.domain.QRegion.region;
import static com.project.BingoApi.jpa.domain.QRestaurant.restaurant;
import static com.project.BingoApi.jpa.domain.QReview.review;
import static com.project.BingoApi.jpa.domain.QUser.user;
import static com.project.BingoApi.jpa.domain.QWishList.wishList;
import static com.querydsl.jpa.JPAExpressions.select;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public UserDto getMyPage(String email) {
        UserDto result = jpaQueryFactory.select(Projections.constructor(
                        UserDto.class,
                        user,
                        ExpressionUtils.as(
                                select(review.id.count()).
                                        from(review).
                                        where(review.user.id.eq(user.id))
                                , "reviewCount"),
                        ExpressionUtils.as(
                                select(wishList.id.count()).
                                        from(wishList).
                                        where(wishList.user.id.eq(user.id))
                                , "wishListCount")

                ))
                .from(user)
                .where(user.email.eq(email))
                .fetchOne();

        return result;
    }



    public List<RestaurantDto> getUserWishList(String email){
        return jpaQueryFactory.select(
                        Projections.constructor(RestaurantDto.class,
                                    restaurant,
                                    ExpressionUtils.as(
                                            select(MathExpressions.round(review.rating.avg(),1)).
                                                    from(review).
                                                    where(review.restaurant.id.eq(restaurant.id))
                                            , "avgRating"),
                                    ExpressionUtils.as(
                                            select(review.rating.count()).
                                                    from(review).
                                                    where(review.restaurant.id.eq(restaurant.id))
                                            , "cnt")
                        )
                )
                .from(wishList)
                .innerJoin(wishList.restaurant,restaurant)
                .leftJoin(restaurant.region,region).fetchJoin()
                .leftJoin(restaurant.category,QCategory.category).fetchJoin()
                .where(wishList.user.id.eq(
                        select(user.id).
                                from(user).
                                where(user.email.eq(email))))
                .orderBy(wishList.id.desc())
                .fetch();


    }

}
