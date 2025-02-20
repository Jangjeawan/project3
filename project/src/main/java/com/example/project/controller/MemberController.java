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

    private final MemberService memberService;
    private final ContactService contactService;

    @GetMapping("/members/signup")
    public String signupForm() {
        return "members/signup";
    }

    @PostMapping("/members/signup")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        memberService.save(memberDTO);
        return "redirect:/members/login";
    }

    @GetMapping("/members/login")
    public String loginForm() {
        return "members/login";
    }

    @PostMapping("/members/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            session.setAttribute("loginId", loginResult.getMemberId());
            return "redirect:/members/Acontacts";
        }
        return "redirect:/members/login";
    }

    @GetMapping("/members/Acontacts")
    public String getContacts(HttpSession session, Model model) {
        String loginId = (String) session.getAttribute("loginId");
        if (loginId == null) {
            return "redirect:/members/login";
        }

        List<ContactDTO> contacts = contactService.findContactsByMemberId(loginId);
        model.addAttribute("contacts", contacts);
        model.addAttribute("loginId", loginId);
        return "members/Acontacts";
    }

    @GetMapping("/members/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/members/login";
    }

    @GetMapping("/members/addContact")
    public String addContactForm(HttpSession session, Model model) {
        String loginId = (String) session.getAttribute("loginId");
        if (loginId == null) {
            return "redirect:/members/login";
        }

        model.addAttribute("contactDTO", new ContactDTO());
        model.addAttribute("loginId", loginId);
        return "members/addContact";
    }

    @PostMapping("/members/addContact")
    public String addContact(@ModelAttribute ContactDTO contactDTO, HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");
        if (loginId == null) {
            return "redirect:/members/login";
        }

        try {
            contactService.saveContact(contactDTO, loginId);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/members/addContact?error";
        }

        return "redirect:/members/Acontacts";
    }

    @GetMapping("/members/Aname")
    public String searchContactsByName(@RequestParam(required = false, defaultValue = "") String name, Model model) {
        List<ContactDTO> contacts = name.isEmpty() ? contactService.getAllContacts() : contactService.searchByName(name);
        model.addAttribute("contacts", contacts);
        return "members/Aname";
    }

    @GetMapping("/members/update/{contactId}")
    public String editContact(@PathVariable Long contactId, Model model) {
        ContactDTO contact = contactService.getContactById(contactId);
        model.addAttribute("contact", contact);
        return "members/update";
    }

    @PostMapping("/members/update/{contactId}")
    public String updateContact(@PathVariable Long contactId,
                                @ModelAttribute ContactDTO contactDTO) {
        contactService.updateContact(contactId, contactDTO);
        return "redirect:/members/Acontacts";
    }

    @GetMapping("/members/deleteContact")
    public String deleteContact(@RequestParam Long contactId) {
        contactService.deleteContact(contactId);
        return "redirect:/members/Acontacts";  // 삭제 후 연락처 목록으로 리디렉션
    }

}
