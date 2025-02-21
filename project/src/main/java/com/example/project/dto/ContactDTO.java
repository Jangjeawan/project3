package com.example.project.dto;

import com.example.project.entity.ContactEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 연락처 정보를 저장하는 DTO 클래스
 */
@Getter
@Setter
@NoArgsConstructor
public class ContactDTO {
    private Long contactId;    // 연락처 ID
    private String name;       // 연락처 이름
    private String phoneNumber; // 연락처 전화번호
    private String address;    // 연락처 주소
    private int moimId;        // 그룹 ID
    private String moimName;   // 그룹 이름 (moimId를 기반으로 설정)
    private LocalDateTime regDt; // 등록 날짜 및 시간

    public static ContactDTO toContactDTO(ContactEntity contactEntity){
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setContactId(contactEntity.getContactId());
        contactDTO.setName(contactEntity.getName());
        contactDTO.setPhoneNumber(contactEntity.getPhoneNumber());
        contactDTO.setAddress(contactEntity.getAddress());
        contactDTO.setMoimId(contactEntity.getMoimId());
        // moimId에 맞는 moimName 설정
        contactDTO.setMoimName(mapMoimIdToName(contactEntity.getMoimId()));
        contactDTO.setRegDt(contactEntity.getRegDt());
        return contactDTO;
    }

    private static String mapMoimIdToName(int moimId) {
        switch (moimId) {
            case 1:
                return "가족";
            case 2:
                return "친구";
            case 3:
                return "회사";
            default:
                return "기타";
        }
    }
}
