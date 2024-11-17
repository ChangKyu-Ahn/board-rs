package co.kr.board.modules.framework.output.rdb.repository;

import co.kr.board.modules.framework.output.rdb.data.BoardData;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardData, Long>, BoardRepositoryCustom  {

	Optional<BoardData> findByIdAndUserId(Long id, String userId);
}
