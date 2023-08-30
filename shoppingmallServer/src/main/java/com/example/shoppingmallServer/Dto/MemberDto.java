package com.example.shoppingmallServer.Dto;

import com.example.shoppingmallServer.Entity.Address;
import com.example.shoppingmallServer.Entity.Member;
import com.example.shoppingmallServer.Enum.GenderEnum;
import com.example.shoppingmallServer.Enum.MemberEnum;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDto {
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

    private Address address;
}
