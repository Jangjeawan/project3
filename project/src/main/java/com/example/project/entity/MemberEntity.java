package com.example.project.entity;

import com.example.project.dto.MemberDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Setter
@Getter
@Table(name = "members") // 데이터베이스에 해당 이름의 테이블과 매핑
public class MemberEntity {

    @Id
    @Column(name = "MEMBERID", unique = true, nullable = false)
    private String memberId;

    @Column(name = "MEMBERPASSWORD", nullable = false)
    private String memberPassword;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberId(memberDTO.getMemberId());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        return memberEntity;
    }
}
