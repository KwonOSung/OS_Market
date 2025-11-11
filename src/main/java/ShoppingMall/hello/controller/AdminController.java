package ShoppingMall.hello.controller;


import ShoppingMall.hello.Service.AdminMemberService;
import ShoppingMall.hello.entity.Member;
import ShoppingMall.hello.enums.Role;
import ShoppingMall.hello.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminMemberService memberService;

    @GetMapping("/admin")
        public String adminHome() {
            return "admin/adminHome";
        }

    @GetMapping("/admin/product/add")          // ← 기존 링크 유지
    public String redirectToProductAdd() {     // 비즈니스 로직 X
        return "redirect:/admin/products/add"; // 진짜 폼 URL로 리다이렉트
    }

    @GetMapping("/admin/product/list")          // ← 기존 링크 유지
    public String redirectToProductList() {     // 비즈니스 로직 X
        return "redirect:/admin/products/list"; // 진짜 폼 URL로 리다이렉트
    }





    @GetMapping("/admin/members")
    public String list(Model m) {
        m.addAttribute("members", memberService.getMembers());
        return "admin/members";   // ↓ HTML 예시 참조
    }



    @GetMapping("/admin/members/{id}")
    public String detail(@PathVariable String id,
                         Model model,
                         RedirectAttributes rttr) {

        return memberService.getMemberOpt(id)          // Optional<Member>
                .map(mem -> {                          // 존재하면
                    model.addAttribute("mem", mem);
                    return "admin/membersDetail";      // templates/admin/member/detail.html
                })
                .orElseGet(() -> {                     // 없으면
                    rttr.addFlashAttribute("error", "존재하지 않는 회원입니다.");
                    return "redirect:/admin/members";
                });
    }

    /* 권한 변경 */
    @PostMapping("/admin/members/{id}/role")
    public String changeRole(
            @PathVariable String id,
            @RequestParam String role) {
        Role roleEnum = Role.valueOf(role.toUpperCase());
        memberService.updateRole(id, roleEnum);
        return "redirect:/admin/members";
    }

    /* 상태 변경 */
    @PostMapping("/admin/members/{id}/status")
    public String changeStatus(
            @PathVariable String id,
            @RequestParam String status) {
        Status statusEnum = Status.valueOf(status.toUpperCase());
        memberService.updateStatus(id, statusEnum);
        return "redirect:/admin/members";
    }


}
