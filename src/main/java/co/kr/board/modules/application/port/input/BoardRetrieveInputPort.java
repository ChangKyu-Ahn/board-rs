package co.kr.board.modules.application.port.input;

import co.kr.board.modules.application.usecase.BoardRetrieveUsecase;
import co.kr.board.modules.domain.entity.Board;
import co.kr.board.modules.domain.mapper.BoardMapper;
import co.kr.board.modules.framework.input.rest.dto.BoardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardRetrieveInputPort extends AbstractBoardInputPort implements BoardRetrieveUsecase {

	@Override
	public BoardResponse detail(Long boardId) {
		Board board = boardManagementOutputPort.retrieve(boardId);
		return BoardMapper.domainToResponseDto(board);
	}
}
