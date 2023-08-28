package com.example.shoppingmallServer.Entity;

import com.example.shoppingmallServer.Enum.GenderEnum;
import com.example.shoppingmallServer.Enum.MemberEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

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
}
