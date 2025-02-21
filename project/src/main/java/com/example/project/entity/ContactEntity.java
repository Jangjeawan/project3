package com.example.project.entity;

import com.example.project.dto.ContactDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "MEMBERSCONTACTS")
public class ContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정
    @Column(name = "CONTACTID")
    private Long contactId; // 연락처 ID

    @Column(name = "NAME")
    private String name; // 연락처 이름

    @Column(name = "PHONENUMBER", unique = true)
    private String phoneNumber; // 연락처의 전화번호

    @Column(name = "ADDRESS")
    private String address; // 주소 정보

    @Column(name = "MOIMID")
    private int moimId; // 그룹 ID

    @Column(name = "REGDT")
    private LocalDateTime regDt; // 등록 날짜


    public static ContactEntity toContactEntity(ContactDTO contactDTO) {
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setName(contactDTO.getName()); // 이름 설정
        contactEntity.setPhoneNumber(contactDTO.getPhoneNumber()); // 전화번호 설정
        contactEntity.setAddress(contactDTO.getAddress()); // 주소 설정
        contactEntity.setMoimId(contactDTO.getMoimId()); // 그룹 ID 설정
        contactEntity.setRegDt(contactDTO.getRegDt()); // 등록 날짜 설정
        return contactEntity;
    }
}
