package co.kr.board.modules.application.port.input;

import co.kr.board.modules.application.usecase.BoardUpdateUsecase;
import co.kr.board.modules.domain.entity.Board;
import co.kr.board.modules.domain.mapper.BoardMapper;
import co.kr.board.modules.framework.input.rest.dto.BoardUpdate;
import co.kr.common.domain.vo.Identifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardUpdateInputPort extends AbstractBoardInputPort implements BoardUpdateUsecase {

	@Override
	public Identifier<Long> update(Long boardId, BoardUpdate boardUpdate) {
		Board board = //
			boardManagementOutputPort.update(
				boardId,
				BoardMapper.updateDtoToDomain(boardUpdate)
			);

		return BoardMapper.toBoardIdResponse(board);
	}
}
