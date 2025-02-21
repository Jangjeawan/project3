package com.example.project.dto;

import com.example.project.entity.MemberEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberDTO {
    private String memberId;       // 회원 ID
    private String memberPassword; // 회원 비밀번호

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId(memberEntity.getMemberId()); // 엔티티의 ID를 DTO로 설정
        memberDTO.setMemberPassword(memberEntity.getMemberPassword()); // 엔티티의 비밀번호를 DTO로 설정
        return memberDTO;
    }
}
