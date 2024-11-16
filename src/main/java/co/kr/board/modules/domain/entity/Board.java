package co.kr.board.modules.domain.entity;

import co.kr.common.domain.vo.Identifier;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Board extends Identifier<Long> {
	private String title;
	private String content;
	private String userId;
	private Long viewCount;
}
