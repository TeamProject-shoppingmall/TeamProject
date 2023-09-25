package com.example.shoppingmallServer.Service;

import com.example.shoppingmallServer.Dto.FAQDto;
import com.example.shoppingmallServer.Entity.FAQ;
import com.example.shoppingmallServer.Entity.Member;
import com.example.shoppingmallServer.Exception.NotFoundException;
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

    @Transactional
    public ResponseEntity<String> insert(FAQDto faqDto) {
        Member oneById = memberRepository.findOneById(faqDto.getMemberId());
        if (oneById == null) {
            throw new NotFoundException("회원을 찾을 수 없습니다.");
        }
        FAQ faq = FAQ.Create(faqDto, oneById);
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
}