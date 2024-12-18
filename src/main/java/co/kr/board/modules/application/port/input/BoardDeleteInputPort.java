package co.kr.board.modules.application.port.input;

import co.kr.board.modules.application.port.output.BoardManagementRedisOutputPort;
import co.kr.board.modules.application.usecase.BoardDeleteUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardDeleteInputPort extends AbstractBoardInputPort implements BoardDeleteUsecase {
	private final BoardManagementRedisOutputPort boardManagementRedisOutputPort;

	@Override
	public void delete(Long boardId) {
		boardManagementOutputPort.delete(boardId);
		deleteViewCountInfo(boardId);
	}

	@Override
	public void deleteAllByUserId(String userId) {
		boardManagementOutputPort.deleteAllByUserId(userId).forEach(this::deleteViewCountInfo);
	}

	private void deleteViewCountInfo(Long boardId) {
		boardManagementRedisOutputPort.deleteViewCountInfo(boardId);
	}
}
