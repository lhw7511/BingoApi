package com.project.BingoApi.jpa.service;

import com.project.BingoApi.jpa.domain.Member;
import com.project.BingoApi.jpa.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TestService {
    private final TestRepository testRepository;

    public Member save(String content){
        Member member = new Member();
        member.setName(content);
        testRepository.save(member);
        Optional<Member> findOne = testRepository.findById(member.getId());
        return findOne.get();
    }
}