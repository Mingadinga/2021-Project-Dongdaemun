package com.dongdaemun.dongdaemun.config.auth;

import com.dongdaemun.dongdaemun.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // 1.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .headers().frameOptions().disable()//
                .and().authorizeRequests()  //
                .antMatchers("/", "h2-console/**").permitAll()//랜딩페이지만 모두가 열람 가능
                //.antMatchers("/", "/css/**", "/images/**",
                 //       "/js/**","h2-console/**").permitAll()
                //.antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated() //설정값 이외 나머지 URL~인증(로그인)된 사용자만 허용
                .and().logout().logoutSuccessUrl("/") //로그아웃하면 /로 이동
                .and().oauth2Login() //OAuth2 로그인 기능에 대한 여러 설정의 진입점
                .userInfoEndpoint() //OAuth2 로그인 성공 후 사용자 정보 가져올 때의 설정 담당
                .userService(customOAuth2UserService); //소셜 로그인 성공 시 후속 조치할 인터페이스의 구현체 등록
    }
}

/*
비로그인 ) 랜딩페이지만
*/

