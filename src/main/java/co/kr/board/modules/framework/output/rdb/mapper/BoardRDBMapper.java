package co.kr.board.modules.framework.output.rdb.mapper;

import co.kr.board.modules.domain.entity.Board;
import co.kr.board.modules.framework.output.rdb.data.BoardData;

public class BoardRDBMapper {
	public static BoardData domainToData(Board board) {
		return BoardData.builder()
			.userId(board.getUserId())
			.title(board.getTitle())
			.content(board.getContent())
			.build();
	}
}
