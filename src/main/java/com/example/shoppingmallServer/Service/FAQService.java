package com.example.shoppingmallServer.Service;

import com.example.shoppingmallServer.Dto.FAQDto;
import com.example.shoppingmallServer.Dto.FAQModifyDto;
import com.example.shoppingmallServer.Entity.FAQ;
import com.example.shoppingmallServer.Entity.Member;
import com.example.shoppingmallServer.Exception.EmptyValueException;
import com.example.shoppingmallServer.Exception.FailedRemoveException;
import com.example.shoppingmallServer.Exception.NotFoundException;
import com.example.shoppingmallServer.JWT.JwtTokenProvider;
import com.example.shoppingmallServer.Repository.FAQRepository;
import com.example.shoppingmallServer.Repository.MemberRepository;
import com.example.shoppingmallServer.Response.FAQAllResponse;
import com.example.shoppingmallServer.Response.FAQResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FAQService {
    private final FAQRepository faqRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public ResponseEntity<String> insert(FAQDto faqDto, String accessToken) {
        String idFromToken = jwtTokenProvider.getIdFromToken(accessToken);
        Member findMember = memberRepository.findOneByUserId(idFromToken);

        if (findMember == null) {
            throw new EmptyValueException("회원 정보가 존재하지 않습니다.");
        }

        FAQ faq = FAQ.Create(faqDto, findMember);
        return faqRepository.insert(faq);
    }

    @Transactional
    public ResponseEntity<List<FAQAllResponse>> findAll() {
        List<FAQ> all = faqRepository.findAll();
        if (all == null) {
            throw new NotFoundException("현재 등록된 문의가 없습니다.");
        }
        List<FAQAllResponse> faqAllResponses = new ArrayList<>();
        for (FAQ faq: all){
            faqAllResponses.add(new FAQAllResponse(faq.getFaqKey(), faq.getFaqTitle(), faq.getFaqDate()));
        }
        return new ResponseEntity<>(faqAllResponses, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<FAQResponse> findOneById(int faqKey) {
        FAQ oneById = faqRepository.findOneById(faqKey);
        FAQResponse faqDto = new FAQResponse(oneById.getFaqKey(), oneById.getMemberKey().getMemberId(), oneById.getFaqTitle(),oneById.getFaqContent(), oneById.getFaqDate());
        return new ResponseEntity<>(faqDto, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> remove(int faqKey, String accessToken) {
        String idFromToken = jwtTokenProvider.getIdFromToken(accessToken);
        Member findMember = memberRepository.findOneByUserId(idFromToken);

        if (findMember == null) {
            throw new EmptyValueException("회원 정보가 존재하지 않습니다.");
        }

        FAQ oneById = faqRepository.findOneById(faqKey);
        if (oneById == null) {
            throw new NotFoundException("등록된 문의가 없습니다.");
        }
        if (oneById.getMemberKey().getMemberKey() != findMember.getMemberKey()) {
            throw new FailedRemoveException("작성자 외에는 삭제를 할 수 없습니다.");
        }
        return faqRepository.remove(oneById);
    }

    @Transactional
    public ResponseEntity<String> modify(int faqKey, String accessToken, FAQModifyDto faqModifyDto) {
        String idFromToken = jwtTokenProvider.getIdFromToken(accessToken);
        Member findMember = memberRepository.findOneByUserId(idFromToken);

        if (findMember == null) {
            throw new EmptyValueException("회원 정보가 존재하지 않습니다.");
        }

        FAQ oneById = faqRepository.findOneById(faqKey);
        if (oneById == null) {
            throw new NotFoundException("등록된 문의가 없습니다.");
        }
        if (oneById.getMemberKey().getMemberKey() != findMember.getMemberKey()) {
            throw new FailedRemoveException("작성자 외에는 삭제를 할 수 없습니다.");
        }
        oneById.Modify(faqModifyDto);
        return faqRepository.modify(oneById);
    }
}