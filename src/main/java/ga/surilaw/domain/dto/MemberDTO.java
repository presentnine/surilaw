package ga.surilaw.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor

//테스트 에러로 NoArgs 추가 - 엄현식
@NoArgsConstructor
public class MemberDTO {

    private String email;
    private String memberName;
    private String password;

}
