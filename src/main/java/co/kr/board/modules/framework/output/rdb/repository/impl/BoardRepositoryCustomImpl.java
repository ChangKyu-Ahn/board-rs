package co.kr.board.modules.framework.output.rdb.repository.impl;

import co.kr.board.modules.framework.input.rest.dto.BoardResponse;
import co.kr.board.modules.framework.input.rest.dto.BoardSearchDto;
import co.kr.board.modules.framework.output.rdb.data.QBoardData;
import co.kr.board.modules.framework.output.rdb.repository.BoardRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Page<BoardResponse> retrieveAll(BoardSearchDto searchDto) {
		QBoardData qBoardData = QBoardData.boardData;

		BooleanBuilder whereClause = getWhereClause(searchDto, qBoardData);
		Pageable pageable = searchDto.pageable();

		List<BoardResponse> boardResponseList = //
			getGoodsResponseList(
				qBoardData,
				searchDto.pageable(),
				whereClause
			);

		JPAQuery<Long> count = getCountQuery(qBoardData, whereClause);

		return PageableExecutionUtils.getPage(boardResponseList, pageable, count::fetchOne);
	}

	private BooleanBuilder getWhereClause(BoardSearchDto searchDto, QBoardData qBoardData) {
		BooleanBuilder whereClause = new BooleanBuilder();

		if (StringUtils.isNotBlank(searchDto.getUserId())) {
			whereClause.and(qBoardData.userId.eq(searchDto.getUserId()));
		}

		if (StringUtils.isNotBlank(searchDto.getTitle())) {
			whereClause.and(qBoardData.title.contains(searchDto.getTitle()));
		}

		if (StringUtils.isNotBlank(searchDto.getContent())) {
			whereClause.and(qBoardData.content.contains(searchDto.getContent()));
		}

		return whereClause;
	}

	private List<BoardResponse> getGoodsResponseList(
			QBoardData qBoardData,
			Pageable pageable,
			BooleanBuilder whereClause
	) {
		return jpaQueryFactory
			.select(
				Projections.fields(BoardResponse.class,
					qBoardData.id,
					qBoardData.title,
					qBoardData.content,
					qBoardData.userId,
					qBoardData.viewCount
				)
			)
			.from(qBoardData)
			.where(whereClause)
			.offset((long) pageable.getPageNumber() * pageable.getPageSize())
			.limit(pageable.getPageSize())
			.fetch();
	}

	private JPAQuery<Long> getCountQuery(QBoardData qBoardData, BooleanBuilder whereClause) {
		return jpaQueryFactory.select(qBoardData.count())
			.from(qBoardData)
			.where(whereClause);
	}
}
