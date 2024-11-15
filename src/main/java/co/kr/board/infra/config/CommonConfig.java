package co.kr.board.infra.config;

import co.kr.common.config.CommonConfigurer;
import co.kr.common.strategy.CustomPhysicalNamingStrategy;
import co.kr.common.wrapper.ResponseWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig extends CommonConfigurer {
	@Bean
	public ObjectMapper objectMapper() {
		return customObjectMapper();
	}

	@Bean
	public ResponseWrapper responseWrapper(ObjectMapper objectMapper) {
		return new ResponseWrapper(objectMapper);
	}

	@Bean
	public CustomPhysicalNamingStrategy physicalNamingStrategy() {
		return new CustomPhysicalNamingStrategy();
	}
}
