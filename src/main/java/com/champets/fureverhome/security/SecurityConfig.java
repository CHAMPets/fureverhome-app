package com.champets.fureverhome.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private CustomUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            request.setAttribute("errorMessage", "Access denied");
            request.getRequestDispatcher("/error").forward(request, response);
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/register","/pets", "/applications/**","/applications", "/css/**", "/js/**")
                //.antMatchers("/login", "/register", "/pets", "/css/**", "/js/**")
                .permitAll()

//
//                .antMatchers("/**").hasAnyRole("ADMIN", "USER")
//                .and()
//                .authorizeRequests()
//
//                .antMatchers("/**").hasAnyAuthority("ADMIN","USER")


                //.antMatchers("/pets/**").hasRole("USER")
                ////                .antMatchers("/**","/pets","/pets/").hasAuthority("ADMIN")
//                .antMatchers("/pets/**","applications/**").hasAuthority("USER")
//                .and()
//                .authorizeRequests()
//                .antMatchers("/pets/**").hasAnyAuthority( "USER")


                //.antMatchers("/**").hasAnyAuthority("ADMIN", "USER")
//                .antMatchers("pets/**").hasAnyAuthority( "USER")


                //.antMatchers("/pets/**").hasAuthority("ADMIN")

                //.antMatchers("/pets/home").hasAuthority("USER")
                .and()
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/login", false)
                        .successHandler((request, response, authentication) -> {
                            if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ADMIN"))) {
                                response.sendRedirect("/pets");
                            }
                            else if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("USER"))) {
                                response.sendRedirect("/pets/home");
                            }
                        })
                        .loginProcessingUrl("/login")
                        .failureUrl("/login?error=true")
                        .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
                );

        return http.build();
    }
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}