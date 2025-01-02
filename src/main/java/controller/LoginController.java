package controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import spring.AuthInfo;
import spring.AuthService;
import spring.WrongIdPasswordException;

/**
 * class         : LoginController
 * date          : 25-01-02
 * description   : 로그인 요청을 처리하는 컨트롤러 클래스.
 */

@Controller
@RequestMapping("/login")
public class LoginController {
    private AuthService authService;


    /**
     * method        : setAuthService
     * date          : 25-01-02
     * param         : AuthService authService - 설정할 인증 서비스 객체
     * return        : void
     * description   : 인증 서비스를 주입받기 위한 Setter 메서드.
     */
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    /**
     * method        : form
     * date          : 25-01-02
     * param         : LoginCommand loginCommand - 초기화된 로그인 데이터 객체
     * return        : String - 로그인 폼 뷰 이름
     * description   : GET 요청 시 로그인 폼을 제공.
     */
    @GetMapping
    public String form(LoginCommand loginCommand ) {
        return "login/loginForm";

    }
    /**
     * method        : submit
     * date          : 25-01-02
     * param         : LoginCommand loginCommand - 사용자가 입력한 로그인 데이터, Errors errors - 검증 오류 저장 객체
     * return        : String - 로그인 결과에 따른 뷰 이름
     * description   : POST 요청 시 로그인 데이터를 검증하고, 인증 성공 시 성공 뷰를, 실패 시 폼 뷰를 반환.
     */
    @PostMapping
    public String submit(LoginCommand loginCommand, Errors errors) {
        new LoginCommandValidator().validate(loginCommand, errors);
        if(errors.hasErrors()) {
            return "login/loginForm";    // 로그인 폼을 보여주기 위한 뷰
        }try{
            AuthInfo authInfo = authService.authenticate(
                     loginCommand.getEmail()
                    ,loginCommand.getPassword() ) ;
            //Todo 세션에 authInfo 저장해야 함
            return "login/loginSuccess";  // 로그인 성공 결과를 보여주기 위한 뷰
        } catch (WrongIdPasswordException e) {
            errors.reject("idPasswordNotMatching");
            return "login/loginForm";     // 로그인 폼을 보여주기 위한 뷰
        }
    }
}
