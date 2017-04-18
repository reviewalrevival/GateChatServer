import com.corundumstudio.socketio.SocketIOClient;
import messages.ChatMessage;

import java.util.*;

/**
 * Created by Eugen on 18.04.2017.
 */
public class Controller {

    private Timer messageTimer;
    private List<String> messageStack;
    private List devices=new ArrayList();
    private Map<String,String> answers=new HashMap<>();
    private boolean isWaiting;

    public Controller(){
        answers=new HashMap<>();
        initAnswersMap();
        messageTimer=new Timer();
        messageStack=new ArrayList<>();
    }

    private void initAnswersMap(){

    }
    public boolean checkIfAuth(String session){
        devices.add(session);
        return false;
    }

    private void disconnectDevice(String session){
        devices.remove(session);
    }

    public Timer getMessageTimer() {
        return messageTimer;
    }

    public void setMessageTimer(Timer messageTimer) {
        this.messageTimer = messageTimer;
    }

    public void startTimer(SocketIOClient client){
        messageTimer=new Timer();
        messageTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                ChatMessage message=new ChatMessage(String.join("\n",messageStack),true,messageStack);
                client.sendEvent("newMessageEvent",message);
                isWaiting=false;
                messageStack.clear();
                System.out.println("Message Timer is over");
            }
        },3000);
    }

    public boolean getWaiting() {
        return isWaiting;
    }

    public void setWaiting(boolean waiting) {
        isWaiting = waiting;
    }

    public List<String> getMessageStack() {
        return messageStack;
    }

    public void setMessageStack(List<String> messageStack) {
        this.messageStack = messageStack;
    }
}
