package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.configs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/register").permitAll()
                .antMatchers(HttpMethod.POST, "/api/review/saveReview/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/review/like/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/review/dislike/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/review/dislike/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/review/findByContent/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/review/findByCriteria/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/report/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/content/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/content/**").permitAll()
                .anyRequest().authenticated();
        http.cors().configurationSource(corsConfigurationSource());
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/h2-console/**"
        );
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8000","https://grupo-b-012021-frontend.herokuapp.com"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
