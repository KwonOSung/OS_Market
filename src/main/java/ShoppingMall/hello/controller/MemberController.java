package ShoppingMall.hello.controller;


import ShoppingMall.hello.DTO.MemberDTO;

import ShoppingMall.hello.Service.ProductService;
import ShoppingMall.hello.Service.memberService;
import ShoppingMall.hello.entity.Product;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final memberService memberService;
    private final ProductService productService;

    @GetMapping("/")
    public String home(Model model) {
        List<Product> products = productService.getRecentProducts();

        model.addAttribute("products", products);
        return "MainPage";  //메인 페이지로 이동
    }

    @GetMapping("/insertMember")
    public String showForm(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        return "insertMember"; // resources/templates/insertMember.html
    }


    @PostMapping("/register")
    public String registerMember (
            @ModelAttribute("memberDTO") MemberDTO memberDTO,
            Model model) {
        try {
            memberService.registerMember(memberDTO);
            model.addAttribute("message", "회원가입 성공!");
            return "success"; // success.html
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "회원가입 실패: " + e.getMessage());
            return "insertMember"; // 다시 폼으로
        }
    }
    @GetMapping("/login")
    public String login() {
        return "loginPage";
    }

    @GetMapping("/member/edit")
    public String showEditForm(Model model, Principal principal) {
        String userId = principal.getName();
        MemberDTO memberDTO = memberService.getMemberById(userId); // 기존 서비스에서 DTO 반환
        model.addAttribute("memberDTO", memberDTO);
        return "mypage/myinfo";
    }

    @PostMapping("/member/edit")
    public String updateMember(@ModelAttribute("memberDTO") MemberDTO memberDTO,
                               Principal principal,
                               Model model) {
        String userId = principal.getName();
        try {
            memberService.updateMember(userId, memberDTO);
            model.addAttribute("message", "회원정보가 성공적으로 업데이트되었습니다.");
            return "member/editSuccess"; // 처리 후 성공 페이지
        } catch (Exception e) {
            model.addAttribute("message", "회원정보 수정 실패: " + e.getMessage());
            return "mypage/myinfo"; // 다시 폼으로
        }
    }

    @GetMapping("/member/password")
    public String showPasswordForm() {
        return "mypage/myPwd"; // resources/templates/member/passwordForm.html
    }

    // 비밀번호 변경 처리
    @PostMapping("/member/password")
    public String updatePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 Principal principal,
                                 Model model) {
        String userId = principal.getName();
        try {
            memberService.changePassword(userId, currentPassword, newPassword);
            model.addAttribute("message", "비밀번호가 성공적으로 변경되었습니다.");
            return "member/passwordSuccess"; // 성공 페이지
        } catch (IllegalArgumentException e) {
            model.addAttribute("message", "비밀번호 변경 실패: " + e.getMessage());
            return "mypage/myPwd"; // 다시 폼으로
        }
    }

    @PostMapping("/member/delete")
    public String deleteMember(Principal principal, HttpSession session) {
        String userId = principal.getName();
        memberService.deleteMember(userId);
        session.invalidate();  // 로그인 세션 종료
        return "redirect:/";    // 탈퇴 후 메인페이지로 이동
    }
}




