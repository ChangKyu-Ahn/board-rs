package co.kr.board.modules.application.port.input;

import co.kr.board.modules.application.port.output.BoardManagementRedisOutputPort;
import co.kr.board.modules.application.usecase.BoardRetrieveUsecase;
import co.kr.board.modules.domain.entity.Board;
import co.kr.board.modules.domain.mapper.BoardMapper;
import co.kr.board.modules.framework.input.rest.dto.BoardResponse;
import co.kr.board.modules.framework.input.rest.dto.BoardSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardRetrieveInputPort extends AbstractBoardInputPort implements BoardRetrieveUsecase {
	private final BoardManagementRedisOutputPort boardManagementRedisOutputPort;

	@Override
	public BoardResponse detail(Long boardId) {
		Board board = boardManagementOutputPort.retrieve(boardId);
		boardManagementRedisOutputPort.incrementViewCount(boardId);
		return BoardMapper.domainToResponseDto(board);
	}

	@Override
	public Page<BoardResponse> list(BoardSearchDto boardSearchDto) {
		return boardManagementOutputPort.retrieveAll(boardSearchDto);
	}
}
