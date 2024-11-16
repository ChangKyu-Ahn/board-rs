package co.kr.board.modules.framework.output.rdb;

import co.kr.board.modules.application.port.output.BoardManagementOutputPort;
import co.kr.board.modules.domain.entity.Board;
import co.kr.board.modules.domain.mapper.BoardMapper;
import co.kr.board.modules.framework.output.rdb.data.BoardData;
import co.kr.board.modules.framework.output.rdb.mapper.BoardRDBMapper;
import co.kr.board.modules.framework.output.rdb.repository.BoardRepository;
import co.kr.common.exception.NotExistDataException;
import co.kr.common.util.SecurityUtil;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardManagementRDBAdapter implements BoardManagementOutputPort {
	private final BoardRepository boardRepository;

	@Override
	public Board persist(Board board) {
		BoardData saved = boardRepository.save(BoardRDBMapper.domainToData(board));
		return BoardMapper.dataToDomain(saved);
	}

	@Override
	@Transactional(readOnly = true)
	public Board retrieve(Long id) {
		BoardData boardData = getBoardData(id);
		return BoardMapper.dataToDomain(boardData);
	}

	@Override
	@Transactional
	public Board update(Long id, Board board) {
		BoardData data = getBoardData(id);

		data.update(board);

		return BoardMapper.dataToDomain(data);
	}

	@Override
	public void delete(Long id) {
		BoardData boardData = getBoardData(id);
		boardRepository.delete(boardData);
	}

	private BoardData getBoardData(Long id) {
		Optional<BoardData> board;

		if (SecurityUtil.isAdmin()) {
			board = boardRepository.findById(id);
		} else {
			board = boardRepository.findByIdAndUserId(id, SecurityUtil.getUserId());
		}

		return board.orElseThrow(NotExistDataException::new);
	}
}
