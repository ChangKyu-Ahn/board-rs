package co.kr.board.modules.framework.output.redis;

import static co.kr.board.infra.constant.Constant.VIEW_COUNT_REDIS_PREFIX_KEY;

import co.kr.board.modules.application.port.output.BoardManagementRedisOutputPort;
import co.kr.board.modules.domain.entity.Board;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class BoardManagementRedisAdapter implements BoardManagementRedisOutputPort {

	private final StringRedisTemplate stringRedisTemplate;

	@Async
	@Override
	public void incrementViewCount(Long boardId) {
		String key = VIEW_COUNT_REDIS_PREFIX_KEY + boardId;
		stringRedisTemplate.opsForValue().increment(key);
		stringRedisTemplate.expire(key, Duration.ofDays(1));
	}

	@Override
	public List<Board> getBoardRedisDataList() {
		List<String> keys = stringRedisTemplate.keys(VIEW_COUNT_REDIS_PREFIX_KEY + "*").stream().toList();

		if (CollectionUtils.isEmpty(keys)) {
			return Collections.emptyList();
		}

		List<Object> values = getValues(keys);

		return IntStream.range(0, keys.size())
			.filter(i -> Objects.nonNull(values.get(i)))
			.mapToObj(i -> {
				String key = keys.get(i);
				String value = (String) values.get(i);

				return Optional.ofNullable(extractBoardId(key))
					.map(boardId -> createBoardRedisData(boardId, value))
					.orElse(null);
			})
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
	}

	private List<Object> getValues(List<String> keys) {
		return stringRedisTemplate.executePipelined(
			(RedisCallback<Object>) connection -> {
				keys.forEach(key -> connection.stringCommands().get(Objects.requireNonNull(stringRedisTemplate.getStringSerializer().serialize(key))));
				return null;
			});
	}
	private Long extractBoardId(String key) {
		String boardId = StringUtils.defaultString(key).replace(VIEW_COUNT_REDIS_PREFIX_KEY, StringUtils.EMPTY);

		if (StringUtils.isBlank(boardId)) {
			return null;
		}

		return Long.parseLong(boardId);
	}

	private Board createBoardRedisData(Long boardId, String viewCount) {
		return Board.builder()
			.id(boardId)
			.viewCount(Long.parseLong(viewCount))
			.build();
	}
}
