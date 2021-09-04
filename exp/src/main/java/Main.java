import messages.CameraMessageWrapper;
import messages.incoming.LoginResponse;
import messages.incoming.SystemInfoResponse;
import messages.outgoing.Login;
import messages.outgoing.SystemInfo;

import java.net.*;


public class Main {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("192.168.178.108",34567);;
            Login loginMessage = new Login("admin", "tlJwpbo6");
            CameraMessageWrapper outgoingMessage = new CameraMessageWrapper(loginMessage);

            // Send message
            clientSocket.getOutputStream().write(outgoingMessage.getByteBufferForMessage().array());

            // Receive reply.
            CameraMessageWrapper incomingMessage = new CameraMessageWrapper(clientSocket.getInputStream());
            LoginResponse loginResponse = (LoginResponse) incomingMessage.getMessageData();
            System.out.println(incomingMessage.getMessageData().toString());

            // Get system info
            SystemInfo systemInfoMessage  = new SystemInfo(loginResponse.getSessionId());
            CameraMessageWrapper outgoingMessage2 = new CameraMessageWrapper(systemInfoMessage);
            clientSocket.getOutputStream().write(outgoingMessage2.getByteBufferForMessage().array());

            incomingMessage = new CameraMessageWrapper(clientSocket.getInputStream());
            System.out.println(incomingMessage.getMessageData().toString());

            System.out.println("Complete");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}