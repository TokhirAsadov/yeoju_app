package uz.yeoju.yeoju_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.yeoju.yeoju_app.secret.JwtFilter;
import uz.yeoju.yeoju_app.service.useServices.authService.AuthService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        prePostEnabled = true,
        jsr250Enabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthService authService;

    @Bean
    public JwtFilter filter(){
        return new JwtFilter();
    };

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                .antMatchers(
//                        "/v2/api-docs",
//                        "/*",
//                        "/",
//                        "/api/v1/desktop/**"
//                        "/swagger-resources",
//                        "/swagger-resources/**",
//                        "/configuration/ui",
//                        "/configuration/security",
//                        "/swagger-ui.html",
//                        "/webjars/**"
//                )
//                .permitAll()
                .antMatchers(
                        "/static/js/**",
                        "/static/css/**",
                        "/static/media/**",
                        "/assets/**",
                        "/favicon.ico",
                        "/",
                        "/login",
                        "/api/v1/desktop/**",
                        "/api/v1/desktop/auth/**",
                        "/api/v1/desktop/user/me",
                        "/api/v1/desktop/attachment/download/**",
                        "/admin/**",
                        "/super/**",
                        "/rektor/**",
                        "/dekan/**",
                        "/boshqarma/**",
                        "/deputydean/**",
                        "/student/**",
                        "/uquv/**",
                        "/bulim/**",
                        "/staff/**",
                        "/teacher/**",
                        "/file/services/reference/**",
                        "/kafedra/**"
                )
                .permitAll()
//                .antMatchers(
//                        "/teacher/**"
//                ).hasAuthority("TEACHER")
//                .antMatchers(
//                        "/kafedra/**"
//                ).hasAuthority("KAFEDRA")
//                .antMatchers(
//                        "/api/v1/desktop/groupConnect/subjectsOfTeacher/**"
//                ).hasAuthority("TEACHER")
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(filter(), UsernamePasswordAuthenticationFilter.class);
    }
}
