package com.example.project.service;

import com.example.project.dto.MemberDTO;
import com.example.project.entity.MemberEntity;
import com.example.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service // 스프링이 관리하는 서비스 빈(Service Bean)으로 등록
@RequiredArgsConstructor // final 멤버 변수를 매개변수로 하는 생성자를 자동으로 생성
public class MemberService {

    private final MemberRepository memberRepository; // JPA를 사용하여 DB와 연결하는 Repository

    public void save(MemberDTO memberDTO) {
        // DTO를 Entity로 변환하여 저장
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

    public MemberDTO login(MemberDTO memberDTO) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberId(memberDTO.getMemberId());

        if (byMemberEmail.isPresent()) {
            // 조회된 회원 정보가 존재할 경우
            MemberEntity memberEntity = byMemberEmail.get(); // Optional에서 객체 꺼내기

            // 입력한 비밀번호와 DB에 저장된 비밀번호 비교
            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                // 비밀번호가 일치하면 Entity를 DTO로 변환하여 반환
                return MemberDTO.toMemberDTO(memberEntity);
            } else {
                // 비밀번호 불일치
                return null;
            }
        } else {
            // 회원 정보가 존재하지 않는 경우
            return null;
        }
    }
}
