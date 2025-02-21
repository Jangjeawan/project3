package com.example.project.service;

import com.example.project.dto.ContactDTO;
import com.example.project.entity.ContactEntity;
import com.example.project.entity.MemberEntity;
import com.example.project.repository.ContactRepository;
import com.example.project.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final MemberRepository memberRepository;

    public List<ContactDTO> findContactsByMemberId(String memberId) {
        List<ContactEntity> contacts = contactRepository.findByMemberId(memberId);
        return contacts.stream().map(ContactDTO::toContactDTO).collect(Collectors.toList());
    }

    @Transactional
    public void saveContact(ContactDTO contactDTO, String loginId) throws IOException {
        // 1. 로그인한 회원 정보 조회
        MemberEntity memberEntity = memberRepository.findByMemberId(loginId).orElse(null);
        if (memberEntity == null) {
            throw new RuntimeException("회원 정보를 찾을 수 없습니다.");
        }

        // 2. 연락처 엔티티 생성 및 저장
        ContactEntity contactEntity = ContactEntity.toContactEntity(contactDTO);
        contactEntity.setRegDt(LocalDateTime.now());  // 등록일 설정
        contactRepository.save(contactEntity);

        // 3. 회원과 연락처를 매핑 (MEMBERCONTACTMAP 테이블에 저장)
        contactRepository.mapMemberToContact(memberEntity.getMemberId(), contactEntity.getContactId());
    }

    public List<ContactDTO> searchByName(String name) {
        List<ContactEntity> contactEntities = contactRepository.findByNameContaining(name);
        return contactEntities.stream()
                .map(ContactDTO::toContactDTO)  // ContactEntity를 ContactDTO로 변환
                .collect(Collectors.toList());
    }

    public List<ContactDTO> getAllContacts() {
        List<ContactEntity> contactEntities = contactRepository.findAll();
        return contactEntities.stream()
                .map(ContactDTO::toContactDTO)
                .collect(Collectors.toList());
    }

    public ContactDTO getContactById(Long contactId) {
        return contactRepository.findById(contactId)
                .map(ContactDTO::toContactDTO)
                .orElseThrow(() -> new RuntimeException("연락처를 찾을 수 없습니다."));
    }

    @Transactional
    public ContactDTO updateContact(Long contactId, ContactDTO contactDTO) {
        // 1. 기존 연락처 정보 조회
        ContactEntity contactEntity = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("연락처를 찾을 수 없습니다."));

        // 2. 정보 업데이트
        contactEntity.setName(contactDTO.getName());
        contactEntity.setPhoneNumber(contactDTO.getPhoneNumber());
        contactEntity.setAddress(contactDTO.getAddress());
        contactEntity.setMoimId(contactDTO.getMoimId());
        contactEntity.setRegDt(LocalDateTime.now());

        // 3. 변경된 정보 저장
        contactRepository.save(contactEntity);
        return ContactDTO.toContactDTO(contactEntity);
    }

    public void deleteContact(Long contactId) {
        contactRepository.deleteById(contactId);
    }
}
