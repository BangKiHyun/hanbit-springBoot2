package me.bang.springbootweb.cotroller;

import org.springframework.web.bind.annotation.GetMapping;

public class AuthorityTestController {

    @GetMapping("/facebook")
    public String facebook() {
        return "facebook";
    }

    @GetMapping("/google")
    public String google() {
        return "google";
    }

    @GetMapping("/kakao")
    public String kakao() {
        return "kakao";
    }
}
