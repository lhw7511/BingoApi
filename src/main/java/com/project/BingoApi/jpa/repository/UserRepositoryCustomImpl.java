package com.project.BingoApi.jpa.repository;


import com.project.BingoApi.jpa.domain.QReview;
import com.project.BingoApi.jpa.domain.QUser;
import com.project.BingoApi.jpa.domain.QWishList;
import com.project.BingoApi.jpa.dto.UserDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

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
}
