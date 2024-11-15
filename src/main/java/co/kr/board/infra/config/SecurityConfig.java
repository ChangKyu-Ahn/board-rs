package co.kr.board.infra.config;

import co.kr.common.security.jwt.JWTRequestFilter;
import co.kr.common.security.jwt.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Import(JwtUserDetailsService.class)
public class SecurityConfig {
	private final JwtUserDetailsService jwtUserDetailsService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(authorize ->
				authorize.requestMatchers(excludeTargetArray())
					.permitAll()
					.anyRequest()
					.authenticated()
			)
			.headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin))
			.addFilterBefore(new JWTRequestFilter(jwtUserDetailsService, excludeTargetArray()), UsernamePasswordAuthenticationFilter.class)
			.httpBasic(Customizer.withDefaults());

		return http.build();
	}

	private String[] excludeTargetArray() {
		return new String[]{
				"/h2-console/**",
				"/users",
				"/users/login"
		};
	}
}
