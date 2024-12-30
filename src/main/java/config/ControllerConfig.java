package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import controller.RegisterController;
import spring.MemberRegisterService;

/**
 * class         : ControllerConfig
 * date          : 24-12-08
 * description   : 컨트롤러를 스프링 빈으로 등록하는 설정 클래스
 */
@Configuration
public class ControllerConfig {

	@Autowired
	private MemberRegisterService memberRegSvc;  // 회원 등록 서비스를 주입받는 필드


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
}