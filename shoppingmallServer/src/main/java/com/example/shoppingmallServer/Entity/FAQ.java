package com.example.shoppingmallServer.Entity;

import com.example.shoppingmallServer.Dto.FAQDto;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
public class FAQ {
    @Id
    @GeneratedValue
    @Column(name = "FAQ_key")
    private int faqKey;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_key")
    private Member memberKey;
    private String faqTitle;
    private LocalDateTime faqDate;
    private String faqContent;

    @OneToMany()
    @JoinColumn(name = "answer_key")
    private List<Answer> answer;

    public FAQ(FAQDto faqDto, Member member) {
        this.memberKey = member;
        this.faqTitle = faqDto.getFaqTitle();
        this.faqContent = faqDto.getFaqContent();
        this.faqDate = faqDto.getFaqDate();
    }

    public static FAQ Create(FAQDto faqDto, Member member) {
        return new FAQ(faqDto, member);
    }
}
