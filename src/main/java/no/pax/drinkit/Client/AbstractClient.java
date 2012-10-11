package no.pax.drinkit.Client;

import no.pax.drinkit.Util.Util;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketClient;
import org.eclipse.jetty.websocket.WebSocketClientFactory;

import java.io.IOException;
import java.net.URI;

/**
 * Created: rak
 * Date: 02.10.12
 */
public abstract class AbstractClient implements WebSocket.OnTextMessage {
    protected Connection connection;
    private String registrationName;

    public AbstractClient(String registrationName) {
        try {
            this.registrationName = registrationName;
            getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void onMessage(String s);

    public void onOpen(Connection connection) {
        try {
            final String sendStringAsJSon = Util.getSendStringAsJSon(Util.SERVER_NAME, registrationName, registrationName);
            connection.sendMessage(sendStringAsJSon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClose(int i, String s) {
    }

    public void disconnect() throws IOException {
        connection.disconnect();
    }

    private Connection getConnection() throws Exception {
        WebSocketClientFactory factory = new WebSocketClientFactory();
        factory.setBufferSize(4096);
        factory.start();

        WebSocketClient client = factory.newWebSocketClient();
        client.setMaxIdleTime(Util.DEFAULT_IDLE_TIME);
        client.setProtocol(Util.PROTOCOL_NAME);

        final String host = "localhost";
        final int port = 8080;
        final String connectionPath = "ws://" + host + ":" + port + "/"+ Util.PROTOCOL_NAME +"/";

        return connection = client.open(new URI(connectionPath), this).get();
    }
}
