package ga.surilaw.common.response;

import ga.surilaw.domain.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice(basePackages = "ga.surilaw.controller")
public class SuriLawExceptionHandler {

        @ExceptionHandler(ApiException.class)
        public ResponseEntity<ErrorResponse> apiException(HttpServletRequest request, final ApiException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(ExceptionEnum.API_EXCEPTION.getCode(), e.getMessage()));
        }

        @ExceptionHandler(AuthException.class)
        public ResponseEntity<ErrorResponse> authException(HttpServletRequest request, final AuthException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(ExceptionEnum.AUTH_EXCEPTION.getCode(), e.getMessage()));
        }

        @ExceptionHandler(EmailNotFoundException.class)
        public ResponseEntity<ErrorResponse> emailNotFoundException(HttpServletRequest request, final EmailNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(ExceptionEnum.EMAIL_NOT_FOUND_EXCEPTION.getCode(), e.getMessage()));
        }

        @ExceptionHandler(DuplicatedEmailException.class)
        public ResponseEntity<ErrorResponse> duplicatedEmailException(HttpServletRequest request, final DuplicatedEmailException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(ExceptionEnum.DUPLICATED_EMAIL_EXCEPTION.getCode(), e.getMessage()));
        }

        @ExceptionHandler(PasswordNotMatchException.class)
        public ResponseEntity<ErrorResponse> passwordNotMatchException(HttpServletRequest request, final PasswordNotMatchException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(ExceptionEnum.PASSWORD_NOT_MATCH_EXCEPTION.getCode(), e.getMessage()));
    }
}
