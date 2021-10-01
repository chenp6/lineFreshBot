import com.corundumstudio.socketio.listener.*;

import java.util.Map;

import com.corundumstudio.socketio.*;

public class linebotServer {
	public static void main(String[] args) {
        Configuration config = new Configuration();
        config.setHostname("192.168.43.97");
        config.setPort(4000);
        SocketIOServer server = new SocketIOServer(config);
        ConnectListener connectListener = new ConnectListener() {
			@Override
			public void onConnect(SocketIOClient client) {
				System.out.println(client.getRemoteAddress());
				client.sendEvent("hi", "test");
			}
        	
        };
        server.addConnectListener(connectListener);
        server.addEventListener("hi", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
                // broadcast messages to all clients
            	System.out.println("hi");
            }
        });
        

        

        

        
        server.start();
        

        try {
        	System.out.println("start");
			Thread.sleep(Integer.MAX_VALUE);//一直執行server Thread
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

        server.stop();
	}
}
