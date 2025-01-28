package epam.com.gymapplication.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;




@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationProvider customAuthenticationProvider;



    public SecurityConfig(CustomUserDetailsService customUserDetailsService,
                          CustomAuthenticationProvider customAuthenticationProvider) {
        this.customUserDetailsService = customUserDetailsService;
        this.customAuthenticationProvider = customAuthenticationProvider;

    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }



    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/trainees/registration")
                                .permitAll()
                                .requestMatchers("/api/trainers/registration")
                                .permitAll()
                                .requestMatchers("/api/session/trainers/trainings/planned")
                                .permitAll()
                                .requestMatchers("/api/session/trainers/trainings/cancelled")
                                .permitAll()
                                .requestMatchers("/sendMessage")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(httpSecurityFormLoginConfigurer ->
                {

                        httpSecurityFormLoginConfigurer.loginPage("/login")
                                .loginProcessingUrl("/auth/user")
                                .defaultSuccessUrl("/home")
                                .failureUrl("/login?error")
                                .permitAll();

                })
                .logout(httpSecurityLogoutConfigurer -> {
                    httpSecurityLogoutConfigurer
                            .logoutSuccessUrl("/login?logout")
                            .permitAll();
                })
                .userDetailsService(customUserDetailsService)
                .exceptionHandling(exh -> exh.authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
                        }
                ));;

        return http.build();
    }




    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

















}
