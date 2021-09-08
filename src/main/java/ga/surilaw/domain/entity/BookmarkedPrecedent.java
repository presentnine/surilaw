package ga.surilaw.domain.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BookmarkedPrecedent {

    @EmbeddedId
    BookmarkedPrecedentId bookmarkedPrecedentId;

    @Column(name = "precedent_optional_name")
    String precedentAlias;

    public void changeAliasTo(String precedentAlias){
        this.precedentAlias = precedentAlias;
    }
}
