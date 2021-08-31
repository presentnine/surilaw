package ga.surilaw.common.response;

public class PasswordNotMatchException extends AuthException{
    public PasswordNotMatchException() {
    }

    public PasswordNotMatchException(String message) {
        super(message);
    }
}
