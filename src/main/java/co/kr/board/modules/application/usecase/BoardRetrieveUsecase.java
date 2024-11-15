package co.kr.board.modules.application.usecase;

import co.kr.board.modules.framework.input.rest.dto.BoardResponse;

public interface BoardRetrieveUsecase {
	BoardResponse detail(Long boardId);
}
