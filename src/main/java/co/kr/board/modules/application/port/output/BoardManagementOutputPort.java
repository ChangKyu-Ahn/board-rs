package co.kr.board.modules.application.port.output;

import co.kr.board.modules.domain.entity.Board;
import co.kr.board.modules.framework.input.rest.dto.BoardResponse;
import co.kr.board.modules.framework.input.rest.dto.BoardSearchDto;
import org.springframework.data.domain.Page;

public interface BoardManagementOutputPort {
	Board persist(Board board);

	Board retrieve(Long id);

	Page<BoardResponse> retrieveAll(BoardSearchDto searchDto);

	Board update(Long id, Board board);

	void delete(Long id);
}
