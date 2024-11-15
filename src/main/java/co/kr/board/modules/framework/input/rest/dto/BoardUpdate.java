package co.kr.board.modules.framework.input.rest.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardUpdate extends BoardDto {
	@NotBlank
	private String title;

	@NotBlank
	private String content;
}
