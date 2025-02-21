package com.example.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(MemberContactMapEntity.class)
@Table(name = "MEMBERCONTACTMAP") // 실제 DB 테이블 이름과 매핑
public class MemberContactMapEntity {

    @Id
    @Column(name = "CONTACTID") // 연락처 ID (복합 키)
    private Long contactId;

    @Id
    @Column(name = "MEMBERID") // 회원 ID (복합 키)
    private String memberId;
}
