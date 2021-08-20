package ga.surilaw.domain.entity;


import ga.surilaw.common.converter.BooleanToYNConverter;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor

@DynamicUpdate

public class Comments extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long commentId;

    //작성글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostInformation posts;

    //부모
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "top_comment_id")
    private Comments parent;

    //자식
    @OneToMany(mappedBy = "parent")
    @Builder.Default
    private List<Comments> child = new ArrayList<>();

    //작성자
    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "comment_content")
    private String commentContent;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "is_answer_exists")
    private boolean isAnswerExists;

    public void setMember(Member member){
        this.member = member;
    }

    //게시글 추가
    public void changePosts(PostInformation posts){
        this.posts = posts;
    }

    //인원 추가
   public void changeMember(Member member){ this.member = member; }

    //대댓글 추가
    public void addParent(Comments parentComment){
        //자식한테 부모 추가
        this.parent = parentComment;

        //부모한테는 자식 추가
        parentComment.getChild().add(this);
        parentComment.isAnswerExists = true;
    }

    //삭제
    public void setDeleted(){
        this.isDeleted = true;
        this.commentContent = null;
    }
}
