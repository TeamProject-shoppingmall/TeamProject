package com.example.shoppingmallServer.Dto;

import com.example.shoppingmallServer.Enum.GenderEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDto {
    private int memberKey; //자동 생성

    private String memberId;

    private String memberPw;

    private String memberName;

    private String memberEmail;

    private String memberPhone;

    private GenderEnum memberGender;

    private String zipcode;

    private String addr;

    private String addrDetail;
}
