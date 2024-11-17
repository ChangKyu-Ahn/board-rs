package co.kr.board.modules.framework.input.scheduler;

import co.kr.board.modules.application.usecase.BoardUpdateUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BoardViewCountManagementSchedulerAdapter {
	private final BoardUpdateUsecase boardUpdateUsecase;

	@Scheduled(cron = "${scheduled.cron.expression.board.count}") // 10분 단위
	public void updateViewCount() {
		log.debug("#### BOARD UPDATE VIEW COUNT START");

		boardUpdateUsecase.updateViewCount();

		log.debug("#### BOARD UPDATE VIEW COUNT END");
	}
}
