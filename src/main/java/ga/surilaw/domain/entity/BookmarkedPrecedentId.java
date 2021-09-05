package ga.surilaw.domain.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BookmarkedPrecedentId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "folder_num")
    Bookmark bookmark;

    @Column(name = "precedent_num")
    int precedentNum;
}
