package co.kr.board.modules.framework.input.rest;

import co.kr.board.modules.application.usecase.BoardCreateUsecase;
import co.kr.board.modules.application.usecase.BoardDeleteUsecase;
import co.kr.board.modules.application.usecase.BoardRetrieveUsecase;
import co.kr.board.modules.application.usecase.BoardUpdateUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/boards")
public class BoardManagementAdapter extends AbstractBoardManagementAdapter{

	public BoardManagementAdapter(
		BoardCreateUsecase boardCreateUsecase,
		BoardUpdateUsecase boardUpdateUsecase,
		BoardDeleteUsecase boardDeleteUsecase,
		BoardRetrieveUsecase boardRetrieveUsecase
	) {
		super(boardCreateUsecase, boardUpdateUsecase, boardDeleteUsecase, boardRetrieveUsecase);
	}

	/**
	 * <pre>
	 *  게시물 삭제 (유저 아이디)
	 * </pre>
	 *
	 * @param userId Board ID
	 */
	@PostMapping("/user/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAllByUserId(@PathVariable String userId) {
		boardDeleteUsecase.deleteAllByUserId(userId);
	}
}
