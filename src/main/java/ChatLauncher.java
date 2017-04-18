import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import messages.AuthorizationMessage;
import messages.ChatMessage;
import messages.ErrorMessage;

/**
 * Created by Eugen on 18.04.2017.
 */
public class ChatLauncher {

    public static void main(String[] args) throws InterruptedException {
        Controller controller = new Controller();
        Configuration config = new Configuration();
        config.setHostname("192.168.0.108");
        config.setPort(3000);
        config.setPingTimeout(-1);
        config.setUpgradeTimeout(500000);

        SocketConfig socketConfig=new SocketConfig();
        socketConfig.setTcpKeepAlive(true);

        config.setSocketConfig(socketConfig);
        final SocketIOServer server = new SocketIOServer(config);
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient socketIOClient) {
                System.out.println("Connection");
                socketIOClient.sendEvent("connectionCallback", new ErrorMessage(0));
            }
        });
        server.addDisconnectListener(socketIOClient -> {
            System.out.println("Disconnect");
            if(controller.getWaiting()) {
                controller.getMessageTimer().cancel();
            }
        });
        server.addEventListener("chatEvent", String.class, (client, data, ackRequest) -> {
            System.out.println("Data:" + data);
            client.sendEvent("newChatMessage", data);
            server.getBroadcastOperations().sendEvent("chatevent", data);
        });
        server.addEventListener("authorizationEvent", AuthorizationMessage.class, ((socketIOClient, authorizationMessage, ackRequest) -> {
            String session = authorizationMessage.getSession_id();
            System.out.println(session);
            ErrorMessage error = new ErrorMessage();
            if (controller.checkIfAuth(session)) {
                error.setError(0);
            } else {
                error.setError(1);
            }
            socketIOClient.sendEvent("authorizationCallback", error);
        }));
        server.addEventListener("sendMessageEvent", ChatMessage.class, ((socketIOClient, message, ackRequest) -> {
            String messageText = message.getText();
            controller.getMessageStack().add(messageText);
            System.out.println(message.getText());
            if (controller.getWaiting()) {
                controller.getMessageTimer().cancel();
                controller.startTimer(socketIOClient);
            } else {
                controller.setWaiting(true);
                controller.startTimer(socketIOClient);
            }
            ChatMessage answer = new ChatMessage(messageText, false, null);
            socketIOClient.sendEvent("newMessageEvent", answer);
        }));
        server.start();
    }

}