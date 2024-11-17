package co.kr.board.modules.application.port.output;

import co.kr.board.modules.domain.entity.Board;
import java.util.List;

public interface BoardManagementRedisOutputPort {
	void incrementViewCount(Long boardId);
	void deleteViewCountInfo(Long boardId);

	List<Board> getBoardRedisDataList();
}
