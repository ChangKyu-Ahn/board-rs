package co.kr.board.infra.config;

import co.kr.common.config.CacheConfigurer;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class RedisConfig extends CacheConfigurer {

	public RedisConfig(RedisProperties redisProperties) {
		super(redisProperties);
	}
}
