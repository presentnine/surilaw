package ga.surilaw.common.response;

import lombok.Getter;

@Getter
public enum ExceptionEnum {
    API_EXCEPTION("API-00"),
    AUTH_EXCEPTION("AUT-00"),
    EMAIL_NOT_FOUND_EXCEPTION("AUT-01"),
    DUPLICATED_EMAIL_EXCEPTION("AUT-02"),
    PASSWORD_NOT_MATCH_EXCEPTION("AUT-03");

    private final String code;
    private String message;

    ExceptionEnum(String code) {
        this.code = code;
    }

    ExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
