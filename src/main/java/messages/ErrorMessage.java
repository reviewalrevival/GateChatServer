package messages;

/**
 * Created by Eugen on 18.04.2017.
 */
public class ErrorMessage {
    private Integer error;

    public ErrorMessage(Integer error) {
        this.error = error;
    }

    public ErrorMessage() {
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }
}
