package ga.surilaw.domain.entity;

import ga.surilaw.domain.dto.MemberDTO;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = "memberId")

//Mapping하기 위해서 추가 - 엄현식
@Builder
@AllArgsConstructor
public class Member implements Persistable<String> {

    @Id
    @Column(name = "member_id")
    private String memberId;

    @Column(name = "email")
    private String email;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "password_salt")
    private String passwordSalt;

    @Column(name = "created_date", updatable = false)
    @CreatedDate
    private LocalDate createdDate;

    @Column(name = "member_type")
    private char memberType;

    public Member(String email, String memberName, String passwordHash, String passwordSalt, char memberType) {
        this.memberId= UUID.randomUUID().toString();
        this.email = email;
        this.memberName = memberName;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
        this.memberType = memberType;
    }

    public Member(String email, String memberName, String passwordHash, char memberType) {
        this.memberId= UUID.randomUUID().toString();
        this.email = email;
        this.memberName = memberName;
        this.passwordHash = passwordHash;
        this.passwordSalt = "";
        this.memberType = memberType;
    }

    //엔티티 persistable 구현 메소드
    @Override
    public String getId() {
        return memberId;
    }

    //엔티티 persistable 구현 메소드
    @Override
    public boolean isNew() {
        return createdDate == null;
    }
}
