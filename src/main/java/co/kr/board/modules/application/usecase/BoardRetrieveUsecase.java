package co.kr.board.modules.application.usecase;

import co.kr.board.modules.framework.input.rest.dto.BoardResponse;
import co.kr.board.modules.framework.input.rest.dto.BoardSearchDto;
import org.springframework.data.domain.Page;

public interface BoardRetrieveUsecase {
	BoardResponse detail(Long boardId);

	Page<BoardResponse> list(BoardSearchDto boardSearchDto);
}
