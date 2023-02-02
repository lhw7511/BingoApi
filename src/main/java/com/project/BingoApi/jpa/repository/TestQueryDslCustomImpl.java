package com.project.BingoApi.jpa.repository;

import com.project.BingoApi.jpa.domain.Member;
import com.project.BingoApi.jpa.domain.QMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.BingoApi.jpa.domain.QMember.*;

@RequiredArgsConstructor
public class TestQueryDslCustomImpl implements  TestQueryDslCustom{

    private  final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Member> getMemberList(Long id) throws Exception {

        return jpaQueryFactory
                .selectFrom(member)
                .where(statusEq(id))
                .fetch();
    }

    private BooleanExpression statusEq(Long id) {
        if(id == null){
            return null;
        }
        return member.id.eq(id);
    }
}
