package ga.surilaw.repository.board;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ga.surilaw.domain.dto.board.ReadPostInfoDto;
import ga.surilaw.domain.dto.board.ReadPostListDto;
import ga.surilaw.domain.dto.board.ReadPostShortDto;
import ga.surilaw.domain.dto.board.pagination.FilterDto;
import ga.surilaw.domain.entity.Comments;
import ga.surilaw.domain.entity.PostInformation;
import ga.surilaw.domain.entity.QComments;
import ga.surilaw.domain.entity.QPostInformation;
import ga.surilaw.domain.entity.enumType.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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

    public ReadPostListDto getPageList(FilterDto filter, Pageable pageable){
        QPostInformation postInfo = QPostInformation.postInformation;
        ReadPostListDto readPostListDto = new ReadPostListDto();

        JPQLQuery<ReadPostShortDto> query = from(postInfo).select(Projections.constructor(ReadPostShortDto.class,
               postInfo.postId, postInfo.member.memberName, postInfo.postTitle, postInfo.category))
                .leftJoin(postInfo.member);

        if(filter != null){
            //set filter;
            query = query.where(
                    eqCategory(filter.getCategory()),
                    inTitle(filter.getTitle()),
                    inWriter(filter.getName()),
                    inContent(filter.getContent()));
        }

        //setting Dto
        readPostListDto.setPostList(getQuerydsl().applyPagination(pageable,query).fetch());
        readPostListDto.setSize(pageable.getPageSize());
        readPostListDto.setPage(pageable.getPageNumber());
        readPostListDto.setTotalElements((int)query.fetchCount());
        readPostListDto.setTotalPages( (readPostListDto.getTotalPages() + readPostListDto.getSize() -1) / readPostListDto.getSize());
        return readPostListDto;
    }

    private BooleanExpression eqCategory(Category category){
        if(category != null){
            System.out.println(category);
            if(category != Category.ALL){
                return QPostInformation.postInformation.category.eq(category);
            }
        }
        return null;
    }

    private BooleanExpression inTitle(String title){
        return StringUtils.hasLength(title) ? QPostInformation.postInformation.postTitle.contains(title) : null;
    }

    private BooleanExpression inWriter(String name){
        return StringUtils.hasLength(name) ? QPostInformation.postInformation.member.memberName.contains(name) : null;
    }

    private BooleanExpression inContent(String content){
        return StringUtils.hasLength(content) ? QPostInformation.postInformation.postContent.contains(content) : null;
    }



}
