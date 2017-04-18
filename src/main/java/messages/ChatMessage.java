package messages;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eugen on 18.04.2017.
 */
public class ChatMessage {
    private String text;
    private Boolean incoming;
    private List<String> replies=new ArrayList<>();

    public ChatMessage(String text) {
        this.text = text;
    }

    public ChatMessage() {
    }

    public ChatMessage(String text, Boolean incoming, List<String> replies) {
        this.text = text;
        this.incoming = incoming;
        if(replies!=null)
            for(String msg:replies)
                this.replies.add(msg);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getIncoming() {
        return incoming;
    }

    public void setIncoming(Boolean incoming) {
        this.incoming = incoming;
    }

    public List<String> getReplies() {
        return replies;
    }

    public void setReplies(List<String> replies) {
        this.replies = replies;
    }
}
