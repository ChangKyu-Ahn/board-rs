package co.kr.board.modules.application.usecase;

import co.kr.board.modules.framework.input.rest.dto.BoardUpdate;
import co.kr.common.domain.vo.Identifier;

public interface BoardUpdateUsecase {
	Identifier<String> update(Long boardId, BoardUpdate boardUpdate);
}
