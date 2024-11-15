package co.kr.board.modules.application.usecase;

import co.kr.board.modules.framework.input.rest.dto.BoardCreate;
import co.kr.common.domain.vo.Identifier;

public interface BoardCreateUsecase {
	Identifier<String> create(BoardCreate userCreate);
}
