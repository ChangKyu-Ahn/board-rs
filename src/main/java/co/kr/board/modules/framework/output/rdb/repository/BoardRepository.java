package co.kr.board.modules.framework.output.rdb.repository;

import co.kr.board.modules.framework.output.rdb.data.BoardData;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardData, Long>, BoardRepositoryCustom  {

	Optional<BoardData> findByIdAndUserId(Long id, String userId);

	@Modifying
	@Query("UPDATE BoardData b SET b.viewCount = :viewCount WHERE b.id = :id")
	void updateViewCountById(@Param("id") Long id, @Param("viewCount") Long viewCount);
}
