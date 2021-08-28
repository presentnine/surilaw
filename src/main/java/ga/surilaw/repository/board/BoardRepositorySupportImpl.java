package ga.surilaw.repository.board;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ga.surilaw.domain.dto.board.ReadPostInfoDto;
import ga.surilaw.domain.entity.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class BoardRepositorySupportImpl extends QuerydslRepositorySupport implements BoardRepositorySupport {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public BoardRepositorySupportImpl(JPAQueryFactory queryFactory, EntityManager em) {
        super(PostInformation.class);
        this.queryFactory = queryFactory;
        this.em = em;
    }

    public ReadPostInfoDto readPost(Long postId){
        QPostInformation postInformation = QPostInformation.postInformation;

        return queryFactory.from(postInformation).select(Projections.constructor(ReadPostInfoDto.class,
                postInformation.postId,
                postInformation.member.memberId,
                postInformation.member.memberName,
                postInformation.category,
                postInformation.postTitle,
                postInformation.postContent,
                postInformation.createdDate
        ))
                .where(postInformation.postId.eq(postId)).fetchOne();
    }

    public List<Comments> readComment(Long postId){
        QComments parent = new QComments("parent");
        QComments child = new QComments("child");

        JPAQuery<Comments> query = queryFactory.from(parent)
                .select(parent)
                .where(parent.posts.postId.eq(postId)
                        .and(parent.parent.isNull()))
                .leftJoin(parent.child,child)
                .fetchJoin()
                .distinct();


        return query.fetch();
    }

}
