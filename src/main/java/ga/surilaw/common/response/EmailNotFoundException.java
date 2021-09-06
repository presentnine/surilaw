package ga.surilaw.common.response;

public class EmailNotFoundException extends AuthException{
    public EmailNotFoundException() {
    }

    public EmailNotFoundException(String message) {
        super(message);
    }
}
