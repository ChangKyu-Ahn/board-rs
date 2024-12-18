package co.kr.board.modules.framework.input.rest;

import co.kr.board.modules.application.usecase.BoardCreateUsecase;
import co.kr.board.modules.application.usecase.BoardDeleteUsecase;
import co.kr.board.modules.application.usecase.BoardRetrieveUsecase;
import co.kr.board.modules.application.usecase.BoardUpdateUsecase;
import co.kr.board.modules.framework.input.rest.dto.BoardCreate;
import co.kr.board.modules.framework.input.rest.dto.BoardResponse;
import co.kr.board.modules.framework.input.rest.dto.BoardSearchDto;
import co.kr.board.modules.framework.input.rest.dto.BoardUpdate;
import co.kr.common.domain.vo.Identifier;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
public class AbstractBoardManagementAdapter {
	protected static final String BOARD_ID_PATH = "/{boardId}";

	protected final BoardCreateUsecase boardCreateUsecase;
	protected final BoardUpdateUsecase boardUpdateUsecase;
	protected final BoardDeleteUsecase boardDeleteUsecase;
	protected final BoardRetrieveUsecase boardRetrieveUsecase;

	/**
	 * <pre>
	 *  게시물 생성
	 * </pre>
	 *
	 * @param boardCreate Board Create DTO
	 * @return Board ID
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Identifier<Long> create(@RequestBody @Valid BoardCreate boardCreate) {
		return boardCreateUsecase.create(boardCreate);
	}

	/**
	 * <pre>
	 *  게시물 목록 조회
	 * </pre>
	 *
	 * @param boardSearchDto Board Search DTO
	 * @return Board Paging Response DTO
	 */
	@GetMapping("/list")
	public Page<BoardResponse> list(BoardSearchDto boardSearchDto) {
		return boardRetrieveUsecase.list(boardSearchDto);
	}

	/**
	 * <pre>
	 *  게시물 조회
	 * </pre>
	 *
	 * @param boardId board ID
	 * @return Board Detail Response DTO
	 */
	@GetMapping(BOARD_ID_PATH)
	public BoardResponse detail(@PathVariable Long boardId) {
		return boardRetrieveUsecase.detail(boardId);
	}

	/**
	 * <pre>
	 *  게시물 수정
	 * </pre>
	 *
	 * @param boardId Board ID
	 * @return Board ID
	 */
	@PutMapping(BOARD_ID_PATH)
	public Identifier<Long> update(@PathVariable Long boardId, @RequestBody @Valid BoardUpdate boardUpdate) {
		return boardUpdateUsecase.update(boardId, boardUpdate);
	}

	/**
	 * <pre>
	 *  게시물 삭제
	 * </pre>
	 *
	 * @param boardId Board ID
	 */
	@DeleteMapping(BOARD_ID_PATH)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long boardId) {
		boardDeleteUsecase.delete(boardId);
	}
}
