package ga.surilaw.domain.entity;

import ga.surilaw.domain.entity.enumType.Category;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor

@DynamicUpdate
public class PostInformation extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    @ColumnDefault("ALL")
    private Category category;

    @Column(name ="post_title")
    private String postTitle;

    @Lob
    @Column(name = "post_content")
    private String postContent;

    //댓글
    @OneToMany(mappedBy = "posts", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Comments> comments = new ArrayList<>();

    public void addMember(Member member){
        this.member = member;
    }

    //업데이트용
    public void updateCategory(Category category){
        this.category = category;
    }

    public void updateContent(String postContent){
        this.postContent = postContent;
    }

    public void updateTitle(String postTitle){
        this.postTitle = postTitle;
    }

    //댓글 추가
    public void addComment(Comments comment){
        this.comments.add(comment);

        //댓글에 게시글 추가
        comment.changePosts(this);
    }


}
