package co.kr.board.infra.config;

import co.kr.common.config.SchedulerConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulerConfig extends SchedulerConfigurer {
	//
}
