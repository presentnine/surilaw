package ga.surilaw.common.config;

import ga.surilaw.service.auth.JwtAuthenticationFilter;
import ga.surilaw.service.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *웹 설정
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() //api 서버이므로 해제
                .csrf().disable() //api 서버이므로 csrf 보안 해제
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //jwt를 통해 인증을 처리하기 때문에 세션 생성 비활성화
                .and()
                    .authorizeRequests()
                    .antMatchers("/api/auth/**").permitAll() //가입과 인증 주소에 대해 모두
                    .anyRequest().hasRole("USER") //이외는 인증된 회원 (아직은)
                .and()
                    .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean //passwordEncoder 지정
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
