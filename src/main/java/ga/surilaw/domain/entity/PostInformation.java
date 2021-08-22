package ga.surilaw.domain.entity;

import ga.surilaw.domain.entity.BaseEntity;
import ga.surilaw.domain.entity.enumType.Category;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class PostInformation extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "member_id", updatable = false)
    private Long memberId;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name ="post_title")
    private String postTitle;

    @Lob
    @Column(name = "post_content")
    private String postContent;
}
