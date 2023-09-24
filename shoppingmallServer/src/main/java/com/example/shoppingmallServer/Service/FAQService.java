package com.example.shoppingmallServer.Service;

import com.example.shoppingmallServer.Dto.FAQDto;
import com.example.shoppingmallServer.Entity.FAQ;
import com.example.shoppingmallServer.Entity.Member;
import com.example.shoppingmallServer.Exception.NotFoundException;
import com.example.shoppingmallServer.Repository.FAQRepository;
import com.example.shoppingmallServer.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FAQService {
    private final FAQRepository faqRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ResponseEntity<String> insert(FAQDto faqDto) {
        Member oneById = memberRepository.findOneById(faqDto.getMemberKey());
        if (oneById == null) {
            throw new NotFoundException("회원을 찾을 수 없습니다.");
        }
        FAQ faq = FAQ.Create(faqDto, oneById);
        return faqRepository.insert(faq);
    }
}
