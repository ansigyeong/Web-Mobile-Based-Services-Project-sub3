package com.web.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    JwtTokenProvider jwtToken;

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**","/resources/**");
    }
    // 암호화에 필요한 PasswordEncoder 를 Bean 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // authenticationManager를 Bean 등록
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .httpBasic().disable()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션 사용 하지 않음
        .and()
        .authorizeRequests() //요청에 대한 사용권한 설정
        .antMatchers("/admin/**").hasRole("ADMIN")
        .antMatchers("/user/**").hasAnyRole("USER","ADMIN")
        .antMatchers("/**").permitAll() // 그 외 누구나 접근 가능
        .and()
           .formLogin()
                .loginPage("/account/login")
                .defaultSuccessUrl("/")
                .permitAll()
            .and()
            .addFilterBefore(new JwtAuthFilter(jwtToken), UsernamePasswordAuthenticationFilter.class);
            // .logout()
            //     .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            //     .logoutSuccessUrl("/")
            //     .invalidateHttpSession(true)
            // .and()
            // .exceptionHandling()
            //     .accessDeniedPage("/account/login");
    }

    // @Override
    // public void configuration(AuthenticationManagerBuilder auth) throws Exception{
    //     auth.userDetailsService()
    // }
}