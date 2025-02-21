package com.example.project.controller;

import com.example.project.dto.ContactDTO;
import com.example.project.dto.MemberDTO;
import com.example.project.service.ContactService;
import com.example.project.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;  // 회원 관련 서비스
    private final ContactService contactService;  // 연락처 관련 서비스

    // 회원 가입 폼을 반환
    @GetMapping("/members/signup")
    public String signupForm() {
        return "members/signup";
    }

    // 회원 가입 처리
    @PostMapping("/members/signup")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        memberService.save(memberDTO);  // 회원 정보 저장
        return "redirect:/members/login";  // 회원 가입 후 로그인 페이지로 이동
    }

    // 로그인 폼을 반환
    @GetMapping("/members/login")
    public String loginForm() {
        return "members/login";
    }

    // 로그인 처리
    @PostMapping("/members/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);  // 로그인 검증
        if (loginResult != null) {
            session.setAttribute("loginId", loginResult.getMemberId());  // 로그인 성공 시 세션에 ID 저장
            return "redirect:/members/Acontacts";  // 연락처 목록 페이지로 이동
        }
        return "redirect:/members/login";  // 로그인 실패 시 로그인 페이지로 이동
    }

    // 로그인한 사용자의 연락처 목록 조회
    @GetMapping("/members/Acontacts")
    public String getContacts(HttpSession session, Model model) {
        String loginId = (String) session.getAttribute("loginId");  // 현재 로그인한 사용자 ID 가져오기
        if (loginId == null) {
            return "redirect:/members/login";  // 로그인되지 않았다면 로그인 페이지로 이동
        }

        List<ContactDTO> contacts = contactService.findContactsByMemberId(loginId);  // 사용자의 연락처 목록 조회
        model.addAttribute("contacts", contacts);
        model.addAttribute("loginId", loginId);
        return "members/Acontacts";  // 연락처 목록 페이지 반환
    }

    // 로그아웃 처리
    @GetMapping("/members/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // 세션 초기화
        return "redirect:/members/login";  // 로그인 페이지로 이동
    }

    // 연락처 추가 폼을 반환
    @GetMapping("/members/addContact")
    public String addContactForm(HttpSession session, Model model) {
        String loginId = (String) session.getAttribute("loginId");  // 로그인한 사용자 확인
        if (loginId == null) {
            return "redirect:/members/login";  // 로그인되지 않았다면 로그인 페이지로 이동
        }

        model.addAttribute("contactDTO", new ContactDTO());  // 새로운 연락처 DTO 객체 추가
        model.addAttribute("loginId", loginId);
        return "members/addContact";  // 연락처 추가 폼 페이지 반환
    }

    // 연락처 추가 처리
    @PostMapping("/members/addContact")
    public String addContact(@ModelAttribute ContactDTO contactDTO, HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");  // 로그인한 사용자 확인
        if (loginId == null) {
            return "redirect:/members/login";  // 로그인되지 않았다면 로그인 페이지로 이동
        }

        try {
            contactService.saveContact(contactDTO, loginId);  // 연락처 저장
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/members/addContact?error";  // 오류 발생 시 다시 추가 페이지로 이동
        }

        return "redirect:/members/Acontacts";  // 연락처 목록 페이지로 이동
    }

    // 이름으로 연락처 검색
    @GetMapping("/members/Aname")
    public String searchContactsByName(@RequestParam(required = false, defaultValue = "") String name, Model model) {
        List<ContactDTO> contacts = name.isEmpty() ? contactService.getAllContacts() : contactService.searchByName(name);
        // 검색어가 없으면 전체 연락처 조회, 있으면 해당 이름으로 검색
        model.addAttribute("contacts", contacts);
        return "members/Aname";  // 검색 결과 페이지 반환
    }

    // 연락처 수정 폼을 반환
    @GetMapping("/members/update/{contactId}")
    public String editContact(@PathVariable Long contactId, Model model) {
        ContactDTO contact = contactService.getContactById(contactId);  // 연락처 정보 조회
        model.addAttribute("contact", contact);
        return "members/update";  // 수정 폼 페이지 반환
    }

    // 연락처 수정 처리
    @PostMapping("/members/update/{contactId}")
    public String updateContact(@PathVariable Long contactId,
                                @ModelAttribute ContactDTO contactDTO) {
        contactService.updateContact(contactId, contactDTO);  // 연락처 정보 업데이트
        return "redirect:/members/Acontacts";  // 연락처 목록 페이지로 이동
    }

    // 연락처 삭제 처리
    @GetMapping("/members/deleteContact")
    public String deleteContact(@RequestParam Long contactId) {
        contactService.deleteContact(contactId);  // 연락처 삭제
        return "redirect:/members/Acontacts";  // 삭제 후 연락처 목록 페이지로 이동
    }
}
