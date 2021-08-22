package ga.surilaw.repository.board;

import com.querydsl.jpa.impl.JPAQueryFactory;
import ga.surilaw.domain.entity.PostInformation;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class BoardRepositorySupportImpl extends QuerydslRepositorySupport implements BoardRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public BoardRepositorySupportImpl(JPAQueryFactory queryFactory) {
        super(PostInformation.class);
        this.queryFactory = queryFactory;
    }
}
