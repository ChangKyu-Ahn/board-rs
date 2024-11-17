package co.kr.board.infra.config;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;

import co.kr.common.code.UserType;
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
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
				authorize
					.requestMatchers(AntPathRequestMatcher.antMatcher("/v{^[\\d]$}/**")).access(hasRole(UserType.CUSTOMER.getCode()))
					.anyRequest().hasRole(UserType.SYS_ADMIN.getCode())
			)
			.headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin))
			.addFilterBefore(new JWTRequestFilter(jwtUserDetailsService, excludeTargetArray()), UsernamePasswordAuthenticationFilter.class)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.requestCache(RequestCacheConfigurer::disable)
			.httpBasic(Customizer.withDefaults());

		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);

		return http.build();
	}

	private String[] excludeTargetArray() {
		return new String[]{};
	}
}
