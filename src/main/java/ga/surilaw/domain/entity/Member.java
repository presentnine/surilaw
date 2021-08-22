package ga.surilaw.domain.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = "memberId")

//Mapping하기 위해서 추가 - 엄현식
@Builder
@AllArgsConstructor
public class Member implements Persistable<String>, UserDetails {

    @Id
    @Column(name = "member_id")
    private String memberId;

    @Column(name = "email")
    private String email;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "created_date", updatable = false)
    @CreatedDate
    private LocalDate createdDate;

    @Column(name = "member_type")
    private char memberType;

    public Member(String email, String memberName, String passwordHash, char memberType) {
        this.memberId= UUID.randomUUID().toString();
        this.email = email;
        this.memberName = memberName;
        this.passwordHash = passwordHash;
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

    //UserDetails 타입 필요 구현
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority authority;

        if (this.memberType == 'A') {
            authority = new SimpleGrantedAuthority("ROLE_ADMIN");
        } else {
            authority = new SimpleGrantedAuthority("ROLE_USER");
        }

        authorities.add(authority);
        return authorities;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
