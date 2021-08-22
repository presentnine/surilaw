package ga.surilaw.domain.dto;

import lombok.*;

/**
 *회원가입 요청 DTO
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberSignUpRequestDto {
    private String email;
    private String memberName;
    private String password;
}
