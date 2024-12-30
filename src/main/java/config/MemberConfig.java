package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import spring.ChangePasswordService;
import spring.MemberDao;
import spring.MemberRegisterService;

@Configuration
@EnableTransactionManagement
public class MemberConfig {

	/**
	 * method        : dataSource
	 * date          : 24-12-04
	 * return        : DataSource - 데이터베이스 연결을 관리하는 DataSource 객체
	 * description   : MySQL 데이터베이스와 연결하기 위한 DataSource 설정.
	 */
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		DataSource ds = new DataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		//ds.setUrl("jdbc:mysql://localhost/spring5fs?characterEncoding=utf8");
		ds.setUrl("jdbc:mysql://localhost/spring5fs?characterEncoding=utf8&useSSL=false&requireSSL=false");  // SSL을 사용하지 않도록 설정
		ds.setUsername("spring5");
		ds.setPassword("spring5");
		ds.setInitialSize(2);
		ds.setMaxActive(10);
		ds.setTestWhileIdle(true);
		ds.setMinEvictableIdleTimeMillis(60000 * 3);
		ds.setTimeBetweenEvictionRunsMillis(10 * 1000);
		return ds;
	}

	/**
	 * method        : transactionManager
	 * date          : 24-12-04
	 * return        : PlatformTransactionManager - 트랜잭션 관리 객체
	 * description   : 데이터 소스에 연동되는 트랜잭션 관리자를 생성 및 반환.
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		tm.setDataSource(dataSource());
		return tm;
	}

	/**
	 * method        : memberDao
	 * date          : 24-12-04
	 * return        : MemberDao - 회원 정보를 관리하는 DAO 객체
	 * description   : DataSource를 주입받아 MemberDao 객체를 생성 및 반환.
	 */
	@Bean
	public MemberDao memberDao() {
		return new MemberDao(dataSource());
	}

	/**
	 * method        : memberRegSvc
	 * date          : 24-12-07
	 * return        : MemberRegisterService
	 * description   : MemberRegisterService 객체를 빈으로 등록.
	 *                 회원 등록 로직 수행을 위해 MemberDao를 의존성으로 설정.
	 */
	@Bean
	public MemberRegisterService memberRegSvc() {
		return new MemberRegisterService(memberDao());
	}

	/**
	 * method        : changePwdSvc
	 * date          : 24-12-04
	 * return        : ChangePasswordService - 비밀번호 변경 서비스 객체
	 * description   : 비밀번호 변경 로직을 처리하는 ChangePasswordService 객체를 생성 및 반환.
	 *                  MemberDao 객체를 주입하여 회원 데이터를 관리하는 데 사용.
	 */
	@Bean
	public ChangePasswordService changePwdSvc() {
		ChangePasswordService pwdSvc = new ChangePasswordService();
		pwdSvc.setMemberDao(memberDao());
		return pwdSvc;
	}
}
