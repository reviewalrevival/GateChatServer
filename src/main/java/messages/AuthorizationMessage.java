package messages;

/**
 * Created by Eugen on 18.04.2017.
 */
public class AuthorizationMessage {
    private String session_id;

    public AuthorizationMessage(String sessionId) {
        this.session_id = sessionId;
    }

    public AuthorizationMessage() {
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
