package co.kr.board.modules.framework.output.rdb.repository;

import co.kr.board.modules.framework.input.rest.dto.BoardResponse;
import co.kr.board.modules.framework.input.rest.dto.BoardSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepositoryCustom {
  Page<BoardResponse> retrieveAll(BoardSearchDto goodsSearchDto);
}
