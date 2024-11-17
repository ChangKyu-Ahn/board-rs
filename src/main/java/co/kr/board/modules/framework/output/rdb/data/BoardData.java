package co.kr.board.modules.framework.output.rdb.data;

import co.kr.board.modules.domain.entity.Board;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BoardData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 15, nullable = false)
	private String userId;

	@Column(length = 100, nullable = false)
	private String title;

	@Column(length = 500, nullable = false, columnDefinition = "TEXT")
	private String content;

	@Default
	private Long viewCount = 0L;

	// ################################################################
	public void update(Board board) {
		this.title = board.getTitle();
		this.content = board.getContent();
	}
}
