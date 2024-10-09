package propensi.propensiun.abuya.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers("/resources/**").permitAll()
                        .requestMatchers("/user/add").permitAll()
                        .requestMatchers("/user/login").permitAll()
                        .requestMatchers("/user/logout").permitAll()
                        .requestMatchers("/user/ubah-password").permitAll()
                        .anyRequest().authenticated()

                )
                .formLogin(formLogin -> formLogin
                        .loginProcessingUrl("/user/login")
                        .defaultSuccessUrl("/user/home")
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login").permitAll());
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }   


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(encoder())
                .withUser("admin")
                .password(encoder().encode("Abuya"))
                .roles("Admin");
    }
    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }
}