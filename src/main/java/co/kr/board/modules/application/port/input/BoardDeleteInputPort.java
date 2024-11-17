package co.kr.board.modules.application.port.input;

import co.kr.board.modules.application.usecase.BoardDeleteUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardDeleteInputPort extends AbstractBoardInputPort implements BoardDeleteUsecase {

	@Override
	public void delete(Long boardId) {
		boardManagementOutputPort.delete(boardId);
	}
}