package ShoppingMall.hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MypageController {

    @GetMapping("/mypage")
    public String myPageHome() {
        return "mypage/myhome";
    }



    @GetMapping("/mypage/modify")
    public String myPageModify() {
        return "mypage/myinfo";
    }

    @GetMapping("/mypage/delete")
    public String myPageDelete() {
        return "mypage/mydel";
    }
}
