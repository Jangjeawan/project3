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
@Table(name = "MEMBERCONTACTMAP")
public class MemberContactMapEntity {

    @Id
    @Column(name = "CONTACTID")
    private Long contactId;

    @Id
    @Column(name = "MEMBERID")
    private String memberId;
}