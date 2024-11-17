package co.kr.board.modules.framework.input.rest.dto;

import co.kr.common.dto.PagingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardSearchDto extends PagingDto {
	private String title;
	private String content;
	private String userId;
}
