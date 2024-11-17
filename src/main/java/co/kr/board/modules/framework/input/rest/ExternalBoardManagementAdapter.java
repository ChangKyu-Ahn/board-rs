package co.kr.board.modules.framework.input.rest;

import co.kr.board.modules.application.usecase.BoardCreateUsecase;
import co.kr.board.modules.application.usecase.BoardDeleteUsecase;
import co.kr.board.modules.application.usecase.BoardRetrieveUsecase;
import co.kr.board.modules.application.usecase.BoardUpdateUsecase;
import co.kr.common.util.SecurityUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/boards")
public class ExternalBoardManagementAdapter extends AbstractBoardManagementAdapter{

	public ExternalBoardManagementAdapter(
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
	 */
	@PostMapping("/user")
	public void deleteAllByUserId() {
		boardDeleteUsecase.deleteAllByUserId(SecurityUtil.getUserId());
	}
}
