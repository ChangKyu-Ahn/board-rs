package co.kr.board.modules.application.port.input;

import co.kr.board.modules.application.port.output.BoardManagementRedisOutputPort;
import co.kr.board.modules.application.usecase.BoardUpdateUsecase;
import co.kr.board.modules.domain.entity.Board;
import co.kr.board.modules.domain.mapper.BoardMapper;
import co.kr.board.modules.framework.input.rest.dto.BoardUpdate;
import co.kr.common.domain.vo.Identifier;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardUpdateInputPort extends AbstractBoardInputPort implements BoardUpdateUsecase {
	private final BoardManagementRedisOutputPort boardManagementRedisOutputPort;
	private final TaskExecutor boardViewUpdateTaskExecutor;

	@Override
	public Identifier<Long> update(Long boardId, BoardUpdate boardUpdate) {
		Board board = //
			boardManagementOutputPort.update(
				boardId,
				BoardMapper.updateDtoToDomain(boardUpdate)
			);

		return BoardMapper.toBoardIdResponse(board);
	}

	@Override
	public void updateViewCount() {
 		List<Board> boardList = boardManagementRedisOutputPort.getBoardRedisDataList();

		if (CollectionUtils.isEmpty(boardList)) {
			return;
		}

		ListUtils.partition(boardList, 1000)
			.forEach(partitionBoardList ->
				CompletableFuture.runAsync(() -> boardManagementOutputPort.updateViewCount(partitionBoardList), boardViewUpdateTaskExecutor)
			);
	}
}
