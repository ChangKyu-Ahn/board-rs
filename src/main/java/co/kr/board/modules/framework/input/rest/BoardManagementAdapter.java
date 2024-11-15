package co.kr.board.modules.framework.input.rest;

import co.kr.board.modules.application.usecase.BoardCreateUsecase;
import co.kr.board.modules.application.usecase.BoardDeleteUsecase;
import co.kr.board.modules.application.usecase.BoardRetrieveUsecase;
import co.kr.board.modules.application.usecase.BoardUpdateUsecase;
import co.kr.board.modules.framework.input.rest.dto.BoardCreate;
import co.kr.board.modules.framework.input.rest.dto.BoardResponse;
import co.kr.common.domain.vo.Identifier;
import co.kr.user.modules.framework.input.rest.dto.UserCreate;
import co.kr.user.modules.framework.input.rest.dto.UserResponse;
import co.kr.user.modules.framework.input.rest.dto.UserUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/boards")
@RequiredArgsConstructor
public class BoardManagementAdapter {
	private static final String USER_ID_PATH = "/{boardId}";

	private final BoardCreateUsecase boardCreateUsecase;
	private final BoardUpdateUsecase boardUpdateUsecase;
	private final BoardDeleteUsecase boardDeleteUsecase;
	private final BoardRetrieveUsecase boardRetrieveUsecase;

	/**
	 * <pre>
	 *  게시물 생성
	 * </pre>
	 *
	 * @param userCreate User Create DTO
	 * @return User ID
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Identifier<String> create(@RequestBody @Valid BoardCreate userCreate) {
		return boardCreateUsecase.create(userCreate);
	}

	/**
	 * <pre>
	 *  사용자 조회
	 * </pre>
	 *
	 * @param userId User ID
	 * @return User Detail Response DTO
	 */
	@GetMapping(USER_ID_PATH)
	public BoardResponse detail(@PathVariable Long boardId) {
		return boardRetrieveUsecase.detail(boardId);
	}

	/**
	 * <pre>
	 *  사용자 수정
	 * </pre>
	 *
	 * @param userId User ID
	 * @return User Detail Response DTO
	 */
	@PutMapping(USER_ID_PATH)
	public Identifier<String> update(@PathVariable String userId, @RequestBody @Valid UserUpdate userUpdate) {
		return userUpdateUsecase.update(userId, userUpdate);
	}

	/**
	 * <pre>
	 *  사용자 삭제
	 * </pre>
	 *
	 * @param userId User ID
	 * @return User Detail Response DTO
	 */
	@DeleteMapping(USER_ID_PATH)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String userId) {
		userDeleteUsecase.delete(userId);
	}
}
