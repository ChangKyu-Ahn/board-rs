package co.kr.board.modules.domain.mapper;

import co.kr.board.modules.domain.entity.Board;
import co.kr.board.modules.domain.entity.Board.BoardBuilder;
import co.kr.board.modules.framework.input.rest.dto.BoardCreate;
import co.kr.board.modules.framework.input.rest.dto.BoardDto;
import co.kr.board.modules.framework.input.rest.dto.BoardResponse;
import co.kr.board.modules.framework.input.rest.dto.BoardUpdate;
import co.kr.board.modules.framework.output.rdb.data.BoardData;
import co.kr.common.domain.vo.Identifier;
import co.kr.common.util.SecurityUtil;

public class BoardMapper {
	public static BoardBuilder<?, ?> dtoToDomainBuilder(BoardDto boardDto) {
		return Board.builder()
			.title(boardDto.getTitle())
			.content(boardDto.getContent())
			.userId(SecurityUtil.getUserId());
	}

	public static Board createDtoToDomain(BoardCreate boardCreate) {
		return dtoToDomainBuilder(boardCreate).build();
	}

	public static Board updateDtoToDomain(BoardUpdate boardUpdate) {
		return dtoToDomainBuilder(boardUpdate).build();
	}

	public static Board dataToDomain(BoardData boardData) {
		return Board.builder()
			.id(boardData.getId())
			.title(boardData.getTitle())
			.content(boardData.getContent())
			.userId(boardData.getUserId())
			.viewCount(boardData.getViewCount())
			.build();
	}

	public static BoardResponse domainToResponseDto(Board board) {
		return BoardResponse.builder()
			.title(board.getTitle())
			.content(board.getContent())
			.viewCount(board.getViewCount())
			.userId(board.getUserId())
			.build();
	}

	public static Identifier<Long> toBoardIdResponse(Board board) {
		return Identifier.<Long>builder()
			.id(board.getId())
			.build();
	}
}
