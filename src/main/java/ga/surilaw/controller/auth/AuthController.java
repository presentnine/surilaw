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

        ResponseEntity<MemberSignUpResponseDto> response;

        try {
            authService.signUp(memberSignUpRequestDto);
            response = ResponseEntity.status(HttpStatus.OK).body(new MemberSignUpResponseDto("성공", memberSignUpRequestDto.getEmail()));
        } catch (RuntimeException runtimeException){
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MemberSignUpResponseDto(runtimeException.getMessage(), null));
        }

        return response;
    }

    @PostMapping(value = "/api/auth/login") //로그인
    public ResponseEntity<MemberLoginResponseDto> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {

        ResponseEntity<MemberLoginResponseDto> response;

        try {
            String token = authService.login(memberLoginRequestDto);
            response = ResponseEntity.status(HttpStatus.OK).body(new MemberLoginResponseDto(token, "성공"));
        } catch (RuntimeException runtimeException) {
            response = ResponseEntity.status(HttpStatus.OK).body(new MemberLoginResponseDto(null, runtimeException.getMessage()));
        }

        return response;
    }
}
