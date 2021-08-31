package ga.surilaw.common.response;

public class AuthException extends ApiException{
    public AuthException() {

    }

    public AuthException(String message) {
        super(message);
    }
}
