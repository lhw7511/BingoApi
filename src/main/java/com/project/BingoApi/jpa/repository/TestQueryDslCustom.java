package com.project.BingoApi.jpa.repository;

import com.project.BingoApi.jpa.domain.Member;

import java.util.List;

public interface TestQueryDslCustom {
    public List<Member> getMemberList(Long id) throws Exception;
}
