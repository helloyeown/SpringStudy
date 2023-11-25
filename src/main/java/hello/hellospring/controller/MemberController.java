package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService service;

    @Autowired
    public MemberController(MemberService service) {
        this.service = service;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";    // 이동할 url 주소
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        System.out.println("member = " + member.getName());

        service.join(member);

        return "redirect:/";    // home으로 돌려보냄
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = service.findMembers();
        model.addAttribute("members", members);

        return "members/memberList";
    }
}
