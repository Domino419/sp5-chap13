package config;

import controller.LogoutController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import controller.RegisterController;
import controller.LoginController;   // 250102 추기
import spring.MemberRegisterService;
import spring.AuthService; // 250102 추기

/**
 * class         : ControllerConfig
 * date          : 24-12-08
 * description   : 컨트롤러를 스프링 빈으로 등록하는 설정 클래스
 */
@Configuration
public class ControllerConfig {

	@Autowired
	private MemberRegisterService memberRegSvc;  // 회원 등록 서비스를 주입받는 필드

	@Autowired
	private AuthService authService;             // 로그인 검증을 위한 서비스를 주입받는 필드

	private static final Log log = LogFactory.getLog(RegisterController.class);  // log

	/**
	 * method        : registerController
	 * date          : 24-12-26
	 * return        : RegisterController
	 * description   : RegisterController를 스프링 빈으로 등록하며,
	 *                 MemberRegisterService를 컨트롤러에 주입합니다.
	 */
	@Bean
	public RegisterController registerController() {
		RegisterController controller = new RegisterController();
		controller.setMemberRegisterService(memberRegSvc);
		return controller;
	}

	/**
	 * method        : loginController
	 * date          : 25-01-02
	 * return        : LoginController - 로그인 컨트롤러 객체
	 * description   : LoginController를 스프링 빈으로 등록하며,
	 *                 AuthService를 컨트롤러에 주입합니다.
	 */
	@Bean
	public LoginController loginController() {
		LoginController controller = new LoginController();
		controller.setAuthService(authService);
		return controller;
	}

	/**
	 * method        : logoutController
	 * date          : 25-01-02
	 * return        : LogoutController - 로그아웃 기능을 처리하는 컨트롤러
	 * description   : 로그아웃 요청을 처리하는 LogoutController를 스프링 빈으로 등록.
	 */
	@Bean
	public LogoutController logoutController() {
		log.info(":::::::::::::::::::::::::::::::::: ControllerConfig.LogoutController ");
		return new LogoutController();
	}
}
