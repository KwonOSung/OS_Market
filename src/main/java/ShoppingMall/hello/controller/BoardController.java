package ShoppingMall.hello.controller;

import ShoppingMall.hello.Repository.BoardRepository;
import ShoppingMall.hello.Repository.MemberRepository;
import ShoppingMall.hello.Service.BoardService;
import ShoppingMall.hello.entity.Board;
import ShoppingMall.hello.entity.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @GetMapping("/free")
    public String BoardPage(Model model) {
        model.addAttribute("postList", boardService.getFreeBoardList());
        return "board/FreeBoard";
    }

    @PostMapping("/free")
    public String createPost(@ModelAttribute Board board,
                             Principal principal) {

        // 로그인된 사용자 이름 가져오기
        String username = principal.getName();

        // username으로 Member 조회
        Member member = memberRepository.findById(username)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        // Board에 작성자 설정
        board.setMember(member);
        board.setType("Free");
        // 저장
        boardRepository.save(board);

        // 목록 페이지로 리다이렉트
        return "redirect:/free";
    }

    // 글 상세보기
    @GetMapping("/free/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        Board post = boardService.getPostAndIncreaseView(id);
        model.addAttribute("post", post);
        return "board/PostDetail"; // 상세 페이지 html
    }

    // 글 작성 페이지
    @GetMapping("/free/new")
    public String newPostForm(Model model) {
        model.addAttribute("board", new Board());
        return "board/PostForm";
    }


}
