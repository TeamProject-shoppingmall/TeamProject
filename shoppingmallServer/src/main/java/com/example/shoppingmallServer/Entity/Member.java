package com.example.shoppingmallServer.Entity;

import com.example.shoppingmallServer.Dto.MemberDto;
import com.example.shoppingmallServer.Enum.GenderEnum;
import com.example.shoppingmallServer.Enum.MemberEnum;
import jakarta.persistence.*;

@Entity
public class Member {
    @Id
    @GeneratedValue
    @Column(name="member_Key")
    private int memberKey;

    private MemberEnum regularMember;

    private MemberEnum kakaoMember;

    private MemberEnum googleMember;

    private String memberId;

    private String memberPw;

    private String memberName;

    private String memberEmail;

    private String memberPhone;

    private GenderEnum memberGender;

    @Embedded
    private Address address;

    private Member(MemberDto memberDto) {
        this.regularMember = memberDto.getRegularMember();
        this.kakaoMember = memberDto.getKakaoMember();
        this.googleMember = memberDto.getGoogleMember();
        this.memberId = memberDto.getMemberId();
        this.memberPw = memberDto.getMemberPw();
        this.memberName = memberDto.getMemberName();
        this.memberEmail = memberDto.getMemberEmail();
        this.memberPhone = memberDto.getMemberPhone();
        this.memberGender = memberDto.getMemberGender();
        this.address = memberDto.getAddress();
    }

    public Member() {

    }

    public static Member createMember(MemberDto memberDto) {
        return new Member(memberDto);
    }
}
