
package ga.surilaw.controller.auth;

import ga.surilaw.domain.dto.MemberLoginResponseDto;
import ga.surilaw.domain.dto.MemberSignUpRequestDto;
import ga.surilaw.domain.dto.MemberLoginRequestDto;
import ga.surilaw.domain.dto.MemberSignUpResponseDto;
import ga.surilaw.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/api/auth/signup") //회원가입
    public ResponseEntity<MemberSignUpResponseDto> signUp(@RequestBody MemberSignUpRequestDto memberSignUpRequestDto) {
        authService.signUp(memberSignUpRequestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MemberSignUpResponseDto("성공", memberSignUpRequestDto.getEmail()));
    }

    @PostMapping(value = "/api/auth/login") //로그인
    public ResponseEntity<MemberLoginResponseDto> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        String token = authService.login(memberLoginRequestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MemberLoginResponseDto(token, "성공"));
    }
}