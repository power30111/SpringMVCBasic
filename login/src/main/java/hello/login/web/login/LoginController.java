package hello.login.web.login;


import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form){
        return "login/loginForm";
    }
    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response){
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if(loginMember == null){
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }


        //쿠키에 시간 정보를 주지않으면 브라우져 종료시 쿠키삭제 (key,value)와 같은 느낌으로 쿠키에 담는듯
        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        response.addCookie(idCookie);


        return "redirect:/";
    }
    @PostMapping("/logout")
    public String logout(HttpServletResponse response){
        expireCookie(response,"memberId");
        return "redirect:/";
    }

    private static void expireCookie(HttpServletResponse response,String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}