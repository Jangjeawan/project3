package com.example.project.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class MemberContactMapId implements Serializable {
    private String memberId;
    private Long contactId;
}