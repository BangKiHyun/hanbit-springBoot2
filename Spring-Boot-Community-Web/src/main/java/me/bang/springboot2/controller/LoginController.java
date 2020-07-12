package me.bang.springboot2.controller;

import me.bang.springboot2.annotation.SocialUser;
import me.bang.springboot2.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/{facebook|google|kakao}complete")
    public String loginComplete(@SocialUser User user) {
        return "redirect:/board/list";
    }
}
