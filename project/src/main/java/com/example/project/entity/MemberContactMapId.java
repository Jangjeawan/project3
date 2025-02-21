package com.example.project.entity;

import lombok.EqualsAndHashCode;
import java.io.Serializable;

@EqualsAndHashCode // equals()와 hashCode() 메서드를 자동 생성
public class MemberContactMapId implements Serializable {
    private String memberId;
    private Long contactId;
}
