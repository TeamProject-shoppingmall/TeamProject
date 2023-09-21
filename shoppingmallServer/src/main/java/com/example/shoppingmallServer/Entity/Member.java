package com.example.shoppingmallServer.Entity;

import com.example.shoppingmallServer.Dto.MemberDto;
import com.example.shoppingmallServer.Enum.GenderEnum;
import com.example.shoppingmallServer.Enum.MemberEnum;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue
    @Column(name="member_key")
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

    private String zipcode;

    private String addr;

    private String addrDetail;

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
        this.zipcode = memberDto.getZipcode();
        this.addr = memberDto.getAddr();
        this.addrDetail = memberDto.getAddrDetail();
    }

    public Member() {

    }

    public static Member createMember(MemberDto memberDto) {
        return new Member(memberDto);
    }

    public void modifyMember(MemberDto memberDto) {
        this.regularMember = memberDto.getRegularMember();
        this.kakaoMember = memberDto.getKakaoMember();
        this.googleMember = memberDto.getGoogleMember();
        this.memberId = memberDto.getMemberId();
        this.memberPw = memberDto.getMemberPw();
        this.memberName = memberDto.getMemberName();
        this.memberEmail = memberDto.getMemberEmail();
        this.memberPhone = memberDto.getMemberPhone();
        this.memberGender = memberDto.getMemberGender();
        this.zipcode = memberDto.getZipcode();
        this.addr = memberDto.getAddr();
        this.addrDetail = memberDto.getAddrDetail();
    }
}
