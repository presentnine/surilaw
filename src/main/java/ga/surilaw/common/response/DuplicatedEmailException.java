package ga.surilaw.common.response;

public class DuplicatedEmailException extends AuthException{
    public DuplicatedEmailException() {
    }

    public DuplicatedEmailException(String message) {
        super(message);
    }
}
