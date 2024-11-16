package co.kr.board.modules.application.port.input;

import co.kr.board.modules.application.usecase.BoardCreateUsecase;
import co.kr.board.modules.domain.entity.Board;
import co.kr.board.modules.domain.mapper.BoardMapper;
import co.kr.board.modules.framework.input.rest.dto.BoardCreate;
import co.kr.common.domain.vo.Identifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardCreateInputPort extends AbstractBoardInputPort implements BoardCreateUsecase {

	@Override
	public Identifier<Long> create(BoardCreate boardCreate) {
		Board board = BoardMapper.createDtoToDomain(boardCreate);
		Board persistedBoard = boardManagementOutputPort.persist(board);

		return BoardMapper.toBoardIdResponse(persistedBoard);
	}
}
