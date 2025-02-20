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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTACTID")
    private Long contactId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONENUMBER", unique = true)
    private String phoneNumber;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "MOIMID")
    private int moimId;

    @Column(name = "REGDT")
    private LocalDateTime regDt;

    public static ContactEntity toContactEntity(ContactDTO contactDTO){
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setName(contactDTO.getName());
        contactEntity.setPhoneNumber(contactDTO.getPhoneNumber());
        contactEntity.setAddress(contactDTO.getAddress());
        contactEntity.setMoimId(contactDTO.getMoimId()); // moimName을 moimId로 저장
        contactEntity.setRegDt(contactDTO.getRegDt());
        return contactEntity;
    }
}
