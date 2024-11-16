package co.kr.board.modules.application.port.output;

import co.kr.board.modules.domain.entity.Board;

public interface BoardManagementOutputPort {
	Board persist(Board board);

	Board retrieve(Long id);

	Board update(Long id, Board board);

	void delete(Long id);
}
