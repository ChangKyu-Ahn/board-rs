package co.kr.board.modules.framework.input.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponse {
	private Long id;

	private String title;

	private String content;

	private Long viewCount;

	private String userId;
}
