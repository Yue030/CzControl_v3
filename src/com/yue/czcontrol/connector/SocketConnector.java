package com.yue.czcontrol.connector;

import com.yue.czcontrol.AlertBox;
import com.yue.czcontrol.ExceptionBox;
import com.yue.czcontrol.utils.StackTrace;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

public final class SocketConnector {

    /**
     * Constructor.
     */
    private SocketConnector() {

    }

    /**
     * Socket.
     */
    private static Socket socket;

    /**
     * Connect Port.
     */
    private static final int PORT = 5200;

    /**
     * Get Socket.
     * @return socket
     */
    public static Socket getSocket() {
        return socket;
    }

    /**
     * Connect Socket.
     * @throws IOException IOException
     */
    public static void init() throws IOException {
        try {
           socket = new Socket("27.147.3.116", PORT);
        } catch (ConnectException e) {
            AlertBox.show("Connect Failed",
                    "\u9023\u7dda\u903e\u6642",
                    AlertBox.Type.INFORMATION);
            socket.close();
            System.exit(0);
        } catch (IOException e) {
            socket.close();
            String message = StackTrace.getStackTrace(e);
            ExceptionBox box = new ExceptionBox(message);
            box.show();
        }
    }
}
