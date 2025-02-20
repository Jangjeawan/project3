package com.example.project.dto;

import com.example.project.entity.ContactEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ContactDTO {
    private Long contactId;
    private String name;
    private String phoneNumber;
    private String address;
    private int moimId;
    private String moimName;  // 여기서 그룹 이름을 저장
    private LocalDateTime regDt;

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
