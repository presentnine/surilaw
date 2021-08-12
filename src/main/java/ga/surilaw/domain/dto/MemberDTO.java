package ga.surilaw.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class MemberDTO {

    private String email;
    private String memberName;
    private String password;

}
