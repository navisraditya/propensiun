package propensi.propensiun.abuya.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                            .requestMatchers("/").permitAll()
                            .requestMatchers("/css/**").permitAll()
                            .requestMatchers("/js/**").permitAll()
                            .requestMatchers("/resources/**").permitAll()
                            .requestMatchers("/images/**").permitAll()
                            .requestMatchers("/styles/**").permitAll()
                            // below, dev purpose onlz
                            .requestMatchers(HttpMethod.GET, "/user/addMember").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/addMember").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/mitra/add").permitAll()
                                    .requestMatchers(HttpMethod.POST, "/mitra/add").permitAll()
                                    .requestMatchers("/mitra/addMitra").permitAll()
                            .requestMatchers("/user/logout").permitAll()
                            .requestMatchers("/user/ubah-password").permitAll()
//                        .requestMatchers("/user/user-view-by-admin").hasRole("Admin")
//                        .requestMatchers("/user/store-manager").hasRole("Store Manager")
                            
                            // Feedback
                            // Anonymous dapat mengakses
                            .requestMatchers("/user/form-add-feedback").permitAll()
                            .requestMatchers(HttpMethod.POST, "/user/form-add-feedback").permitAll()

                            .anyRequest().authenticated()
                                            )
                    .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/user/form-add-feedback") // Nonaktifkan CSRF untuk endpoint ini jika diperlukan
                    )

                    .formLogin(formLogin -> formLogin
                            .loginProcessingUrl("/user/login")
                            .defaultSuccessUrl("/redirectHomepage")
                            .permitAll())
                    .logout(logout -> logout
                            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                            .logoutSuccessUrl("/redirectHomepage").permitAll());
//                .exceptionHandling(exceptionHandling -> exceptionHandling
//                        .accessDeniedPage("/access-denied")
//                );
            return http.build();
        }

        @Bean
        public BCryptPasswordEncoder encoder() {
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
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }
}
